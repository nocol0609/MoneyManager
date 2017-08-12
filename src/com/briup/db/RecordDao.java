package com.briup.db;

import java.util.ArrayList;
import java.util.List;

import com.briup.bean.Record;
import com.briup.bean.User;
import com.briup.moneymanager.ZhichuActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecordDao {
	private SqliteHelper helper;
	private SQLiteDatabase db;

	public RecordDao(Context context) {
		helper = new SqliteHelper(context);
	}

	// 添加记录
	public void addRecord(Record record) {
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("type", record.getType());
		values.put("content", record.getContent());
		values.put("money", record.getMoney());
		values.put("date", record.getDate());
		db.insert("tbl_record", null, values);
		Log.i("RecordDao", "插入数据成功");

	}

	// 查询所有记录
	public List<Record> findAllRecords() {
		List<Record> list = new ArrayList<Record>();
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			float money = cursor.getFloat(cursor.getColumnIndex("money"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Record record = new Record(type, content, money, date);
			list.add(record);

		}
		return list;
	}

	// 根据记录类型查询记录的ID
	public int findIdByType(String type) {
		int id = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", new String[] { "_id" }, "name=?", new String[] { type }, null, null,
				null);
		while (cursor.moveToNext()) {
			id = cursor.getInt(cursor.getColumnIndex("_id"));
		}

		return id;

	}

	// 根据记录类型查询记录
	public List<Record> findRecordsByType(String type) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", null, "type=?", new String[] { type }, null, null, null);
		List<Record> list = new ArrayList<Record>();
		while (cursor.moveToNext()) {
			String record_type = cursor.getString(1);
			String record_content = cursor.getString(2);
			float record_money = cursor.getFloat(3);
			String record_date = cursor.getString(4);
			Record record = new Record(record_type, record_content, record_money, record_date);
			list.add(record);
		}
		return list;

	}

	// 实现对插入数据库中type=收入的money数据的求和
	public float findShouruMoney() {
		float Shourusum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", new String[] { "money" }, "type=?", new String[] { "收入" }, null, null,
				null);
		while (cursor.moveToNext()) {
			Shourusum = Shourusum + cursor.getFloat(cursor.getColumnIndex("money"));
			Log.i("RecordDao", "找到所有收入数据的和");
		}
		return Shourusum;
	}

	// 实现对插入数据库中type=支出的money数据的求和
	public float findZhichuMoney() {
		float Zhichusum = 0;
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", new String[] { "money" }, "type=?", new String[] { "支出" }, null, null,
				null);
		while (cursor.moveToNext()) {
			Zhichusum = Zhichusum + cursor.getFloat(cursor.getColumnIndex("money"));
			Log.i("RecordDao", "找到所有支出数据的和");
		}
		return Zhichusum;
	}

	// 根据记录类型和用于哪方面查询记录
	public Record findRecordByTypeAndContent(String type, String content) {
		db = helper.getReadableDatabase();
		Cursor cursor = db.query("tbl_record", null, "type=? and content=?", new String[] { type, content }, null, null,
				null);
		// List<User> list=new ArrayList<User>();
		Record record = null;
		while (cursor.moveToNext()) {
			String record_type1 = cursor.getString(1);
			String record_content1 = cursor.getString(2);
			float record_money1 = cursor.getFloat(3);
			String record_date1 = cursor.getString(4);
			record = new Record(record_type1, record_content1, record_money1, record_date1);

		}
		return record;
	}
}
