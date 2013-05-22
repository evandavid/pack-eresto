package com.eresto.finder.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eresto.finder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
	private int mCurrentPage;
	private LinearLayout body;
	private ScrollView _scroll;
	private String number;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private GoogleMap map;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_map_fragment, container,false);
//        TextView _number = (TextView)v.findViewById(R.id.telephone);
//        number = _number.getText().toString().replace("-", "");
////        RelativeLayout map = (RelativeLayout)v.findViewById(R.id.map);
////        map.setVisibility(View.GONE);
//        //call
//        ImageView tel_logo = (ImageView)v.findViewById(R.id.telephone_logo);
//        tel_logo.setOnClickListener(telephoneListener);
//        _number.setOnClickListener(telephoneListener);
        if (haveNetworkConnection()){
//        	WebView wv = (WebView)v.findViewById(R.id.map);
        	Fragment fragment = getFragmentManager().findFragmentById(R.id.map);
            SupportMapFragment mapFragment = (SupportMapFragment)fragment;
            map = mapFragment.getMap();
//        	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
////        	        .getMap();
        	    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
        	        .title("Hamburg"));
        	    Marker kiel = map.addMarker(new MarkerOptions()
        	        .position(KIEL)
        	        .title("Kiel")
        	        .snippet("Kiel is cool")
        	        .icon(BitmapDescriptorFactory
        	            .fromResource(R.drawable.ic_launcher)));

        	    // Move the camera instantly to hamburg with a zoom of 15.
        	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        	    // Zoom in, animating the camera.
        	    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
            TextView tv = (TextView)v.findViewById(R.id.des);
            tv.setVisibility(View.GONE);
//            map.setVisibility(View.VISIBLE);
//            wv.loadUrl("file:///android_asset/index.html");
//            wv.loadUrl("javascript:alert('functionThatReturnsSomething')");
        }
        return v;
    }
    
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

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
    }
}
