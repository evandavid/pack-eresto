package com.eresto.finder.adapter;

import com.eresto.finder.PreviewActivity;
import com.eresto.finder.R;
import com.eresto.finder.model.Photo;
import com.eresto.utils.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoAdapter extends BaseAdapter {
	private Context context;
	private Photo[] data;
	public ImageLoader imageLoader; 
	public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
 
	public PhotoAdapter(Context context, Photo[] mobileValues) {
		this.context = context;
		this.data = mobileValues;
		imageLoader=new ImageLoader(context.getApplicationContext());
	}
 
	public View getView(final int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
			if(data != null){
				gridView = new View(context);
	 
				// get layout from mobile.xml
				gridView = inflater.inflate(R.layout.item_photo, null);
		        ImageView image=(ImageView)gridView.findViewById(R.id.image);
		        
		        Drawable myIcon = context.getResources().getDrawable( R.drawable.logo2);
		        if (stringContainsItemFromList(data[position].url, filter))
	        		imageLoader.DisplayImage(data[position].url, image);
	        	else
		        	image.setImageDrawable(myIcon);
	        	image.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
			        	Intent myIntent = new Intent(context, PreviewActivity.class);
				        myIntent.putExtra("url", data[position].url);
			        	context.startActivity(myIntent);
					}
				});
			}else
				gridView = (View) convertView;
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
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
 
	public int getCount() {
		return data.length;
	}
 
	public Object getItem(int position) {
		return null;
	}
 
	public long getItemId(int position) {
		return 0;
	}
 
}