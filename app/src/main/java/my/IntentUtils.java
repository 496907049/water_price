package my;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisApp;

import java.io.File;
import java.util.List;

public class IntentUtils {

    /**
     * 以文本形式发送来分享内容，支持文本和图片以及链接
     **/
    public static void doShare(Context mContext, String info, String picPath,
                               String linkUrl) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.putExtra(Intent.EXTRA_TEXT, info + linkUrl);
        intent.putExtra(Intent.EXTRA_TITLE, mContext.getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.app_name));
        if (!TextUtils.isEmpty(picPath) && FileUtils.isFileExist(picPath)) {
            Uri uri = Uri.parse("file:///" + picPath);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }
        intent.putExtra("sms_body", info + linkUrl);

        intent.setType("image/*");
        intent.setType("text/plain");
        try {
            mContext.startActivity(Intent.createChooser(intent, "分享"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看链接
     */
    public static void ViewUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 拨打电话，弹出对话框确认
     */
    public static void CallWithShowDialog(final Context context,
                                          final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("拨打电话 " + number + " ？");
        DialogInterface.OnClickListener l = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
            }
        };
        builder.setPositiveButton("确定", l);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    /**
     * 发送短信
     *
     * @param body 短信内容
     */
    public static void sendSMS(Context context, String number, String body) {
        try {
            String head = "smsto:";
            if (number.contains(";")) {
                head = "smsto:";
            }
            LogUtil.e("number ---->" + number);
            Uri smsToUri = Uri.parse(head + number);
            Intent mIntent = new Intent(Intent.ACTION_SENDTO,
                    smsToUri);
            mIntent.putExtra("sms_body", body);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Intent mIntent = new Intent(Intent.ACTION_VIEW);
            // mIntent.putExtra("address", number);
            // mIntent.setType("vnd.android-dir/mms-sms");
            context.startActivity(mIntent);
        } catch (Exception e) {
            BasisApp.showToast("");
        }
    }

    /**
     * 拨号
     */
    public static void call(final Context context, String number) {
        try {
            if (number.contains(";")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final String[] numbers = number.split(";");
                builder.setItems(numbers,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                call(context, numbers[which]);
                            }
                        });
                builder.create().show();
            } else {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.setData(Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            BasisApp.showToast("拨号失败");
        }
    }

    /**
     * 拨号不直接拨打，只是到拨号盘
     */
    public static void callToDial(final Context context, String number) {
        try {
            if (number.contains(";")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final String[] numbers = number.split(";");
                builder.setItems(numbers,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                callToDial(context, numbers[which]);
                            }
                        });
                builder.create().show();
            } else {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
            }
        } catch (Exception e) {
            BasisApp.showToast("拨号失败");
        }
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(Context context, String emial) {
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setType("plain/text");
        String[] strEmailReceive = new String[]{emial};
        mEmailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL,
                strEmailReceive);
        mEmailIntent.putExtra(Intent.EXTRA_CC, "");
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(mEmailIntent, "Send Email"));
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context, String pacagename) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = pacagename;

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkActivityRunning(Context context, Class<?> name) {
        Intent mainIntent = new Intent(context, name);
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> appTask = am.getRunningTasks(1);
        if (appTask.size() > 0
                && appTask.get(0).baseActivity
                .equals(mainIntent.getComponent())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkServiceRunning(Context context, Class<?> name) {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (name.getName().equals(service.service.getClassName())) {
                isServiceRunning = true;
            }

        }
        return isServiceRunning;

    }

    public static boolean checkAppRunning(Context context, boolean needOpen) {
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        int MAX_RECENT_TASKS = 24;
        // 从ActivityManager中取出用户最近launch过的 MAX_RECENT_TASKS + 1 个，以从早到晚的时间排序，
        // 注意这个 0x0002,它的值在launcher中是用ActivityManager.RECENT_IGNORE_UNAVAILABLE
        // 但是这是一个隐藏域，因此我把它的值直接拷贝到这里
        final List<ActivityManager.RecentTaskInfo> recentTasks = am
                .getRecentTasks(MAX_RECENT_TASKS + 1, 0x0002);

        // 这个activity的信息是我们的launcher
        // ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory(
        // Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
        int numTasks = recentTasks.size();

        for (int i = 0; i < numTasks && (i < MAX_RECENT_TASKS); i++) {
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
            if (info.baseIntent.getComponent().getPackageName()
                    .equals(context.getPackageName())) {

                if (needOpen) {
                    Intent intent = new Intent(info.baseIntent);
                    if (info.origActivity != null) {
                        intent.setComponent(info.origActivity);
                    }
                    intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 根据包名检测app是否安装
     */
    public static boolean checkAppInstalled(String packagename, Context mContext) {
        PackageInfo packageInfo;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(
                    packagename, 0);
            Log.v("CheckAppInstalled", "packageInfo------>" + packageInfo);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            Log.e("isInstalledApp", "not installed");
            return false;
        } else {
            Log.e("isInstalledApp", "is installed");
            return true;
        }
    }

    public static void uninstalllAppByPackage(Context mContext,
                                              String packagename) {
        Uri packageURI = Uri.parse("package:" + packagename);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mContext.startActivity(uninstallIntent);
    }

    /**
     * 根据包名得到app版本号
     */
    public static int getAppInstalledVersionCode(String packagename,
                                                 Context mContext) {
        PackageInfo packageInfo;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(
                    packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            Log.e("isInstalledApp", "not installed");
        } else {
            Log.e("isInstalledApp", "is installed ,versionCode is  "
                    + packageInfo.versionCode);
            return packageInfo.versionCode;
        }
        return -1;
    }

    /**
     * 根据app包名打开对应程序
     */
    public static void openAppByPacagename(Context mContext, String packagename) {
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage(packagename);
        intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent != null) {
            mContext.startActivity(intent);
            return;
        }

        intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(packagename);
        PackageManager pManager = mContext.getPackageManager();
        List<ResolveInfo> apps = pManager.queryIntentActivities(intent, 0);
        try {
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packagename = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                intent = new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(packagename, className);
                intent.setComponent(cn);
                intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                LogUtil.i("未找到packagename对应的apk");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 根据app包名打开对应程序
     */
    public static Intent getAppIntentByPacagename(Context mContext,
                                                  String packagename) {
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage(packagename);
        if (intent != null) {
            // mContext.startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        }

        intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(packagename);
        PackageManager pManager = mContext.getPackageManager();
        List<ResolveInfo> apps = pManager.queryIntentActivities(intent, 0);
        try {
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packagename = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packagename, className);
                intent.setComponent(cn);
                return intent;
                // mContext.startActivity(intent);
            } else {
                LogUtil.i("未找到packagename对应的apk");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据app包名获取应用版本号
     */
    public static int getAppVersionCodeByPacagename(Context mContext,
                                                    String packagename) {
        PackageManager packageManager = mContext.getPackageManager();
        int code = 0;
        try {
            code = packageManager.getPackageInfo(packagename,
                    PackageManager.GET_ACTIVITIES).versionCode;
            LogUtil.i("test----->" + code);
            return code;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }

    @SuppressLint("NewApi")
    public static void showNotification(Context context, Intent intent,
                                        String msgTitle, String msgText) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0); // 消息触发后调用
        Notification n = builder.setContentTitle(msgTitle + "")
                .setContentText(msgText + "").setContentInfo(msgText)
                .setWhen(System.currentTimeMillis()).setAutoCancel(true)
                .setSmallIcon(R.drawable.base_transparent).setContentIntent(pi)
                .build();

        nm.notify(1, n); // 发送通知
    }

    public static void installApp(Context context, String abselutPath) {

        File apkFile = new File(abselutPath);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        context.startActivity(installIntent);
    }

    public static void ToMarketScore(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void sendBrocastAction(Context mContext, String action) {
        if (mContext == null)
            return;
        Intent intent = new Intent(action);
        mContext.sendBroadcast(intent);
    }

    /**
     * 获取其他应用的Intent
     *
     * @param packageName 包名
     * @param className   全类名
     * @return 意图
     */
    public static Intent getComponentNameIntent(String packageName, String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null) intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 拷贝到剪贴板
     */
    public static void CopyToClipboard(Context mContext,
                                       String text) {
        //获取剪贴板管理器：
       ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }
}
