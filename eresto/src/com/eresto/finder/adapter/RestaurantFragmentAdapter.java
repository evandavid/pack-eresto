package com.eresto.finder.adapter;
import com.eresto.finder.fragment.EventFragment;
import com.eresto.finder.fragment.MapFragment;
import com.eresto.finder.fragment.MenuFragment;
import com.eresto.finder.fragment.OverviewFragment;
import com.eresto.finder.fragment.PhotoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class RestaurantFragmentAdapter extends FragmentPagerAdapter {
	final int PAGE_COUNT = 5;
	public String id;
	 
    /** Constructor of the class */
    public RestaurantFragmentAdapter(FragmentManager fm, String id) {
        super(fm);
        this.id = id;
    }
 
    /** This method will be invoked when a page is requested to create */
    @Override
    public Fragment getItem(int arg0) {
    	switch (arg0) {
		case 0:
			OverviewFragment overview = new OverviewFragment();
	        Bundle data_overview = new Bundle();
	        data_overview.putInt("current_page", arg0+1);
	        data_overview.putString("id_resto", id);
	        overview.setArguments(data_overview);
	        return overview;
		case 1:
			MenuFragment menu = new MenuFragment();
	        Bundle data_menu = new Bundle();
	        data_menu.putString("id_resto", id);
	        data_menu.putInt("current_page", arg0+1);
	        menu.setArguments(data_menu);
	        return menu;
		case 2:
			PhotoFragment photo = new PhotoFragment();
	        Bundle data_photo = new Bundle();
	        data_photo.putInt("current_page", arg0+1);
	        data_photo.putString("id_resto", id);
	        photo.setArguments(data_photo);
	        return photo;
		case 3:
			MapFragment myFragment = new MapFragment();
	        Bundle data = new Bundle();
	        data.putInt("current_page", arg0+1);
	        data.putString("id_resto", id);
	        myFragment.setArguments(data);
	        return myFragment;
		default:
			EventFragment Event = new EventFragment();
	        Bundle data_Event = new Bundle();
	        data_Event.putInt("current_page", arg0+1);
	        data_Event.putString("id_resto", id);
	        Event.setArguments(data_Event);
	        return Event;
			
		}
        
    }
 
    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
    	CharSequence menu = "" ;
    	switch (position) {
		case 0:
			menu = "Overview";
			break;
		case 1:
			menu = "Menu";
			break;
		case 2:
			menu = "Photos";
			break;
		case 3:
			menu = "Map";
			break;
		case 4:
			menu = "Events &Promos";
			break;
		}
    	return menu;
    }
}
