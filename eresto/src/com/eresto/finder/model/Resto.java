package com.eresto.finder.model;

import com.eresto.utils.CurrentCityDb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Resto {
	 public Menu[] menu;
	 public Photo[] photo;
	 public String id_resto;
	 public String resto_nama;
	 public String resto_latt;
	 public String resto_long;  
	 public String resto_like;
	 public String resto_telp;
	 public String resto_harga1;
	 public String resto_harga2;
	 public String resto_alamat;
	 public String resto_fb;
	 public String resto_twitter;
	 public String resto_thumb;
	 public String resto_email;
	 public String resto_web;
	 public String resto_jamb;
	 public String resto_jamt;
	 public String nama_kota;
	 public String kategori;
	 public Context context;
	 private CurrentCityDb dbhelper;
	 private SQLiteDatabase db = null;
	 
	 public Resto(Context context){
		 this.context = context;
	 }
	 
	 public Resto(){}
	 
	 public Resto[] getAllResto(String limit){
		 dbhelper = new CurrentCityDb(this.context);
	     db = dbhelper.getWritableDatabase();
	     Cursor cursor = dbhelper.getAllResto(db, limit);
	     Resto[] resto = construtResto(cursor);
	     return resto;
	 }
	 
	 public Resto[] getAllFeatureResto(){
		 dbhelper = new CurrentCityDb(this.context);
	     db = dbhelper.getWritableDatabase();
	     Cursor cursor = dbhelper.getAllFeatureResto(db);
	     Resto[] resto = construtResto(cursor);
	     return resto;
	 }
	 
	 public Resto[] getRandomResto(){
		 dbhelper = new CurrentCityDb(this.context);
	     db = dbhelper.getWritableDatabase();
	     Cursor cursor = dbhelper.getRandomResto(db);
	     Resto[] resto = construtResto(cursor);
	     return resto;
	 }
	 
	 public Resto[] getRandomRestoCity(){
		 dbhelper = new CurrentCityDb(this.context);
	     db = dbhelper.getWritableDatabase();
	     String city = dbhelper.getCity(db);
	     Cursor cursor = dbhelper.getRandomRestoCity(db, city);
	     Resto[] resto = construtResto(cursor);
	     return resto;
	 }
	 
	 private Resto[] construtResto(Cursor cursor){
		 Resto[] resto = new Resto[cursor.getCount()];
		 int i = 0;
		 if (cursor.moveToFirst()){
    	   do{
    		  Resto tmpr = new Resto();
    		  tmpr.id_resto = cursor.getString(cursor.getColumnIndex("id_resto"));
    		  tmpr.resto_nama = cursor.getString(cursor.getColumnIndex("resto_nama"));
    		  tmpr.resto_latt = cursor.getString(cursor.getColumnIndex("resto_latt"));
    		  tmpr.resto_long = cursor.getString(cursor.getColumnIndex("resto_long"));
    		  tmpr.resto_like = cursor.getString(cursor.getColumnIndex("resto_like"));
    		  tmpr.resto_telp = cursor.getString(cursor.getColumnIndex("resto_telp"));
    		  tmpr.resto_harga1 = cursor.getString(cursor.getColumnIndex("resto_harga1"));
    		  tmpr.resto_harga2 = cursor.getString(cursor.getColumnIndex("resto_harga2"));
    		  tmpr.resto_alamat = cursor.getString(cursor.getColumnIndex("resto_alamat"));
    		  tmpr.resto_fb = cursor.getString(cursor.getColumnIndex("resto_fb"));
    		  tmpr.resto_twitter = cursor.getString(cursor.getColumnIndex("resto_twitter"));
    		  tmpr.resto_thumb = cursor.getString(cursor.getColumnIndex("resto_thumb"));
    		  tmpr.resto_email = cursor.getString(cursor.getColumnIndex("resto_email"));
    		  tmpr.resto_web = cursor.getString(cursor.getColumnIndex("resto_web"));
    		  tmpr.resto_jamb = cursor.getString(cursor.getColumnIndex("resto_jamb"));
    		  tmpr.resto_jamt = cursor.getString(cursor.getColumnIndex("resto_jamt"));
    		  tmpr.nama_kota = cursor.getString(cursor.getColumnIndex("nama_kota"));
    		  tmpr.kategori = cursor.getString(cursor.getColumnIndex("kategori"));
    		  Cursor menu = dbhelper.getRestoMenu(db, tmpr.id_resto);
    		  if (menu != null){
	    		  tmpr.menu = new Menu[menu.getCount()];
	    		  int j = 0;
	    		  if (menu.moveToFirst()){
	    			  do{
	    				  Menu tmpm = new Menu();
	    				  tmpm.id_menu = menu.getString(menu.getColumnIndex("id_menu"));
	    				  tmpm.id_resto = menu.getString(menu.getColumnIndex("id_resto"));
	    				  tmpm.menu_harga = menu.getString(menu.getColumnIndex("menu_harga"));
	    				  tmpm.menu_nama = menu.getString(menu.getColumnIndex("menu_nama"));
	    				  tmpm.menu_thumb = menu.getString(menu.getColumnIndex("menu_thumb"));
	    				  tmpr.menu[j] = tmpm;
	    				  j = j + 1;
	    			  }while(menu.moveToNext());
	    		  } 
    		  }
    		  
    		  Cursor photo = dbhelper.getRestoPhoto(db, tmpr.id_resto);
    		  if (photo != null){
	    		  tmpr.photo = new Photo[photo.getCount()];
	    		  int k = 0;
	    		  if (photo.moveToFirst()){
	    			  do{
	    				  Photo tmpp = new Photo();
	    				  tmpp.id_resto = photo.getString(photo.getColumnIndex("id_resto"));
	    				  tmpp.url = photo.getString(photo.getColumnIndex("url"));
	    				  tmpr.photo[k] = tmpp;
	    				  k = k + 1;
	    			  }while(photo.moveToNext());
	    		  } 
    		  }
    		  resto[i] = tmpr;
    		  i = i + 1;
    	      // do what ever you want here
    	   }while(cursor.moveToNext());
    	 } 
		 return resto;
	 }
	 
	 

}
