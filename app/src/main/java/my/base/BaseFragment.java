package my.base;

import java.lang.reflect.Field;

import my.base.widget.LoadingDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
	public static final int RESULT_OK = -1;
	public static final int RESULT_CANCELED = 0;

	private LayoutInflater mInflater;
	private ViewGroup mContainer;

	private View mParentView;

	private int mRequestCode = RESULT_CANCELED;
	private int mResultCode = RESULT_CANCELED;
	private Intent mResultData;
	public Activity mContext;
	private LoadingDialog mLoadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mRequestCode = bundle.getInt("ExpandFragment_requestCode",
					RESULT_CANCELED);
		}
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		mContainer = container;
		initCreate(savedInstanceState);
		return mParentView;
	}

	public void setContentView(int layoutResID) {
		mParentView = mInflater.inflate(layoutResID, mContainer, false);
		ButterKnife.bind(this, mParentView);
	}

	public View findViewById(int id) {
		return mParentView.findViewById(id);
	}

	public final void finish() {
		if (getActivity() instanceof BaseFragmentActivity) {
			BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
			BaseFragment fragment = activity.popFragment();
			if (fragment != null) {
				fragment.onFragmentResult(mRequestCode, mResultCode,
						mResultData);
			}
		}
	}

	public final void startFragment(Intent intent) {
		try {
			String className = intent.getComponent().getClassName();
			if (getActivity() instanceof BaseFragmentActivity) {
				BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
				BaseFragment fragment = (BaseFragment) Class.forName(
						className).newInstance();
				fragment.setArguments(intent.getExtras());
				activity.replaceFragment(fragment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void startFragmentForResult(Intent intent, int requestCode) {
		String code = intent.getStringExtra("ExpandFragment_requestCode");
		if (code != null) {
			throw new RuntimeException(
					"intent params can't named ExpandFragment_requestCode");
		}
		intent.putExtra("ExpandFragment_requestCode", requestCode);
		startFragment(intent);
	}

	public final void setResult(int resultCode) {
		mResultCode = resultCode;
	}

	public final void setResult(int resultCode, Intent data) {
		mResultCode = resultCode;
		mResultData = data;
	}

	protected void initCreate(Bundle savedInstanceState) {
		initConfig(savedInstanceState);
		initViews();
		initData(savedInstanceState);
	}

	public void initConfig(Bundle savedInstanceState) {
		mLoadingDialog = new LoadingDialog(getActivity());
	}

	public abstract void initViews();

	public abstract void initData(Bundle savedInstanceState);

	public void onFragmentResult(int requestCode, int resultCode, Intent data) {
	}

	@Override
	public void onDetach() {

		super.onDetach();

		try {

			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");

			childFragmentManager.setAccessible(true);

			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {

			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {

			throw new RuntimeException(e);

		}

	}


	/**
	 * 显示载入窗口
	 */
	public void showProgress() {
		if (getActivity() != null) {
			mLoadingDialog.show();
		}
	}

	/**
	 * 隐藏载入窗口
	 */
	public void dismissProgress() {
		if (mLoadingDialog.isShowing() && getActivity() != null) {
			mLoadingDialog.dismiss();
		}
	}
	
}
