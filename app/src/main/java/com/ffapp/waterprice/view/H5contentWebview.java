package com.ffapp.waterprice.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;

import com.ffapp.waterprice.basis.BasisActivity;
import com.ffapp.waterprice.other.PhotoScanActivity;
import com.ffapp.waterprice.other.WebViewX5Activity;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.URLUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * 自定义webview
 * */
public class H5contentWebview extends WebView {

	BasisActivity mActivity;
	
	public H5contentWebview(Context context) {
		super(context);
	}

	public H5contentWebview(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public H5contentWebview(Context context, AttributeSet attributeSet, int i) {
		super(context, attributeSet, i);
	}

	public H5contentWebview(Context context, AttributeSet attributeSet, int i, boolean b) {
		super(context, attributeSet, i, b);
	}

	public H5contentWebview(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
		super(context, attributeSet, i, map, b);
	}

	public void initActivity(BasisActivity activity){
		mActivity = activity;
		initSetting();
	}
	
	public void setContentUrl(String url){
		loadUrl(url);
	}
	
	public void setContent(String content){
		loadDataWithBaseURL(null,
				content,
						"text/html", "utf-8", null);
	}



	 void initSetting() {
		// TODO Auto-generated method stub
//		 getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		getSettings().setJavaScriptEnabled(true);
		getSettings().setSupportZoom(true);
		getSettings().setBuiltInZoomControls(true);
//		setInitialScale(20);
//		setDownloadListener(downloadListener);
		addJavascriptInterface(new MJavascriptInterface(), "imagelistener");
		getSettings().setUseWideViewPort(true);
		getSettings().setLoadWithOverviewMode(true);
		getSettings().setAppCacheEnabled(true);
		getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//         getSettings().setAppCachePath("external/chromium/net/disk_cache/");
		getSettings().setDisplayZoomControls(false);
		setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
		setSaveEnabled(true);
		getSettings().setAllowContentAccess(true);
		getSettings().setAllowFileAccess(true);
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		getSettings().setPluginState(WebSettings.PluginState.ON);
		getSettings().setDomStorageEnabled(true);
		// setZoomControlGone(mWebView);
//        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		clearCache(true);
//		webSet.setRenderPriority(WebSettings.RenderPriority.HIGH);
		//
		setWebViewClient(new MyWebViewClient());
//        setWebChromeClient(new MyWebChromeClient());
		setWebChromeClient(chromeClient);
		requestFocus();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		try {
			setWebViewClient(null);
			destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


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
				mActivity.startActivity(intent);
			} else if (url.startsWith("data:")) {
				return true;
			} else if (URLUtil.isNetworkUrl(url)) {
//				view.loadUrl(url);
                WebViewX5Activity.toWebView(mActivity,url,"");
			} else {
				return super.shouldOverrideUrlLoading(view, url);
			}
			return true;
		}

		@Override
		public void onReceivedError(WebView webView, int i, String s, String s1) {
			super.onReceivedError(webView, i, s, s1);
			mActivity.dismissProgress();
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
			super.onPageFinished(view, url);
			mActivity.dismissProgress();
			imgReset();
			addImageClickListener(view);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
			if (!mActivity.isFinishing()) {
				mActivity.showProgress();
			}
		}
		private void addImageClickListener(WebView webView) {
//			LogUtil.i("addImageClickListener------->");
			webView.loadUrl("javascript:(function(){" +
					"var objs = document.getElementsByTagName(\"img\"); " +
					"for(var i=0;i<objs.length;i++)  " +
					"{"
					+ "    objs[i].onclick=function()  " +
					"    {  "
					+ "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
					"    }  " +
					"}" +
					"})()");
		}

	}
	public class MJavascriptInterface {

		public MJavascriptInterface() {
		}

		@android.webkit.JavascriptInterface
		public void openImage(String img) {
			PhotoScanActivity.toImgScanActivityt(mActivity, img, "");
		}
	}


	private WebChromeClient chromeClient = new WebChromeClient() {

		@Override
		public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
			return super.onJsConfirm(arg0, arg1, arg2, arg3);
		}

		@Override
		public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
			return super.onJsAlert(webView, s, s1, jsResult);
		}

		@Override
		public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
			return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
		}


		@Override
		public void onReceivedTitle(WebView arg0, final String arg1) {
			super.onReceivedTitle(arg0, arg1);
//            Log.i("yuanhaizhou", "webpage title is " + arg1);
		}
	};

	/**
	 * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
	 **/
	private void imgReset() {
		loadUrl("javascript:(function(){" +
				"var objs = document.getElementsByTagName('img'); " +
				"for(var i=0;i<objs.length;i++)  " +
				"{"
				+ "var img = objs[i];   " +
				"    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
				"}" +
				"})()");
	}
}
