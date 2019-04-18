package com.ffapp.baseapp.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery.LayoutParams;

import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisActivity;
import com.ffapp.baseapp.basis.BasisApp;

import java.util.ArrayList;

import my.LogUtil;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * @作者 suncco
 * @备注 新闻详情图片查看activity发、
 */
public class PhotoScanActivity extends BasisActivity implements OnClickListener,
        OnItemClickListener {

	ViewPager mGallery;
	ArrayList<String> mListBean;
	PicGalleryAdapterNew mAdapter;
	int index = 0;
	Activity mContext;
	View mVTitle;
	boolean isTitleVisible = true;

	public static void toImgScanActivityt(Context context, String url,
                                          String title) {
		Intent intent = new Intent(context, PhotoScanActivity.class);
		ArrayList<String> images = new ArrayList<String>();
		images.add(url);
		intent.putExtra("list", images);
		context.startActivity(intent);
	}

	public static void toImgScanActivityt(Context context,
			ArrayList<String> images) {
		Intent intent = new Intent(context, PhotoScanActivity.class);
		intent.putExtra("list", images);
		context.startActivity(intent);
	}

	public static void toImgScanActivityt(Context context,
                                          ArrayList<String> images, int position) {
		Intent intent = new Intent(context, PhotoScanActivity.class);
		intent.putExtra("list", images);
		intent.putExtra("index", position);
		context.startActivity(intent);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		setContentView(R.layout.photo_scan_activity);
		mContext = this;
//		myProgressDialog = new LoadingProgressDialog(mContext);
		mVTitle = findViewById(R.id.base_title_root);
		setTitle("图片详情");
		setTitleLeftButton(null);
		mGallery = (ViewPager) findViewById(R.id.photo_viewpager);

	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.initData(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		if (!TextUtils.isEmpty(title)) {
			setTitle(title);
		}
		mListBean = getIntent().getStringArrayListExtra("list");
		if (mListBean == null || mListBean.size() == 0) {
			BasisApp.showToast("图片不存在");
			finish();
		}
		index = getIntent().getIntExtra("index", 0);
		setListView();
		if(index != 0){
			mGallery.setCurrentItem(index);
		}
	}


	private void setListView() {
		 mAdapter = new PicGalleryAdapterNew(mContext, new OnTapListener() {
			@Override
            public void OnTap() {
				// TODO Auto-generated method stub
				changeTitleMode();
			}
		});

		 mGallery.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
//		case R.id.base_btn_back:
//			mContext.finish();
//			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		changeTitleMode();
	}

	private void changeTitleMode() {
		isTitleVisible = !isTitleVisible;
		mVTitle.clearAnimation();
		if (isTitleVisible) {
			mVTitle.setVisibility(View.VISIBLE);
			mVTitle.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.down_in));

		} else {
			mVTitle.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.up_out));
			mVTitle.setVisibility(View.GONE);
		}
	}

	public interface OnTapListener {
		public void OnTap();

	}

	public class PicGalleryAdapterNew extends PagerAdapter {

		private Context context;
		private OnTapListener mOnTouchListener;

		// private int images[] = { R.drawable.chrysanthemum, R.drawable.desert,
		// R.drawable.hydrangeas,R.drawable.penguins};

		public PicGalleryAdapterNew(Context context,
				OnTapListener mOnTouchListener) {
			this.context = context;
			this.mOnTouchListener = mOnTouchListener;
		}

		@Override
		public int getCount() {
			return mListBean == null ? 0 : mListBean.size();
		}

		@SuppressWarnings("deprecation")
		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(context);
			photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
				@Override
                public void onViewTap(View view, float x, float y) {
					// TODO Auto-generated method stub
					mOnTouchListener.OnTap();
				}
			});
			String imgUrl = getItem(position);
			LogUtil.i("imgUrl-->"+imgUrl);

			if (imgUrl.contains("http")) {
//				BasisApp.mBitmapHandler.loadBitmap(new DisplayView(photoView, imgUrl));
				BasisApp.loadImg(mContext,imgUrl,photoView,R.drawable.ic_default_image);


			} else {
//				BasisApp.mBitmapHandler.loadBitmap(new DisplayView(photoView, imgUrl));
//				BasisApp.imageLoader.displayImage(Constants.FILE_PREFIX + imgUrl,
//						photoView, BasisApp.defautOptions,
//						BasisApp.defautDisplaylitener);
				BasisApp.loadImg(mContext,imgUrl,photoView,R.drawable.ic_default_image);
			}
			photoView.setMinimumScale(0.5f);
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return photoView;
		}

		private String getItem(int position) {
			if (position > -1 && position < getCount()) {
                return mListBean.get(position);
            }
			return "";
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

	}

}
