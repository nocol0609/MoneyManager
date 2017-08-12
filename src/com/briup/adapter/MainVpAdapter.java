package com.briup.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MainVpAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;

	public MainVpAdapter(FragmentManager fm) {
		super(fm);

	}

	public MainVpAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.mFragments = fragments;

	}

	// 返回每个界面对应的fragment对象
	@Override
	public Fragment getItem(int arg0) {

		return mFragments.get(arg0);
	}

	// 返回fragment的数量
	@Override
	public int getCount() {

		return mFragments.size();
	}

}
