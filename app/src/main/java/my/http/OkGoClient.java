package my.http;

import android.content.Context;

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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import my.LogUtil;
import my.MD5;
import my.SystemParamsUtils;
import my.TokenInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.net.sip.SipErrorCode.TIME_OUT;


public class OkGoClient {
    public final static String tag = "HTTP";
    public final static boolean DEBUG = Constants.DEBUG;

    private static final String BASE_URL = "";




    public static void get(Context context, String url, HttpParams params,
                MyHttpListener mListener, int what, Class<?> class1) {
            OkGo.<String>get(getAbsoluteUrl(url))
                    .tag(context)
                    .retryCount(3)
                    .cacheTime(5000)
                    .params(params)
                    .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                    .execute(new OkGoCallBack(mListener,what,class1));
    }

    public static void get(Context context, String url, HttpParams params,
                           StringCallback mListener, int what, Class<?> class1) {
        OkGo.<String>get(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .params(params)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(mListener);
    }

    public static void get(Context context, String url, StringCallback mListener, int what, Class<?> class1) {
        OkGo.<String>get(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(mListener);
    }

    public static void get(Context context, String url, MyHttpListener mListener, int what, Class<?> class1) {
        String a = LoginBean.getUserToken();
        OkGo.<String>get(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(new OkGoCallBack(mListener,what,class1));
    }

    public static void post(Context context, String url,
                            MyHttpListener mListener, int what, Class<?> class1) {

        OkGo.<String>post(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(new OkGoCallBack(mListener,what,class1));
    }

    public static void post(Context context, String url, HttpParams params,
                            StringCallback stringCallback, int what, Class<?> class1) {

        OkGo.<String>post(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .params(params)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(stringCallback);
    }

    public static void post(Context context, String url, HttpParams params,
                            MyHttpListener mListener, int what, Class<?> class1) {

        OkGo.<String>post(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .params(params)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(new OkGoCallBack(mListener,what,class1));
    }

    public static void post(Context context, String url, RequestBody body,
                            MyHttpListener mListener, int what, Class<?> class1) {

        OkGo.<String>post(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .upRequestBody(body)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(new OkGoCallBack(mListener,what,class1));
    }


    public static void post(Context context, String url, RequestBody body,
                            StringCallback mListener, int what, Class<?> class1) {
        OkGo.<String>post(getAbsoluteUrl(url))
                .tag(context)
                .retryCount(3)
                .cacheTime(5000)
                .upRequestBody(body)
                .headers("Authorization","Bearer\""+LoginBean.getUserToken()+"\"")
                .execute(mListener);
    }




    private static String getAbsoluteUrl(String relativeUrl) {
        if (relativeUrl.startsWith("http")) {
            LogUtil.i(tag,relativeUrl);
            return relativeUrl;
        } else {

//			return Constants.getServerIp() + relativeUrl;
            LogUtil.i(tag, MyUtils.getServiceAPI()+relativeUrl);
            return MyUtils.getServiceAPI()+relativeUrl;
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
        hashMap.put(HEADER_PARAMS1, MyUtils.getTenant());
        hashMap.put(HEADER_PARAMS2, time + "");
        hashMap.put("XF-API-app_type", "android");
        hashMap.put("XF-API-app_os", SystemParamsUtils.getPhoneModel());
        hashMap.put("XF-API-app_version", SystemParamsUtils.getAppVersonName(BasisApp.mContext) + "");

        ArrayList<String> listP = new ArrayList<>();
        listP.add(MyUtils.getAccessKey());
        listP.add(time);
        if(LoginBean.isLogin()){
            listP.add(LoginBean.getUserToken());
            hashMap.put("XF-API-token", LoginBean.getUserToken());
        }
        hashMap.put(HEADER_PARAMS3,getSecretStr(listP));

        return  hashMap;

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

