package com.eresto.finder.tab.activity;

import com.eresto.finder.R;
import com.eresto.finder.adapter.HomeAdapter;
import com.eresto.finder.model.Resto;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;



import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity; 

public class RestaurantListActivity extends Activity {
	private HomeAdapter _adapter;
	public String[][] data = new String[5][2];
	public PullToRefreshListView _list;
	public Resto[] resto;
	public String offset = "10";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		Resto tmp = new Resto(this);
		this.resto = tmp.getAllResto(offset);
		
		this._adapter = new HomeAdapter(this, this.resto);
		this._list = (PullToRefreshListView)findViewById(R.id.listView1);
		this._list.setAdapter(this._adapter);
		this._list.setMode(Mode.PULL_FROM_END);    // mode refresh for top and bottom
		this._list.setPullLabel("Loading");
		this._list.setShowIndicator(true);
		
		this._list.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new GetDataTask().execute();
			}
		});
		
	}
	
	public Resto[] combineResto(Resto[] first, Resto[] second){
		int count = first.length + second.length;
		Resto[] returnresto = new Resto[count];
		
		int i;
		for(i=0;i<first.length;i++){
			returnresto[i] = first[i];
		}
		
		for(int j=0;j<second.length;j++){
			returnresto[i] = second[j];
			i++;
		}

		return returnresto;
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        private Resto tmp;

        @Override
        protected String[] doInBackground(Void... params) {
        // Simulates a background job.
	        try {
	        	Thread.sleep(2000);
	        } catch (InterruptedException e) {
	        }
	        	String[] mStrings = null;
	        	return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
        	RestaurantListActivity.this.runOnUiThread(new Runnable() {

        		@Override
        		public void run() {
        			tmp = new Resto(getApplicationContext());
        			Resto[] old = resto;
        			offset = String.valueOf(Integer.parseInt(offset)+10);
        			Resto[] rnew = tmp.getAllResto(offset);
    		        resto = combineResto(old,rnew);
    		        int position = _list.getFirstVisible();
    		        _adapter = new HomeAdapter(RestaurantListActivity.this, resto);
    				_list = (PullToRefreshListView)findViewById(R.id.listView1);
    				_list.setAdapter(_adapter);
    				_list.setSelection(position);
        		    }
        		});

	        // Call onRefreshComplete when the list has been refreshed.
	        _list.onRefreshComplete();
	
	        super.onPostExecute(result);
        }
    }
}
