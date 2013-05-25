package com.eresto.finder;

import com.eresto.finder.R;
import com.eresto.finder.adapter.HomeAdapter;
import com.eresto.finder.model.Resto;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;



import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity; 
import android.app.SearchManager;
import android.content.Intent;

public class RestaurantListSearchActivity extends Activity {
	private HomeAdapter _adapter;
	public String[][] data = new String[5][2];
	public PullToRefreshListView _list;
	public Resto[] resto;
	public String offset = "10";
	public String search;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_search_list);
		if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }else{
			this.search = getIntent().getStringExtra("search");
			TextView tv = (TextView)findViewById(R.id.search_result);
			tv.setText("Search result for "+search);
			
			Resto tmp = new Resto(this);
			this.resto = tmp.getSearchResto(offset, this.search);
			
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
	}
	
	@Override
    public void onBackPressed(){
		super.onBackPressed();
		Intent myIntent = new Intent(this, DashboardActivity.class);
    	this.startActivity(myIntent);
    	this.finish();
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
        	RestaurantListSearchActivity.this.runOnUiThread(new Runnable() {

        		@Override
        		public void run() {
        			tmp = new Resto(getApplicationContext());
        			Resto[] old = resto;
        			offset = String.valueOf(Integer.parseInt(offset)+10);
        			Resto[] rnew = tmp.getSearchResto(offset, search);
    		        resto = combineResto(old,rnew);
    		        int position = _list.getFirstVisible();
    		        _adapter = new HomeAdapter(RestaurantListSearchActivity.this, resto);
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
	
	public void BackClicked(View view){
    	this.onBackPressed();
    }
    
    /** handle android search button pressed */
    private void handleIntent(Intent intent) { 
      if (Intent.ACTION_SEARCH.equals(intent.getAction())) { 
         String query =  intent.getStringExtra(SearchManager.QUERY); 
     	 doSearch(query);
     	 this.finish();
      } 
    }    
    
    /** handle search button pressed */
    public void searchPressed(View view){
    	onSearchRequested();
    }
    
    /** override onsearchRequested */
    @Override
    public boolean onSearchRequested(){
    	super.onSearchRequested();
    	String query = getIntent().getStringExtra(SearchManager.QUERY);
    	doSearch(query);
    	return true;
    }
    
    private void doSearch(String q) { 
    	if(q != null){
    		Intent myIntent = new Intent(this, RestaurantListSearchActivity.class);
	        myIntent.putExtra("search", q);
        	this.startActivity(myIntent);
        	this.finish();
    	}
    }
}
