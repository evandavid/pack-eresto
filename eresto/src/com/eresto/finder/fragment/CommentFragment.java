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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

public class CommentFragment extends Fragment {
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
	private WebView webDisqus;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);
        convert = new ConvertStreamToString();
    }
    
    public void goBack(){
    	webDisqus.goBack();
    }
    
    public boolean canGoBack(){
    	return webDisqus.canGoBack();
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_comment, container,false);
        tv = (TextView)v.findViewById(R.id.ops);
        lv = (ListView)v.findViewById(R.id.listView1);
        webDisqus = (WebView)v.findViewById(R.id.web);
      //set up disqus
		WebSettings webSettings2 = webDisqus.getSettings();
		 
		webSettings2.setJavaScriptEnabled(true);
		 
		webSettings2.setBuiltInZoomControls(true);
		 
		webDisqus.requestFocusFromTouch();
		 
		webDisqus.setWebViewClient(new WebViewClient());
		 
		webDisqus.setWebChromeClient(new WebChromeClient());
		 
		webDisqus.loadUrl("http://beta.eresto.co.id/api/disqus2.php?id="+id_resto);
        hasNetwork = haveNetworkConnection2();
        this.id_resto = getArguments().getString("id_resto");
        return v;
    }
    
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(event.getAction() == KeyEvent.ACTION_DOWN){
//            switch(keyCode)
//            {
//            case KeyEvent.KEYCODE_BACK:
//            	if(webDisqus.canGoBack())
//                    webDisqus.goBack();
//                return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	
	    if (isVisibleToUser) {
	    	 if(hasNetwork){  
	    		 
	    		 
	         }else{
//	         	lv.setVisibility(View.GONE);
//	         	tv.setVisibility(View.VISIBLE);
//	         	tv.setText("Ops, you don't have any internet connection");
	         }
	    }

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
