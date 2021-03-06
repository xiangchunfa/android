package com.zqds.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

public class BitmapUtils {
	/**
	 * TAG
	 */
	private static final String TAG = "BitmapUtils";
	
	public static Bitmap getBitmap(InputStream is){
	     BitmapFactory.Options opt = new BitmapFactory.Options();
	     opt.inPreferredConfig=Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
	     opt.inInputShareable=true;
	     opt.inPurgeable=true;//设置图片可以被回收
	     return BitmapFactory.decodeStream(is, null, opt);
	}
	
	//将Bitmap 转换成数组
	public static byte[] bitmapToBytes(Bitmap bitmap){
		  if (bitmap == null) {
		     return null;
		  }
		  final ByteArrayOutputStream os = new ByteArrayOutputStream();
		  // 将Bitmap压缩成PNG编码，质量为100%存储
		  bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);//除了PNG还有很多常见格式，如jpeg等。
		  return os.toByteArray();
	}
	
	public static Bitmap getBitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	public static Bitmap getBitmap(byte[] data, int scale) {
		Options opts = new Options();
		opts.inSampleSize = scale;
		opts.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
	}

	public static Bitmap getBitmap(byte[] data, int widht, int height) {
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
	    opts.inInputShareable=true;
	    opts.inPurgeable=true;//设置图片可以被回收
		BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		int scaleX = opts.outWidth / widht;
		int scaleY = opts.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		return getBitmap(data, scale);
	}

	/**
     * 
     * @param path
     * @param width
     * @param height
     * @return
     * @throws FileNotFoundException
     */
	public static Bitmap getBitmap(String path) throws FileNotFoundException{
		InputStream is = new FileInputStream(path);
		Options opts = new Options();
		opts.inPreferredConfig=Bitmap.Config.RGB_565;//表示16位位图 565代表对应三原色占的位数
		opts.inJustDecodeBounds = true;
	    opts.inInputShareable=true;
	    opts.inPurgeable=true;//设置图片可以被回收
	    BitmapFactory.decodeStream(is, null, opts);
		opts.inSampleSize = 5;
		opts.inJustDecodeBounds = false;
		is = new FileInputStream(path);
		return BitmapFactory.decodeStream(is, null, opts);
	}
	
	public static void saveBitmap(Bitmap bitmap ,String path) throws IOException{
		//根据路径创建文件对象
		if(path.endsWith(".bmp")){
			path = path.substring(0, path.lastIndexOf("."))+".jpg";
		}
		File file = new File(path);
		System.out.println("save path Util : "+path);
		//如果该 文件夹不存在，则创建该文件夹
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if(!file.exists()){
			file.createNewFile();
		}
		//创建指向该文件的输出流对象
		FileOutputStream stream = new FileOutputStream(file);
		//保存图片到该文件
		bitmap.compress(CompressFormat.JPEG, 100, stream);
	}
	
	
   /**
    * 保存上传的图片
    * @param fileName
    * @param bm
    * @param fileType
    * @throws IOException
    */
   public static void saveBitmapByAbsolutePath(Context context,String fileName, Bitmap bm) throws IOException {
       FileOutputStream outStream = null;
       try {
           outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
           if (outStream != null) {
               bm.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
           }
       } catch (FileNotFoundException e) {
           LogUtils.e(TAG, "", e);
       } catch (Exception e) {
           LogUtils.e(TAG, "", e);
       } finally {
           if (outStream != null) {
               try {
                   outStream.close();
                   outStream = null;
               } catch (Exception e) {
                   LogUtils.e(TAG, "", e);
               }
           }
           if(bm != null){
				// 释放原始图片占用的内存，防止out of memory异常发生
				//bm.recycle();
           }
       }
   }
   
   public static void saveBitmapByAbsolutePath(Context context,String fileName, Bitmap bm,int persent) throws IOException {
       FileOutputStream outStream = null;
       try {
           outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
           if (outStream != null) {
               bm.compress(Bitmap.CompressFormat.JPEG, persent, outStream);
           }
       } catch (FileNotFoundException e) {
           LogUtils.e(TAG, "", e);
       } catch (Exception e) {
           LogUtils.e(TAG, "", e);
       } finally {
           if (outStream != null) {
               try {
                   outStream.close();
                   outStream = null;
               } catch (Exception e) {
                   LogUtils.e(TAG, "", e);
               }
           }
           if(bm != null){
				// 释放原始图片占用的内存，防止out of memory异常发生
				bm.recycle();
           }
       }
   }
	
	/**
	 * 清空所有图片缓存
	 */
	public static void clearImageCache(File imageCacheDir){
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			File[] imageFiles = imageCacheDir.listFiles();
			int length = imageFiles.length;
			for(int i=0;i<length;i++){
				if(imageFiles[i].isDirectory()){
					 clearImageCache(imageFiles[i]);
				}
				imageFiles[i].delete();
			}
		}
	}
	
	/**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
		Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        		bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	

	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
    
	/**
     * 对图片进行压缩(按照比例大小压缩)
     * @param bitmap bitmap对象
     * @param width 宽度
     * @param height 高度
     * @return
     */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}
	
	/**
     * 对图片进行压缩(按照质量压缩)
     * @param bitmap bitmap对象
     * @param width 宽度
     * @param height 高度
     * @return
     */
	public static Bitmap compressImage(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / (1024 * 1024) >1) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            options /= 1.1;//每次都除以1.1  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
	
	 /**
     * 保存上传的图片
     * @param fileName 文件名
     * @param bm 位图对象
     * @throws IOException
     */
    public static String saveBitmap(Context context, String fileName, Bitmap bm) throws IOException {
        FileOutputStream outStream = null;
        try {
            outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (outStream != null) {
                bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            }
        } catch (FileNotFoundException e) {
            LogUtils.e(TAG, "", e);
        } catch (Exception e) {
            LogUtils.e(TAG, "", e);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                    outStream = null;
                } catch (Exception e) {
                    LogUtils.e(TAG, "", e);
                }
            }
        }
        return fileName;
    }
    
    public static Bitmap getBitmapFromRes(Context context, int resId) {
        Bitmap bm = null;
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bm = BitmapFactory.decodeResource(context.getResources(), resId, opt);
        } catch (OutOfMemoryError e) {
            LogUtils.e(TAG, "", e);
        }
        return bm;
    }
    
    /**
     * 压缩图片文件
     * @param act
     * @param tempfileName 临时图片文件名
     * @return 返回压缩后图片路径
     */
	public static String compress(Activity act, String tempfileName) {
		String tempPath = getAvatarFile(act, tempfileName).getAbsolutePath();
		File file = new File(tempPath);
		FileOutputStream outStream = null;
		int persent = 100;
		Bitmap bm = null;
		try {
			bm = BitmapUtils.getBitmap(tempPath);
			while (file.length() > (1024 * 1024)) {
				persent /= 2;// 每次都除以2
				outStream = act.openFileOutput(tempfileName, Context.MODE_PRIVATE);
				if (outStream != null) {
					bm.compress(Bitmap.CompressFormat.JPEG, persent, outStream);
				}
			}
		} catch (FileNotFoundException e) {
			tempPath = "";
			LogUtils.e(TAG, "", e);
		} catch (Exception e) {
			tempPath = "";
			LogUtils.e(TAG, "", e);
		} 
		if (outStream != null) {
			try {
				outStream.close();
				outStream = null;
			} catch (Exception e) {
				LogUtils.e(TAG, "", e);
			}
		}
		if (bm != null) {
			// 释放原始图片占用的内存，防止out of memory异常发生
			bm.recycle();
		}
		return tempPath;
	}
	
	/**
	 * 得到压缩文件的路径 
	 * @param context 
	 * @param fileName 文件名
	 * @return
	 */
	private static File getAvatarFile(Activity context, String fileName) {
		File file = context.getFilesDir();
		String path = file.getAbsolutePath();
		return new File(path + File.separator + fileName);
	}
}
