package com.ffapp.baseapp.basis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.text.format.Time;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

/**
 * 异常日志保存类
 * 
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

	private static final int MAX_LOG_MESSAGE_LENGTH = 200000;

	private static final long LOG_OUT_TIME = 1000 * 60 * 60 * 24 * 14; // 14天 *
																		// 60 *
																		// 24 *
																		// 5

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	// private static final String LOG_FILE= "exception.log.txt";

	public static final String LOG_DIR = Constants.DIR_LOG;

	public static final String LOG_CRASH = LOG_DIR + "error.log";

	private static final UncaughtExceptionHandler defaultHandler = Thread
			.getDefaultUncaughtExceptionHandler();

	private String verName;

	private int verCode;

	private String phone_model;

	private String phone_sdk;

	@SuppressWarnings("deprecation")
	public ExceptionHandler(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
//			BasisApp.isProgramExit = true;
			// 获取基本信息
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			verName = pi.versionName;
			verCode = pi.versionCode;
			phone_model = android.os.Build.MODEL;
			phone_sdk = android.os.Build.VERSION.SDK;
			new File(LOG_DIR).mkdirs();
			 //删除过期的日志
			new Thread() {
				@Override
				public void run() {
					deleteOutLogs();
				}
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteOutLogs() {
		File dir = new File(LOG_DIR);
		try {
			final long currTime = System.currentTimeMillis();
			File[] files = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					File f = new File(dir.getAbsolutePath() + "/" + filename);
					long time = f.lastModified();
					if (currTime - time > LOG_OUT_TIME) {
						return true;
					}
					return false;
				}
			});
			if (files == null) {
				return;
			}
			for (File f : files) {
				f.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		final Writer stackResult = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stackResult);
		e.printStackTrace(printWriter);
		try {
			File dir = new File(LOG_DIR);
			if (!dir.isDirectory() && !dir.exists()) {
				dir.mkdirs();
			}
			Time tmtxt = new Time();
			tmtxt.setToNow();
			String sTime = tmtxt.format("%Y-%m-%d %H:%M:%S");

			File logFile = new File(LOG_DIR + "/" + System.currentTimeMillis()
					+ ".log");
			logFile.createNewFile();

			BufferedWriter bos = new BufferedWriter(new FileWriter(logFile));
			bos.write("\t\n==================LOG=================\t\n");
			bos.write("APP_VERSION:" + verName + "|" + verCode + "\t\n");
			bos.write("PHONE_MODEL:" + phone_model + "\t\n");
			bos.write("ANDROID_SDK:" + phone_sdk + "\t\n");
			bos.write(sTime + "\t\n");
			bos.write(stackResult.toString());
			bos.write("\t\n--------------------------------------\t\n");
			bos.flush();
			StringBuilder log = getLog();
			int keepOffset = Math.max(log.length() - MAX_LOG_MESSAGE_LENGTH, 0);
			if (keepOffset > 0) {
				log.delete(0, keepOffset);
			}
			bos.write(getLog().toString());
			bos.flush();
			bos.close();
		} catch (Exception ebos) {
			ebos.printStackTrace();
		}
		defaultHandler.uncaughtException(t, e);
	}

	public StringBuilder getLog() {

		final StringBuilder log = new StringBuilder();
		try {
			ArrayList<String> commandLine = new ArrayList<String>();
			commandLine.add("logcat");//$NON-NLS-1$
			commandLine.add("-d");//$NON-NLS-1$

			Process process = Runtime.getRuntime().exec(
					commandLine.toArray(new String[0]));
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				log.append(line);
				log.append(LINE_SEPARATOR);
			}
		} catch (IOException e) {
			Log.e("TAG", "getLog failed", e);//$NON-NLS-1$
		}
		return log;
	}

	/**
	 * 保存内存堆栈信息
	 */
	public static void saveHprofData() {
		try {
			Debug.dumpHprofData(LOG_DIR + System.currentTimeMillis() + ".hprof");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
