package my.useful;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.ffapp.waterprice.BuildConfig;
import com.ffapp.waterprice.R;
import com.ffapp.waterprice.basis.BasisApp;
import com.ffapp.waterprice.basis.Constants;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import my.LogUtil;
import my.http.HttpRestClient;


public class DownAPKService extends Service {

    private final int NotificationID = 0x10000;
    private NotificationManager mNotificationManager = null;
    private NotificationCompat.Builder builder;

    // 文件下载路径
    private String APK_url = "";
    // 文件保存路径(如果有SD卡就保存SD卡,如果没有SD卡就保存到手机包名下的路径)
    private String APK_dir = "";
    private String app_name = "";
    Intent installIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAPKDir();// 创建保存路径
    }

    public static void DownloadAndShowNotifition(Context mContext, String name, String url) {
        Intent intent = new Intent(mContext, DownAPKService.class);
        intent.putExtra("app_name", name);
        intent.putExtra("apk_url", url);
//		intent.putExtra("iconImg", iconImg);
        mContext.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        // 接收Intent传来的参数:
        APK_url = intent.getStringExtra("apk_url");
        app_name = intent.getStringExtra("app_name");
        initAPKDir();
        DownFile(APK_url);

        return super.onStartCommand(intent, flags, startId);
    }

    private void initAPKDir() {
        /**
         * 创建路径的时候一定要用[/],不能使用[\],但是创建文件夹加文件的时候可以使用[\].
         * [/]符号是Linux系统路径分隔符,而[\]是windows系统路径分隔符 Android内核是Linux.
         */
        if (isHasSdcard())// 判断是否插入SD卡
        {
            // APK_dir = Environment.getExternalStorageDirectory()
            // .getAbsolutePath() + "/jimeioa/download/";// 保存到SD卡路径下

            APK_dir = Constants.DIR_DOWNLOAD;
        } else {
            APK_dir = getApplicationContext().getFilesDir().getAbsolutePath()
                    + "/download/";// 保存到app的包名路径下
        }
        LogUtil.i("" + APK_dir);
        File destDir = new File(APK_dir);
        if (!destDir.exists()) {// 判断文件夹是否存在
            destDir.mkdirs();
        }
    }

    /**
     * @Description:判断是否插入SD卡
     */
    private boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private void DownFile(String file_url) {
        System.out.println("DownFile----------->" + file_url);
        String[] mAllowedContentTypes = new String[]{".*"};
        // 文件夹地址
        String tempPath = APK_dir;
        // 文件地址
        String filePath = APK_dir + "" + app_name;
        File file = new File(filePath);
        HttpRestClient.download(file_url, new FileAsyncHttpResponseHandler(file) {

            @Override
            public void onSuccess(int i, Header[] headers, File file) {
                installIntent = new Intent(Intent.ACTION_VIEW);
                System.out.println(file.getPath().toString());
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    uri = FileProvider.getUriForFile(BasisApp.getInstance(), BuildConfig.APPLICATION_ID + ".provider", file);
                } else {
                    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    uri = Uri.fromFile(file);
                }
//				uri = Uri.fromFile(file);
                installIntent.setDataAndType(uri,
                        "application/vnd.android.package-archive");

                PendingIntent mPendingIntent = PendingIntent.getActivity(
                        DownAPKService.this, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentText("下载完成,请点击安装");
                builder.setContentIntent(mPendingIntent);
                builder.setSound(null);
//				mNotificationManager.createNotificationChannel(channelbody);

                mNotificationManager.notify(NotificationID, builder.build());
                // 震动提示
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000L);// 参数是震动时间(long类型)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        mNotificationManager.deleteNotificationChannel(NotificationID+"");
                    }
                    mNotificationManager.cancel(NotificationID);
                    //关闭通知通道
                }else{
                    mNotificationManager.cancel(NotificationID);
                }
                startActivity(installIntent);// 下载完成之后自动弹出安装界面
                stopSelf();
            }

            @Override
            public void onFailure(int arg0, Header[] headers, Throwable throwable, File file) {
                // TODO Auto-generated method stub
                System.out.println("文件下载失败");
                mNotificationManager.cancel(NotificationID);
                Toast.makeText(getApplicationContext(), "下载失败，请检查网络！",
                        Toast.LENGTH_SHORT).show();
            }

            long nextchange = 0;
            long everystep = 0;

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                // TODO Auto-generated method stub
                super.onProgress(bytesWritten, totalSize);
                System.out.println("文件下载中" + bytesWritten + "--" + totalSize);
//				LogUtil.i(""+APK_dir);
                int x = Long.valueOf(bytesWritten).intValue();
                int totalS = Long.valueOf(totalSize).intValue();
                builder.setProgress(totalS, x, false);
                builder.setContentInfo(getPercent(x, totalS));
                builder.setContentText("正在下载,请稍后...");
                builder.setSound(null);
                if (nextchange == 0) {
                    everystep = totalSize / 10;
                    nextchange = everystep;
                } else {
                    if (bytesWritten > nextchange) {
                        mNotificationManager.notify(NotificationID, builder.build());
                        nextchange += everystep;
                    } else {

                        if (nextchange >= everystep * 10) {
                            nextchange = 0;
                        }
                    }

                }
            }

            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                super.onStart();
                System.out.println("开始下载文件");
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel(NotificationID+"", "name", NotificationManager.IMPORTANCE_MIN);
                    channel.setSound(null,null);
                    mNotificationManager.createNotificationChannel(channel);
                }
                builder = new NotificationCompat.Builder(
                        getApplicationContext(), NotificationID+"");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setTicker("正在下载");
                builder.setContentTitle(app_name);
                builder.setContentText("正在下载,请稍后...");
                builder.setNumber(0);
                builder.setAutoCancel(true);
                builder.setSound(null);
                builder.setChannelId(NotificationID+"");
                builder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
                mNotificationManager.notify(NotificationID, builder.build());
            }

        });
    }

    /**
     * @param x     当前值
     * @param total 总值 [url=home.php?mod=space&uid=7300]@return[/url] 当前百分比
     * @Description:返回百分之值
     */
    private String getPercent(int x, int total) {
        String result = "";// 接受百分比的值
        double x_double = x * 1.0;
        double tempresult = x_double / total;
        // 百分比格式，后面不足2位的用0补齐 ##.00%
        DecimalFormat df1 = new DecimalFormat("0.00%");
        result = df1.format(tempresult);
        return result;
    }

    /**
     * @return
     * @Description:获取当前应用的名称
     */
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager
                .getApplicationLabel(applicationInfo);
        return applicationName;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    private void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}