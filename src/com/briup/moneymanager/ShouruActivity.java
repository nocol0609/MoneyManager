package com.briup.moneymanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.briup.bean.Record;
import com.briup.db.AccountDao;
import com.briup.db.RecordDao;
import com.briup.utils.PreferenceUtils;
import com.briup.utils.ToastUtil;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.os.Build;

public class ShouruActivity extends Activity {
	private GridView gridView;
	private EditText mEt_money;
	private Button mBtn_zhichu, mBtn_shouru;
	private RelativeLayout mRl_save_shouru;
	private RecordDao dao;
	private DatePicker datePicker;
	private AlertDialog dialog;
	private TextView mTv_show_day;
	private ImageView mIv_show_calendar, mIv_back;

	private int[] images = { R.drawable.bt_wages, R.drawable.bt_bouns, R.drawable.bt_fuli, R.drawable.bt_invest,
			R.drawable.bt_hongbao, R.drawable.bt_jianzhi, R.drawable.bt_shenghuofei, R.drawable.bt_baoxiao,
			R.drawable.bt_tuikuan, R.drawable.bt_gongjijin, R.drawable.bt_shebao, R.drawable.bt_yiwai };
	private String[] titles = { "工资", "奖金", "福利", "投资收益", "红包", "兼职", "生活费", "医药", "退款", "公积金", "社保金", "意外收获" };
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouru_or_zhichu_type);
		// 实例化
		mBtn_zhichu = (Button) findViewById(R.id.jizhang_btn_zhichu);
		mBtn_shouru = (Button) findViewById(R.id.jizhang_btn_shouru);
		mEt_money = (EditText) findViewById(R.id.shouru_et_money);

		mRl_save_shouru = (RelativeLayout) findViewById(R.id.shouru_save_rl);
		dao = new RecordDao(this);
		mIv_show_calendar = (ImageView) findViewById(R.id.calendar_iv);
		mTv_show_day = (TextView) findViewById(R.id.day_tv_show);
		mIv_back = (ImageView) findViewById(R.id.back1);

		String day = PreferenceUtils.getString(ShouruActivity.this, "riqi", "");
		PreferenceUtils.putString(ShouruActivity.this, "riqi", day);
		mTv_show_day.setText(day);

		// 箭头的监听
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 显示日历
		mIv_show_calendar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(ShouruActivity.this);
				// 自定义一个布局文件
				View view = View.inflate(ShouruActivity.this, R.layout.calendar, null);
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
						ToastUtil.showToast(ShouruActivity.this,
								"您当前选择了" + year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
						PreferenceUtils.putString(ShouruActivity.this, "yuefen", (monthOfYear + 1) + "");
						PreferenceUtils.putString(ShouruActivity.this, "riqi", dayOfMonth + "");
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// 点击支出按钮的监听
		mBtn_zhichu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShouruActivity.this, ZhichuActivity.class);
				startActivity(intent);
			}
		});

		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < images.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", images[i]);
			map.put("title", titles[i]);
			list.add(map);
		}

		simpleAdapter = new SimpleAdapter(this, list, R.layout.myitem, new String[] { "image", "title" },
				new int[] { R.id.image_Iv, R.id.title_Tv });

		gridView = (GridView) findViewById(R.id.gv);
		gridView.setAdapter(simpleAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					// 设置content为”工资“，将其保存在sp中
					String content = "工资";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了工资");
					break;
				case 1:
					content = "奖金";
					ToastUtil.showToast(ShouruActivity.this, "您选择了奖金");
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					break;
				case 2:
					content = "福利";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了福利");
					break;
				case 3:
					content = "投资收益";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了投资收益");
					break;

				case 4:
					content = "红包";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了红包");
					break;

				case 5:
					content = "兼职";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了兼职");
					break;

				case 6:
					content = "生活费";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了生活费");
					break;

				case 7:
					content = "报销";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了报销");
					break;

				case 8:
					content = "退款";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了退款");
					break;

				case 9:
					content = "公积金";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了公积金");
					break;

				case 10:
					content = "社保金";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了社保金");
					break;

				case 11:
					content = "意外收获";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "您选择了意外收获");
					break;

				}
			}
		});
		// 点击最下面含有打勾那个图片的那一行的监听
		mRl_save_shouru.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String money = mEt_money.getText().toString();
				String type = "收入";
				String content1 = PreferenceUtils.getString(ShouruActivity.this, "content", "");
				PreferenceUtils.putString(ShouruActivity.this, "content", content1);
				String day = PreferenceUtils.getString(ShouruActivity.this, "riqi", "");
				PreferenceUtils.putString(ShouruActivity.this, "riqi", day);
				dao.addRecord(new Record(type, content1, Float.valueOf(money), day));
				ToastUtil.showToast(ShouruActivity.this, "插入数据成功");
				finish();
			}
		});

	}
}
