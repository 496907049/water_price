package my;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StringUtil {

    public static List<String> toListFromString(String s) {
        List<String> list = new ArrayList<String>();
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        if (s.indexOf(",") == -1) {
            list.add(s);
            return list;
        }
        String[] arrayStr = new String[]{};
        arrayStr = s.split(",");
        list = Arrays.asList(arrayStr);
        return list;
    }

    public static ArrayList<String> toListFromString2(String s) {
        ArrayList<String> list = new ArrayList<String>();
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        if (s.indexOf(",") == -1) {
            list.add(s);
            return list;
        }
        String[] arrayStr = new String[]{};
        arrayStr = s.split(",");
        list = (ArrayList<String>) Arrays.asList(arrayStr);
        return list;
    }

    public static ArrayList<String> HashMap2ArrayList(
            HashMap<String, String> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : map.keySet()) {
            list.add(map.get(key));
        }

        return list;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        dest = str;
        return dest.replaceAll("\\s", " ").replaceAll("[' ']+", " ");
    }

    public static String UnicodeToString(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(
                        utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * unicode 转换成 中文
     *
     * @param theString
     * @return
     */

    public static String decodeUnicode(String theString) {

        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {

            aChar = theString.charAt(x++);

            if (aChar == '\\') {

                aChar = theString.charAt(x++);

                if (aChar == 'u') {

                    // Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

    public static String reEncoding(String text, String newEncoding) {
        String str = null;
        try {
            str = new String(text.getBytes(), newEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public static String getNumHanzi(int i) {
        switch (i) {
            case 0:
                return "零";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
        }
        return "";
    }

    /***
     * 计算百分比
     * **/
    public static String getPercent(int x, int total) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) x / (float) total * 100);
        return result +"%";
    }


}
