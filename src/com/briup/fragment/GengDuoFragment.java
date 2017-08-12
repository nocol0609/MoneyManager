package com.briup.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.briup.adapter.MainVpAdapter;
import com.briup.moneymanager.GeRenZhongXinActivity;
import com.briup.moneymanager.R;
import com.briup.moneymanager.UserLoginActivity;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class GengDuoFragment extends Fragment {
	private ImageView mIv_touxiang_login, mIv_setup_login;
	private RadioButton mRd_login, mRd_notLogin;
	private Button mBtn_setting_cancel;
	private TextView mTv_denglu_type;

	private AlertDialog dialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gengduo, container, false);
		initView(view);
		initListener();

		return view;
	}

	private void initListener() {
		// 设置用户登录后的图片以及图片下面的文字
		if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
			mIv_touxiang_login.setImageResource(R.drawable.login_mine_pic);

			String name = PreferenceUtils.getString(getActivity(), "name", "");
			PreferenceUtils.putString(getActivity(), "name", name);
			mTv_denglu_type.setText(name);
		} else {
			mIv_touxiang_login.setImageResource(R.drawable.mine_pic_nor);
			mTv_denglu_type.setText("请登录");

		}
		mIv_touxiang_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
					Intent intent = new Intent(getActivity(), GeRenZhongXinActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent);
				}
			}

		});

		mIv_setup_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// 自定义一个布局文件
				View view = View.inflate(getActivity(), R.layout.fragment_dialog_setup_login, null);
				mRd_login = (RadioButton) view.findViewById(R.id.login_Rb);
				mRd_notLogin = (RadioButton) view.findViewById(R.id.notlogin_Rb);
				mBtn_setting_cancel = (Button) view.findViewById(R.id.setting_btn_cancel);
				// 还需要写一个判断，当登录 的时候是一种情况，没有登录的时候是另一种情况
				// 已经登录的时候，当点击那2个单选框的时候，对话框会消失，并且可以记住上一次点击的是那个单选框
				// 未登录的时候弹出吐司，对话框不会自动消失
				if (!PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {

					// 这里，当prefutils取出isLogin的值为true时，取非表示未登录，这里写未登录的逻辑
					mRd_notLogin.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// 未登录的情况下，点击radioButton弹出吐司
							ToastUtil.showToast(getActivity(), "您还没有登录");
						}
					});
					mRd_login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							ToastUtil.showToast(getActivity(), "您还没有登录");
						}
					});
					mBtn_setting_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// 在未登录的时候，点击取消按钮，dismiss对话框，即alertdialog
							dialog.dismiss();
						}
					});

					builder.setView(view);
					dialog = builder.show();
				} else {

					// 在已登录的情况下，这里写登录过后的逻辑

					// 登录之后，当点击按钮的时候，会记住这个按钮被选中的状态
					mRd_notLogin.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// PreferenceUtils.setBoolean(getActivity(),
							// "isChecked", true);

							dialog.dismiss();
						}
					});
					mRd_login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// PreferenceUtils.setBoolean(getActivity(),
							// "isChecked", true);
							// if(PreferenceUtils.getBoolean(getActivity(),
							// "isChecked", true)){
							//
							// }
							dialog.dismiss();
						}
					});
					mBtn_setting_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					builder.setView(view);
					dialog = builder.show();
				}
			}
		});

	}

	private void initView(View view) {
		mIv_touxiang_login = (ImageView) view.findViewById(R.id.main_iv_touxiang);
		mIv_setup_login = (ImageView) view.findViewById(R.id.setting_iv_alterDialog);
		mTv_denglu_type = (TextView) view.findViewById(R.id.denglu_tv_type);

	}
}
