package com.eresto.finder.tab.activity;

import com.eresto.finder.R;
import com.eresto.finder.adapter.HomeAdapter;
import com.eresto.finder.model.Resto;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class RestaurantListActivity extends Activity {
	private HomeAdapter _adapter;
	public String[][] data = new String[5][2];
	public ListView _list;
	public Resto[] resto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		Resto tmp = new Resto(this);
		this.resto = tmp.getAllResto();
		
		for (int i = 0; i < 5; i++){
        	data[i][0] = "http://bisnis-jabar.com/wp-content/uploads/2011/08/bober.gif";
        	data[i][1] = "Bober";
        }
		
		this._adapter = new HomeAdapter(this, this.resto);
		this._list = (ListView)findViewById(R.id.listView1);
		this._list.setAdapter(this._adapter);
		
	}
}
