package my;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    public static final int TIME_H = 1000 * 60 * 60;

    public static int countDays(long oldTime) {
        long time = new Date().getTime();
        long objectTime = time - oldTime;
        int days = (int) (objectTime / (3600 * 1000 * 24));
        return days;
        // int day = between/

    }

    public static String formatToFomat(String timeF, String formatF,
                                       String formatR) {
        SimpleDateFormat format = new SimpleDateFormat(formatF);
        Long time = (long) 0;
        try {
            time = format.parse(timeF).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat format2 = new SimpleDateFormat(formatR);

        String d = format2.format(time);
        return d;
    }

    public static long getLongTimeByFormat(String strTime, String format) {
        SimpleDateFormat formatS = new SimpleDateFormat(format);
        long time = 0;
        try {
            time = formatS.parse(strTime).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeLongToStrByFormat(long time, String fomatStr) {
        SimpleDateFormat format = new SimpleDateFormat(fomatStr);
        // Long time = new Long(new Date().getTime());
        String d = format.format(time);
        return d;
    }

    public static String getCurrentTimeByFormat(String fomat) {
        SimpleDateFormat format = new SimpleDateFormat(fomat);
        Long time = Long.valueOf(new Date().getTime());
        String d = format.format(time);

        return d;
    }

    public static String getMonthNameByLong(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTimeInMillis(time * 1000L);
        int monthNum = calendar.get(Calendar.MONTH) + 1;

        String result = "";
        if (currentMonth == monthNum) {
            return "本月";
        } else {
            switch (monthNum) {
                case 1:
                    result = "一月";
                    break;
                case 2:
                    result = "二月";
                    break;
                case 3:
                    result = "三月";
                    break;
                case 4:
                    result = "四月";
                    break;
                case 5:
                    result = "五月";
                    break;
                case 6:
                    result = "六月";
                    break;
                case 7:
                    result = "七月";
                    break;
                case 8:
                    result = "八月";
                    break;
                case 9:
                    result = "九月";
                    break;
                case 10:
                    result = "十月";
                    break;
                case 11:
                    result = "十一月";
                    break;
                case 12:
                    result = "十二月";
                    break;

            }
        }

        return result;
    }

    public static String getWeekName(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
        String str = formatter.format(new Date());
      return str;
    }

    public static String getYearLastTwo(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);

        return new SimpleDateFormat("yy", Locale.CHINESE).format(calendar
                .getTime());

    }

    public static Date getDateByStr(String timeStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Long time = (long) 0;
        try {
            time = format.parse(timeStr).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date data = new Date(time);
        return data;
    }

    public static String getTimeByDate(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(date);
    }

    /**
     * 根据时间类型比较时间大小
     *
     * @param source
     * @param traget
     * @param type      "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义
     * 传递时间的对比格式
     * @return 0 ：source和traget时间相同
     * 1 ：source比traget时间大
     * -1：source比traget时间小
     * @throws Exception
     */
    public static int DateCompare(String source, String traget, String type) {

        int ret = 2;
        try {
            SimpleDateFormat format = new SimpleDateFormat(type);
            Date sourcedate = format.parse(source);
            Date tragetdate = format.parse(traget);
            ret = sourcedate.compareTo(tragetdate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 根据时间类型比较时间大小
     *
     * @param source
     * @param traget
     * 传递时间的对比格式
     * @return 0 ：source和traget时间相同
     * 1 ：source比traget时间大
     * -1：source比traget时间小
     * @throws Exception
     */
    public static int DateCompare(long source, long traget) {

        LogUtil.i("intDateCompare-->"+source);
        LogUtil.i("intDateCompare-->"+traget);
        int ret = 2;
        try {
            Date sourcedate =new Date(source);
            Date tragetdate = new Date(traget);
            ret = sourcedate.compareTo(tragetdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past, String formatStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String result = format.format(today);
        return result;
    }


    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param time1 时间参数 1：
     * @param time2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(long time1, long time2) {
        long days = 0;
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
            LogUtil.i("getDistanceDays--->"+days);
        return days;
    }


    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTimeFuture(long nowtime, long futuretime) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        one = new Date(nowtime);
        two = new Date(futuretime);
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx小时
     */
    public static String getDistanceTimeFutureHour(long nowtime, long futuretime) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        one = new Date(nowtime);
        two = new Date(futuretime);
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
        if(hour == 0){
         return    min+"分钟";
        }
        return  hour + "小时" ;
    }

}
