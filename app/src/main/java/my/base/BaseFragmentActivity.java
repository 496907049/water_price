package my.base;

import java.util.ArrayList;
import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity extends FragmentActivity  {
	
	protected FragmentManager mFManager;
	private Stack<String> mStack;
	private int mResId = -1;

	private int mAnimEnter;
	private int mAnimExit;
	private int mAnimPopEnter;
	private int mAnimPopExit;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFManager = getSupportFragmentManager();
		mStack = new Stack<String>();
		if (savedInstanceState != null) {
			ArrayList<String> data = (ArrayList<String>) savedInstanceState
					.getSerializable("ExpandFragmentActivity_stack");
			mStack.addAll(data);
		}

		initCreate(savedInstanceState);
	}

	protected void initCreate(Bundle savedInstanceState) {
		initConfig(savedInstanceState);
		initViews();
		initData(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		ButterKnife.bind(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("ExpandFragmentActivity_stack", mStack);
	}

	public final void setFragmentViewId(int resId) {
		mResId = resId;
	}

	public final void setFragmentAnim(int enter, int exit) {
		mAnimEnter = enter;
		mAnimExit = exit;
		mAnimPopEnter = 0;
		mAnimPopExit = 0;
	}

	public final void setFragmentAnim(int enter, int exit, int popEnter,
			int popExit) {
		mAnimEnter = enter;
		mAnimExit = exit;
		mAnimPopEnter = popEnter;
		mAnimPopExit = popExit;
	}

	public final synchronized void replaceFragment(BaseFragment fragment) {
		replaceFragment(fragment, fragment.getClass().getName());
	}

	public final synchronized void replaceFragment(BaseFragment fragment,
			String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();
		ft.setCustomAnimations(mAnimEnter, mAnimExit, mAnimPopEnter,
				mAnimPopExit);

		ft.replace(mResId, fragment, tag);
		if (!mStack.isEmpty()) {
			ft.addToBackStack(null);
		}
		mStack.add(tag);

		ft.commitAllowingStateLoss();
	}

	public final BaseFragment popFragment() {
		if (mStack.size() <= 1) {
			finish();
			return null;
		} else {
			mStack.pop();
			String top = mStack.peek();
			BaseFragment fragment = (BaseFragment) mFManager
					.findFragmentByTag(top);
			mFManager.popBackStack();
			return fragment;
		}
	}

	public final void removeFragment(BaseFragment fragment) {
		removeFragment(fragment, fragment.getClass().getName());
	}

	public final void removeFragment(BaseFragment fragment, String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();
		ft.remove(fragment);
		mStack.remove(tag);
		ft.commitAllowingStateLoss();
	}

	public final void changeFragment(BaseFragment fragment) {
		changeFragment(fragment, fragment.getClass().getName());
	}

	public final void changeFragment(BaseFragment fragment, String tag) {
		FragmentTransaction ft = mFManager.beginTransaction();
		ft.setCustomAnimations(mAnimEnter, mAnimExit, mAnimPopEnter,
				mAnimPopExit);

		if (mFManager.getFragments() != null) {
			for (Fragment f : mFManager.getFragments()) {
				if (f.isVisible()) {
					ft.hide(f);
				}
			}
		}

		if (mFManager.findFragmentByTag(tag) == null) {
			ft.add(mResId, fragment, tag);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();
	}

	public void initConfig(Bundle savedInstanceState) {
	}

	public abstract void initViews();

	public abstract void initData(Bundle savedInstanceState);
}
