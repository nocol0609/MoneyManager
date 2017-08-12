package com.briup.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.briup.bean.Account;
import com.briup.bean.Record;
import com.briup.bean.User;
import com.briup.moneymanager.GeRenZhongXinActivity;
import com.briup.utils.PreferenceUtils;

public class AccountDao {
	private SqliteHelper helper;
	private SQLiteDatabase db;

	public AccountDao(Context context) {
		helper = new SqliteHelper(context);
	}

	// 添加资金账号
	public void addAccount(Account account) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", account.getName());
		values.put("price", account.getPrice());
		db.insert(" tbl_account", null, values);
		Log.i("AccountDao", "插入数据成功");

	}

	// 查询所有资金账号
	public List<Account> findAllAccount() {
		List<Account> list = new ArrayList<Account>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			float price = cursor.getFloat(cursor.getColumnIndex("price"));
			Account account = new Account(name, price);
			list.add(account);
			// sum=
			// list.add(sum);
			Log.i("www", "正在查询");

		}

		return list;
	}

	// 根据账号名字查询资金账号的ID
	public int findIdByName(String name) {
		int id = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "_id" }, "name=?", new String[] { name }, null, null,
				null);
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("_id"));
		}

		return id;

	}

	// 实现对插入数据库中数据的求和
	public float findAllPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));
			Log.i("AccountDao", "找到所有数据的和");
		}
		return sum;
	}

	// 找到名字为现金那一列资金的总金额
	public float findXianjinPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, "name=?", new String[] { "现金" }, null, null,
				null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));

			Log.i("现金", "执行了该方法");
		}
		return sum;

	}

	// 找到名字为储蓄卡那一列资金的总金额
	public float findChuXuPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, "name=?", new String[] { "储蓄卡" }, null, null,
				null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));

			Log.i("现金", "执行了该方法");
		}
		return sum;

	}

	// 根据账户名查询账户
	public List<Account> findAccountsByName(String name) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", null, "name=?", new String[] { name }, null, null, null);
		List<Account> list = new ArrayList<Account>();
		while (cursor.moveToNext()) {
			String account_name = cursor.getString(1);
			float account_price = cursor.getFloat(2);
			Account account = new Account(account_name, account_price);
			list.add(account);
		}
		return list;

	}

	// 修改账户
	public void changeAccount(String oldName, Account newAccount) {
		int id = findIdByName(oldName);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", newAccount.getName());
		values.put("price", newAccount.getPrice());
		db.update("tbl_account", values, "_id=?", new String[] { id + "" });
		Log.i("AccountDao", "修改数据成功");
	}

	// 修改现金
	public void changeXianjinAccount(String name, Account account) {
		int id = findIdByName(name);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		// values.put("name", newUser.getName());
		values.put("price", account.getPrice());
		db.update("tbl_account", values, "_id=?", new String[] { id + "" });
		Log.i("AccountDao", "修改数据成功");
		Log.i("AccountDao", id + ":" + account.getPrice());
	}

}
