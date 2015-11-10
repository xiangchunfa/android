package com.zqds.client.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zqds.client.db.CloseUtils;
import com.zqds.client.helper.Global;
import com.zqds.client.util.LogUtils;
import com.zqds.client.util.StrTime;

public class FileUtil {

	private static String TAG = FileUtil.class.getSimpleName();
	public static File updateDir = null;
	public static File updateFile = null;
	/**下载包安装路径 */
//	private String savePath = Global.TEMP_FILE;

	/***
	 * 创建文件
	 */
	public static void createFile(String name,Context context) {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			updateDir = new File(Global.downLoadPath(context));
			updateFile = new File(updateDir + "/" + name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	@SuppressLint("ParserError")
	public static boolean copyFile(String srcFileName, String destFileName,
			boolean overlay) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			LogUtils.i(TAG,"复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			LogUtils.i(TAG,"复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在，而且复制时允许覆盖。
			if (overlay) {
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				LogUtils.i(TAG,"目标文件已存在，准备删除它！");
				if (!DeleteFolder(destFileName)) {
					LogUtils.i(TAG,"复制文件失败：删除目标文件" + destFileName + "失败！");
					return false;
				}
			} else {
				LogUtils.i(TAG,"复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				LogUtils.i(TAG,"目标文件所在的目录不存在，准备创建它！");
				if (!destFile.getParentFile().mkdirs()) {
					LogUtils.i(TAG,"复制文件失败：创建目标文件所在的目录失败！");
					return false;
				}
			}
		}
		// 准备复制文件
		int byteread = 0;// 读取的位数
		InputStream in = null;
		OutputStream out = null;
		try {
			// 打开原文件
			in = new FileInputStream(srcFile);
			// 打开连接到目标文件的输出流
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			// 一次读取1024个字节，当byteread为-1时表示文件已经读完
			while ((byteread = in.read(buffer)) != -1) {
				// 将读取的字节写入输出流
				out.write(buffer, 0, byteread);
			}
			LogUtils.i(TAG,"复制单个文件" + srcFileName + "至" + destFileName
					+ "成功！");
			return true;
		} catch (Exception e) {
			LogUtils.i(TAG,"复制文件失败：" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，注意先关闭输出流，再关闭输入流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 *  根据路径删除指定的目录或文件，无论存在与否
	 *@param sPath  要删除的目录或文件
	 *@return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) {  // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) {  // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else {  // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * @param   sPath    被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}


	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * @param   sPath 被删除目录的文件路径
	 * @return  目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		//如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		//删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			//删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) break;
			} //删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) break;
			}
		}
		if (!flag) return false;
		//删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	// 复制文件夹   
	public static void copyDirectiory(String sourceDir, String targetDir)  
			throws IOException {  
		// 新建目标目录   
		(new File(targetDir)).mkdirs();  
		// 获取源文件夹当前下的文件或目录   
		File[] file = (new File(sourceDir)).listFiles();  
		for (int i = 0; i < file.length; i++) {  
			if (file[i].isFile()) {  
				// 源文件   
				File sourceFile=file[i];  
				// 目标文件   
				File targetFile=new   
						File(new File(targetDir).getAbsolutePath()  
								+File.separator+file[i].getName());  
				copyFile(sourceFile.getAbsolutePath(),targetFile.getAbsolutePath(),true);  
			}  
			if (file[i].isDirectory()) {  
				// 准备复制的源文件夹   
				String dir1=sourceDir + "/" + file[i].getName();  
				// 准备复制的目标文件夹   
				String dir2=targetDir + "/"+ file[i].getName();  
				copyDirectiory(dir1, dir2);  
			}  
		}  
	}  
	


	public static final String SAVE_PATH = "mnt/sdcard/delcom/img";
//	public static final String DOWNLOAD_PATH = "mnt/sdcard/FashionMusic";

	public static void save(String path, Bitmap bitmap) {

		String name = path.substring(path.lastIndexOf("/"));
		File file = new File(SAVE_PATH + name);
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap != null) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 * @param msg
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";

		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}

	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

	/**
	 * 向手机写图片
	 * 
	 * @param buffer
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String folder,
			String fileName) {
		boolean writeSucc = false;

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

		String folderPath = "";
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory()
					+ File.separator + folder + File.separator;
		} else {
			writeSucc = false;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StrTime.isBlank(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StrTime.isBlank(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StrTime.isBlank(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}
	
	/**  
     * 获取文件夹大小  
     * @param file File实例  
     * @return long     
     */   
    public static long getFolderSize(java.io.File file){
    	long size = 0;  
        try {
        	if(file.exists()){
    			java.io.File[] fileList = file.listFiles();
    			int fileListLength = fileList.length;
    			for (int i = 0; i < fileListLength; i++){
    				if (fileList[i].isDirectory()){
    					size = size + getFolderSize(fileList[i]);
    			    }else{
    			    	size = size + fileList[i].length();
    			    }   
    			}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}   
        return size;
    }  

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *           
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "MB";
		} else {
			return df.format(temp) + "KB";
		}
	}

	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}
	
    /**  
     * 删除指定目录下文件及目录   
     * @param deleteThisPath  
     * @param filepath  
     * @return   
     */   
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {  
        if (!TextUtils.isEmpty(filePath)) {   
            try {
				File file = new File(filePath);   
				if (file.isDirectory()) {
					// 处理目录   
				    File files[] = file.listFiles();
				    int filesLength = files.length;
				    for (int i = 0; i < filesLength; i++) {   
				        deleteFolderFile(files[i].getAbsolutePath(), true);   
				    }    
				}else if(deleteThisPath){
					file.delete();
				}  
				/*if (deleteThisPath) {
					if (!file.isDirectory()) {
						// 如果是文件，删除   
				        file.delete();   
				    } else {
				    	// 目录 
				    	if (file.listFiles().length == 0) {
				    		// 目录下没有文件或者目录，删除   
				            file.delete();   
				        }   
				    }   
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}   
        }   
    }
    
    /**
     * 格式化单位
     * @param size
     * @return
     */
	public static String getFormatSize(double size) {
		double kiloByte = size/1024;
		if(kiloByte < 1) {
			return size + "Byte(s)";
		}
		
		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}
		
		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}
		
		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	/**
	 * 读取表情配置文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getEmojiFile(Context context) {
		InputStream in = null;
		BufferedReader br = null;
		List<String> list = new ArrayList<String>(104);
		try {
			in = context.getResources().getAssets().open("emoji");
			br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    if(br != null) {
		      try {
				  br.close();
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		   }
		   CloseUtils.closeInputStream(in);
		}
		return list;
	}
	
	/**
	 * 将Assets中的文件复制到指定路径
	 * @param name:目标文件名称
	 * @param directory:目标文件目录
	 * @param overlay:是否覆盖
	 */
	public static boolean copyDatabaseFile(Context context,String name, String directory,boolean overlay) {
//		String directory = getDatabasesPath(context);
		String filePath = directory + name;
		// 确认目录存在
		File fDirectory = new File(directory);
		if (!fDirectory.exists()) {
			fDirectory.mkdirs();
		}
		File dbFile = new File(filePath);
		boolean ok = false; 
		//如果数据库文件已经存在就不处理
		if (dbFile.exists()&&!overlay) {
			return true;
		} else {
			InputStream ins = null;
			OutputStream ous = null;
			try {
				ins = context.getAssets().open(name);
				ous = new FileOutputStream(dbFile);
				byte[] buffer = new byte[1024 * 8];
				int len = -1;
				while ((len = ins.read(buffer)) > 0) {
					ous.write(buffer, 0, len);
				}
				ous.flush();
				ok = true;
				Log.d(TAG,"copy exits "+name+" databases");
			} catch (IOException e) {
				Log.d(TAG, " assest file " + name+ " not exists or copy error!");
				ok = false;
			} finally {
			    CloseUtils.closeOutputStream(ous);
			    CloseUtils.closeInputStream(ins);
			}
		} 
		return ok;
	}
} 
