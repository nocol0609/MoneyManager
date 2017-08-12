package com.briup.moneymanager;

import java.util.ArrayList;
import java.util.List;

import com.briup.adapter.MainVpAdapter;
import com.briup.fragment.BaoZhangFragment;
import com.briup.fragment.GengDuoFragment;
import com.briup.fragment.JiZhangFragment;
import com.briup.fragment.ZiJinFragment;

import android.app.Activity;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.os.Build;

public class MainActivity extends FragmentActivity {
	private ViewPager mVp_show;
	private RadioGroup mRg_showRb;
	private RadioButton mRb_jizhang, mRb_baobiao, mRb_zijin, mRb_gengduo;
	private Fragment mJiZhangFragment, mBaoBiaoFragment, mZiJinFragment, mGengDuoFragment;
	private List<Fragment> mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initAdapter();
		initListener();

	}

	private void initListener() {
		mVp_show.setOnPageChangeListener(new OnPageChangeListener() {
			// 当界面被选中的时候的监听
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					mRb_jizhang.setChecked(true);
					break;
				case 1:
					mRb_baobiao.setChecked(true);
					break;
				case 2:
					mRb_zijin.setChecked(true);
					break;
				case 3:
					mRb_gengduo.setChecked(true);
					break;
				default:
					break;
				}
			}

			// 界面滑动结束的监听
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			// 界面滑动状态改变的监听
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		mRg_showRb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// 当RadioGroup中按钮改变时的监听
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.main_rb_jizhang:
					mVp_show.setCurrentItem(0);
					refresh(checkedId);
					break;
				case R.id.main_rb_baobiao:
					mVp_show.setCurrentItem(1);
					refresh(checkedId);
					break;

				case R.id.main_rb_zijin:
					mVp_show.setCurrentItem(2);
					refresh(checkedId);
					break;

				case R.id.main_rb_gengduo:
					mVp_show.setCurrentItem(3);
					refresh(checkedId);
					break;

				default:
					break;
				}
			}

			private void refresh(int id) {
				mRb_jizhang.setTextColor(Color.parseColor("#111111"));
				mRb_baobiao.setTextColor(Color.parseColor("#111111"));
				mRb_zijin.setTextColor(Color.parseColor("#111111"));
				mRb_gengduo.setTextColor(Color.parseColor("#111111"));
				switch (id) {

				case R.id.main_rb_jizhang:
					mRb_jizhang.setTextColor(Color.parseColor("#00d5b9"));
					break;
				case R.id.main_rb_baobiao:
					mRb_baobiao.setTextColor(Color.parseColor("#00d5b9"));
					break;
				case R.id.main_rb_zijin:
					mRb_zijin.setTextColor(Color.parseColor("#00d5b9"));
					break;
				case R.id.main_rb_gengduo:
					mRb_gengduo.setTextColor(Color.parseColor("#00d5b9"));
					break;
				default:
					break;
				}
			}
		});

	}

	private void initAdapter() {
		mVp_show.setAdapter(new MainVpAdapter(getSupportFragmentManager(), mFragments));
	}

	private void initData() {
		mFragments = new ArrayList<Fragment>();
		mJiZhangFragment = new JiZhangFragment();
		mBaoBiaoFragment = new BaoZhangFragment();
		mZiJinFragment = new ZiJinFragment();
		mGengDuoFragment = new GengDuoFragment();
		mFragments.add(mJiZhangFragment);
		mFragments.add(mBaoBiaoFragment);
		mFragments.add(mZiJinFragment);
		mFragments.add(mGengDuoFragment);
	}

	private void initView() {
		mVp_show = (ViewPager) findViewById(R.id.main_vp);
		mRg_showRb = (RadioGroup) findViewById(R.id.main_rg);
		mRb_jizhang = (RadioButton) findViewById(R.id.main_rb_jizhang);
		mRb_baobiao = (RadioButton) findViewById(R.id.main_rb_baobiao);
		mRb_zijin = (RadioButton) findViewById(R.id.main_rb_zijin);
		mRb_gengduo = (RadioButton) findViewById(R.id.main_rb_gengduo);
	}
}
