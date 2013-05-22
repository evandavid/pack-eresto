package com.eresto.finder.adapter;

import com.eresto.finder.R;
import com.eresto.utils.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private Context context;
	private final String[][] data;
	public ImageLoader imageLoader; 
 
	public MenuAdapter(Context context, String[][] mobileValues) {
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
			gridView = inflater.inflate(R.layout.item_menu, null);
 
			// set value into textview
			TextView text=(TextView)gridView.findViewById(R.id.text);
	        ImageView image=(ImageView)gridView.findViewById(R.id.image);
        
        	text.setText(data[position][1]);
        	imageLoader.DisplayImage(data[position][0], image);
 
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