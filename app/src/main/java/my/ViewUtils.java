package my;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ViewUtils {

	public static void hideInput(Activity mContext, EditText currentET) {

		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (currentET == null) {
			if (mContext.getCurrentFocus() != null) {
				imm.hideSoftInputFromInputMethod(mContext.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			} else {
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} else {
			currentET.requestFocusFromTouch();
			imm.hideSoftInputFromWindow(currentET.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void hideInput(Activity mContext,View rootView) {

		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
	}

	public static  void hideInput(Activity mContext){
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(mContext.getWindow().getDecorView().getWindowToken(), 0);
		}

	}

	public static void showInput(Activity mContext, EditText currentET) {

		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (currentET == null) {
			if (mContext.getCurrentFocus() != null) {
				imm.hideSoftInputFromInputMethod(mContext.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				imm.showSoftInput(mContext.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
			} else {
				imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
			}
		} else {
			currentET.requestFocusFromTouch();
//			imm.hideSoftInputFromWindow(currentET.getWindowToken(),
//					InputMethodManager.HIDE_NOT_ALWAYS);
			imm.showSoftInput(currentET, InputMethodManager.SHOW_IMPLICIT);
		}
	}


	/**
	 * RecyclerView 移动到当前位置，
	 *
	 * @param manager   设置RecyclerView对应的manager
	 * @param mRecyclerView  当前的RecyclerView
	 * @param n  要跳转的位置
	 */
	public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {


		int firstItem = manager.findFirstVisibleItemPosition();
		int lastItem = manager.findLastVisibleItemPosition();
		if (n <= firstItem) {
			mRecyclerView.scrollToPosition(n);
		} else if (n <= lastItem) {
			int top = mRecyclerView.getChildAt(n - firstItem).getTop();
			mRecyclerView.scrollBy(0, top);
		} else {
			mRecyclerView.scrollToPosition(n);
		}

	}

	/**
	 * by mos on 2017.11.13
	 * func:view转bitmap
	 */
	public static Bitmap convertViewToBitmap(View view) {

		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

		view.buildDrawingCache();

		Bitmap bitmap = view.getDrawingCache();

		return bitmap;

	}
}
