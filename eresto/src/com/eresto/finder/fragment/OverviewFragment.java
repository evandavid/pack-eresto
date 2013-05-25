package com.eresto.finder.fragment;

import com.eresto.finder.R;
import com.eresto.finder.model.Resto;

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
	public int mCurrentPage;
	public LinearLayout body;
	public ScrollView _scroll;
	public String number;
	public String id_resto;
	public Resto resto;
	 
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
        this.id_resto = getArguments().getString("id_resto");
        this.resto = new Resto(getActivity());
        this.resto = this.resto.getResto(this.id_resto);
        this.initiateData(v);
        return v;
    }
    
    private void initiateData(View v){
    	TextView telpon = (TextView)v.findViewById(R.id.telephone);
    	TextView address = (TextView)v.findViewById(R.id.address);
    	TextView website = (TextView)v.findViewById(R.id.website);
    	TextView price = (TextView)v.findViewById(R.id.price);
    	TextView working = (TextView)v.findViewById(R.id.working);		
    	TextView category = (TextView)v.findViewById(R.id.category);
    			
    	telpon.setText(this.resto.resto_telp.replace("-", ""));
    	address.setText(this.resto.resto_alamat+"\n"+this.resto.nama_kota);
    	website.setText(this.resto.resto_web+"\nemail: "+this.resto.resto_email+"\nfacebook: "+this.resto.resto_fb+"\ntwitter: "+this.resto.resto_twitter);
    	price.setText("Rp "+this.resto.resto_harga1+" - Rp"+this.resto.resto_harga2);
    	working.setText("everyday: "+this.resto.resto_jamb+" - "+this.resto.resto_jamt);
    	category.setText(this.resto.kategori);
        //call
        ImageView tel_logo = (ImageView)v.findViewById(R.id.telephone_logo);
        tel_logo.setOnClickListener(telephoneListener);
        telpon.setOnClickListener(telephoneListener);
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
