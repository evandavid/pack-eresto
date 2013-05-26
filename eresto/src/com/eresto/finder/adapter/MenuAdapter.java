package com.eresto.finder.adapter;

import com.eresto.finder.PreviewActivity;
import com.eresto.finder.R;
import com.eresto.finder.model.Menu;
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
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	private Context context;
	private Menu[] data;
	public ImageLoader imageLoader; 
	public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
	
 
	public MenuAdapter(Context context, Menu[] mobileValues) {
		this.context = context;
		this.data = mobileValues;
		imageLoader=new ImageLoader(context.getApplicationContext());
	}
	
	
	public View getView(final int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
			if (this.data != null){
				gridView = new View(context);
	 
				// get layout from mobile.xml
				gridView = inflater.inflate(R.layout.item_menu, null);
	 
				// set value into textview
				TextView text=(TextView)gridView.findViewById(R.id.text);
				TextView price=(TextView)gridView.findViewById(R.id.text_price);
		        ImageView image=(ImageView)gridView.findViewById(R.id.image);
	        
	        	text.setText(data[position].menu_nama);
	        	price.setText("Rp"+data[position].menu_harga);
	        	
		        Drawable myIcon = context.getResources().getDrawable( R.drawable.logo2);
		        System.out.println("url he"+data[position].menu_thumb);
		        if (stringContainsItemFromList(data[position].menu_thumb, filter)){
		        	imageLoader.DisplayImage(data[position].menu_thumb, image);
		        }
		        else
		        	image.setImageDrawable(myIcon);
		        
		        image.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
			        	Intent myIntent = new Intent(context, PreviewActivity.class);
				        myIntent.putExtra("url", data[position].menu_thumb);
			        	context.startActivity(myIntent);
					}
				});
//	        	imageLoader.DisplayImage(data[position][0], image);
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