package com.ffapp.waterprice.basis;

import android.os.Environment;

public class Constants {

	public static final boolean DEBUG = true;
	//开发地址
	 public static final String URL_SERVICE = "http://api.dev.gk100.ff-cloud.net/";

	public static String URL_API = "";

	//登录-登录
	public static String URL_LOGIN = URL_API+"login";

	//首页-天气
	public static String URL_HOME_WEATHER = URL_API+"weather/get";

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



}
