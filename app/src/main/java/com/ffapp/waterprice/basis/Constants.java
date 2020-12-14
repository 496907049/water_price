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
	//获取设备数
	public static String URL_GET_WATER_TREE = URL_API+"api/water/tree";
	//获取流量列表分页数据
	public static String URL_MONITOR_PAGE = URL_API+"api/monitor/page";

	//运维管理-获取待办任务分页数据
	public static String URL_MANAGE_TODO_LIST  = URL_API+"api/todo/page";
	//运维管理-获取巡检任务分页数据
	public static String URL_MANAGE_PATROL_LIST  = URL_API+"api/patrol/page";
	//运维管理-获取运维任务分页数据
	public static String URL_MANAGE_MAINTAIN_LIST  = URL_API+"api/maintenance/page";
	//运维管理-获取巡检任务详情
	public static String URL_MANAGE_PATROL_DETAIL  = URL_API+"api/patrol/";
	//运维管理-编辑、保存、执行巡检任务
	public static String URL_MANAGE_PATROL_UPDATE = URL_API+"api/patrol/update";
	//运维管理-获取维保任务详情
	public static String URL_MANAGE_MAINTAIN_DETAIL = URL_API+"api/maintenance/";
	//运维管理-编辑、保存、执行维保-运维-任务
	public static String URL_MANAGE_MAINTAIN_UPDATE = URL_API+"api/maintenance/update";



	//提交轨迹
	public static String URL_PATROL_TRACK_ADD =  "http://222.80.32.65:19008/api.php/PatrolTrack/add";
	//获取巡检记录
	public static String URL_PATROL_TRACK_INDEX =  "http://222.80.32.65:19008/api.php/PatrolTrack/index";
	//巡检列表
	public static String URL_PATROL_LIST =  "http://222.80.32.65:19008/api.php/InspectionPlan/index";
	//巡检列表-巡检计划详情
	public static String URL_PATROL_DETAIL =  "http://222.80.32.65:19008/api.php/InspectionPlan/detail";

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
	//土壤墒情分页
	public static String URL_SOIL= ANALYSIS_BASE_URL_STATT+"soil";
	//土壤墒情分页
	public static String URL_WARNING= ANALYSIS_BASE_URL_STATT+"warning";

	//海康视频
	public static String HK_SERVICE_IP  = "218.75.178.16";
	public static String HK_SERVICE_PORT  = "8000";
	public static String HK_SERVICE_USERNAME  = "admin";
	public static String HK_SERVICE_PWD = "12345abc";
	public static String HK_SERVICE_NAME = "TieShan";









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
