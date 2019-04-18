package com.ffapp.baseapp.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ffapp.baseapp.R;
import com.ffapp.baseapp.basis.BasisActivity;
import com.ffapp.baseapp.basis.BasisApp;

import my.StringUtil;
import my.ViewUtils;


/**
 * @作者 suncco
 * @备注 String编辑
 */
public class StringEditActivity extends BasisActivity implements
        OnClickListener {
    /**
     * Called when the activity is first created.
     */
    String name;
    EditText mETName;

    public static final String TAG = "string";

    public static void ToStringEditActivity(Activity context, int requestCode,
                                            String currentStr, String hint, String tag, String title,
                                            int maxlenth) {

        Intent intent = new Intent(context, StringEditActivity.class);
        intent.putExtra("string", currentStr);
        intent.putExtra("hint", hint);
        intent.putExtra("tag", tag);
        intent.putExtra("title", title);
        if (maxlenth > 0)
            intent.putExtra("max", maxlenth);
        context.startActivityForResult(intent, requestCode);

    }

    public static void ToStringEditActivity(Activity context, int requestCode,
                                            String currentStr, String hint, String tag, String title) {
        ToStringEditActivity(context, requestCode, currentStr, hint, tag,
                title, -1);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.util_string_edit_activity);
        setTitle("自定义");
        setTitleRightText(R.string.app_ok, this);
        setTitleLeftButton(null);
//		setTitleLeftText(R.string.app_cancel, new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
        mETName = (EditText) findViewById(R.id.name);
        mETName.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int arg1,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_DONE) {
                    findViewById(R.id.base_title_right_text).performClick();
                }
                return true;
            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);
        Intent data = getIntent();
        String title = data.getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        String hint = data.getStringExtra("hint");
        if (!TextUtils.isEmpty(hint)) {
            mETName.setHint(hint);
        }
        name = getIntent().getStringExtra("string");
        if (!TextUtils.isEmpty(name)) {
            mETName.setText(name + "");
        }
        String tag = getIntent().getStringExtra("tag");
        if (!TextUtils.isEmpty(tag)) {
            ((TextView) findViewById(R.id.string_tag)).setText(tag);
            findViewById(R.id.string_tag).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.string_tag).setVisibility(View.GONE);
        }
        int maxlength = getIntent().getIntExtra("max", -1);
        if (maxlength > 0) {
            InputFilter[] filters = {new InputFilter.LengthFilter(maxlength)};
            mETName.setFilters(filters);
        }
//		ViewUtils.showInput(mContext, mETName);
        mETName.requestFocus();

        mETName.postDelayed(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                ViewUtils.showInput(mContext, mETName);
            }
        }, 300);
//	    Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                	ViewUtils.showInput(mContext, mETName);
////                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//                }
//        }, 300);
    }

    private void onDoneClick() {
        String newName = mETName.getText().toString();
        if (TextUtils.isEmpty(newName)) {
            BasisApp.showToast("输入不能为空");
        } else {
            name = StringUtil.replaceBlank(newName);
            Intent data = new Intent();
            data.putExtra("string", name);
            setResult(Activity.RESULT_OK, data);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = null;
        switch (v.getId()) {
            case R.id.base_btn_back:
                ViewUtils.hideInput(mContext, mETName);
                finish();
                break;
            case R.id.btn_clear:
                mETName.setText("");
                break;
            case R.id.base_title_right_text:
                onDoneClick();
                break;
            default:
                break;
        }
    }

}
