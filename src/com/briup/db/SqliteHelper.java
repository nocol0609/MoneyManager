package com.briup.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	public SqliteHelper(Context context) {
		super(context, "user.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 用户表
		String sql = "create table tbl_user(_id integer primary key autoincrement,name,passwd)";
		db.execSQL(sql);
		// 账户表
		String sql1 = "create table tbl_account(_id integer primary key autoincrement,name,price)";
		db.execSQL(sql1);

		/**
		 * 记录表 type:是收入还是支出 content:用于哪方面 money:用了多少钱
		 */
		String sql2 = "create table tbl_record(_id integer primary key autoincrement,type,content,money,date)";
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
