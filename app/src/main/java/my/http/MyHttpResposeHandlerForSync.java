package my.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.ffapp.waterprice.bean.BaseMsgBean;
import com.ffapp.waterprice.bean.BasicBeanStr;
import com.ffapp.waterprice.bean.BasisBean;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import my.LogUtil;

class MyHttpResposeHandlerForSync extends AsyncHttpResponseHandler {
    private static String tag = "HTTP";
    int what = 0;
    MyHttpListener mListener;
    Class<?> clazz;

    public MyHttpResposeHandlerForSync() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MyHttpResposeHandlerForSync(MyHttpListener mListener, int what,
                                       Class<?> clazz) {
        super();
        this.what = what;
        this.mListener = mListener;
        this.clazz = clazz;

    }

    @Override
    public void onFailure(int statusCode, Header[] headers,
                          byte[] responseBody, Throwable arg3) {
        LogUtil.i(tag, "onFailure" + statusCode);
        if (mListener == null)
            return;
        Object object = parseResoposeBody(responseBody, clazz);
        if (object == null) {
            mListener.onFailure(what, null);
        } else {
            mListener.onFailure(what, object);
        }
//		mListener.onHttpFailure(what, arg3);
        mListener.onFinish(what);
    }

    //	Object object;
    @Override
    public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {
        // TODO Auto-generated method stub
        LogUtil.i(tag, "onSuccess" + statusCode);
        if (mListener == null)
            return;
//		object = null;
        Object object = parseResoposeBody(responseBody, clazz);
        if (object == null) {
            mListener.onFailure(what, null);
        } else {
            BasisBean bean = (BasisBean) object;
            if (bean.isCodeOk()) {
                mListener.onSuccess(what, object);
            } else {
                mListener.onFailure(what, object);
            }
        }
        mListener.onFinish(what);
//        new AsyncTask<String, Integer, Object>() {
//
//            @Override
//            protected Object doInBackground(String... strings) {
//                Object object = parseResoposeBody(responseBody, clazz);
//                return object;
//            }
//
//            @Override
//            protected void onPostExecute(Object object) {
//                super.onPostExecute(object);
//                if (object == null) {
//                    mListener.onFailure(what, null);
//                } else {
//                    BasisBean bean = (BasisBean) object;
//                    if (bean.isCodeOk()) {
//                        mListener.onSuccess(what, object);
//                    } else {
//                        mListener.onFailure(what, object);
//                    }
//                }
//                mListener.onFinish(what);
//            }
//        }.executeOnExecutor(Executors.newCachedThreadPool(), "");

    }


    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        LogUtil.i(tag, "onCancel");
        super.onCancel();
        if (mListener == null)
            return;
        mListener.onCancel(what);
        mListener.onFinish(what);
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        super.onFinish();
        LogUtil.i(tag, "onFinish");
        if (mListener == null)
            return;
//		mListener.onFinish(what);
    }

    @Override
    public void onProgress(long bytesWritten, long totalSize) {
        // TODO Auto-generated method stub
        super.onProgress(bytesWritten, totalSize);
        if (mListener == null)
            return;
        mListener.onProgress(what, bytesWritten, totalSize);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (mListener == null)
            return;
        mListener.onStart(what);
    }

    private static Object parseResoposeBody(byte[] responseBody, Class<?> clazz) {
        if (responseBody == null) return null;
        String result = new String(responseBody);
        LogUtil.i(tag, "" + result);
        if (TextUtils.isEmpty(result))
            return null;
        try {
            // result = StringUtil.decodeUnicode(result);
            MyBaseBean bean = JSON.parseObject(result, MyBaseBean.class);
//			MyBaseBean bean = JSON.parseObject(result, MyBaseBean.class);
            BasisBean resultBean;
            if (clazz == null) {
                resultBean = new BasisBean();
            } else if (!TextUtils.isEmpty(bean.getResultData())) {
                if (bean.getResultData().startsWith("[")) {
                    if (bean.getResultData().startsWith("[\"")) {
                        bean.setResultData("{\"list\":[]}");
                    } else {

                        bean.setResultData("{\"list\":" + bean.getResultData() + "}");
                    }
                }
                if (BasicBeanStr.class.isAssignableFrom(clazz)) {
//					JSONObject jsonObject = JSON.parseObject(bean.getResultData());
//					bean.setResultData("{\"msg\":\""+bean.getResultData()+"\"}");
                }
                if (BaseMsgBean.class.isAssignableFrom(clazz)) {
//					JSONObject jsonObject = JSON.parseObject(bean.getResultData());
                    bean.setResultData("{\"msg\":\"" + bean.getResultData() + "\"}");
                }
//				 if(!TextUtils.isEmpty(bean.getResultData()) && bean.getResultData().equals("数据为空")) {
//					bean.setResultData("{\"list\":[]}");
//				}

                if (!TextUtils.isEmpty(bean.getResultData()) && !bean.getResultData().startsWith("{")) {
                    resultBean = new BasisBean();
                    bean.setStatusCode(-1);
                } else {

                    resultBean = (BasisBean) JSON.parseObject(
                            bean.getResultData(), clazz);
                }
            } else if (bean.getResultData() == null
                    || bean.getResultData().equals("null")
                    || bean.getResultData().equals("{}")
                    || (bean.getResultData().length() == 0)) {
                resultBean = (BasisBean) clazz.newInstance();
            } else {
                resultBean = (BasisBean) JSON.parseObject(bean.getResultData(),
                        clazz);
            }
//            resultBean.setStatusCode(bean.getStatusCode());
            resultBean.setStatusCode((bean.getStatusCode()));
            resultBean.setStatusInfo(bean.getStatusInfo());
            resultBean.setResultData(bean.getResultData());
//            resultBean.setApi_time(bean.getApi_time());
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
