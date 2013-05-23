package com.eresto.finder;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eresto.utils.ConvertStreamToString;
import com.eresto.utils.CurrentCityDb;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2500;
	private CurrentCityDb dbcity;
	private SQLiteDatabase db = null;
	private String city;
	private String status;
	private ProgressDialog loading;
	private ConvertStreamToString convert;
	private String[][] resto, menu, photo, feature;
	private String[] resto_field, menu_field, photo_field, feature_field;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(icicle);
	    setContentView(R.layout.activity_splash);
	    this.convert = new ConvertStreamToString();
	    this.dbcity = new CurrentCityDb(this);
        this.db = dbcity.getWritableDatabase();
        this.city = dbcity.getCity(db);
        this.status = dbcity.getStatus(db);
        if (this.city == null){
        	this.dbcity.saveData(db, "Bandung");
        }
        if (this.status == null){
        	this.dbcity.saveStatus(db, "False");
        }
        this.status = dbcity.getStatus(db);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        if (this.status.equals("False")) {
            //first run
        	this.loading = ProgressDialog
    				.show(this, "", "Fetch data for the first time, please wait..", true);
        	this.loading.setCancelable(false);
        	new Thread(new Runnable() {
    			public void run() {
    				getData();
    			} 		
    		}).start();
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
	
	public void getData(){
		JSONArray Jresto, Jmenu, Jphoto, Jfeature;
    	JSONObject object;
    	HttpClient httpclient = new DefaultHttpClient();    
    	String url = "http://beta.eresto.co.id/api/getFirst.json";
    	HttpGet httppost = new HttpGet(url);
        try {                             
	         HttpResponse response = httpclient.execute(httppost);
	         if (response.getStatusLine().getStatusCode() == 200){
		         HttpEntity entity = response.getEntity();
		         if (entity != null) {
		        	 InputStream instream = entity.getContent();
		        	 String result = this.convert.doConvert(instream);
		        	 instream.close();
		        	 //parse data
		        	 object= new JSONObject(result);
		        	 Jresto = object.getJSONArray("resto");
		        	 Jmenu = object.getJSONArray("menu");
		        	 Jphoto = object.getJSONArray("photo");
		        	 Jfeature = object.getJSONArray("feature");
		        	 
		        	 this.resto= new String[Jresto.length()][18];
		        	 for(int i = 0; i < Jresto.length(); i++){
		        		 this.resto[i][0] = Jresto.getJSONObject(i).getString("id_resto");
		        		 this.resto[i][1] = Jresto.getJSONObject(i).getString("resto_nama");
		        		 this.resto[i][2] = Jresto.getJSONObject(i).getString("resto_latt");
		        		 this.resto[i][3] = Jresto.getJSONObject(i).getString("resto_long");
		        		 this.resto[i][4] = Jresto.getJSONObject(i).getString("resto_like");
		        		 this.resto[i][5] = Jresto.getJSONObject(i).getString("resto_telp");
		        		 this.resto[i][6] = Jresto.getJSONObject(i).getString("resto_harga1");
		        		 this.resto[i][7] = Jresto.getJSONObject(i).getString("resto_harga2");
		        		 this.resto[i][8] = Jresto.getJSONObject(i).getString("resto_alamat");
		        		 this.resto[i][9] = Jresto.getJSONObject(i).getString("resto_fb");
		        		 this.resto[i][10] = Jresto.getJSONObject(i).getString("resto_twitter");
		        		 this.resto[i][11] = Jresto.getJSONObject(i).getString("resto_thumb");
		        		 this.resto[i][12] = Jresto.getJSONObject(i).getString("resto_email");
		        		 this.resto[i][13] = Jresto.getJSONObject(i).getString("resto_web");
		        		 this.resto[i][14] = Jresto.getJSONObject(i).getString("resto_jamb");
		        		 this.resto[i][15] = Jresto.getJSONObject(i).getString("resto_jamt");
		        		 this.resto[i][16] = Jresto.getJSONObject(i).getString("nama_kota");
		        		 this.resto[i][17] = Jresto.getJSONObject(i).getString("kategori");
		             }
		        	 this.resto_field = new String[18];
		        	 this.resto_field[0] = "id_resto";
		        	 this.resto_field[1] = "resto_nama";
		        	 this.resto_field[2] = "resto_latt";
		        	 this.resto_field[3] = "resto_long";
		        	 this.resto_field[4] = "resto_like";
		        	 this.resto_field[5] = "resto_telp";
		        	 this.resto_field[6] = "resto_harga1";
		        	 this.resto_field[7] = "resto_harga2";
		        	 this.resto_field[8] = "resto_alamat";
		        	 this.resto_field[9] = "resto_fb";
		        	 this.resto_field[10] = "resto_twitter";
		        	 this.resto_field[11] = "resto_thumb";
		        	 this.resto_field[12] = "resto_email";
		        	 this.resto_field[13] = "resto_web";
		        	 this.resto_field[14] = "resto_jamb";
		        	 this.resto_field[15] = "resto_jamt";
		        	 this.resto_field[16] = "nama_kota";
		        	 this.resto_field[17] = "kategori";
		        	 
		        	 this.photo= new String[Jphoto.length()][2];
		        	 for(int i = 0; i < Jphoto.length(); i++){
		        		 this.photo[i][0] = Jphoto.getJSONObject(i).getString("id_resto");
		        		 this.photo[i][1] = Jphoto.getJSONObject(i).getString("url");
		             }
		        	 this.photo_field = new String[2];
		        	 this.photo_field[0] = "id_resto";
		        	 this.photo_field[1] = "url";
		        	 
		        	 this.menu= new String[Jmenu.length()][5];
		        	 for(int i = 0; i < Jmenu.length(); i++){
		        		 this.menu[i][0] = Jmenu.getJSONObject(i).getString("id_menu");
		        		 this.menu[i][1] = Jmenu.getJSONObject(i).getString("id_resto");
		        		 this.menu[i][2] = Jmenu.getJSONObject(i).getString("menu_nama");
		        		 this.menu[i][3] = Jmenu.getJSONObject(i).getString("menu_harga");
		        		 this.menu[i][4] = Jmenu.getJSONObject(i).getString("menu_thumb");
		        		 
		             }
		        	 this.menu_field = new String[5];
		        	 this.menu_field[0] = "id_menu";
		        	 this.menu_field[1] = "id_resto";
		        	 this.menu_field[2] = "menu_nama";
		        	 this.menu_field[3] = "menu_harga";
		        	 this.menu_field[4] = "menu_thumb";
		        	 
		        	 this.feature= new String[Jfeature.length()][3];
		        	 for(int i = 0; i < Jfeature.length(); i++){
		        		 this.feature[i][0] = Jfeature.getJSONObject(i).getString("id");
		        		 this.feature[i][1] = Jfeature.getJSONObject(i).getString("id_resto");
		        		 this.feature[i][2] = Jfeature.getJSONObject(i).getString("orders");
		             }
		        	 this.feature_field = new String[3];
		        	 this.feature_field[0] = "id";
		        	 this.feature_field[1] = "id_resto";
		        	 this.feature_field[2] = "orders";
		        	 
		        	 this.dbcity.saveDataBulk(db, this.resto, this.resto_field, "resto");
		        	 this.dbcity.saveDataBulk(db, this.menu, this.menu_field, "menu");
		        	 this.dbcity.saveDataBulk(db, this.photo, this.photo_field, "photo");
		        	 this.dbcity.saveDataBulk(db, this.feature, this.feature_field, "feature");
		        	 this.dbcity.updateStatus(db, "True");
		        	 
		        	 myHandler.post(updateSuccess);
		         }
	         }else if (response.getStatusLine().getStatusCode() == 404){
	        	 myHandler.post(updateError);
	         }else 
	        	 myHandler.post(updateError);
         } 
        catch (ClientProtocolException e) {       
         } 
        catch (IOException e){         
          } catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private Handler myHandler = new Handler();
	
	final Runnable updateSuccess = new Runnable() {
        public void run() {
            success();
        }
    };
    
    final Runnable updateError = new Runnable() {
        public void run() {
            error();
        }
    };
    
    public void success(){
    	this.loading.dismiss();
    	Toast.makeText( this, "success fetching data, enjoy the application", Toast.LENGTH_LONG ).show();
    	Intent mainIntent = new Intent(SplashActivity.this,DashboardActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        dbcity.close();
        SplashActivity.this.finish();
    }
    
    public void error(){
    	this.loading.dismiss();
    	Toast.makeText( this, "error to fetch data, please restart the application", Toast.LENGTH_LONG ).show();
    	this.finish();
    }
}
