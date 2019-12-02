package my;

import com.alibaba.fastjson.JSON;
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.ffapp.waterprice.bean.BaseListData;
import com.ffapp.waterprice.bean.BaseListDataListBean;
import com.ffapp.waterprice.bean.LoginBean;
import com.ffapp.waterprice.util.MyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
//        LogUtil.print("response.code=" + response.code());

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
//            LogUtil.print("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("Cookie", "JSESSIONID=" + newSession)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 500) {
            return true;
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private String getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
        BaseListData dataCurrent = MyUtils.getSerciceData();
        if(dataCurrent == null)return "";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dataCurrent));
        String url =""+ dataCurrent.getId()+Constants.URL_GET_TOKEN ;
        Response response =  OkGo.<String>post(dataCurrent.getId()+Constants.URL_GET_TOKEN)
                .tag(BasisApp.getInstance())
                .retryCount(3)
                .cacheTime(5000)
                .upRequestBody(body)
                .execute();

//        Call<Response_Login> call = WebHelper.getSyncInterface().synclogin(new Request_Login(username, password));
//        loginInfo = call.execute().body();
//        LogUtil.print("loginInfo=" + loginInfo.toString());
//
//        loginInfo.setPassword(password);
//        CacheManager.saveLoginInfo(loginInfo);
        String b = response.body().string();
        LoginBean bean = JSON.parseObject(b, LoginBean.class);
        LoginBean.getInstance().setAccessToken(bean.getToken());
        LoginBean.getInstance().save();
        return bean.getToken();
    }

}
