package com.briup.moneymanager;

import com.briup.bean.Account;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Build;

public class AddAccountActivity extends Activity {
	private AccountDao dao;
	private EditText mEt_accountType, mEt_accountName, mEt_accountPrice;
	private AlertDialog dialog;
	private Button mBtn_setting_cancel, mBtn_save;
	private ImageView mIv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_account);

		mEt_accountType = (EditText) findViewById(R.id.zijin_et_type);
		mBtn_save = (Button) findViewById(R.id.tianjiazijin_btn_save);
		mEt_accountName = (EditText) findViewById(R.id.tianjiazijin_et_name);
		mEt_accountPrice = (EditText) findViewById(R.id.tianjiazijin_et_price);
		mIv_back = (ImageView) findViewById(R.id.back1);
		dao = new AccountDao(AddAccountActivity.this);

		// �����ͷ�ļ����¼�
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// ����˻����͵�ʱ�򣬵���һ���Ի���ѡ���˻����ͣ�����û��ʵ�ֵ������ʱ������Ǹ�Ч��
		mEt_accountType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				// TODO Auto-generated method stu private void showDialog() {
				AlertDialog.Builder builder = new Builder(AddAccountActivity.this);
				// �Զ���һ�������ļ�
				View view = View.inflate(AddAccountActivity.this, R.layout.dialog_accounttype, null);
				mBtn_setting_cancel = (Button) view.findViewById(R.id.setting_btn_cancel);

				//
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
		});

		// ������ʽ��˻�����ı��水ť�ĵ�������¼�
		mBtn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String accountName = mEt_accountName.getText().toString();
				String accountPrice = mEt_accountPrice.getText().toString();
				dao.addAccount(new Account(accountName, Float.valueOf(accountPrice)));
				PreferenceUtils.putString(AddAccountActivity.this, "accountname", accountName);
				PreferenceUtils.putFloat(AddAccountActivity.this, "accountprice", Float.valueOf(accountPrice));
				finish();
			}
		});

	}

}
