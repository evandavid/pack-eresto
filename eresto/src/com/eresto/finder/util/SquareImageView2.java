package com.eresto.finder.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView2 extends ImageView{
	public int viewWidth = 0;
    public int viewHeight = 0;

	public SquareImageView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public SquareImageView2(Context context, AttributeSet attrs) {
		super( context, attrs );
	}

	@SuppressLint("NewApi")
	public SquareImageView2(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
	}

	@Override 
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		@SuppressWarnings("unused")
	    int height = MeasureSpec.getSize(heightMeasureSpec);
	    setMeasuredDimension(width, width);
	}
	
	@Override
	 protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
	     super.onSizeChanged(xNew, yNew, xOld, yOld);

	     viewWidth = xNew;
	     viewHeight = yNew;
	}
}
