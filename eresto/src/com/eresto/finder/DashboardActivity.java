package com.eresto.finder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eresto.finder.tab.activity.HomeActivity;
import com.eresto.finder.tab.activity.RestaurantListActivity;
import com.eresto.utils.ConvertStreamToString;
import com.eresto.utils.CurrentCityDb;
import com.eresto.utils.GPSTracker;

import android.os.Bundle;
import android.os.Handler;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class DashboardActivity extends TabActivity {
	private static final LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0.5f);

    private static TabHost tabHost;
    private static TabHost.TabSpec spec;
    private static Intent intent;
    private static LayoutInflater inflater;
    private View tab;
    private TextView label;
    private TextView divider;
    private double latitude = 0;
    private double longitude = 0;
    private String city;
    private CurrentCityDb dbcity;
	private SQLiteDatabase db = null;
	private boolean firstRun = true;
    
    private final Handler myHandler = new Handler();
    final Runnable updateRunnable = new Runnable() {
        public void run() {
            updateCity();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dbcity = new CurrentCityDb(this);
        db = dbcity.getWritableDatabase();
        
        // Get inflator so we can start creating the custom view for tab
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Get tab manager
        tabHost = getTabHost();
        
        // This converts the custom tab view we created and injects it into the tab widget
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        tab.setLayoutParams(params);
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("home");
        divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
        divider.setVisibility(View.VISIBLE);
        intent = new Intent(this, HomeActivity.class);
        intent.putExtra("username", "extra");
        spec = tabHost.newTabSpec("home").setIndicator(tab).setContent(intent);
        tabHost.addTab(spec);
        
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        tab.setLayoutParams(params);
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("restaurants");
        divider = (TextView) tab.findViewById(R.id.tabSelectedDivider);
        intent = new Intent(this, RestaurantListActivity.class);
        intent.putExtra("username", "extra");
        spec = tabHost.newTabSpec("restaurants").setIndicator(tab).setContent(intent);
        tabHost.addTab(spec);
        
        
        // Add another tab
        tab = inflater.inflate(R.layout.tab, getTabWidget(), false);
        tab.setLayoutParams(params);
        label = (TextView) tab.findViewById(R.id.tabLabel);
        label.setText("events &promos");
        divider = (TextView) tab.findViewById(R.id.tabSplitter);
        divider.setVisibility(View.GONE);
        intent = new Intent(this, HomeActivity.class);
        intent.putExtra("content", "member");
        spec = tabHost.newTabSpec("promos").setIndicator(tab).setContent(intent);
        tabHost.addTab(spec);
        
        
        // Listener to detect when a tab has changed. I added this just to show 
        // how you can change UI to emphasize the selected tab
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tag) {
                // reset some styles
                clearTabStyles();
                View tabView = null;
                // Use the "tag" for the tab spec to determine which tab is selected
                if (tag.equals("home")) {
                    tabView = getTabWidget().getChildAt(0);
                }
                else if (tag.equals("restaurants")) {
                    tabView = getTabWidget().getChildAt(1);
                }
                else if (tag.equals("promos")) {
                    tabView = getTabWidget().getChildAt(2);
                }
                tabView.findViewById(R.id.tabSelectedDivider).setVisibility(View.VISIBLE);
            }       
        });
    }
    
    private void clearTabStyles() {
        for (int i = 0; i < getTabWidget().getChildCount(); i++) {
            tab = getTabWidget().getChildAt(i);
            tab.findViewById(R.id.tabSelectedDivider).setVisibility(View.GONE);
        }
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onResume(){
    	super.onResume();
	    	GPSTracker gps = new GPSTracker(DashboardActivity.this);
			// check if GPS enabled
	        if(gps.canGetLocation()){
	        	latitude = gps.getLatitude();
	        	longitude = gps.getLongitude();
	        	new Thread(new Runnable() {
	    			public void run() {
	    				getCity();
	    			} 		
	    		}).start();
	        }else{
	        	// can't get location
	        	// GPS or Network is not enabled
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
	        }
    }
    
    public void f(){
    }
    
    
    final Runnable updateRunnable3 = new Runnable() {
        public void run() {
        	f();
        }
    };
    
    public void getCity(){
    	if(longitude != 0) {
	    	JSONObject object;
	    	HttpClient httpclient = new DefaultHttpClient();    
	    	HttpPost httppost = new HttpPost("http://maps.google.com/maps/geo?q="+latitude+","+longitude+"&output=json&oe=utf8");
	        try {               
		         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);        
		
		         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));               
		         HttpResponse response = httpclient.execute(httppost); 
		         
		         if (response.getStatusLine().getStatusCode() == 200){
			         HttpEntity entity = response.getEntity();
			         if (entity != null) {
			        	 InputStream instream = entity.getContent();
			        	 ConvertStreamToString convert = new ConvertStreamToString();
			        	 String result = convert.doConvert(instream);
			        	 instream.close();
			        	 //parse data
			        	 object= new JSONObject(result);
			        	 JSONObject status = object.getJSONObject("Status");
			        	 
			        	 if(status.getInt("code") == 200){
			        		 try {
								JSONArray Placemark = object.getJSONArray("Placemark");
								JSONObject AddressDetail = Placemark.getJSONObject(0).getJSONObject("AddressDetails");
								JSONObject country = AddressDetail.getJSONObject("Country");
								JSONObject AdministrativeArea = country.getJSONObject("AdministrativeArea");
								JSONObject SubAdministrativeArea = AdministrativeArea.getJSONObject("SubAdministrativeArea");
								city = SubAdministrativeArea.getString("SubAdministrativeAreaName");
							} catch (Exception e) {
								city = null;
							}
			        	 }
			        	 myHandler.post(updateRunnable);
			         }
		         }
	         } 
	        catch (ClientProtocolException e) {
	        	myHandler.post(updateRunnable3);
	        	
	         } 
	        catch (IOException e){         
	          } catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void updateCity(){
    	if(city != null){
    		dbcity.update(db, city);
    		Toast.makeText(this, "Your current city: "+city, Toast.LENGTH_LONG).show();
    	}
    }
    
    @Override
    public void onBackPressed(){
    	Intent startMain = new Intent(Intent.ACTION_MAIN);
    	startMain.addCategory(Intent.CATEGORY_HOME);
    	startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(startMain);
    }
}
