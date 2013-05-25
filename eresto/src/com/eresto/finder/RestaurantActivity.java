package com.eresto.finder;

import com.eresto.finder.adapter.RestaurantFragmentAdapter;
import com.eresto.finder.model.Resto;
import com.eresto.utils.ImageLoader;
import com.eresto.utils.getRoundedCornerBitmap;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantActivity extends FragmentActivity {
	public getRoundedCornerBitmap imageLoader;
	public ImageLoader imageLoaderCover;
	public String id_resto;
	public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
	public Resto resto;
	private TextView resto_name;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }else{
	        this.id_resto = getIntent().getStringExtra("id_resto");
	        this.resto = new Resto(this);
	        this.resto = this.resto.getResto(this.id_resto);
	        this.resto_name = (TextView)findViewById(R.id.restorant_name);
	        this.resto_name.setText(this.resto.resto_nama);
	        
	        float roundX = 40;
	//        imageLoaderCover=new ImageLoader(this.getApplicationContext());
	//        ImageView image_cover=(ImageView)findViewById(R.id.cover_image);
	//    	imageLoaderCover.DisplayImage("http://www.millenniumhotels.co.uk/content/dam/uk/en/millennium-knightsbridge/images/UK.FB.KNI.Le-Chinois.jpg", image_cover);
	    	
	    	imageLoader=new getRoundedCornerBitmap(this.getApplicationContext(),roundX);
	        ImageView image=(ImageView)findViewById(R.id.image);
	    	Bitmap bmp;
	    	try {
	    		bmp = imageLoader.getBitmap(this.resto.resto_thumb);
			} catch (Exception e) {
				bmp = null;
			}
	        Drawable myIcon = this.getResources().getDrawable( R.drawable.logo3);
	        if (stringContainsItemFromList(this.resto.resto_thumb, filter)){
	        	if (bmp != null)
	        		imageLoader.DisplayImage(this.resto.resto_thumb, image);
	        	else
		        	image.setImageDrawable(myIcon);
	        }
	        else
	        	image.setImageDrawable(myIcon);
	    	
	    	ViewPager pager = (ViewPager) findViewById(R.id.pager);
	    	 
	        /** Getting fragment manager */
	        FragmentManager fm = getSupportFragmentManager();
	 
	        /** Instantiating FragmentPagerAdapter */
	        RestaurantFragmentAdapter pagerAdapter = new RestaurantFragmentAdapter(fm, this.id_resto);
	 
	        /** Setting the pagerAdapter to the pager object */
	        pager.setAdapter(pagerAdapter);
        }
    }
    
    public void onNewIntent(Intent intent) { 
        setIntent(intent); 
        handleIntent(intent); 
    } 
    
    public static boolean stringContainsItemFromList(String inputString, String[] items)
	{
	    for(int i =0; i < items.length; i++)
	    {
	        if(inputString.contains(items[i]))
	        {
	            return true;
	        }
	    }
	    return false;
	}
    
    public void BackClicked(View view){
    	this.onBackPressed();
    }
    
    /** handle android search button pressed */
    private void handleIntent(Intent intent) { 
      if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
         String query =  intent.getStringExtra(SearchManager.QUERY); 
     	 doSearch(query);
     	 this.finish();
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
    		Intent myIntent = new Intent(this, RestaurantListSearchActivity.class);
	        myIntent.putExtra("search", q);
        	this.startActivity(myIntent);
        	this.finish();
    	}
    }
    
}
