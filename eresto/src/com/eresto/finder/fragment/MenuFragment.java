package com.eresto.finder.fragment;

import com.eresto.finder.R;
import com.eresto.finder.adapter.MenuAdapter;
import com.eresto.finder.model.Resto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MenuFragment extends Fragment {
	
	int mCurrentPage;
	private static String[][] data1 = new String[5][2];
	private MenuAdapter adapter;
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
         View v = inflater.inflate(R.layout.activity_menu_fragment, container,false);
         
         this.id_resto = getArguments().getString("id_resto");
         this.resto = new Resto(getActivity());
         this.resto = this.resto.getResto(this.id_resto);
         
         adapter = new MenuAdapter(v.getContext(), this.resto.menu);
         
         // feature
         GridView feature = (GridView)v.findViewById(R.id.gridView1);  
         feature.setAdapter(adapter);
//         TextView tv = (TextView ) v.findViewById(R.id.tv);
//         tv.setText("You are viewing the page #" + mCurrentPage + "\n\n" + "Swipe Horizontally left / right");
         return v;
     }
}
