package my.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity  {

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	public void initConfig(Bundle savedInstanceState) {
	}

	public abstract void initViews();

	public abstract void initData(Bundle savedInstanceState);
}
