package com.eresto.finder.adapter;

import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;
import com.eresto.finder.model.Resto;
import com.eresto.finder.util.SquareImageView;
import com.eresto.utils.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HorizontalListAdapter extends BaseAdapter {
    
    private Activity activity;
    private Resto[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
    
    public HorizontalListAdapter(Activity a, Resto[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
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
    
    public View getView(final int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null)
	            vi = inflater.inflate(R.layout.item_horizontal_image, null);
	    if(data[position] != null){
	        TextView text=(TextView)vi.findViewById(R.id.text);
	        SquareImageView image=(SquareImageView)vi.findViewById(R.id.image);
	        text.getLayoutParams().width=image.viewWidth;
	        
        	text.setText(data[position].resto_nama);
        	
        	Bitmap bmp = imageLoader.getBitmap(data[position].resto_thumb);
	        Drawable myIcon = activity.getResources().getDrawable( R.drawable.logo2);
	        if (stringContainsItemFromList(data[position].resto_thumb, filter)){
	        	if (bmp != null)
	        		imageLoader.DisplayImage(data[position].resto_thumb, image);
	        	else
		        	image.setImageDrawable(myIcon);
	        }
	        else
	        	image.setImageDrawable(myIcon);
        	imageLoader.DisplayImage(data[position].resto_thumb, image);
        	
        	image.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		        	Intent myIntent = new Intent(activity, RestaurantActivity.class);
			        myIntent.putExtra("id_resto", data[position].id_resto);
		        	activity.startActivity(myIntent);
				}
			});
        }else {
        	vi.findViewById(R.id.text).setVisibility(View.GONE);
	        vi.findViewById(R.id.image).setVisibility(View.GONE);
        }
        return vi;
    }
}