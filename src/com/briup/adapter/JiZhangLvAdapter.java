package com.briup.adapter;

import java.util.List;

import com.briup.bean.Account;
import com.briup.bean.Record;
import com.briup.moneymanager.R;
import com.briup.moneymanager.ZhichuActivity;
import com.briup.utils.PreferenceUtils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JiZhangLvAdapter extends BaseAdapter {
	private List<Record> records;
	private Context context;

	public JiZhangLvAdapter(List<Record> records, Context context) {
		this.records = records;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return records.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 实例化
		View view = View.inflate(context, R.layout.jizhang_lv_item, null);
		// Log.i("jizhAG", "SHUCHU");
		TextView tv_xianshi_riqi = (TextView) view.findViewById(R.id.jizhang_tv_xianshi_riqi);
		TextView tv_xianshi_content = (TextView) view.findViewById(R.id.jizhang_tv_xianshi_content);
		TextView tv_xianshi_money = (TextView) view.findViewById(R.id.jizhang_tv_xianshi_money);
		// Log.i("jizhAG", "SHUCHU1");
		// 在资金页面显示listView，其中包含选择的日期，记录的content和money
		tv_xianshi_riqi.setText(records.get(position).getDate() + "日");
		tv_xianshi_content.setText(records.get(position).getContent());
		if (records.get(position).getType().equals("收入")) {
			tv_xianshi_money.setText("+" + records.get(position).getMoney() + "");
		} else {
			tv_xianshi_money.setText("-" + records.get(position).getMoney() + "");
		}

		return view;
	}

}
