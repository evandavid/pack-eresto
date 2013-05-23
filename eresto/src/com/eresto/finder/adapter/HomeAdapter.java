package com.eresto.finder.adapter;

import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;
import com.eresto.finder.model.Resto;
import com.eresto.utils.ImageLoader;

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
import android.widget.ImageView;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter implements OnClickListener{
    
    private Activity activity;
    private Resto[] data;
    private static LayoutInflater inflater=null;
	public String username;
	public ImageLoader imageLoader;
	public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
    
    public HomeAdapter(Activity a, Resto[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
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
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
    	View vi=convertView;
        if(convertView==null)
	    		 vi = inflater.inflate(R.layout.item_list_resto_image, null);
	           
	    if(data[position] != null){
	    	
//	    		TextView table=(TextView)vi.findViewById(R.id.table);
		        TextView name=(TextView)vi.findViewById(R.id.resto_name);
		        TextView alamat=(TextView)vi.findViewById(R.id.alamat);
		        ImageView image=(ImageView)vi.findViewById(R.id.image);
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
		        name.setText(data[position].resto_nama);
		        alamat.setText(data[position].resto_alamat+"\n"+data[position].resto_telp+"\nBuka : "+data[position].resto_jamb+" - "+data[position].resto_jamt);
//		        
//		        vi.setTag(data[position][0]);
//		        table.setText("Table "+data[position][1]);
//		        first.setText(data[position][3]+"/");
//		        second.setText(data[position][2]);
		        
		        vi.setOnClickListener(new OnClickListener() {
	                public void onClick(View v) {
	                	Intent myIntent = new Intent(activity, RestaurantActivity.class);
//			        	myIntent.putExtra("name", data[position][7]);
//			        	myIntent.putExtra("username", data[position][5]);
	                	activity.startActivity(myIntent);
	                }
	            });
        }
	    convertView = null;
        return vi;
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}