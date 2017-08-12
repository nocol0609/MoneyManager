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

		// 当未登录的时候进入的是最开始的资金的页面，否则进入有信用卡那些东西的界面
		if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
			// 登录时的情况
			View view = inflater.inflate(R.layout.fragment_zijin_login, container, false);
			initView(view);
			initData();
			initAdapter();
			initListener1();

			// 从数据库中取出所有的钱，将他们求和的值放在结余后面
			float jieyuAccount = dao.findAllPrice();
			mTv_jieyuaccount.setText(jieyuAccount + "");

			// 将name="现金"那一列的钱放在现金那一列的最右边
			float xianjinAccount = dao.findXianjinPrice();
			mTv_money.setText(String.valueOf(xianjinAccount + ""));

			// 将name="储蓄卡"那一列的钱放在现金那一列的最右边
			float chuxuAccount = dao.findChuXuPrice();
			mTv_chuxu.setText(String.valueOf(chuxuAccount + ""));
			return view;
		} else {
			// 未登录时的情况
			View view = inflater.inflate(R.layout.fragment_zijin, container, false);
			initView(view);
			initListener();
			return view;
		}

	}

	// 当未登录时跳到的页面产生的反应
	private void initListener() {
		mRl_tianjiaAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToastUtil.showToast(getActivity(), "请先登录");

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
		// 当登录的时候，点击添加按钮，会进入添加账户的那个界面
		mRl_tianjiaAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddAccountActivity.class);
				startActivity(intent);
			}
		});

		// 点击现金，进入现金的界面
		mRl_zhanghuType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// 自定义一个布局文件
				View view = View.inflate(getActivity(), R.layout.fragment_zhanghu_type, null);
				// 对弹出框中取消存入按钮的实例化
				mBtn_save = (Button) view.findViewById(R.id.zijin__btn_save);
				mBtn_cancel = (Button) view.findViewById(R.id.zijin_btn_cancel);
				// 对弹出框中输入框的实例化
				mEt_shurujiner = (EditText) view.findViewById(R.id.zijin_et_jiner);
				// 对弹出框中取消按钮的监听，点击之后，对话框会消失
				mBtn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				// 对弹出框存入按钮的监听，当点击按钮，会将资金添加到数据库中，
				// 而且结余的资金会加上现在添加的资金
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String name = "现金";
						String xianjinPrice = mEt_shurujiner.getText().toString();
						PreferenceUtils.putFloat(getActivity(), "xianjinprice", Float.valueOf(xianjinPrice));
						float price = dao.findXianjinPrice();
						// 修改现金的金额
						// dao.addAccount(new Account("现金", 0));
						// dao.changeXianjinAccount(name, new
						// Account(Float.valueOf(xianjinPrice)));
						int id = dao.findIdByName(name);
						if (id != 0) {
							dao.changeXianjinAccount(name,
									new Account(Float.valueOf(price) + Float.valueOf(xianjinPrice)));
						} else {
							dao.addAccount(new Account(name, Float.valueOf(xianjinPrice)));

						}
						// dao.addAccount(new Account("现金", Float
						// .valueOf(xianjinPrice)));
						dialog.dismiss();
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// 点击储蓄卡，进入储蓄卡的界面
		mRl_zhanghuType1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// 自定义一个布局文件
				View view = View.inflate(getActivity(), R.layout.fragment_zhanghu_type, null);
				// 对弹出框中取消存入按钮的实例化
				mBtn_save = (Button) view.findViewById(R.id.zijin__btn_save);
				mBtn_cancel = (Button) view.findViewById(R.id.zijin_btn_cancel);
				// 对弹出框中输入框的实例化
				mEt_shurujiner = (EditText) view.findViewById(R.id.zijin_et_jiner);
				// 对弹出框中取消按钮的监听，点击之后，对话框会消失
				mBtn_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				// 对弹出框存入按钮的监听，当点击按钮，会将资金添加到数据库中，
				// 而且结余的资金会加上现在添加的资金
				mBtn_save.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String name = "储蓄卡";
						String chuxukaPrice = mEt_shurujiner.getText().toString();
						PreferenceUtils.putFloat(getActivity(), "chuxukaPrice", Float.valueOf(chuxukaPrice));
						float price = dao.findChuXuPrice();
						// 修改现金的金额
						// dao.addAccount(new Account("现金", 0));
						// dao.changeXianjinAccount(name, new
						// Account(Float.valueOf(xianjinPrice)));
						int id = dao.findIdByName(name);
						if (id != 0) {
							dao.changeXianjinAccount(name,
									new Account(Float.valueOf(price) + Float.valueOf(chuxukaPrice)));
						} else {
							dao.addAccount(new Account(name, Float.valueOf(chuxukaPrice)));

						}
						// dao.addAccount(new Account("现金", Float
						// .valueOf(xianjinPrice)));
						dialog.dismiss();
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// 点击转账界面，会进入转账页面
		mBtn_zhuanzhang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), ZhuanZhangActivity.class);
				startActivity(intent);
			}
		});

	}

}
