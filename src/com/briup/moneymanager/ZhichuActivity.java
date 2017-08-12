package com.briup.moneymanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.briup.bean.Record;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.os.Build;

public class ZhichuActivity extends Activity {
	private GridView gridView;
	private Button mBtn_zhichu, mBtn_shouru;
	private RecordDao dao;
	private AlertDialog dialog;
	private RelativeLayout mRl_save;
	private DatePicker datePicker;
	private EditText mEt_money;
	private TextView mTv_show_day;
	private ImageView mIv_show_calendar, mIv_back;
	private int[] images = { R.drawable.bt_food, R.drawable.bt_wine, R.drawable.bt_car, R.drawable.bt_shopping,
			R.drawable.bt_yule, R.drawable.bt_kuisun, R.drawable.bt_service, R.drawable.bt_chongzhi,
			R.drawable.bt_madecine, R.drawable.bt_house, R.drawable.bt_water, R.drawable.bt_shicai };
	private String[] titles = { "����", "�̾�", "��ͨ", "����", "����", "Ͷ�ʿ���", "�������", "��ֵ", "ҽҩ", "ס��", "ˮ��ú", "ʳ��" };
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhi_chu_type);
		mBtn_zhichu = (Button) findViewById(R.id.jizhang_btn_zhichu);
		mBtn_shouru = (Button) findViewById(R.id.jizhang_btn_shouru);
		mEt_money = (EditText) findViewById(R.id.zhichu_et_money);
		mRl_save = (RelativeLayout) findViewById(R.id.save_rl);
		dao = new RecordDao(this);
		mIv_show_calendar = (ImageView) findViewById(R.id.calendar_iv);
		mTv_show_day = (TextView) findViewById(R.id.day_tv_show);
		String day = PreferenceUtils.getString(ZhichuActivity.this, "riqi", "");
		PreferenceUtils.putString(ZhichuActivity.this, "riqi", day);
		mTv_show_day.setText(day);
		mIv_back = (ImageView) findViewById(R.id.back1);

		// ��ͷ�ļ���
		mIv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mIv_show_calendar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}

			private void showDialog() {
				AlertDialog.Builder builder = new Builder(ZhichuActivity.this);
				// �Զ���һ�������ļ�
				View view = View.inflate(ZhichuActivity.this, R.layout.calendar, null);
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
						ToastUtil.showToast(ZhichuActivity.this,
								"����ǰѡ����" + year + ":" + (monthOfYear + 1) + ":" + dayOfMonth);
						PreferenceUtils.putString(ZhichuActivity.this, "yuefen", (monthOfYear + 1) + "");
						PreferenceUtils.putString(ZhichuActivity.this, "riqi", dayOfMonth + "");
					}
				});
				builder.setView(view);
				dialog = builder.show();
			}
		});
		mBtn_shouru.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ZhichuActivity.this, ShouruActivity.class);
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
				// TODO Auto-generated method stub

				switch (position) {
				case 0:

					String content = "����";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);

					// �������ת
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ���˲���");
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					break;
				case 1:
					content = "�̾�";
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ�����̾�");
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					break;
				case 2:
					content = "��ͨ";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ���˽�ͨ");
					break;
				case 3:
					content = "����";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ���˹���");
					break;

				case 4:
					content = "����";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ��������");
					break;

				case 5:
					content = "Ͷ�ʿ���";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ����Ͷ�ʿ���");
					break;

				case 6:
					content = "�������";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ�����������");
					break;

				case 7:
					content = "��ֵ";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ���˳�ֵ");
					break;

				case 8:
					content = "ҽҩ";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ����ҽҩ");
					break;

				case 9:
					content = "ס��";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ����ס��");
					break;

				case 10:
					content = "ˮ��ú";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ����ˮ��ú");
					break;

				case 11:
					content = "ʳ��";
					PreferenceUtils.putString(ZhichuActivity.this, "content", content);
					ToastUtil.showToast(ZhichuActivity.this, "��ѡ����ʳ��");
					break;

				}

			}
		});
		// ��������溬�д��Ǹ�ͼƬ����һ�еļ���
		mRl_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String money = mEt_money.getText().toString();
				String type = "֧��";
				String content1 = PreferenceUtils.getString(ZhichuActivity.this, "content", "");
				PreferenceUtils.putString(ZhichuActivity.this, "content", content1);
				String day = PreferenceUtils.getString(ZhichuActivity.this, "riqi", "");
				PreferenceUtils.putString(ZhichuActivity.this, "riqi", day);

				dao.addRecord(new Record(type, content1, Float.valueOf(money), day));
				ToastUtil.showToast(ZhichuActivity.this, "�������ݳɹ�");
				finish();
			}
		});
	}

}
