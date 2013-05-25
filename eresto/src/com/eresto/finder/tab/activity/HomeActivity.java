package com.eresto.finder.tab.activity;
import com.devsmart.android.ui.HorizontalListView;
import com.eresto.finder.R;
import com.eresto.finder.adapter.HorizontalListAdapter;
import com.eresto.finder.model.Resto;
import com.eresto.utils.CurrentCityDb;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;



public class HomeActivity extends Activity {  
	private static String[][] data1 = new String[5][2];
	private HorizontalListAdapter adapter, random_adapter, random_city_adapter;
	private EditText search_field;
	private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    public Resto[] resto, random, random_city;
    private CurrentCityDb dbhelper;
	private SQLiteDatabase db = null;
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_home);  
        mGestureDetector = new GestureDetector(this, new YScrollDetector());
        
        TextView random_label = (TextView)findViewById(R.id.top_label);
        dbhelper = new CurrentCityDb(this);
	    db = dbhelper.getWritableDatabase();
	    String city = dbhelper.getCity(db);
	    char[] stringArray = city.toCharArray();
	    stringArray[0] = Character.toUpperCase(stringArray[0]);
	    city = new String(stringArray);
	    random_label.setText("Random Places "+city);
        
        Resto tmp = new Resto(this);
        resto = tmp.getAllFeatureResto();
        random = tmp.getRandomResto();
        random_city = tmp.getRandomRestoCity();
        
        adapter = new HorizontalListAdapter(this, resto);
        random_adapter = new HorizontalListAdapter(this, random);
        random_city_adapter = new HorizontalListAdapter(this, random_city);
        
        // feature
        HorizontalListView feature = (HorizontalListView) findViewById(R.id.feature_list);  
        feature.setAdapter(adapter);
        // top
        HorizontalListView top = (HorizontalListView) findViewById(R.id.top_list);  
        top.setAdapter(random_adapter);
        // top global
        HorizontalListView top_global = (HorizontalListView) findViewById(R.id.top_global_list);  
        top_global.setAdapter(random_city_adapter);
        
        //search
//        search_field = (EditText)findViewById(R.id.search_input);
    }  
    
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }
    
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_SEARCH:
           /* handling search button pressed */
           search_field.requestFocus();
           InputMethodManager imm = (InputMethodManager)getSystemService(
     		      Context.INPUT_METHOD_SERVICE);
           imm.showSoftInput(search_field, 0);
           return true;
        }
        return false;
    } 
  
}  
