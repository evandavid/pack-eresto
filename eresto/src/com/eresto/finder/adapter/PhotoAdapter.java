package com.eresto.finder.adapter;

import com.eresto.finder.PreviewActivity;
import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;
import com.eresto.utils.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoAdapter extends BaseAdapter {
	private Context context;
	private final String[] data;
	public ImageLoader imageLoader; 
 
	public PhotoAdapter(Context context, String[] mobileValues) {
		this.context = context;
		this.data = mobileValues;
		imageLoader=new ImageLoader(context.getApplicationContext());
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.item_photo, null);
	        ImageView image=(ImageView)gridView.findViewById(R.id.image);
        	imageLoader.DisplayImage(data[position], image);
        	image.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		        	Intent myIntent = new Intent(context, PreviewActivity.class);
//			        	myIntent.putExtra("name", data[position][7]);
//			        	myIntent.putExtra("username", data[position][5]);
		        	context.startActivity(myIntent);
				}
			});
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
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