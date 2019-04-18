package my.useful;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import my.LogUtil;
import my.SystemParamsUtils;

/**
 * Created by Administrator on 2016/10/29 0029.
	 */
	public class UrlImageGetter implements Html.ImageGetter {
		Context c;
		TextView container;

		public UrlImageGetter(TextView t,Context c){
			this.c = c;
			this.container = t;
		}

		public Drawable getDrawable(String source){
			final UrlDrawable urlDrawable = new UrlDrawable();
//			ImageLoader.getInstance().loadImage(source,new SimpleImageLoadingListener(){
//				@Override
//				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){
//
//					ViewGroup.LayoutParams params = container.getLayoutParams();
//					params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//					container.setLayoutParams(params);
//
//					float scaleWidth = ((float) container.getMeasuredWidth())/loadedImage.getWidth();
//
//					Matrix matrix = new Matrix();
//					matrix.postScale(scaleWidth,scaleWidth);
//
//					loadedImage = Bitmap.createBitmap(loadedImage,0,0,loadedImage.getWidth(),loadedImage.getHeight(),matrix,true);
//					urlDrawable.bitmap = loadedImage;
//					urlDrawable.setBounds(0,0,loadedImage.getWidth(),loadedImage.getHeight());
//					container.invalidate();
//					container.setText(container.getText());
//				}
//			});
			Glide.with(c).load(source).asBitmap().listener(new RequestListener<String, Bitmap>() {
				@Override
				public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
					return false;
				}

				@Override
				public boolean onResourceReady(Bitmap loadedImage, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
					LogUtil.i("onResourceReady--->"+isFirstResource+isFromMemoryCache);
					ViewGroup.LayoutParams params = container.getLayoutParams();
					params.width = ViewGroup.LayoutParams.MATCH_PARENT;
					container.setLayoutParams(params);

//					float scaleWidth = ((float) container.getMeasuredWidth())/loadedImage.getWidth();
					float scaleWidth = ((float) SystemParamsUtils.getScreenWidth()-100)/loadedImage.getWidth();

					Matrix matrix = new Matrix();
					matrix.postScale(scaleWidth,scaleWidth);

					loadedImage = Bitmap.createBitmap(loadedImage,0,0,loadedImage.getWidth(),loadedImage.getHeight(),matrix,true);
					urlDrawable.bitmap = loadedImage;
					urlDrawable.setBounds(0,0,loadedImage.getWidth(),loadedImage.getHeight());
					container.invalidate();
					container.setText(container.getText());
					return false;
				}
			}).into(500,500);
			return urlDrawable;
		}

		public class UrlDrawable extends BitmapDrawable {
			protected Bitmap bitmap;

			@Override
			public void draw(Canvas canvas) {
				// override the draw to facilitate refresh function later
				if (bitmap != null) {
					canvas.drawBitmap(bitmap, 0, 0, getPaint());
				}
			}
		}
	}