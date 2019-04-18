package my;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ImageUtils {
	/**
	 * 获得圆角图片
	 * 
	 * @author huxj
	 * @param bitmap
	 *            Bitmap资源
	 * @param roundPx
	 *            弧度
	 * @return Bitmap 圆角Bitmap资源
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);

		paint.setColor(color);

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static void saveMyBitmap(Bitmap bmp, String path) {
		File f = new File(path);
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			bmp.compress(CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String savePicToSdcard(Bitmap bitmap, String path,
			String fileName) {
		String filePath = "";
		if (bitmap == null) {
			return filePath;
		} else {
			File parentFile = new File(path);
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}

			filePath = path + "/"+fileName;
			File destFile = new File(filePath);
			OutputStream os = null;
			try {
				destFile.createNewFile();
				os = new FileOutputStream(destFile);
				bitmap.compress(CompressFormat.JPEG, 100, os);
				os.flush();
				os.close();
			} catch (IOException e) {
				filePath = "";
			}
		}
		return filePath;
	}

	public static Bitmap getBitmapNoCompress(String path) {
		if (path != null) {
			BitmapFactory.Options options = null;
			InputStream is = null;
			try {
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				int w = options.outWidth;
				int h = options.outHeight;
				options = new BitmapFactory.Options();
				options.inSampleSize = Math.max(
						(int) (w / SystemParamsUtils.getScreenWidth()), h
								/ SystemParamsUtils.getScreenHeight());
				options.inJustDecodeBounds = false;
				options.inPurgeable = true;
				options.inInputShareable = true;
				is.close();
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Bitmap getBitmap(String path) {
		if (path != null) {
			BitmapFactory.Options options = null;
			InputStream is = null;
			try {
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				int w = options.outWidth;
				int h = options.outHeight;
				options = new BitmapFactory.Options();
				options.inSampleSize = Math.max(
						(int) (w / SystemParamsUtils.getScreenWidth()), h
								/ SystemParamsUtils.getScreenHeight());
				options.inJustDecodeBounds = false;
				options.inPreferredConfig = Config.ARGB_8888;
				options.inPurgeable = true;
				options.inInputShareable = true;
				is.close();
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Bitmap getBitmapBySize(String path, int maxWidth,
			int maxHeight) {
		if (path != null) {
			BitmapFactory.Options options = null;
			InputStream is = null;
			try {
				options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				int w = options.outWidth;
				int h = options.outHeight;
				options = new BitmapFactory.Options();
				options.inSampleSize = Math.max((int) (w / maxWidth), h
						/ maxHeight);
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);
				return BitmapFactory.decodeStream(is, null, options);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Bitmap getBitmapOrg(String path) {
		if (path != null) {
			InputStream is = null;
			Bitmap bm = null;
			BitmapFactory.Options options = null;
			try {
				options = new BitmapFactory.Options();
				options.inTempStorage = new byte[1024 * 1024 * 8]; // 5MB的临时存储空间
				options.inJustDecodeBounds = false;
				options.inPreferredConfig = Config.ARGB_4444;
				options.inPurgeable = true;
				options.inInputShareable = true;
				is = new FileInputStream(path);
				bm = BitmapFactory.decodeStream(is, null, options);

				return bm;
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Bitmap getBitmapHalf(String path) {
		if (path != null) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is = new FileInputStream(path);
				options = new BitmapFactory.Options();
				options.inSampleSize = 2;// 缩小2倍
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);

				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap getBitmapFour(String path) {
		if (path != null) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is = new FileInputStream(path);
				options = new BitmapFactory.Options();
				options.inSampleSize = 4;// 缩小4倍
				options.inJustDecodeBounds = false;
				is.close();
				is = new FileInputStream(path);

				return BitmapFactory.decodeStream(is, null, options);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap getFitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		try {
			File file = new File(path);
			Bitmap resizeBmp = null;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 数字越大读出的图片占用的heap越小 不然总是溢出
			if (file.length() < 20480) { // 0-20k
				opts.inSampleSize = 1;
			} else if (file.length() < 51200) { // 20-50k
				opts.inSampleSize = 2;
			} else if (file.length() < 307200) { // 50-300k
				opts.inSampleSize = 4;
			} else if (file.length() < 819200) { // 300-800k
				opts.inSampleSize = 6;
			} else if (file.length() < 1048576) { // 800-1024k
				opts.inSampleSize = 8;
			} else {
				opts.inSampleSize = 10;
			}
			resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
			return resizeBmp;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Bitmap getBitmapForResource(Context context, int resourceId) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(context.getResources(), resourceId,
					options);
			int w = options.outWidth;
			int h = options.outHeight;
			options = new BitmapFactory.Options();
			// options.inSampleSize = 5;
			options.inSampleSize = Math.max((int) (w / SystemParamsUtils.getScreenWidth()),
					h / SystemParamsUtils.getScreenHeight());
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(context.getResources(),
					resourceId, options);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressLint("NewApi")
	public static Bitmap stringtoBitmap(String string) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            所要转换的bitmap
	 * @param //newWidth新的宽
	 * @param //newHeight新的高
	 * @return 指定宽高的bitmap
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片

		Bitmap newbm = null;
		try {
			newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return newbm;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            所要转换的bitmap ，按宽度保持比例
	 * @param //newWidth新的宽
	 * @return 指定宽的bitmap
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	public static Bitmap zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

	// drawable转换成bitmap类型
	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
						: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		// canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获得图片的高与宽度的比例
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static float getImagehwScale(Context context, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		return ((float) options.outHeight) / options.outWidth;
	}

	@SuppressWarnings("deprecation")
	public static Drawable cropDrawable(Context context, int resId, int wSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		// int width = options.outWidth;
		int height = options.outHeight;
		options.inSampleSize = SystemParamsUtils.getScreenHeight() / height;
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		Bitmap bt = BitmapFactory.decodeResource(context.getResources(), resId,
				options);
		bt = Bitmap.createBitmap(bt, 0, 0, wSize, bt.getHeight());
		return new BitmapDrawable(bt);
	}

	public static Bitmap getBitmapForResourceBySize(Context context,
			int resourceId, int maxWidth, int maxHeight) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(context.getResources(), resourceId,
					options);
			int w = options.outWidth;
			int h = options.outHeight;
			options = new BitmapFactory.Options();
			// options.inSampleSize = 5;
			// options.inSampleSize = Math.max((int) (w / BaseApp.sScreenWidth),
			// h
			// / BaseApp.sScreenHeight);
			options.inSampleSize = Math
					.max((int) (w / maxWidth), h / maxHeight);
			options.inJustDecodeBounds = false;
			options.inPurgeable = true;
			options.inInputShareable = true;
			Bitmap bit = null;
			InputStream is = null;
			try {
				// bit = BitmapFactory.decodeResource(context.getResources(),
				// resourceId, options);
				is = context.getResources().openRawResource(resourceId);
				bit = BitmapFactory.decodeStream(is, null, options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (bit != null) {
				return bit;
			} else {
				options.inSampleSize = 10;
				is = context.getResources().openRawResource(resourceId);
				bit = BitmapFactory.decodeStream(is, null, options);
				return bit;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件流转为base64
	 * 
	 * @param bitmapPath
	 * @return
	 */
	public static String filePathToBase64(String bitmapPath) {

		String result = null;
		File file = null;
		FileInputStream inputStream = null;
		try {
			file = new File(bitmapPath);
			inputStream = new FileInputStream(file);
			int length = inputStream.available();
			byte[] bytes = new byte[length];
			inputStream.read(bytes);
			result = Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * bitmap转为base64
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 * 
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * base64转为bitmap
	 * 
	 * @param base64Data
	 * @return
	 */
	public static boolean saveBase64ToFile(Context mContext, String base64Data,
			File file) {
		FileOutputStream fos = null;
		try {
			if(file.exists()){
				file.delete();
				file.createNewFile();
			}else {
//				file.mkdirs();
//				file.mkdir();
				file.createNewFile();
			}
			byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
			 fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
//			LogUtil.i("file--name"+file.getName());
//			LogUtil.i("file--path"+file.getAbsolutePath());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				 if(null!=fos)
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}


	//保存view的图片至文件夹
	public static void saveBitmap(View view, String filePath){

		// 创建对应大小的bitmap
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

		//存储
		FileOutputStream outStream = null;
		File file=new File(filePath);
		if(file.isDirectory()){//如果是目录不允许保存
			return;
		}
		try {
			outStream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, outStream);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bitmap.recycle();
				if(outStream!=null){
					outStream.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
