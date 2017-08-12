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
	private String[] titles = { "����", "����", "����", "Ͷ������", "���", "��ְ", "�����", "ҽҩ", "�˿�", "������", "�籣��", "�����ջ�" };
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shouru_or_zhichu_type);
		// ʵ����
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

		// ��ͷ�ļ���
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// ��ʾ����
		mIv_show_calendar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(ShouruActivity.this);
				// �Զ���һ�������ļ�
				View view = View.inflate(ShouruActivity.this, R.layout.calendar, null);
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				datePicker = (DatePicker) view.findViewById(R.id.dp);

				datePicker.init(year, month, day, new OnDateChangedListener() {

					@Override
					// �����ڷ����ı��ʱ��ᴥ��
					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						ToastUtil.showToast(ShouruActivity.this,
								"����ǰѡ����" + year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
						PreferenceUtils.putString(ShouruActivity.this, "yuefen", (monthOfYear + 1) + "");
						PreferenceUtils.putString(ShouruActivity.this, "riqi", dayOfMonth + "");
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});

		// ���֧����ť�ļ���
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
					// ����contentΪ�����ʡ������䱣����sp��
					String content = "����";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˹���");
					break;
				case 1:
					content = "����";
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˽���");
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					break;
				case 2:
					content = "����";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˸���");
					break;
				case 3:
					content = "Ͷ������";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ����Ͷ������");
					break;

				case 4:
					content = "���";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˺��");
					break;

				case 5:
					content = "��ְ";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˼�ְ");
					break;

				case 6:
					content = "�����";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���������");
					break;

				case 7:
					content = "����";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˱���");
					break;

				case 8:
					content = "�˿�";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ�����˿�");
					break;

				case 9:
					content = "������";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���˹�����");
					break;

				case 10:
					content = "�籣��";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ�����籣��");
					break;

				case 11:
					content = "�����ջ�";
					PreferenceUtils.putString(ShouruActivity.this, "content", content);
					ToastUtil.showToast(ShouruActivity.this, "��ѡ���������ջ�");
					break;

				}
			}
		});
		// ��������溬�д��Ǹ�ͼƬ����һ�еļ���
		mRl_save_shouru.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String money = mEt_money.getText().toString();
				String type = "����";
				String content1 = PreferenceUtils.getString(ShouruActivity.this, "content", "");
				PreferenceUtils.putString(ShouruActivity.this, "content", content1);
				String day = PreferenceUtils.getString(ShouruActivity.this, "riqi", "");
				PreferenceUtils.putString(ShouruActivity.this, "riqi", day);
				dao.addRecord(new Record(type, content1, Float.valueOf(money), day));
				ToastUtil.showToast(ShouruActivity.this, "�������ݳɹ�");
				finish();
			}
		});

	}
}
