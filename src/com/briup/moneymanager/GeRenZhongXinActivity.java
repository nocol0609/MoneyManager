package com.briup.moneymanager;

import com.briup.bean.User;
import com.briup.db.AccountDao;
import com.briup.db.UserDao;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class GeRenZhongXinActivity extends Activity {
	private UserDao dao;
	private Button mBtn_tuichuLogin;
	private ImageView mIv_back;
	private TextView mTv_nicheng;
	private AlertDialog dialog, dialog1;
	private RelativeLayout mRl_xiugaimima, mRl_changeName;
	private Button mBtn_quxiao, mBtn_save, mBtn_changeName_quxiao, mBtn_changeName_save;
	private EditText mEt_passwd, mEt_checkedPasswd, mEt_changeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ge_ren_zhong_xin);
		// 实例化
		mIv_back = (ImageView) findViewById(R.id.back1);
		mBtn_tuichuLogin = (Button) findViewById(R.id.genrenzhongxin_btn_tuichulogin);
		mTv_nicheng = (TextView) findViewById(R.id.gengduo_tv_nicheng);
		mRl_xiugaimima = (RelativeLayout) findViewById(R.id.xiugaimima_tv);
		dao = new UserDao(GeRenZhongXinActivity.this);
		mRl_changeName = (RelativeLayout) findViewById(R.id.change_rl_name);

		// 点击修改昵称，会将数据库中的名字改变
		mRl_changeName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog1();
			}

			private void showDialog1() {
				AlertDialog.Builder builder = new Builder(GeRenZhongXinActivity.this);
				// 自定义一个布局文件
				View view = View.inflate(GeRenZhongXinActivity.this, R.layout.dialog_change_name, null);

				mEt_changeName = (EditText) view.findViewById(R.id.change_et_name);
				mBtn_changeName_quxiao = (Button) view.findViewById(R.id.changeName_btn_cancel);
				mBtn_changeName_save = (Button) view.findViewById(R.id.changeName_btn_save);
				Log.i("GeRenZhongXinActivity", mBtn_changeName_quxiao + "");

				// 点击对话框中的取消按钮，会退出对话框
				mBtn_changeName_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// 点击保存，修改用户名
				mBtn_changeName_save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String newName = mEt_changeName.getText().toString();

						String oldName = PreferenceUtils.getString(GeRenZhongXinActivity.this, "name", "");
						PreferenceUtils.putString(GeRenZhongXinActivity.this, "name", oldName);
						Log.i("GeRenZhongXinActivity", oldName);
						Log.i("GeRenZhongXinActivity", newName);
						dao.changeUserName(oldName, new User(newName, ""));
						Log.i("GerenzhongxinActivity", newName);
						ToastUtil.showToast(GeRenZhongXinActivity.this, "修改昵称成功");
						finish();
					}
				});

				builder.setView(view);
				dialog = builder.show();

			}
		});

		// 点击修改密码，弹出对话框
		mRl_xiugaimima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(GeRenZhongXinActivity.this);
				// 自定义一个布局文件
				View view = View.inflate(GeRenZhongXinActivity.this, R.layout.dialog_xiugaimima, null);

				mBtn_save = (Button) view.findViewById(R.id.xiugaimima_btn_save);
				mBtn_quxiao = (Button) view.findViewById(R.id.xiugaimima_btn_cancel);
				mEt_passwd = (EditText) view.findViewById(R.id.xiugai_et_passwd);
				mEt_checkedPasswd = (EditText) view.findViewById(R.id.xiugai_et_querenmima);
				// 点击取消，让对话框消失
				mBtn_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// 点击保存，修改密码，得到系统当前的用户名，然后获得输入的密码和确认密码，当点击保存将数据库中的密码修改
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						String newPasswd = mEt_passwd.getText().toString();
						String checkedPasswd = mEt_checkedPasswd.getText().toString();

						Log.i("GeRenZhongXinActivity", newPasswd);

						if (newPasswd.equals(checkedPasswd) == false) {
							ToastUtil.showToast(GeRenZhongXinActivity.this, "密码与确认密码不一致，请重新输入");
						} else {

							String name = PreferenceUtils.getString(GeRenZhongXinActivity.this, "name", "");
							PreferenceUtils.putString(GeRenZhongXinActivity.this, "name", name);
							// 修改密码
							dao.changeUser(name, new User(newPasswd));
							Log.i("GerenzhongxinActivity", name + ":" + newPasswd);
							ToastUtil.showToast(GeRenZhongXinActivity.this, "修改密码成功");
							finish();
						}
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		if (PreferenceUtils.getBoolean(GeRenZhongXinActivity.this, "isLogin", false)) {
			String name = PreferenceUtils.getString(GeRenZhongXinActivity.this, "name", "");
			PreferenceUtils.putString(GeRenZhongXinActivity.this, "name", name);
			mTv_nicheng.setText(name);
		}
		// 点击箭头的监听事件
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 点击退出登录的监听按钮
		mBtn_tuichuLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PreferenceUtils.setBoolean(GeRenZhongXinActivity.this, "isLogin", false);
				finish();
			}
		});
	}
}
