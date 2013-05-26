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

import com.eresto.finder.adapter.EventAdapter;
import com.eresto.finder.model.Resto;
import com.eresto.utils.ConvertStreamToString;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class EventActivity extends Activity {

	private ListView lv;
	private TextView tv;
	public boolean hasNetwork;
	public String id_resto;
	public ProgressDialog loading;
	public ConvertStreamToString convert;
	public String[][] data;
	public Resto resto;
	public EventAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		convert = new ConvertStreamToString();
		tv = (TextView)findViewById(R.id.ops);
        lv = (ListView)findViewById(R.id.listView1);
        hasNetwork = haveNetworkConnection2();
	}

	public void getData(){
    	JSONArray json;
    	JSONObject object;
    	HttpClient httpclient = new DefaultHttpClient();    
    	String url = "http://beta.eresto.co.id/api/getEvent.json";
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
		        	 json = object.getJSONArray("promo");
		        	 if (json.length() > 0){
		        		 this.data= new String[json.length()][7];
		        		 for (int i = 0; i < json.length(); i++) {
		        			resto = new Resto(this);
		        			resto = resto.getResto(json.getJSONObject(i).getString("resto_id_resto"));
							this.data[i][0] = json.getJSONObject(i).getString("promo_judul");
							this.data[i][1] = resto.resto_nama;
							this.data[i][2] = "http://dev.eresto.co.id/"+json.getJSONObject(i).getString("promo_poster");
							this.data[i][3] = json.getJSONObject(i).getString("promo_mulai");
							this.data[i][4] = json.getJSONObject(i).getString("promo_akhir");
							this.data[i][5] = json.getJSONObject(i).getString("promo_des");
							this.data[i][6] = json.getJSONObject(i).getString("promo_jenis");
						}
		        		 myHandler.post(updateSuccess);
		        	 }else{
		        		 myHandler.post(error);
		        	 }
		        	 
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
    
    final Runnable error = new Runnable() {
        public void run() {
            error_nil();
        }
    };
    
    final Runnable updateError = new Runnable() {
        public void run() {
            error();
        }
    };
    
    public void error(){
    	lv.setVisibility(View.GONE);
    	tv.setVisibility(View.VISIBLE);
    	tv.setText("Ops, something happened. Please try again later");
    	loading.dismiss();
    }
    
    public void error_nil(){
    	lv.setVisibility(View.GONE);
    	tv.setVisibility(View.VISIBLE);
    	tv.setText("Ops, there are no event and promo now");
    	loading.dismiss();
    }
    
    public void success(){
    	tv.setVisibility(View.GONE);
    	lv.setVisibility(View.VISIBLE);
    	this.adapter = new EventAdapter(this, this.data);
    	lv.setAdapter(adapter);
    	loading.dismiss();
    }
    
    public boolean haveNetworkConnection2() {
    	boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
        	ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;
		} catch (Exception e) {
			return false;
		}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
		return true;
	}
    
    @Override
    public void onResume(){
    	if(hasNetwork){  
        	loading = ProgressDialog.show(this, "", "Downloading data, please wait..", true);
        	loading.setCancelable(true);         	
	     	new Thread(new Runnable() {
	 			public void run() {
	 				getData();
	 			} 		
	 		}).start();
	     }else{
	     	lv.setVisibility(View.GONE);
	     	tv.setVisibility(View.VISIBLE);
	     	tv.setText("Ops, you don't have any internet connection");
	     }
	    	super.onResume();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_settings:
	    	onResume();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
    }
    
}
