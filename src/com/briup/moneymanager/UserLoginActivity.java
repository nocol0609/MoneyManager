package com.briup.moneymanager;

import java.util.List;

import com.briup.adapter.QueryLvAdapter;
import com.briup.bean.User;
import com.briup.db.UserDao;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Build;

public class UserLoginActivity extends Activity {
	private EditText mEt_login_name, mEt_login_passwd;
	private ImageView mIv_back;
	private UserDao dao;
	private Button mBtn_register_sure, mBtn_login_sure;

	public void register(View view) {
		Intent intent = new Intent(this, UserRegisteActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_login);

		mEt_login_name = (EditText) findViewById(R.id.login_et_name);
		mEt_login_passwd = (EditText) findViewById(R.id.login_et_passwd);
		dao = new UserDao(UserLoginActivity.this);
		mBtn_login_sure = (Button) findViewById(R.id.user_btn_login);
		mBtn_register_sure = (Button) findViewById(R.id.user_btn_register);
		mIv_back = (ImageView) findViewById(R.id.iv_back);
		// 将用户输入的登录信息保存在user,实例化
		// sp = getSharedPreferences("user", Context.MODE_APPEND);

		mBtn_login_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 实例化
				String name = mEt_login_name.getText().toString();
				String passwd = mEt_login_passwd.getText().toString();
				User user = dao.findUserByNameAndPasswd(name, passwd);
				// 当输入完成后会自动清空输入框
				mEt_login_name.setText("");
				mEt_login_passwd.setText("");

				// 判断登录条件是否和注册时的信息一致
				if (TextUtils.isEmpty(name)) {
					// getActivity得到父容器
					ToastUtil.showToast(UserLoginActivity.this, "用户名不能为空");
				} else if (TextUtils.isEmpty(passwd)) {
					ToastUtil.showToast(UserLoginActivity.this, "密码不能为空");
				} else if (user == null) {
					ToastUtil.showToast(UserLoginActivity.this, "用户名或密码错误，请重新输入");
				} else {
					ToastUtil.showToast(UserLoginActivity.this, "登录成功");
					// 当登录成功的时候，将isLogin设为true
					PreferenceUtils.setBoolean(UserLoginActivity.this, "isLogin", true);
					PreferenceUtils.putString(UserLoginActivity.this, "name", name);
					finish();
				}
			}
		});
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

}
