package my;

import android.text.TextUtils;

import com.ffapp.baseapp.basis.BasisApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {
	public static boolean isMobileNO(String mobiles) {
		if (TextUtils.isEmpty(mobiles)) {
			return false;
		}
//		Pattern p = Pattern
//				.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
//
//		Matcher m = p.matcher(mobiles);
//
//		return m.matches();

		String pattern = "^1[\\d]{10}";

		boolean isMatch = Pattern.matches(pattern, mobiles);
		return isMatch;
	}
	
	/**
	 * 检查Email格式是否正确
	 * 
	 * @param line
	 * @return
	 */
	public static boolean checkEmail(String line) {
		if (line == null || line.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(line);
		return matcher.matches();
	}
	
	public static boolean checkID(String IDStr) {
		if (TextUtils.isEmpty(IDStr)) {
			return false;
		}
		Pattern p = Pattern
				.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher m = p.matcher(IDStr);
		return m.matches();
	}

	public static boolean checkPassword(String password) {
		if (TextUtils.isEmpty(password)) {
			return false;
		}
//		Pattern p = Pattern
//				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
		Pattern p = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}
	
	public static boolean checkPasswordLength(String password) {
		if (TextUtils.isEmpty(password)) {
			return false;
		}
		 int strLenth = password.length();
         if (strLenth < 8 || strLenth > 20) {
            return false;
         }
		return true;
	}

	public static boolean isAllChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isContansChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isStrEmpty(String str) {
		if (TextUtils.isEmpty(str)) {
			return true;
		}
		return false;
	}


	public static boolean checkEmptyToast(String str,String toast) {
		if (TextUtils.isEmpty(str)) {
			BasisApp.showToast(toast);
			return true;
		}
		return false;
	}
}
