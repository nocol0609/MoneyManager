package com.briup.moneymanager;

import com.briup.bean.Account;
import com.briup.db.AccountDao;
import com.briup.db.RecordDao;
import com.briup.utils.ToastUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
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

public class ZhuanZhangActivity extends Activity {
	private EditText mEt_zhuanzhang_jiner;
	private Button mBtn_querenZhuanzhang;
	private AccountDao dao;
	private ImageView mIv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuan_zhang1);

		// ʵ����
		mEt_zhuanzhang_jiner = (EditText) findViewById(R.id.zhuanzhangjiner_et);
		mBtn_querenZhuanzhang = (Button) findViewById(R.id.queren_btn_zhuanzhang);
		dao = new AccountDao(this);
		mIv_back = (ImageView) findViewById(R.id.back1);

		// ��ͷ�ļ���
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mBtn_querenZhuanzhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String zhuanzhangMoney = mEt_zhuanzhang_jiner.getText().toString();
				String zhanghuName = "�ֽ�";
				float price = dao.findXianjinPrice();
				// �޸��ֽ�Ľ��
				int id = dao.findIdByName(zhanghuName);
				if (id != 0) {
					dao.changeXianjinAccount(zhanghuName,
							new Account(Float.valueOf(price) + Float.valueOf("-" + zhuanzhangMoney)));
				} else {
					dao.addAccount(new Account(zhanghuName, Float.valueOf("-" + zhuanzhangMoney)));

				}
				// �޸Ĵ���Ľ��
				String ChuXukaName = "���";
				float price1 = dao.findChuXuPrice();
				// �޸��ֽ�Ľ��
				int id1 = dao.findIdByName(ChuXukaName);
				if (id1 != 0) {
					dao.changeXianjinAccount(ChuXukaName,
							new Account(Float.valueOf(price1) + Float.valueOf(zhuanzhangMoney)));
				} else {
					dao.addAccount(new Account(ChuXukaName, Float.valueOf(zhuanzhangMoney)));

				}

				ToastUtil.showToast(ZhuanZhangActivity.this, "ת�˳ɹ�");
				finish();

			}
		});
	}

}
