package my.http;


import android.os.AsyncTask;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ffapp.waterprice.bean.BaseMsgBean;
import com.ffapp.waterprice.bean.BasicBeanStr;
import com.ffapp.waterprice.bean.BasisBean;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.concurrent.Executors;

import my.LogUtil;

class OkGoCallBack  extends StringCallback {

    private static String tag = "HTTP";
    int what = 0;
    MyHttpListener mListener;
    Class<?> clazz;

    public OkGoCallBack() {
        super();
    }

    public OkGoCallBack(MyHttpListener mListener, int what, Class<?> clazz) {
        super();
        this.what = what;
        this.mListener = mListener;
        this.clazz = clazz;

    }

    @Override
    public void onSuccess(final Response<String> response) {
        LogUtil.i(tag, "onSuccess" + response.code());
        if (mListener == null)
            return;
//		object = null;
        new AsyncTask<String, Integer, Object>() {

            @Override
            protected Object doInBackground(String... strings) {
                Object object = parseResoposeBody(response.body(), clazz);
                return object;
            }

            @Override
            protected void onPostExecute(Object object) {
                super.onPostExecute(object);
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
            }
        }.executeOnExecutor(Executors.newCachedThreadPool(), "");
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        LogUtil.i(tag, "onFailure" + response.code());
        if (mListener == null)
            return;
        Object object = parseResoposeBody(response.body(), clazz);
        if (object == null) {
            mListener.onFailure(what, null);
        } else {
            mListener.onFailure(what, object);
        }
//		mListener.onHttpFailure(what, arg3);
        mListener.onFinish(what);
    }



    @Override
    public void onFinish() {
        super.onFinish();
        if (mListener == null)
            return;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (mListener == null)
            return;
        mListener.onStart(what);
    }

    private static Object parseResoposeBody(String responseBody, Class<?> clazz) {
        if (responseBody == null) return null;
        String result = responseBody;
        LogUtil.i(tag, "" + result);
        if (TextUtils.isEmpty(result))
            return null;
        try {
            // result = StringUtil.decodeUnicode(result);
            MyBaseBean bean = JSON.parseObject(result, MyBaseBean.class);

            result =  JSON.toJSONString(bean,SerializerFeature.WriteNullStringAsEmpty);
            bean = JSON.parseObject(result, MyBaseBean.class);
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
//
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
            resultBean.setStatusCode(bean.getStatusCode());
//            resultBean.setCode((bean.getCode()));
            resultBean.setStatusInfo(bean.getStatusInfo());
            resultBean.setResultData(bean.getResultData());
//            resultBean.setApi_time(bean.getApi_time());
//            resultBean.setRecordsTotal(bean.getRecordsTotal());
            return resultBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
