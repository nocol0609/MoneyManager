package com.briup.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {
	private PreferenceUtils() {

	}

	public static int getInt(Context context, final String key, int defaultValues) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getInt(key, defaultValues);

	}

	public static void putInt(Context context, final String key, int value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putInt(key, value).commit();

	}

	public static long getLong(Context context, final String key, long defaultValues) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getLong(key, defaultValues);

	}

	public static void putLong(Context context, final String key, long values) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putLong(key, values).commit();

	}

	public static boolean getBoolean(Context context, final String key, boolean defaultValues) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getBoolean(key, defaultValues);

	}

	public static void setBoolean(Context context, final String key, boolean value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putBoolean(key, value).commit();

	}

	public static String getString(Context context, final String key, String defaultValues) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getString(key, defaultValues);

	}

	public static void putString(Context context, final String key, String test) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putString(key, test).commit();

	}

	public static float getFloat(Context context, final String key, float defaultValues) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getFloat(key, defaultValues);

	}

	public static void putFloat(Context context, final String key, float value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		sp.edit().putFloat(key, value).commit();

	}

}
