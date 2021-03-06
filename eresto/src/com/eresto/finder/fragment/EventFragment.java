package com.eresto.finder.fragment;

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

import com.eresto.finder.DashboardActivity;
import com.eresto.finder.R;
import com.eresto.finder.SplashActivity;
import com.eresto.finder.adapter.EventAdapter;
import com.eresto.finder.adapter.HomeAdapter;
import com.eresto.finder.model.Resto;
import com.eresto.utils.ConvertStreamToString;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class EventFragment extends Fragment {
	@SuppressWarnings("unused")
	private int mCurrentPage;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);
        convert = new ConvertStreamToString();
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_event_list, container,false);
        tv = (TextView)v.findViewById(R.id.ops);
        lv = (ListView)v.findViewById(R.id.listView1);
        hasNetwork = haveNetworkConnection2();
        this.id_resto = getArguments().getString("id_resto");
        return v;
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	
	    if (isVisibleToUser) {
	    	 if(hasNetwork){  
 	        	loading = ProgressDialog.show(getActivity(), "", "Downloading data, please wait..", true);
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
	    }

    }
    
    public void getData(){
    	JSONArray json;
    	JSONObject object;
    	HttpClient httpclient = new DefaultHttpClient();    
    	String url = "http://beta.eresto.co.id/api/getEvent.json?id="+id_resto;
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
		        			resto = new Resto(getActivity());
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
    	this.adapter = new EventAdapter(getActivity(), this.data);
    	lv.setAdapter(adapter);
    	loading.dismiss();
    }
    
    public boolean haveNetworkConnection2() {
    	boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
        	ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    
}
