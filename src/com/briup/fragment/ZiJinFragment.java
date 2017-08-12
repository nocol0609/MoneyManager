package com.briup.fragment;

import java.util.List;

import com.briup.adapter.QueryLvAdapter;
import com.briup.bean.Account;
import com.briup.db.AccountDao;
import com.briup.moneymanager.AddAccountActivity;
import com.briup.moneymanager.R;
import com.briup.moneymanager.ZhuanZhangActivity;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.R.string;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZiJinFragment extends Fragment {
	private RelativeLayout mRl_tianjiaAccount;
	private Button mBtn_zhuanzhang;
	private TextView mTv_jieyuaccount, mTv_money, mTv_chuxu;
	private ListView mLv_show;
	private List<Account> accounts;
	private RelativeLayout mRl_zhanghuType, mRl_zhanghuType1;
	private AccountDao dao;
	private QueryLvAdapter adapter;
	private Button mBtn_save, mBtn_cancel;
	private EditText mEt_shurujiner;

	private AlertDialog dialog;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// ��δ��¼��ʱ���������ʼ���ʽ��ҳ�棬������������ÿ���Щ�����Ľ���
		if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
			// ��¼ʱ�����
			View view = inflater.inflate(R.layout.fragment_zijin_login, container, false);
			initView(view);
			initData();
			initAdapter();
			initListener1();

			// �����ݿ���ȡ�����е�Ǯ����������͵�ֵ���ڽ������
			float jieyuAccount = dao.findAllPrice();
			mTv_jieyuaccount.setText(jieyuAccount + "");

			// ��name="�ֽ�"��һ�е�Ǯ�����ֽ���һ�е����ұ�
			float xianjinAccount = dao.findXianjinPrice();
			mTv_money.setText(String.valueOf(xianjinAccount + ""));

			// ��name="���"��һ�е�Ǯ�����ֽ���һ�е����ұ�
			float chuxuAccount = dao.findChuXuPrice();
			mTv_chuxu.setText(String.valueOf(chuxuAccount + ""));
			return view;
		} else {
			// δ��¼ʱ�����
			View view = inflater.inflate(R.layout.fragment_zijin, container, false);
			initView(view);
			initListener();
			return view;
		}

	}

	// ��δ��¼ʱ������ҳ������ķ�Ӧ
	private void initListener() {
		mRl_tianjiaAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtil.showToast(getActivity(), "���ȵ�¼");

			}
		});
	}

	private void initAdapter() {
		mLv_show.setAdapter(new QueryLvAdapter(accounts, getActivity()));
	}

	private void initData() {
		accounts = dao.findAllAccount();

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		mRl_tianjiaAccount = (RelativeLayout) view.findViewById(R.id.zijin_rl_tianjiazhanghu);
		mBtn_zhuanzhang = (Button) view.findViewById(R.id.zijin_btn_zhuanzhang);
		mLv_show = (ListView) view.findViewById(R.id.query_lv);
		mTv_jieyuaccount = (TextView) view.findViewById(R.id.zijin_tv_jieyu);
		dao = new AccountDao(getActivity());
		mRl_zhanghuType = (RelativeLayout) view.findViewById(R.id.zijin_rl_zhanghutype);
		mRl_zhanghuType1 = (RelativeLayout) view.findViewById(R.id.zijin_rl_zhanghutype1);
		mTv_money = (TextView) view.findViewById(R.id.zijin_tv_money);
		mTv_chuxu = (TextView) view.findViewById(R.id.zijin_tv_chuxu);
	}

	private void initListener1() {
		// ����¼��ʱ�򣬵����Ӱ�ť�����������˻����Ǹ�����
		mRl_tianjiaAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddAccountActivity.class);
				startActivity(intent);
			}
		});

		// ����ֽ𣬽����ֽ�Ľ���
		mRl_zhanghuType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// �Զ���һ�������ļ�
				View view = View.inflate(getActivity(), R.layout.fragment_zhanghu_type, null);
				// �Ե�������ȡ�����밴ť��ʵ����
				mBtn_save = (Button) view.findViewById(R.id.zijin__btn_save);
				mBtn_cancel = (Button) view.findViewById(R.id.zijin_btn_cancel);
				// �Ե�������������ʵ����
				mEt_shurujiner = (EditText) view.findViewById(R.id.zijin_et_jiner);
				// �Ե�������ȡ����ť�ļ��������֮�󣬶Ի������ʧ
				mBtn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				// �Ե�������밴ť�ļ������������ť���Ὣ�ʽ���ӵ����ݿ��У�
				// ���ҽ�����ʽ�����������ӵ��ʽ�
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String name = "�ֽ�";
						String xianjinPrice = mEt_shurujiner.getText().toString();
						PreferenceUtils.putFloat(getActivity(), "xianjinprice", Float.valueOf(xianjinPrice));
						float price = dao.findXianjinPrice();
						// �޸��ֽ�Ľ��
						// dao.addAccount(new Account("�ֽ�", 0));
						// dao.changeXianjinAccount(name, new
						// Account(Float.valueOf(xianjinPrice)));
						int id = dao.findIdByName(name);
						if (id != 0) {
							dao.changeXianjinAccount(name,
									new Account(Float.valueOf(price) + Float.valueOf(xianjinPrice)));
						} else {
							dao.addAccount(new Account(name, Float.valueOf(xianjinPrice)));

						}
						// dao.addAccount(new Account("�ֽ�", Float
						// .valueOf(xianjinPrice)));
						dialog.dismiss();
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// �����������봢��Ľ���
		mRl_zhanghuType1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// �Զ���һ�������ļ�
				View view = View.inflate(getActivity(), R.layout.fragment_zhanghu_type, null);
				// �Ե�������ȡ�����밴ť��ʵ����
				mBtn_save = (Button) view.findViewById(R.id.zijin__btn_save);
				mBtn_cancel = (Button) view.findViewById(R.id.zijin_btn_cancel);
				// �Ե�������������ʵ����
				mEt_shurujiner = (EditText) view.findViewById(R.id.zijin_et_jiner);
				// �Ե�������ȡ����ť�ļ��������֮�󣬶Ի������ʧ
				mBtn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				// �Ե�������밴ť�ļ������������ť���Ὣ�ʽ���ӵ����ݿ��У�
				// ���ҽ�����ʽ�����������ӵ��ʽ�
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String name = "���";
						String chuxukaPrice = mEt_shurujiner.getText().toString();
						PreferenceUtils.putFloat(getActivity(), "chuxukaPrice", Float.valueOf(chuxukaPrice));
						float price = dao.findChuXuPrice();
						// �޸��ֽ�Ľ��
						// dao.addAccount(new Account("�ֽ�", 0));
						// dao.changeXianjinAccount(name, new
						// Account(Float.valueOf(xianjinPrice)));
						int id = dao.findIdByName(name);
						if (id != 0) {
							dao.changeXianjinAccount(name,
									new Account(Float.valueOf(price) + Float.valueOf(chuxukaPrice)));
						} else {
							dao.addAccount(new Account(name, Float.valueOf(chuxukaPrice)));

						}
						// dao.addAccount(new Account("�ֽ�", Float
						// .valueOf(xianjinPrice)));
						dialog.dismiss();
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// ���ת�˽��棬�����ת��ҳ��
		mBtn_zhuanzhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), ZhuanZhangActivity.class);
				startActivity(intent);
			}
		});

	}

}
