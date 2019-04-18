package my.http;

import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.util.MyUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import my.LogUtil;
import my.MD5;
import my.SystemParamsUtils;


public class HttpRestClient {
    public final static String tag = "HTTP";
    public final static boolean DEBUG = Constants.DEBUG;

    private static final String BASE_URL = "";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final int TIME_OUT = 30 * 1000;

    static {
        client.setTimeout(TIME_OUT); // 设置链接超时，如果不设置，默认为10s
//       setHeader(client,params);
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,true);
        setCommentParams(params);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, RequestParams params,
                           MyHttpListener mListener, int what) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,true);
        setCommentParams(params);
        client.get(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
                mListener, what, null));
    }

    public static void get(String url, RequestParams params,
                           MyHttpListener mListener, int what, Class<?> class1) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,true);
        setCommentParams(params);
        client.get(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
                mListener, what, class1));
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            JsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            TextHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            MyHttpListener mListener, int what) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);
        client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
                mListener, what, null));
    }

    public static void post(String url, RequestParams params,
                            MyHttpListener mListener, int what, Class<?> class1) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);

        client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
                mListener, what, class1));
    }

    public static void postSync(String url, RequestParams params,
                                MyHttpListener mListener, int what, Class<?> class1) {
//        SyncHttpClient syncHttpClient = new SyncHttpClient(true, 80, 443);
        SyncHttpClient syncHttpClient = new SyncHttpClient();
        setHeader(syncHttpClient,params,false);
//        setCommentParams(params);
        if (DEBUG) {
            LogUtil.i(tag, params.toString());
        }
        syncHttpClient.post(getAbsoluteUrl(url), params, new MyHttpResposeHandlerForSync(
                mListener, what, class1));
    }

    public static void post(String url, RequestParams params,
                            MyHttpListener mListener, int what, int timeout, Class<?> class1) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
       setHeader(client,params,false);
        setCommentParams(params);
        client.setTimeout(timeout);
        client.post(getAbsoluteUrl(url), params, new MyHttpResposeHandler(
                mListener, what, class1));
    }

    public static void download(String url,
                                FileAsyncHttpResponseHandler mListener) {
//		setHeader(client);
//		setCommentParams(params);
        client.setMaxRetriesAndTimeout(0, TIME_OUT);
        client.get(getAbsoluteUrl(url), mListener);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        if (relativeUrl.startsWith("http")) {
            LogUtil.i(tag,relativeUrl);
            return relativeUrl;
        } else {

//			return Constants.getServerIp() + relativeUrl;
            LogUtil.i(tag, MyUtils.getServiceAPI()+relativeUrl);
            return MyUtils.getServiceAPI()+relativeUrl;
//            return relativeUrl;
        }
    }

    private static final String HEADER_PARAMS1 = "XF-API-app_id";
    private static final String HEADER_PARAMS2 = "XF-API-timestamp";
    private static final String HEADER_PARAMS3 = "XF-API-signature";
    private static final String HEADER_HTTP_SourceID = "155245784442";//appid
    private static final String HEADER_HTTP_AuthKey = "SN68jqYEhdU4v8Dp";

    private static void setHeader(AsyncHttpClient client, RequestParams params,boolean isGet) {
        long time = new Date().getTime()/1000;
        client.addHeader("APP-ID", HEADER_HTTP_SourceID);
//        client.addHeader(
//                "APP-SIGN",
//                MD5.getMD5ofStr(
//                        HEADER_HTTP_AuthKey + time + HEADER_HTTP_SourceID)
//                        .substring(0, 32));
        client.addHeader("APP-AUTHTIME", time + "");
        client.addHeader("APP-VERSION",SystemParamsUtils.getAPPVersonCode(BasisApp.mContext)+"");
        client.addHeader("APP-TYPE",  "android");
        client.addHeader("APP-IMEI",  SystemParamsUtils.getIMEI());
        client.addHeader("APP-DEVICE",
                SystemParamsUtils.getPhoneModel() + "");
//        if(LoginBean.isLogin()){
//            client.addHeader("APP-TOKEN", LoginBean.getUserToken());
//        }
        client.addHeader("APP-TOKEN", LoginBean.getUserToken());

        ArrayList<String> listP = new ArrayList<>();
        listP.add("header_app_id="+HEADER_HTTP_SourceID);
            listP.add("header_app_token="+LoginBean.getUserToken());
        listP.add("header_app_authtime="+time+"");
        if(isGet){
            if(params !=null){
                String[] pramsStrs = params.toString().split("&");
                for(String data:pramsStrs){
                    if(data.startsWith("file["))continue;
                    if(data.startsWith("uploadFile"))continue;
//                listP.add(data.substring(data.indexOf("=")+1));
                    listP.add(data);
                }
            }
        }

        client.addHeader("APP-SIGN",getSecretStr(listP));
//        LogUtil.i("APP-TOKEN", LoginBean.getUserToken());
    }

    private static  String getSecretStr(ArrayList<String>  list) {
        String result = "";
        Collections.sort(list);
        StringBuilder builder = new StringBuilder();
        for(String data:list){
//            LogUtil.i(""+data);
            builder.append(data).append("&");
        }
        builder.append("appkey="+HEADER_HTTP_AuthKey);
//        LogUtil.i("字符串"+builder.toString());
        result = MD5.getMD5ofStrLowercase(builder.toString()).substring(0,32);

//        if(result.length()> 48){
//            result = result.substring(0,48).toUpperCase();
//        }
//            LogUtil.i("签名值"+result);
        return result;
    }

    public static  HashMap<String,String> getWebviewHeader() {
//		client.addHeader("pwd", "test_password");
        // client.addHeader("", "");
//		 long time = new Date().getTime();
        HashMap<String,String>  hashMap = new HashMap<>();
//        String time = TimeUtils.getCurrentTimeByFormat("yyyyMMddHHmmss");
        String time = new Date().getTime()/1000+"";
//        hashMap.put(HEADER_PARAMS1, HEADER_HTTP_SourceID);
        hashMap.put(HEADER_PARAMS1, MyUtils.getAppid());
        hashMap.put(HEADER_PARAMS2, time + "");
        hashMap.put("XF-API-app_type", "android");
        hashMap.put("XF-API-app_os", SystemParamsUtils.getPhoneModel());
        hashMap.put("XF-API-app_version", SystemParamsUtils.getAppVersonName(BasisApp.mContext) + "");

        ArrayList<String> listP = new ArrayList<>();
        listP.add(MyUtils.getAuthKey());
        listP.add(time);
        if(LoginBean.isLogin()){
            listP.add(LoginBean.getUserToken());
            hashMap.put("XF-API-token", LoginBean.getUserToken());
        }
        hashMap.put(HEADER_PARAMS3,getSecretStr(listP));

        return  hashMap;

    }


    private static void setCommentParams(RequestParams params) {


        if (DEBUG) {
            LogUtil.i(tag, params.toString());
        }
        client.setTimeout(TIME_OUT);
        // if (params != null)
        // LogUtil.i("RequestParams--->" + params.toString());
        // if (!TextUtils.isEmpty(BasisApp.sAppId)
        // && !TextUtils.isEmpty(BasisApp.sAppId)) {
        // params.add("app_id", BasisApp.sAppId);
        // params.add("msisdn", BasisApp.sMsisdn);
        // }
    }

}

//class MyDownloadListener extends BinaryHttpResponseHandler {
//
//	@Override
//	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProgress(long bytesWritten, long totalSize) {
//		// TODO Auto-generated method stub
//		super.onProgress(bytesWritten, totalSize);
//	}
//
//}

