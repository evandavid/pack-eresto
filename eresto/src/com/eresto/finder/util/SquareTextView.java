package com.eresto.finder.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SquareTextView extends TextView{

	public SquareTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SquareTextView(Context context, AttributeSet attrs) {
		super( context, attrs );
	}

	@SuppressLint("NewApi")
	public SquareTextView(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
	}

	@Override 
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    @SuppressWarnings("unused")
		int width = MeasureSpec.getSize(widthMeasureSpec);
	    int height = MeasureSpec.getSize(heightMeasureSpec);
	    int h = (int) (height * 0.15);
	    setMeasuredDimension(height, h);
	}
}
