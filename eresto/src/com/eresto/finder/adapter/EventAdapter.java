package com.eresto.finder.adapter;

import com.eresto.finder.PreviewActivity;
import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;
import com.eresto.finder.model.Resto;
import com.eresto.utils.ImageLoader;
import com.google.android.gms.internal.da;

import android.app.Activity;
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

public class EventAdapter extends BaseAdapter implements OnClickListener{
    
    private Activity activity;
    private String[][] data;
    private static LayoutInflater inflater=null;
	public String username;
	public ImageLoader imageLoader;
	public String[] filter = new String[]{"jpg", "jpeg", "png", "gif"};
    
    public EventAdapter(Activity a, String[][] d) {
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
	    		 vi = inflater.inflate(R.layout.item_list_event, null);
	           
	    if(data.length > 0){
		        TextView name=(TextView)vi.findViewById(R.id.resto_name);
		        TextView alamat=(TextView)vi.findViewById(R.id.alamat);
		        ImageView image=(ImageView)vi.findViewById(R.id.image);
		        Drawable myIcon = activity.getResources().getDrawable( R.drawable.logo);
		        
		        if (stringContainsItemFromList(data[position][2], filter))
		        	imageLoader.DisplayImage(data[position][2], image);
		        else
		        	image.setImageDrawable(myIcon);
		        name.setText(data[position][0]);
		        String jenis;
		        if (data[position][6].equals("0"))
		        	jenis = "Gratis";
		        else
		        	jenis = "Berbayar";
		        String desc = "Restoran: "+data[position][1]+"\nDeskripsi: "+data[position][5]+"\nMulai promo: "+data[position][3]+"\nAkhir promo: "+data[position][4]+"\nJenis promo: "+jenis;
		        alamat.setText(desc);

		        image.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
			        	Intent myIntent = new Intent(activity, PreviewActivity.class);
				        myIntent.putExtra("url", data[position][2]);
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