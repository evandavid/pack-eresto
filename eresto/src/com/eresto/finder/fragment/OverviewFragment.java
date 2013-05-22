package com.eresto.finder.fragment;

import com.eresto.finder.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class OverviewFragment extends Fragment {
	int mCurrentPage;
	LinearLayout body;
	ScrollView _scroll;
	String number;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        /** Getting integer data of the key current_page from the bundle */
        mCurrentPage = data.getInt("current_page", 0);
 
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_overview_fragment, container,false);
        TextView _number = (TextView)v.findViewById(R.id.telephone);
        number = _number.getText().toString().replace("-", "");
        //call
        ImageView tel_logo = (ImageView)v.findViewById(R.id.telephone_logo);
        tel_logo.setOnClickListener(telephoneListener);
        _number.setOnClickListener(telephoneListener);
        return v;
    }
    
    OnClickListener telephoneListener = new OnClickListener() {
		public void onClick(View v) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
	    	callIntent.setData(Uri.parse("tel:"+number));
	    	callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	    	startActivity(callIntent);
		}
	};
}
