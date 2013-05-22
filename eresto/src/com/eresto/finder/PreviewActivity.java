package com.eresto.finder;

import com.eresto.utils.ImageLoader;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.Window;
import android.widget.Toast;

public class PreviewActivity extends Activity {
	private ImageViewTouch mImage;
	public ImageLoader imageLoader;
	private Matrix imageMatrix;
	protected ProgressDialog loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		this.loading = ProgressDialog
				.show(this, "", "Downloading image, please wait..", true);
		this.loading.setCancelable(false);
		
		mImage = (ImageViewTouch) findViewById( R.id.image );

		// set the default image display type
		mImage.setDisplayType( DisplayType.FIT_IF_BIGGER );
		
		imageLoader=new ImageLoader(this.getApplicationContext());
		Bitmap bitmap = imageLoader.getBitmap("http://bisnis-jabar.com/wp-content/uploads/2011/08/bober.gif");
		if( null != bitmap )
		{
			// calling this will force the image to fit the ImageView container width/height

			if( null == imageMatrix ) {
				imageMatrix = new Matrix();
			} else {
				// get the current image matrix, if we want restore the 
				// previous matrix once the bitmap is changed
				// imageMatrix = mImage.getDisplayMatrix();
			}
//			System.out.println("haha "+bitmap);
			mImage.setImageBitmap( bitmap, imageMatrix.isIdentity() ? null : imageMatrix, ImageViewTouchBase.ZOOM_INVALID, ImageViewTouchBase.ZOOM_INVALID );
//			Toast.makeText( this, "success to load the image", Toast.LENGTH_LONG ).show();
			this.loading.dismiss();
		} else {
			this.loading.dismiss();
			Toast.makeText( this, "Failed to load the image", Toast.LENGTH_LONG ).show();
		}
	}

}
