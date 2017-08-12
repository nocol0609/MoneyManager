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

	// ����ʽ��˺�
	public void addAccount(Account account) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", account.getName());
		values.put("price", account.getPrice());
		db.insert(" tbl_account", null, values);
		Log.i("AccountDao", "�������ݳɹ�");

	}

	// ��ѯ�����ʽ��˺�
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
			Log.i("www", "���ڲ�ѯ");

		}

		return list;
	}

	// �����˺����ֲ�ѯ�ʽ��˺ŵ�ID
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

	// ʵ�ֶԲ������ݿ������ݵ����
	public float findAllPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));
			Log.i("AccountDao", "�ҵ��������ݵĺ�");
		}
		return sum;
	}

	// �ҵ�����Ϊ�ֽ���һ���ʽ���ܽ��
	public float findXianjinPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, "name=?", new String[] { "�ֽ�" }, null, null,
				null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));

			Log.i("�ֽ�", "ִ���˸÷���");
		}
		return sum;

	}

	// �ҵ�����Ϊ�����һ���ʽ���ܽ��
	public float findChuXuPrice() {
		float sum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_account", new String[] { "price" }, "name=?", new String[] { "���" }, null, null,
				null);
		while (cursor.moveToNext()) {
			sum = sum + cursor.getFloat(cursor.getColumnIndex("price"));

			Log.i("�ֽ�", "ִ���˸÷���");
		}
		return sum;

	}

	// �����˻�����ѯ�˻�
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

	// �޸��˻�
	public void changeAccount(String oldName, Account newAccount) {
		int id = findIdByName(oldName);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", newAccount.getName());
		values.put("price", newAccount.getPrice());
		db.update("tbl_account", values, "_id=?", new String[] { id + "" });
		Log.i("AccountDao", "�޸����ݳɹ�");
	}

	// �޸��ֽ�
	public void changeXianjinAccount(String name, Account account) {
		int id = findIdByName(name);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		// values.put("name", newUser.getName());
		values.put("price", account.getPrice());
		db.update("tbl_account", values, "_id=?", new String[] { id + "" });
		Log.i("AccountDao", "�޸����ݳɹ�");
		Log.i("AccountDao", id + ":" + account.getPrice());
	}

}
