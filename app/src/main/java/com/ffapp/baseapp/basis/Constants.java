package com.ffapp.baseapp.basis;

import android.os.Environment;

public class Constants {

//	 public static final boolean DEBUG = false;
	public static final boolean DEBUG = true;

	// 正式服务器
//	 public static final String URL_SERVICE = "http://xfxf.four-faith.com/FourFaithXF/";
//	 public static final String URL_SERVICE = "http://192.168.25.60:8080/xf/";
	//开发地址
	 public static final String URL_SERVICE = "http://api.dev.gk100.ff-cloud.net/";

	// 测试服务器-外网
//	 public static final String URL_SERVICE = "http://api.ukl-beta.suncco.com/";



//	public static String URL_API = URL_SERVICE+"api/v1/";
	public static String URL_API = "";

	//登录-登录
	public static String URL_LOGIN = URL_API+"User/login";
	//登录-注销
	public static String URL_LOGIN_LOGOUT = URL_API+"User/logout";
	//登录-短信验证码
	public static String URL_LOGIN_MSGCODE = URL_API+"User/sendCode.json";
	//用户-修改密码
	public static String URL_USER_MODIFY_PWD= URL_API+"User/change_password";
	//我的-获取当前用户的消息列表
	public static String URL_USER_MSG_LIST= URL_API+"common/getAppMessage";
	//我的-获取当前用户的消息列表
	public static String URL_USER_MSG_SETREAD = URL_API+"common/updateMessageRead";
	//基础接口-获取单位信息
	public static String URL_OTHER_GET_UNIT = URL_API+"Other/get_unit";

	//通告-消息列表-通告列表
	public static String URL_MESSAGE_LIST = URL_API+"message_push/get_already_read";
	//通告-标记已读消息
	public static String URL_MESSAGE_SAVE_ALREADY_READ= URL_API+"message_push/save_already_read";
	//通告-消息列表-通告列表
	public static String URL_GET_MESSAGE_DETAIL = URL_API+"message_push/get_message_detail";
	//通告-标记全部已读
	public static String URL_MESSAGE_ALL_READ = URL_API+"message_push/all_is_read";

	//更新
	public static String URL_UPDATE= URL_API+"Other/check_versions";



    //首页-推荐灌溉
    public static String URL_HOME_RECOMENT_WATERING= URL_API+"Home/recommend_irrigation";
	//首页-地块-地块列表-获取用户管辖的地块以及所需设备-0或者不传：只返回地块信息，1：返回地块下的GK、FK、阀门信息，2：返回地块下的墒情站信息
	public static String URL_HOME_BLOCK_LIST= URL_API+"massif/get_massif_info";

	//首页-天气
	public static String URL_HOME_WEATHER = URL_API+"weather/get";

	//土壤墒情-列表
	public static String URL_SOIL_STATION_LIST= URL_API+"soil_moisture/get_all_soil_moistrue";
	//土壤墒情-列表
	public static String URL_SOIL_STATION_DETAIL =  URL_API+"soil_moisture/get_soil_moistrue_data";

	//自动灌溉-今日任务
	public static String URL_AUTO_TODAY_TASK = URL_API+"AutoIrrigation/today_task";
	//自动灌溉-刷新每日任务状态
	public static String URL_AUTO_REFRESH_STATUS= URL_API+"AutoIrrigation/refresh_status";
	//自动灌溉-暂停今日任务
	public static String URL_AUTO_CANCEL_TODAY_PLAN = URL_API+"AutoIrrigation/cancel_today_plan";
	//自动灌溉-编辑今日任务
	public static String URL_AUTO_EDIT_DAILY_PLAN = URL_API+"AutoIrrigation/edit_daily_plan";
	//自动灌溉-计划管理
	public static String URL_AUTO_PLAN_MANAGE = URL_API+"AutoIrrigation/auto_plan_manage";
	//自动灌溉-新增计划
	public static String URL_AUTO_ADD_PLAN = URL_API+"AutoIrrigation/add_irrigation";
	//自动灌溉-编辑计划
	public static String URL_AUTO_EDIT_PLAN = URL_API+"AutoIrrigation/edit_irrigation";
	//自动灌溉-删除计划任务
	public static String URL_AUTO_DELETE_PLAN= URL_API+"AutoIrrigation/delete_irrigation";
	//自动灌溉-计划管理获取单位
	public static String URL_AUTO_GET_UNIT = URL_API+"AutoIrrigation/get_unit";
	//自动灌溉-计划管理获取轮灌组
	public static String URL_AUTO_GET_GROUP = URL_API+"AutoIrrigation/get_group";
	//自动灌溉-计划管理-计划详情（编辑前调用）
	public static String URL_AUTO_PLAN_DETAIL= URL_API+"AutoIrrigation/detail_irrigation";

	//手动灌溉-获取阀门列表
	public static String URL_MANUAL_VALVE_LIST = URL_API+"manual_mode/get_manual_valve";
	//手动灌溉-获取轮灌组列表
	public static String URL_MANUAL_GET_MANUL_GROUP = URL_API+"manual_mode/get_manual_irrigation";
	//手动灌溉-启动轮灌组
	public static String URL_MANUAL_OPEN_GROUP = URL_API+"manual_mode/handle_irrigation_group";
	//手动灌溉-关闭轮灌组
	public static String URL_MANUAL_CLOSE_GROUP = URL_API+"manual_mode/close_irrigation_group";
	//手动灌溉-获取轮灌组的灌溉进度
	public static String URL_MANUAL_GET_PROGRESS_BAR= URL_API+"manual_mode/get_progress_bar";
	//手动灌溉-获取未响应轮灌组的状态（当taskStatus为-1时，请求该接口，获取设备是否有任务进行）
	public static String URL_MANUAL_GET_UNRESPONSE_STATE = URL_API+"manual_mode/get_unresponse_state";
	//手动灌溉-获取轮灌组下的阀门列表
	public static String URL_MANUAL_VALVE_LIST_BELONG_GROUP = URL_API+"manual_mode/manual_irrigation_detail";
	//手动灌溉-开启阀门
	public static String URL_VALVE_OPEN = URL_API+"valve/handle_value";
	//手动灌溉-关闭阀门
	public static String URL_VALVE_CLOSE= URL_API+"valve/close_manual_value";


	//灌溉报告
	//阀门报告列表
	public static String URL_VAL_REPORT_LIST = URL_API+"/Report/value_report_list";
	//轮灌报告一览  (轮灌组)
	public static String URL_IRR_LIST = URL_API+"/Report/irrigation_list";
	//查看阀门详情
	public static String URL_AJAX_GET_REPORT = URL_API+"/Report/ajax_get_report";


	//土壤墒情接口
	//获取用户管理的所有土壤墒情站
	public static String URL_GET_ALL_SOIL_MOISTRUE = URL_API+"/soil_moisture/get_all_soil_moistrue";


	//国家地区
	public static String URL_COUNTRY= URL_API+"System/country.json";
	//首页-设备信息和人员信息
	public static String URL_HOME_DEVICE_USER_INFO = URL_API+"common/counts";
	//首页-获取首页报表数据
	public static String URL_HOME_CHART_INDEX = URL_API+"chart/index";
	//首页-当日告警信息
	public static String URL_HOME_CHART_TODAY = URL_API+"chart/allEquipmentHandleStatus";
	//首页-首页综合统计报表
	public static String URL_HOME_CHART_TIME_REPORT_DATA= URL_API+"chart/getTimeReportData";
	//首页-首页综合统计报表
	public static String URL_HOME_NEWS= URL_API+"map/queryPushMessage";






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



}
