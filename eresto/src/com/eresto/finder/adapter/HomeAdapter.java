package com.eresto.finder.adapter;

import com.eresto.finder.R;
import com.eresto.finder.RestaurantActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeAdapter extends BaseAdapter implements OnClickListener{
    
    private Activity activity;
    private String[][] data;
    private static LayoutInflater inflater=null;
	public String username;
    
    public HomeAdapter(Activity a, String[][] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	           
	    if(data[position][1] != null){
	    		System.out.println("hehe "+(data[position][0]== "You"));
	    	
//	    		TextView table=(TextView)vi.findViewById(R.id.table);
//		        TextView first=(TextView)vi.findViewById(R.id.right_first);
//		        TextView second=(TextView)vi.findViewById(R.id.right_second);
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