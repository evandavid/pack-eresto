package com.eresto.finder.adapter;

import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;
import com.eresto.utils.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[][] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public HorizontalListAdapter(Activity a, String[][] d) {
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
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null)
	            vi = inflater.inflate(R.layout.item_horizontal_image, null);
	    if(data[position][1] != null){
	        TextView text=(TextView)vi.findViewById(R.id.text);
	        ImageView image=(ImageView)vi.findViewById(R.id.image);
	        
        	text.setText(data[position][1]);
        	imageLoader.DisplayImage(data[position][0], image);
        	
        	image.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
		        	Intent myIntent = new Intent(activity, RestaurantActivity.class);
//			        	myIntent.putExtra("name", data[position][7]);
//			        	myIntent.putExtra("username", data[position][5]);
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