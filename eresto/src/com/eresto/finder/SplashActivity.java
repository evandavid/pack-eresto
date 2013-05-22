package com.eresto.finder;

import com.eresto.utils.CurrentCityDb;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.Window;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2500;
	SharedPreferences prefs = null;
	private CurrentCityDb dbcity;
	private SQLiteDatabase db = null;
	private String city;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_splash);
	    prefs = getSharedPreferences("com.eresto.finder", MODE_PRIVATE);
	    dbcity = new CurrentCityDb(this);
        db = dbcity.getWritableDatabase();
        city = dbcity.getCity(db);
        if (city != null){
        	dbcity.saveData(db, "Jakarta");
        }
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        /** check is first run
         first run == true, run help activity **/
        if (prefs.getBoolean("firstrun", true)) {
            /** Do first run stuff here then set 'firstrun' as false
             using the following line to edit/commit prefs **/
            prefs.edit().putBoolean("firstrun", false).commit();
        }else{
        	/** New Handler to start the DashboardActivity 
    	      and close this Splash-Screen after some seconds.**/
    	    new Handler().postDelayed(new Runnable(){
    	        public void run() {    
    	        	Intent mainIntent = new Intent(SplashActivity.this,DashboardActivity.class);
    	            SplashActivity.this.startActivity(mainIntent);
    	            dbcity.close();
    	            SplashActivity.this.finish();
    	        }
    	    }, SPLASH_DISPLAY_LENGHT);
        }
    }
}
