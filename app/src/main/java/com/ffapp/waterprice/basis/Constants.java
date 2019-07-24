package com.ffapp.waterprice.basis;

import android.os.Environment;

public class Constants {

	public static final boolean DEBUG = true;
	//开发地址
	 public static final String URL_SERVICE = "http://api.dev.gk100.ff-cloud.net/";

//	public static String URL_API = "http://192.168.25.245:8081/";
	public static String URL_API = "";

	//登录-登录
	public static String URL_LOGIN = URL_API+"api/security/login";
	//修改密码
	public static String URL_RESET = URL_API+"api/security/reset";

	//TOKEN接口
	public static String URL_GET_TOKEN = URL_API+"api/token";

	//首页-天气
	public static String URL_HOME_WEATHER = URL_API+"api/weather";

	//获取设备信息分页数据
	public static String URL_DEVICE_PAGE = URL_API+"api/device/page";
	//获取数据概况数据
	public static String URL_GET_DATA_OVERVIEW = URL_API+"api/dashboard/getDataOverview";
	//当前登录用户区域信息
	public static String URL_API_AREA = URL_API+"api/area";
	//获取设备类型数据
	public static String URL_DEVICE_TYPE = URL_API+"api/device/type";
	//用水情况信息查询
	public static String URL_GET_WATER_USER = URL_API+"api/dashboard/getWaterUser";
	//获取设备数
	public static String URL_GET_TREE = URL_API+"api/area/getDeviceTree";
	//获取流量列表分页数据
	public static String URL_MONITOR_PAGE = URL_API+"api/monitor/page";

	/**
	 * 分析接口
	 */
	public static String ANALYSIS_BASE_URL_STATT=URL_API+"api/stats/";
	public static String ANALYSIS_BASE_URL_END_PORT=URL_API+"/loadReport";
	public static String ANALYSIS_BASE_URL_END_PAGE=URL_API+"/page";

	//用水户分析
	public static String URL_WATER= ANALYSIS_BASE_URL_STATT+"waterconsumption";
	//流量分析分页
	public static String URL_FLOW= ANALYSIS_BASE_URL_STATT+"flow";
	//环境分析分页
	public static String URL_MOTEOROLOGY= ANALYSIS_BASE_URL_STATT+"meteorology";
	//雨量分析分页
	public static String URL_RAIN= ANALYSIS_BASE_URL_STATT+"rainfall";











	public static String aaa = URL_API+"aaat";

	public static String FILE_PREFIX = "file://";
	public static String DRAWABLE_PREFIX = "drawable://";
	public static int PAGE_SIZE = 10;

	public static final String TEL = "";

	public static final long TIME_DELAY = 500;


	/**
	 * 程序目录
	 */
	public static final String DIR = Environment.getExternalStorageDirectory()
			+ "/ZHGG/";
	public static final String DIR_IN = BasisApp.getInstance().getFilesDir().getPath()
			+ "/";

	public static final String DIR_DOWNLOAD = DIR + "download/";
	public static final String DIR_TEMP = DIR + "temp/";
	public static final String DIR_LOG = DIR + "log/";
	public static final String DIR_FILE = DIR + "file/";
	public static final String DIR_FILECACHE = DIR + "file/";


	/**
	 * 返回参数
	 */
	//站点信息返回
	public static int SITE_CALLBACK = 23;
	//区域信息返回
	public static int AREA_CALLBACK = 24;



}
