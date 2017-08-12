package com.briup.moneymanager;

import com.briup.bean.User;
import com.briup.db.UserDao;
import com.briup.utils.ToastUtil;

import android.app.Activity;
import android.app.ActionBar;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class UserRegisteActivity extends Activity {
	private EditText mEt_name, mEt_passwd, mEt_querenpasswd;
	private UserDao dao;
	private Button mBtn_register_sure, mBtn_reset_sure;
	private SharedPreferences sp; // 声明sharedpreferences对象

	public void backLogin(View view) {
		Intent intent = new Intent(UserRegisteActivity.this, UserLoginActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_registe);

		mEt_name = (EditText) findViewById(R.id.register_et_name);
		mEt_passwd = (EditText) findViewById(R.id.register_et_passwd);
		mEt_querenpasswd = (EditText) findViewById(R.id.register_et_querenpasswd);
		dao = new UserDao(UserRegisteActivity.this);
		mBtn_register_sure = (Button) findViewById(R.id.btn_register_sure);
		mBtn_reset_sure = (Button) findViewById(R.id.btn_reset_sure);
		// 实例化一个sharepreference对象
		sp = getSharedPreferences("user_register", Context.MODE_APPEND);
		mBtn_register_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// 实例化
				String registername = mEt_name.getText().toString();
				String registerpasswd = mEt_passwd.getText().toString();
				String querenpasswd = mEt_querenpasswd.getText().toString();

				// 从数据库中找寻是否存在该用户
				int id = dao.findIdByName(registername);

				// 设置当点击下面的按钮时，此时输入的值清空
				mEt_name.setText("");
				mEt_passwd.setText("");
				mEt_querenpasswd.setText("");
				// 对输入数据的处理，判断登录
				if (TextUtils.isEmpty(registername)) {
					// getActivity得到父容器
					ToastUtil.showToast(UserRegisteActivity.this, "请输入用户名");
				} else if (TextUtils.isEmpty(registerpasswd)) {
					ToastUtil.showToast(UserRegisteActivity.this, "请输入密码");
				} else if (TextUtils.isEmpty(querenpasswd)) {
					ToastUtil.showToast(UserRegisteActivity.this, "请输入确认密码");
				} else if (id != 0) {
					ToastUtil.showToast(UserRegisteActivity.this, "已存在该用户，注册失败");
				} else if (registerpasswd.equals(querenpasswd) == false) {
					ToastUtil.showToast(UserRegisteActivity.this, "密码与确认密码不一致，请重新输入 ");
				} else {
					dao.addUser(new User(registername, registerpasswd, querenpasswd));
					// 创建一个可以写入数据的对象
					Editor edit = sp.edit();
					// 写入数据
					edit.putString("name", registername);
					edit.putString("passwd", registerpasswd);
					edit.putBoolean("isLogin", false);
					// 提交数据，将数据提交到程序内存的同时，提交到磁盘上
					edit.commit();// ***非常重要
					// edit.apply();//它不是同步的，先提交到内存，再提交到磁盘上
					ToastUtil.showToast(UserRegisteActivity.this, "注册成功");
					finish();

				}

			}
		});
		mBtn_reset_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mEt_name.setText("");
				mEt_passwd.setText("");
				mEt_querenpasswd.setText("");
				ToastUtil.showToast(UserRegisteActivity.this, "重置");
			}
		});

	}

}
