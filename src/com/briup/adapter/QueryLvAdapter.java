package com.briup.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.briup.bean.Account;
import com.briup.moneymanager.R;
import com.briup.utils.PreferenceUtils;

public class QueryLvAdapter extends BaseAdapter {
	private List<Account> accounts;
	private Context context;

	public QueryLvAdapter(List<Account> accounts, Context context) {
		this.accounts = accounts;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return accounts.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.query_lv_item, null);
		TextView tv_name = (TextView) view.findViewById(R.id.query_lv_item_tv_name);
		TextView tv_price = (TextView) view.findViewById(R.id.query_lv_item_tv_price);

		tv_name.setText(accounts.get(position).getName());

		tv_price.setText(accounts.get(position).getPrice() + "");
		return view;
	}

}
