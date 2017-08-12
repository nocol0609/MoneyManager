package com.briup.db;

import java.util.ArrayList;
import java.util.List;

import com.briup.bean.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDao {
	private SqliteHelper helper;
	private SQLiteDatabase db;

	public UserDao(Context context) {
		helper = new SqliteHelper(context);
	}

	// ����û���ע�ᣩ
	public void addUser(User user) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", user.getName());
		values.put("passwd", user.getPasswd());
		db.insert("tbl_user", null, values);
		Log.i("UserDao", "�������ݳɹ�");

	}

	// ��ѯ�����û�
	public List<User> findAllUser() {
		List<User> list = new ArrayList<User>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_user", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String passwd = cursor.getString(cursor.getColumnIndex("passwd"));
			// String
			// querenpasswd=cursor.getString(cursor.getColumnIndex("querenpasswd"));
			User user = new User(name, passwd);
			list.add(user);

		}
		return list;
	}

	// �����û�����ѯ�û���ID
	public int findIdByName(String name) {
		int id = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_user", new String[] { "_id" }, "name=?", new String[] { name }, null, null, null);
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("_id"));
		}

		return id;

	}

	// �����û�����ѯ�û�������
	public String findPasswdByName(String name) {
		String passwd = null;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_user", new String[] { "passwd" }, "name=?", new String[] { name }, null, null,
				null);
		while (cursor.moveToNext()) {
			passwd = cursor.getString(cursor.getColumnIndex("passwd"));
		}
		return passwd;

	}

	// �����û�����ѯ�û�
	public List<User> findUsersByName(String name) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_user", null, "name=?", new String[] { name }, null, null, null);
		List<User> list = new ArrayList<User>();
		while (cursor.moveToNext()) {
			String user_name = cursor.getString(1);
			String user_passwd = cursor.getString(2);
			// String user_querenpasswd=cursor.getString(3);
			User user = new User(user_name, user_passwd);
			list.add(user);
		}
		return list;

	}

	// �����û����������ѯ�û�
	public User findUserByNameAndPasswd(String name, String passwd) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_user", null, "name=? and passwd=?", new String[] { name, passwd }, null, null,
				null);
		// List<User> list=new ArrayList<User>();
		User user = null;
		while (cursor.moveToNext()) {
			String user_name1 = cursor.getString(1);
			String user_passwd = cursor.getString(2);
			user = new User(user_name1, user_passwd);
			// list.add(user);
		}
		return user;
	}

	// �޸�����
	public void changeUser(String name, User newUser) {
		int id = findIdByName(name);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		// values.put("name", newUser.getName());
		values.put("passwd", newUser.getPasswd());
		db.update("tbl_user", values, "_id=?", new String[] { id + "" });
		Log.i("UserDao", "�޸����ݳɹ�");
		Log.i("UserDao", id + ":" + newUser.getPasswd());
	}

	// �޸��û���
	public void changeUserName(String name, User newUser) {
		int id = findIdByName(name);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", newUser.getName());
		// values.put("passwd", newUser.getPasswd());
		db.update("tbl_user", values, "_id=?", new String[] { id + "" });
		Log.i("UserDao", "�޸��û����ɹ�");
		Log.i("UserDao", id + ":" + newUser.getName());
	}

	// ɾ���û�
	public void deleteBook(String name) {
		db = helper.getWritableDatabase();
		db.delete("tbl_user", "name=?", new String[] { name });
		Log.i("UserDao", "ɾ�����ݳɹ�");
	}

}
