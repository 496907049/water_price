package com.ffapp.waterprice.other;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.basis.BasisApp;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

import my.LogUtil;
import my.http.HttpRestClient;


public class WebViewX5Activity extends BasisActivity implements OnClickListener {

    WebView mWebView;
    String wapurl;
    String title;
    boolean isHeader = true;

    public static void toWebView(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewX5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        LogUtil.i("url---->" + url);
        context.startActivity(intent);
    }

    public static void toWebViewWeixin(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewX5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("weixin", true);
        LogUtil.i("url---->" + url);
        context.startActivity(intent);
    }

    public static void toWebViewPaddingTop(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewX5Activity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("top", true);
        LogUtil.i("url---->" + url);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        setContentView(R.layout.base_webview_local_x5);
        super.initViews();

        setTitle("网页");
        setTitleLeftButton(this);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        // mWebView.getSettings().setSupportZoom(true);
        // mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.setInitialScale(20);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setSavePassword(false);
//         mWebView.getSettings().setAppCachePath("external/chromium/net/disk_cache/");
        // mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setSaveEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        // setZoomControlGone(mWebView);
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearCache(false);
//		webSet.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //
        mWebView.setWebViewClient(new MyWebViewClient());
//        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebChromeClient(chromeClient);
        mWebView.requestFocus();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.initData(savedInstanceState);


        initUrl();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (mWebView != null) {
            try {
                mWebView.setWebViewClient(null);
                mWebView.destroy();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void initUrl() {
        wapurl = getIntent().getStringExtra("url");
         title = getIntent().getStringExtra("title");
        if (wapurl == null || wapurl.equals("")) {
            BasisApp.showToast("网页地址不存在");
        }

        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        } else {
            setTitle("");
        }
        // myProgressDialog.show();
        if(isHeader){
            Map<String, String > map = HttpRestClient.getWebviewHeader();
            mWebView.loadUrl(wapurl,map);
        }else {
            mWebView.loadUrl(wapurl);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (mWebView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private Handler mHandler = new Handler();

    @SuppressLint("NewApi")
    class MyWebViewClient extends WebViewClient {
        String loginCookie;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            // if(myProgressDialog != null &&
            // !myProgressDialog.isShowing())myProgressDialog.show();
            if (url.startsWith("mailto:") || url.startsWith("geo:")
                    || url.startsWith("tel:") || url.indexOf(".3gp") != -1 || url.indexOf(".mp4") != -1
                    || url.indexOf(".flv") != -1
                    || url.indexOf(".swf") != -1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                mWebView.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            // TODO Auto-generated method stub
            // CookieManager cookieManager = CookieManager.getInstance();
            // loginCookie = cookieManager.getCookie(url);
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            // CookieManager cookieManager = CookieManager.getInstance();
            // cookieManager.setCookie(url, loginCookie);
            super.onPageFinished(view, url);
            dismissProgress();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            if (!WebViewX5Activity.this.isFinishing()) {
                showProgress();
            }
        }

    }

    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;

        ///////////////////////////////////////////////////////////
        //  `

        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            View normalView =  WebViewX5Activity.this.findViewById(R.id.webview);
            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
            viewGroup.removeView(normalView);
            viewGroup.addView(view);
            myVideoView = view;
            myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            WebViewX5Activity.this.uploadFile = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            (WebViewX5Activity.this).startActivityForResult(Intent.createChooser(i, "选择文件"), FILE_CHOOSER);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType ) {
            WebViewX5Activity.this.uploadFile = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            (WebViewX5Activity.this).startActivityForResult(Intent.createChooser(i, "选择文件"), FILE_CHOOSER);
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
            LogUtil.i("openFileChooser--->");
            WebViewX5Activity.this.uploadFile = uploadFile;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            (WebViewX5Activity.this).startActivityForResult(Intent.createChooser(i, "选择文件"),
                    FILE_CHOOSER);
            super.openFileChooser(uploadFile, acceptType, captureType);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            LogUtil.i("openFileChooser--5.0->");
            WebViewX5Activity.this.mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            WebViewX5Activity.this.startActivityForResult(
                    Intent.createChooser(i, "选择文件"),
                    FILE_CHOOSER);
            return true;
        }

        @Override
        public void onReceivedTitle(WebView arg0, final String arg1) {
            super.onReceivedTitle(arg0, arg1);
//            Log.i("yuanhaizhou", "webpage title is " + arg1);
            if (TextUtils.isEmpty(title)) {
                setTitle(arg1);
            }

        }
    };

    private ValueCallback<Uri> uploadFile;
    ValueCallback<Uri[]> mUploadCallbackAboveL;
    private static final int FILE_CHOOSER = 22;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_CHOOSER
             ) {
            return;
        }
        LogUtil.i("onActivityResult--onActivityResult->"+resultCode);
        if (resultCode == RESULT_OK) {

            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
                return;
            }

            if (null != uploadFile) {
                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                uploadFile.onReceiveValue(result);
                uploadFile = null;
            }
        } else  {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }

        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILE_CHOOSER
                || mUploadCallbackAboveL == null) {
            return;
        }
        LogUtil.i("onActivityResultAboveL-------->"+resultCode);
        Uri[] results =  null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_btn_back:

                finish();
                break;
        }
    }

}
