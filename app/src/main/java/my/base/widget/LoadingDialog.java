package my.base.widget;

import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class LoadingDialog extends Dialog {
	static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
	static final int ROTATION_ANIMATION_DURATION = 800;

	private Bitmap mBitmapTransparent;
	private Bitmap mBitmapLayout;
	private Bitmap mBitmapView;

	private RotateAnimation mRotateAnimation;
	private ProgressBar mProgressBar;

	public LoadingDialog(Context context) {
		super(context, true, listener);
		init(context);
	}

	private void init(Context context) {
		setCanceledOnTouchOutside(false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 设置Dialog背景透明
		InputStream is = LoadingDialog.class
				.getResourceAsStream("/com/my/my/base/bitmap/transparent.png");
		mBitmapTransparent = BitmapFactory.decodeStream(is);
		getWindow().setBackgroundDrawable(
				new BitmapDrawable(context.getResources(), mBitmapTransparent));

		// 设置View背景颜色
		is = LoadingDialog.class
				.getResourceAsStream("/com/my/my/base/bitmap/bg_loading.png");
		mBitmapLayout = BitmapFactory.decodeStream(is);

		// 设置载入中图片
		is = LoadingDialog.class
				.getResourceAsStream("/com/my/my/base/bitmap/ic_loading.png");
		mBitmapView = BitmapFactory.decodeStream(is);

		// 去除模糊背景
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		// 载入旋转动画
		mRotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				70, getContext().getResources().getDisplayMetrics());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size,
				size);
		params.gravity = Gravity.CENTER;
		mProgressBar = new ProgressBar(getContext());
		mProgressBar.setLayoutParams(params);
		mProgressBar.setIndeterminateDrawable(new BitmapDrawable(getContext()
				.getResources(), mBitmapView));

		params = new FrameLayout.LayoutParams(size, size);

		FrameLayout fl = new FrameLayout(getContext());
		fl.setBackgroundDrawable(new BitmapDrawable(
				getContext().getResources(), mBitmapLayout));
		fl.setLayoutParams(params);
		size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
				getContext().getResources().getDisplayMetrics());
		fl.setPadding(size, size, size, size);
		
		fl.addView(mProgressBar);
		setContentView(fl);
	}

	@Override
	public void show() {
		super.show();
		mProgressBar.setAnimation(mRotateAnimation);
	}

	private static OnCancelListener listener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			dialog.dismiss();
		}
	};
}
