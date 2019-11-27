package my.http;


import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import my.LogUtil;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyParams {

	private final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<String, String>();
	private final ConcurrentHashMap<String, File> paramsFiles = new ConcurrentHashMap<String, File>();

	public ConcurrentHashMap<String, String> getUrlParams() {
		return urlParams;
	}

	public void put(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}
	public void put(String key, Object value) {
		if (key != null && value != null) {
			urlParams.put(key, String.valueOf(value));
		}
	}

	public void add(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key,value);
		}
	}
	public void add(String key, Object value) {
		if (key != null && value != null) {
			urlParams.put(key, String.valueOf(value));
		}
	}

	public void put(String key, File file) {
		if (key != null && file != null) {
			paramsFiles.put(key,file);
		}
	}

	public RequestBody getOkGoRequestBody(){
		StringBuilder stringBuilder = new StringBuilder();
		MediaType mediaType = MediaType.parse("application/json");
		JSONObject jsonObject = new JSONObject();
		for(String key:urlParams.keySet()){
			jsonObject.put(key,urlParams.get(key));
			stringBuilder.append(key).append("=").append(urlParams.get(key)).append("&");
		}
		LogUtil.i(OkGoClient.tag,stringBuilder.toString());
		RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
		return body;
	}

//	public String getJsonString(){
//		JSONObject jsonObject = new JSONObject();
//		for(String key:urlParams.keySet()){
//			jsonObject.put(key,urlParams.get(key));
//			stringBuilder.append(key).append("=").append(urlParams.get(key)).append("&");
//		}
//	}


	public void addParamsToBuilder(FormBody.Builder builder){
		StringBuilder stringBuilder = new StringBuilder();
		for(String key:urlParams.keySet()){
			builder.add(key,urlParams.get(key));
			stringBuilder.append(key).append("=").append(urlParams.get(key)).append("&");
		}
//		LogUtil.i(MyHttpUtils.tag,stringBuilder.toString());
	}

	public void addParamsToBuilder(MultipartBody.Builder builder){
		StringBuilder stringBuilder = new StringBuilder();
		for(String key:urlParams.keySet()){
			builder.addFormDataPart(key,urlParams.get(key));
			stringBuilder.append(key).append("=").append(urlParams.get(key)).append("&");
		}

		String TYPE = "application/octet-stream";
		if(paramsFiles != null && paramsFiles.size() > 0){
			builder .setType(MultipartBody.FORM);
			for(String key:paramsFiles.keySet()){
				File file = paramsFiles.get(key);
				RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
				builder.addFormDataPart(key,file.getName(),fileBody);
				stringBuilder.append(key).append("=").append(file.getName()).append("&");
			}
		}
//		LogUtil.i(MyHttpUtils.tag,stringBuilder.toString());
	}
}
