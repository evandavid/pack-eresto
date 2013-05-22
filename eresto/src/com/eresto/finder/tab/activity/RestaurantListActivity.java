package com.eresto.finder.tab.activity;

import com.eresto.finder.R;
import com.eresto.finder.adapter.HomeAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class RestaurantListActivity extends Activity {
	private HomeAdapter _adapter;
	private String[][] data = new String[10][2];
	private ListView _list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		
		for (int i = 0; i < 10; i++){
        	data[i][0] = "http://bisnis-jabar.com/wp-content/uploads/2011/08/bober.gif";
        	data[i][1] = "Bober";
        }
		
		this._adapter = new HomeAdapter(this, this.data);
		this._list = (ListView)findViewById(R.id.listView1);
		this._list.setAdapter(this._adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant_list, menu);
		return true;
	}

}
