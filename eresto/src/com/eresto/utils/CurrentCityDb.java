package com.eresto.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CurrentCityDb extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "dberestoxx";
	//table name
	public static final String DATABASE_TABLE = "user";
	public static final String DATABASE_TABLE_RESTO = "resto";
	public static final String DATABASE_TABLE_PHOTO = "photo";
	public static final String DATABASE_TABLE_MENU = "menu";
	public static final String DATABASE_TABLE_FEATURE = "feature";
	
	//field
	public static final String RESTO_FIELD = "id_resto TEXT,resto_nama TEXT,resto_latt TEXT,resto_long TEXT,resto_like TEXT,resto_telp TEXT,resto_harga1 TEXT,resto_harga2 TEXT,resto_alamat TEXT,resto_fb TEXT,resto_twitter TEXT,resto_thumb TEXT,resto_email TEXT,resto_web TEXT,resto_jamb TEXT,resto_jamt TEXT,nama_kota TEXT,kategori TEXT";
	public static final String MENU_FIELD = "id_menu TEXT,id_resto TEXT,menu_nama TEXT,menu_harga TEXT,menu_thumb TEXT";
	public static final String PHOTO_FIELD = "id_resto TEXT, url TEXT";
	public static final String FEATURE_FIELD = "id TEXT, id_resto TEXT, orders TEXT";
	public static final String USER_FIELD = "ids TEXT, city TEXT";
	
	public CurrentCityDb(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	public void createTable(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE if not exists "+DATABASE_TABLE+" ("+USER_FIELD+")");
		db.execSQL("CREATE TABLE if not exists "+DATABASE_TABLE_RESTO+" ("+RESTO_FIELD+")");
		db.execSQL("CREATE TABLE if not exists "+DATABASE_TABLE_PHOTO+" ("+PHOTO_FIELD+")");
		db.execSQL("CREATE TABLE if not exists "+DATABASE_TABLE_MENU+" ("+MENU_FIELD+")");
		db.execSQL("CREATE TABLE if not exists "+DATABASE_TABLE_FEATURE+" ("+FEATURE_FIELD+")");
		db.execSQL("CREATE TABLE if not exists datas (status TEXT, ids TEXT)");
	}
	
	public void saveDataBulk(SQLiteDatabase db, String[][] data, String[] field, String database){
		for (int i = 0; i < data.length; i++) {
			ContentValues values = new ContentValues();
			for (int j = 0; j < field.length; j++) {
				values.put(field[j], data[i][j]);
			}
			db.insert(database, null, values);
		}
	}
	
	public void saveStatus(SQLiteDatabase db, String status){
		ContentValues values = new ContentValues();
        values.put("status", status);
        values.put("ids", "1");
        db.insert("datas", null, values);
	}
	
	public void updateStatus(SQLiteDatabase db, String status){
		ContentValues value = new ContentValues();
		String where = "ids=?";
		String[] whereArgs = new String[] {"1"};
        value.put("status", status);
        db.update("datas", value, where, whereArgs);
	}
	
	public Cursor getAllResto(SQLiteDatabase db, String limit){
		Cursor mCount = db.rawQuery("SELECT * FROM "+DATABASE_TABLE_RESTO+" order by cast(id_resto as int) ASC LIMIT 10 OFFSET "+limit, null);
		return mCount;
	}
	
	public Cursor getRestoMenu(SQLiteDatabase db, String idresto){
		Cursor mCount = db.rawQuery("SELECT * FROM "+DATABASE_TABLE_MENU+" where id_menu IS NOT NULL and id_resto= "+idresto+" order by cast(id_menu as int) ASC", null);
		return mCount;
	}
	
	public Cursor getRestoPhoto(SQLiteDatabase db, String idresto){
		Cursor mCount = db.rawQuery("SELECT * FROM "+DATABASE_TABLE_PHOTO+" where id_resto IS NOT NULL and id_resto= "+idresto, null);
		return mCount;
	}

	public void saveData(SQLiteDatabase db, String city) {
		ContentValues values = new ContentValues();
        values.put("city", city);
        values.put("ids", "1");
        db.insert("user", null, values);
	}
	
	public void update(SQLiteDatabase db, String city){
		ContentValues value = new ContentValues();
		String where = "ids=?";
		String[] whereArgs = new String[] {"1"};
        value.put("city", city);
        db.update(DATABASE_TABLE, value, where, whereArgs);
	}
	
	public String getCity(SQLiteDatabase db){
		Cursor mCount= db.rawQuery("select city from user", null);
		mCount.moveToFirst();
		String count;
		try {
			count= mCount.getString(0);
			mCount.close();
		} catch (Exception e) {
			count = "";
		}
		
		if(count != ""){
			return count;
		}else{
			return null;
		}
	}
	
	public String getStatus(SQLiteDatabase db){
		Cursor mCount= db.rawQuery("select status from datas", null);
		mCount.moveToFirst();
		String count;
		try {
			count= mCount.getString(0);
			mCount.close();
		} catch (Exception e) {
			count = "";
		}
		
		if(count != ""){
			return count;
		}else{
			return null;
		}
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
