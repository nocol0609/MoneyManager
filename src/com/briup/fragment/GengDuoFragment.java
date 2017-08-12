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
		// �����û���¼���ͼƬ�Լ�ͼƬ���������
		if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
			mIv_touxiang_login.setImageResource(R.drawable.login_mine_pic);

			String name = PreferenceUtils.getString(getActivity(), "name", "");
			PreferenceUtils.putString(getActivity(), "name", name);
			mTv_denglu_type.setText(name);
		} else {
			mIv_touxiang_login.setImageResource(R.drawable.mine_pic_nor);
			mTv_denglu_type.setText("���¼");

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
				// �Զ���һ�������ļ�
				View view = View.inflate(getActivity(), R.layout.fragment_dialog_setup_login, null);
				mRd_login = (RadioButton) view.findViewById(R.id.login_Rb);
				mRd_notLogin = (RadioButton) view.findViewById(R.id.notlogin_Rb);
				mBtn_setting_cancel = (Button) view.findViewById(R.id.setting_btn_cancel);
				// ����Ҫдһ���жϣ�����¼ ��ʱ����һ�������û�е�¼��ʱ������һ�����
				// �Ѿ���¼��ʱ�򣬵������2����ѡ���ʱ�򣬶Ի������ʧ�����ҿ��Լ�ס��һ�ε�������Ǹ���ѡ��
				// δ��¼��ʱ�򵯳���˾���Ի��򲻻��Զ���ʧ
				if (!PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {

					// �����prefutilsȡ��isLogin��ֵΪtrueʱ��ȡ�Ǳ�ʾδ��¼������дδ��¼���߼�
					mRd_notLogin.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// δ��¼������£����radioButton������˾
							ToastUtil.showToast(getActivity(), "����û�е�¼");
						}
					});
					mRd_login.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							ToastUtil.showToast(getActivity(), "����û�е�¼");
						}
					});
					mBtn_setting_cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							// ��δ��¼��ʱ�򣬵��ȡ����ť��dismiss�Ի��򣬼�alertdialog
							dialog.dismiss();
						}
					});

					builder.setView(view);
					dialog = builder.show();
				} else {

					// ���ѵ�¼������£�����д��¼������߼�

					// ��¼֮�󣬵������ť��ʱ�򣬻��ס�����ť��ѡ�е�״̬
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
