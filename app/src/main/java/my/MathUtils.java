package my;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 科学计算数字的帮助类
 */
public class MathUtils {
	/**
	 * 返回科学计算后的乘法结果
	 * 
	 * @param val1
	 * @param val2
	 * @param mc
	 *            精度
	 * @param more
	 *            更多
	 * @return
	 */
	public static double multiply(double val1, double val2, int mc,
			double... more) {
		if (val1 == 0 || val2 == 0)
			return 0;
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);

		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				if (i < len - 1) {
					result = result.multiply(new BigDecimal(more[i]));
				} else {
					result = result.multiply(new BigDecimal(more[i]),
							new MathContext(mc, RoundingMode.HALF_UP));
				}
			}
		} else {
			result = bg1.multiply(bg2,
					new MathContext(mc, RoundingMode.HALF_UP));
		}

		return result.doubleValue();
	}

	/**
	 * 返回科学计算后的除法结果
	 * 
	 * @param fz
	 *            分子
	 * @param fm
	 *            分母
	 * @param mc
	 *            精度
	 * @param more
	 *            更多
	 * @return
	 */
	public static double divide(double fz, double fm, int mc, double... more) {
		if (fz == 0 || fm == 0)
			return 0;
		BigDecimal bg1 = new BigDecimal(fz);
		BigDecimal bg2 = new BigDecimal(fm);
		BigDecimal result = new BigDecimal(0);

		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				if (i < len - 1) {
					result = result.divide(new BigDecimal(more[i]));
				} else {
					result = result.divide(new BigDecimal(more[i]),
							new MathContext(mc, RoundingMode.HALF_UP));
				}
			}
		} else {
			result = bg1.divide(bg2, new MathContext(mc, RoundingMode.HALF_UP));
		}

		return result.doubleValue();
	}

	/**
	 * 返回科学计算后的减法结果
	 * 
	 * @param val1
	 * @param val2
	 * @param mc
	 *            精度
	 * @param more
	 *            更多
	 * @return
	 */
	public static double subtract(double val1, double val2, int mc,
			double... more) {
		if (val2 == 0)
			return val1;
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);

		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				if (i < len - 1) {
					result = result.subtract(new BigDecimal(more[i]));
				} else {
					result = result.subtract(new BigDecimal(more[i]),
							new MathContext(mc, RoundingMode.HALF_UP));
				}
			}
		} else {
			result = bg1.subtract(bg2,
					new MathContext(mc, RoundingMode.HALF_UP));
		}

		return result.doubleValue();
	}

	/**
	 * 返回科学计算后的加法结果
	 * 
	 * @param val1
	 * @param val2
	 * @param mc
	 *            精度
	 * @param more
	 *            更多
	 * @return
	 */
	public static double add(double val1, double val2, int mc, double... more) {
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);

		if (more.length > 0) {
			for (int i = 0, len = more.length; i < len; i++) {
				if (i < len - 1) {
					result = result.add(new BigDecimal(more[i]));
				} else {
					result = result.add(new BigDecimal(more[i]),
							new MathContext(mc, RoundingMode.HALF_UP));
				}
			}
		} else {
			result = bg1.add(bg2, new MathContext(mc, RoundingMode.HALF_UP));
		}

		return result.doubleValue();
	}

	/**
	 * 返回科学计算后的加法结果
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double add(double val1, double val2) {
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);

		result = bg1.add(bg2);

		return result.doubleValue();
	}

	/**
	 * 返回科学计算后的乘法结果
	 * 
	 * @param val1
	 * @param val2
	 *            精度
	 *            更多
	 * @return
	 */
	public static double multiply(double val1, double val2) {
		if (val1 == 0 || val2 == 0)
			return 0;
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);
		result = bg1.multiply(bg2, new MathContext(2, RoundingMode.DOWN));
		return result.doubleValue();
	}

	public static double div(double d1, double d2) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		
		return b1.divide(b2).doubleValue();
	}
	public static String div(String d1, String d2) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		
		return b1.divide(b2).toString();
	}

	
	public static int compareTo(double val1, double val2) {
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		return bg1.compareTo(bg2);
	}

	/**
	 * 返回科学计算后的减法结果
	 * 
	 * @param val1
	 * @param val2
	 * @return
	 */
	public static double subtract(double val1, double val2) {
		BigDecimal bg1 = new BigDecimal(val1);
		BigDecimal bg2 = new BigDecimal(val2);
		BigDecimal result = new BigDecimal(0);

		result = bg1.subtract(bg2);

		return result.doubleValue();
	}
	
	
	public static double setScale(double f,int newScale ,int roundingMode) {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, roundingMode).doubleValue();
        
        return f1;
    }
}
