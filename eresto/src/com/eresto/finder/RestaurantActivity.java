package com.eresto.finder;

import com.eresto.finder.adapter.RestaurantFragmentAdapter;
import com.eresto.utils.ImageLoader;
import com.eresto.utils.getRoundedCornerBitmap;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class RestaurantActivity extends FragmentActivity {
	public getRoundedCornerBitmap imageLoader;
	public ImageLoader imageLoaderCover;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        handleIntent(getIntent()); 
        
        float roundX = 40;
        imageLoaderCover=new ImageLoader(this.getApplicationContext());
        ImageView image_cover=(ImageView)findViewById(R.id.cover_image);
    	imageLoaderCover.DisplayImage("http://www.millenniumhotels.co.uk/content/dam/uk/en/millennium-knightsbridge/images/UK.FB.KNI.Le-Chinois.jpg", image_cover);
        imageLoader=new getRoundedCornerBitmap(this.getApplicationContext(),roundX);
        ImageView image=(ImageView)findViewById(R.id.image);
    	imageLoader.DisplayImage("", image);
    	
    	ViewPager pager = (ViewPager) findViewById(R.id.pager);
    	 
        /** Getting fragment manager */
        FragmentManager fm = getSupportFragmentManager();
 
        /** Instantiating FragmentPagerAdapter */
        RestaurantFragmentAdapter pagerAdapter = new RestaurantFragmentAdapter(fm);
 
        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);
    }
    
    public void onNewIntent(Intent intent) { 
        setIntent(intent); 
        handleIntent(intent); 
    } 
    
    public void BackClicked(View view){
    	this.onBackPressed();
    }
    
    /** handle android search button pressed */
    private void handleIntent(Intent intent) { 
      if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
         String query =  intent.getStringExtra(SearchManager.QUERY); 
     	 doSearch(query);
      } 
    }    
    
    /** handle search button pressed */
    public void searchPressed(View view){
    	onSearchRequested();
    }
    
    /** override onsearchRequested */
    @Override
    public boolean onSearchRequested(){
    	super.onSearchRequested();
    	String query = getIntent().getStringExtra(SearchManager.QUERY);
    	doSearch(query);
    	return true;
    }
    
    private void doSearch(String q) { 
    	if(q != null){
    		Toast.makeText(this, q, Toast.LENGTH_LONG).show();
    	}
    }
    
}
