package com.briup.fragment;

import java.util.Calendar;

import java.util.List;

import com.briup.adapter.JiZhangLvAdapter;
import com.briup.bean.Record;
import com.briup.db.RecordDao;
import com.briup.moneymanager.R;
import com.briup.moneymanager.ZhichuActivity;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

public class JiZhangFragment extends Fragment {
	private ImageView mIv_pressPen, mIv_add_yusuan;
	private ImageView mIv_pressCalendar;
	private AlertDialog dialog;
	private DatePicker datePicker;
	private ListView mLv_xianshi;
	private List<Record> records;
	private TextView mTv_shouruyuefen, mTv_zhichuyuefen, mTv_riqi;
	private RecordDao dao;
	private TextView mTv_yueshouru, mTv_yuezhichi;
	private JiZhangLvAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_jizhang, container, false);
		initView(view);
		initData();
		initAdapter();
		initListener();

		String yuefen = PreferenceUtils.getString(getActivity(), "yuefen", "");
		PreferenceUtils.putString(getActivity(), "yuefen", yuefen);

		String day = PreferenceUtils.getString(getActivity(), "riqi", "");
		PreferenceUtils.putString(getActivity(), "riqi", day);

		mTv_shouruyuefen.setText(yuefen + "月收入");
		mTv_zhichuyuefen.setText(yuefen + "月支出");
		mTv_riqi.setText(day);

		float shouruMoney = dao.findShouruMoney();
		mTv_yueshouru.setText(shouruMoney + "");

		float zhichuMoney = dao.findZhichuMoney();
		mTv_yuezhichi.setText(zhichuMoney + "");
		return view;
	}

	private void initData() {
		records = dao.findAllRecords();
	}

	private void initAdapter() {
		Log.i("jizhang", "zhixingle");
		mLv_xianshi.setAdapter(new JiZhangLvAdapter(records, getActivity()));
	}

	private void initListener() {
		mIv_pressPen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PreferenceUtils.getBoolean(getActivity(), "isLogin", false)) {
					Intent intent = new Intent(getActivity(), ZhichuActivity.class);
					startActivity(intent);
				} else {
					ToastUtil.showToast(getActivity(), "您还没有登录");
				}
			}
		});
		// 显示日历，并获得当前的月份和日期
		mIv_pressCalendar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(getActivity());
				// 自定义一个布局文件
				View view = View.inflate(getActivity(), R.layout.calendar, null);
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				datePicker = (DatePicker) view.findViewById(R.id.dp);
				datePicker.init(year, month, day, new OnDateChangedListener() {

					@Override
					// 在日期发生改变的时候会触发
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						ToastUtil.showToast(getActivity(),
								"您当前选择了" + year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
						PreferenceUtils.putString(getActivity(), "yuefen", (monthOfYear + 1) + "");
						PreferenceUtils.putString(getActivity(), "riqi", dayOfMonth + "");
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});
		mIv_add_yusuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ZhichuActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView(View view) {
		mIv_pressPen = (ImageView) view.findViewById(R.id.jizhang_iv_presspen);
		mIv_pressCalendar = (ImageView) view.findViewById(R.id.calendar_iv_settime);
		mTv_shouruyuefen = (TextView) view.findViewById(R.id.tv_yuefenshouru);
		mTv_zhichuyuefen = (TextView) view.findViewById(R.id.tv_yuefenzhichu);
		mTv_riqi = (TextView) view.findViewById(R.id.tv_riqi);
		mLv_xianshi = (ListView) view.findViewById(R.id.jizhang_lv_xianshi);
		dao = new RecordDao(getActivity());
		mTv_yueshouru = (TextView) view.findViewById(R.id.xianshi_tv_shouruAccount);
		mTv_yuezhichi = (TextView) view.findViewById(R.id.xianshi_tv_zhichu);
		mIv_add_yusuan = (ImageView) view.findViewById(R.id.addYuSuan);
	}
}
