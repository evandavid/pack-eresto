package com.eresto.finder.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eresto.finder.R;
import com.eresto.finder.model.Resto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
	@SuppressWarnings("unused")
	private int mCurrentPage;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static LatLng positions;
	private GoogleMap map;
	public String id_resto;
	public Resto resto;
	 
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
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((ViewGroup) v).addView(frameLayout,
            new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        this.id_resto = getArguments().getString("id_resto");
        this.resto = new Resto(getActivity());
        this.resto = this.resto.getResto(this.id_resto);
        
        if (haveNetworkConnection2()){
        	try {
        		Fragment fragment = getFragmentManager().findFragmentById(R.id.map);
                SupportMapFragment mapFragment = (SupportMapFragment)fragment;
                map = mapFragment.getMap();
                positions = new LatLng(Double.valueOf(this.resto.resto_latt), Double.valueOf(this.resto.resto_long));
            	    @SuppressWarnings("unused")
    				Marker hamburg = map.addMarker(new MarkerOptions().position(positions)
            	        .title(this.resto.resto_nama));

            	    // Move the camera instantly to hamburg with a zoom of 15.
            	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 15));

            	    // Zoom in, animating the camera.
            	    map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
                TextView tv = (TextView)v.findViewById(R.id.des);
                tv.setVisibility(View.GONE);
			} catch (Exception e) {
				TextView tv = (TextView)v.findViewById(R.id.des);
	            tv.setVisibility(View.VISIBLE);
	            tv.setText("You don't have internet connection :(");
			}
        	
        }else {
        	TextView tv = (TextView)v.findViewById(R.id.des);
            tv.setVisibility(View.VISIBLE);
            tv.setText("You don't have internet connection :(");
        }
        return v;
    }
    
    @Override
    public void onDestroyView ()
    {
          try{
        	SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
          }catch(Exception e){
          }
        super.onDestroyView();  
    }
    
    public final static boolean haveNetworkConnection(Activity activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
        	ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
