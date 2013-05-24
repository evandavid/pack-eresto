package com.eresto.finder.tab.activity;
import com.devsmart.android.ui.HorizontalListView;
import com.eresto.finder.R;
import com.eresto.finder.adapter.HorizontalListAdapter;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;



public class HomeActivity extends Activity {  
	private static String[][] data1 = new String[5][2];
	private HorizontalListAdapter adapter;
	private EditText search_field;
	private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_home);  
        mGestureDetector = new GestureDetector(this, new YScrollDetector());
        //data dummy  
        for (int i = 0; i < 5; i++){
        	data1[i][0] = "http://bisnis-jabar.com/wp-content/uploads/2011/08/bober.gif";
        	data1[i][1] = "Bober";
        }
        adapter = new HorizontalListAdapter(this, data1);
        
        // feature
        HorizontalListView feature = (HorizontalListView) findViewById(R.id.feature_list);  
        feature.setAdapter(adapter);
        // top
        HorizontalListView top = (HorizontalListView) findViewById(R.id.top_list);  
        top.setAdapter(adapter);
        // top global
        HorizontalListView top_global = (HorizontalListView) findViewById(R.id.top_global_list);  
        top_global.setAdapter(adapter);
        
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
