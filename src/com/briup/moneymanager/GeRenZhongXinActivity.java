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
		// ʵ����
		mIv_back = (ImageView) findViewById(R.id.back1);
		mBtn_tuichuLogin = (Button) findViewById(R.id.genrenzhongxin_btn_tuichulogin);
		mTv_nicheng = (TextView) findViewById(R.id.gengduo_tv_nicheng);
		mRl_xiugaimima = (RelativeLayout) findViewById(R.id.xiugaimima_tv);
		dao = new UserDao(GeRenZhongXinActivity.this);
		mRl_changeName = (RelativeLayout) findViewById(R.id.change_rl_name);

		// ����޸��ǳƣ��Ὣ���ݿ��е����ָı�
		mRl_changeName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog1();
			}

			private void showDialog1() {
				AlertDialog.Builder builder = new Builder(GeRenZhongXinActivity.this);
				// �Զ���һ�������ļ�
				View view = View.inflate(GeRenZhongXinActivity.this, R.layout.dialog_change_name, null);

				mEt_changeName = (EditText) view.findViewById(R.id.change_et_name);
				mBtn_changeName_quxiao = (Button) view.findViewById(R.id.changeName_btn_cancel);
				mBtn_changeName_save = (Button) view.findViewById(R.id.changeName_btn_save);
				Log.i("GeRenZhongXinActivity", mBtn_changeName_quxiao + "");

				// ����Ի����е�ȡ����ť�����˳��Ի���
				mBtn_changeName_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// ������棬�޸��û���
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
						ToastUtil.showToast(GeRenZhongXinActivity.this, "�޸��ǳƳɹ�");
						finish();
					}
				});

				builder.setView(view);
				dialog = builder.show();

			}
		});

		// ����޸����룬�����Ի���
		mRl_xiugaimima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(GeRenZhongXinActivity.this);
				// �Զ���һ�������ļ�
				View view = View.inflate(GeRenZhongXinActivity.this, R.layout.dialog_xiugaimima, null);

				mBtn_save = (Button) view.findViewById(R.id.xiugaimima_btn_save);
				mBtn_quxiao = (Button) view.findViewById(R.id.xiugaimima_btn_cancel);
				mEt_passwd = (EditText) view.findViewById(R.id.xiugai_et_passwd);
				mEt_checkedPasswd = (EditText) view.findViewById(R.id.xiugai_et_querenmima);
				// ���ȡ�����öԻ�����ʧ
				mBtn_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				// ������棬�޸����룬�õ�ϵͳ��ǰ���û�����Ȼ��������������ȷ�����룬��������潫���ݿ��е������޸�
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						String newPasswd = mEt_passwd.getText().toString();
						String checkedPasswd = mEt_checkedPasswd.getText().toString();

						Log.i("GeRenZhongXinActivity", newPasswd);

						if (newPasswd.equals(checkedPasswd) == false) {
							ToastUtil.showToast(GeRenZhongXinActivity.this, "������ȷ�����벻һ�£�����������");
						} else {

							String name = PreferenceUtils.getString(GeRenZhongXinActivity.this, "name", "");
							PreferenceUtils.putString(GeRenZhongXinActivity.this, "name", name);
							// �޸�����
							dao.changeUser(name, new User(newPasswd));
							Log.i("GerenzhongxinActivity", name + ":" + newPasswd);
							ToastUtil.showToast(GeRenZhongXinActivity.this, "�޸�����ɹ�");
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
		// �����ͷ�ļ����¼�
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// ����˳���¼�ļ�����ť
		mBtn_tuichuLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PreferenceUtils.setBoolean(GeRenZhongXinActivity.this, "isLogin", false);
				finish();
			}
		});
	}
}
