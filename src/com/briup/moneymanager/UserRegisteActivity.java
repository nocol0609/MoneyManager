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
	private SharedPreferences sp; // ����sharedpreferences����

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
		// ʵ����һ��sharepreference����
		sp = getSharedPreferences("user_register", Context.MODE_APPEND);
		mBtn_register_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// ʵ����
				String registername = mEt_name.getText().toString();
				String registerpasswd = mEt_passwd.getText().toString();
				String querenpasswd = mEt_querenpasswd.getText().toString();

				// �����ݿ�����Ѱ�Ƿ���ڸ��û�
				int id = dao.findIdByName(registername);

				// ���õ��������İ�ťʱ����ʱ�����ֵ���
				mEt_name.setText("");
				mEt_passwd.setText("");
				mEt_querenpasswd.setText("");
				// ���������ݵĴ����жϵ�¼
				if (TextUtils.isEmpty(registername)) {
					// getActivity�õ�������
					ToastUtil.showToast(UserRegisteActivity.this, "�������û���");
				} else if (TextUtils.isEmpty(registerpasswd)) {
					ToastUtil.showToast(UserRegisteActivity.this, "����������");
				} else if (TextUtils.isEmpty(querenpasswd)) {
					ToastUtil.showToast(UserRegisteActivity.this, "������ȷ������");
				} else if (id != 0) {
					ToastUtil.showToast(UserRegisteActivity.this, "�Ѵ��ڸ��û���ע��ʧ��");
				} else if (registerpasswd.equals(querenpasswd) == false) {
					ToastUtil.showToast(UserRegisteActivity.this, "������ȷ�����벻һ�£����������� ");
				} else {
					dao.addUser(new User(registername, registerpasswd, querenpasswd));
					// ����һ������д�����ݵĶ���
					Editor edit = sp.edit();
					// д������
					edit.putString("name", registername);
					edit.putString("passwd", registerpasswd);
					edit.putBoolean("isLogin", false);
					// �ύ���ݣ��������ύ�������ڴ��ͬʱ���ύ��������
					edit.commit();// ***�ǳ���Ҫ
					// edit.apply();//������ͬ���ģ����ύ���ڴ棬���ύ��������
					ToastUtil.showToast(UserRegisteActivity.this, "ע��ɹ�");
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
				ToastUtil.showToast(UserRegisteActivity.this, "����");
			}
		});

	}

}
