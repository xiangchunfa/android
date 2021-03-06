package com.zqds.client.db;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zqds.client.util.StringUtils;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

/**
* @Company    : 深圳市荣迪信息技术有限公司
* @author     : 向春发
**/
@SuppressWarnings("unchecked")
public class BeanUtils {
    
    public static final Class loadClass(String className){
		Class c = null;
		try{ 
			c = Class.forName(className);
		}catch(Exception e){
			c = null;
		}
		return c;  
	}

	public static Object loadInstance(Class cls) {
		Object c = null;
		try{ 
			c = cls.newInstance();
		}catch(Exception e){
			c = null;
		} 
		return c;  
	}
	
    public static final boolean emptyCollection(Collection collection){
        return (collection == null) || (collection.size() < 1);
    }
	
	public static String formatDate(Date date,String format){
	    SimpleDateFormat simpleDate = new SimpleDateFormat(format);
	    return simpleDate.format(date);
	}
	
	public static Date parseDate(String dateStr,String format){
		try{
			if(StringUtils.isEmpty(dateStr)){
				SimpleDateFormat simpleDate = new SimpleDateFormat(format);
				return simpleDate.parse(dateStr);
			}
			return new Date();
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static String formatDate(int year,int month,int day ,String format){
	    Date date = new Date(year,month,day);
	    return formatDate(date, format);
	}
	
	public static String formatData(String dateStr,String oldformat,String format){
	    Date date = parseDate(dateStr, oldformat);
	    return formatDate(date, format);
	}
	
	public static final String join(List<String> lists,String split){
		String result = "";
		if(!emptyCollection(lists)){
			Iterator<String> iteratr = lists.iterator();
			while(iteratr.hasNext()){
				result += iteratr.next();
				if(iteratr.hasNext()){
					result += split;
				}
			}
		}
        return result;
    }
	
	public static final String join(String[] arrays,String split){
		if(!BeanUtils.emptyArray(arrays)){
			return join(Arrays.asList(arrays),split);
		}
		return "";
	}
	
	/**
	 * 判断数组是否为空
	 * @param objects
	 * @return
	 */
	public static boolean emptyArray(Object[] objects){
	    return objects ==null || objects.length < 1;
	}
	
	/**
	 * 判断JsonArray是否为空
	 * @param jsonArray
	 * @return
	 */
	public static boolean emptyJsonArray(JSONArray jsonArray){
		return jsonArray == null || jsonArray.length() < 1;
	}
	
	/**
	 * 判断JsonArray是否为空
	 * @param jsonArray
	 * @return
	 */
	public static boolean emptyJsonObject(JSONObject jsonArray){
		return jsonArray == null || jsonArray.length() < 1;
	}
	/**
	 * 获取Json的String属性
	 * @param object
	 * @param key
	 * @return
	 */
	public static String getJsonString(JSONObject object,String key){
	    String value = "";
	    try{
	        value = object.getString(key);
	    }catch(Exception e){
	    }
	    return value;
	}
	
	/**
	 * 获取Json的int属性
	 * @param object
	 * @param key
	 * @return
	 */
	public static int getJsonInteger(JSONObject object,String key){
		String value = BeanUtils.getJsonString(object, key);
		int num = 0 ;
		try{
			num = Integer.parseInt(value);
		}catch(Exception e){
		}
		return num;
	}
	
	/**
	 * 获取Json的boolean属性
	 * @param object
	 * @param key
	 * @return
	 */
	public static boolean getJsonBoolean(JSONObject object,String key){
		String value = BeanUtils.getJsonString(object, key);
		boolean result = false ;
		try{
			result = Boolean.valueOf(value);
		}catch(Exception e){
		}
		return result;
	}
	
	/**
	 * 获取json对个属性的值
	 * @param object
	 * @param keys
	 * @return
	 */
	public static String[] getJsonString(JSONObject object,String[] keys){
	    String[] values = null;
	    if(!BeanUtils.emptyArray(keys)){
	        values = new String[keys.length];
	        for(int i = 0 ;i< keys.length ;i++){
	            values[i] = getJsonString(object, keys[i]);
	        }
	    }
	    return values;
	}
	
	/**
	 * 根据字段或者对于的集合集
	 * @param arrays
	 * @param key
	 * @return
	 */
	public static List<String> getListByKey(JSONArray arrays,String key){
		List<String> temps = new ArrayList<String>();
		if(arrays!=null && arrays.length() > 0){
			for(int i=0;i<arrays.length();i++){
				try {
					temps.add(getJsonString(arrays.getJSONObject(i), key));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return temps;
	}
	
	/**
	 * 将查询的结果集转换为具体的JsonArray对象
	 * @param cusor
	 * @return
	 */
	public static JSONArray convertCusorJsonArray(Cursor cursor){
		JSONArray jsonArray = new JSONArray();
		if(cursor!=null && cursor.getCount() > 0){
			String[] columns = cursor.getColumnNames();
			for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){ 
				if(BeanUtils.emptyArray(columns)){
					break;
				}else{
					JSONObject jsonObject = new JSONObject();
					for(String columnName : columns){
						try{
							if("".equals(cursor.getString(cursor.getColumnIndex(columnName))) || cursor.getString(cursor.getColumnIndex(columnName)) == null){
								jsonObject.put(columnName.toUpperCase(), "");
							}else{								
								jsonObject.put(columnName.toUpperCase(), cursor.getString(cursor.getColumnIndex(columnName)));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					jsonArray.put(jsonObject);
				}
			}
		}
		return jsonArray;
	}
	
	/**
	 * 将查询的结果集转换为具体的JSONObject对象
	 * @param cusor
	 * @return
	 */
	public static JSONObject convertCusorJsonObj(Cursor cursor){
		JSONObject jsonObject = null;
		if(cursor!=null && cursor.getCount() > 0 && cursor.moveToFirst()){
			String[] columns = cursor.getColumnNames();
			if(!BeanUtils.emptyArray(columns)){
				jsonObject = new JSONObject();
				for(String columnName : columns){
					try{
						jsonObject.put(columnName, cursor.getString(cursor.getColumnIndex(columnName)));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * 将一个jsonObject对象转换成MAP
	 * @param obj
	 * @return
	 */
	public static Map<String,String> convertJsonMap(JSONObject obj){
	    Map<String,String> params = new HashMap<String, String>();
	    if(obj!=null){
    	    Iterator<String> keys = obj.keys();
            while(keys.hasNext()){
                String keyt = keys.next();
                params.put(keyt,BeanUtils.getJsonString(obj, keyt));
            }
	    }
        return params;
	}
	
	/**
	 * 一个Map追加几个JsonObject
	 * @param params
	 * @param obj
	 */
	public static void joinMap(Map<String,String> params,JSONObject obj){
		if(obj!=null){
    	    Iterator<String> keys = obj.keys();
            while(keys.hasNext()){
                String keyt = keys.next();
                params.put(keyt,BeanUtils.getJsonString(obj, keyt));
            }
	    }
	}
	
	/**
	 * 获取图片信息
	 * @param filePath
	 * @return
	 */
	public static Bitmap getFileImge(String filePath){
		 BitmapFactory.Options options = new BitmapFactory.Options();
         options.inSampleSize = 5;
         return BitmapFactory.decodeFile(filePath, options);
	}
	
	/**
	 * 设置图片
	 * @param image
	 * @param filePath
	 */
	public static void setFileImage(ImageView image,String filePath){
		File file = new File(filePath);
		if(file.exists()){
			try{
				image.setImageBitmap(getFileImge(filePath));
			}catch(Exception e){
				
			}
		}
	}
	
	/**
	 * 解压文件
	 * @param filePath
	 * @param descPath
	 */
	public static void ectractZipFile(String filePath,String descPath){
	    try{
	        ectractZipFile(new FileInputStream(filePath), descPath);
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	}
	
	/**
	 * 解压文件
	 * @param inputstream
	 * @param sDestPath
	 */
	public synchronized static void ectractZipFile(InputStream inputstream, String sDestPath){
	    FileOutputStream fouts = null;
        ZipInputStream zins = null;
        try {
            Log.e("EMOP", " START ECTRACT ZIP DATABASE FILE .............................");
            // 将fins传入ZipInputStream中
            zins = new ZipInputStream(inputstream);
            ZipEntry ze = null;
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(sDestPath + ze.getName());
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists()){
                        zfile.mkdirs();
                    }
                    zins.closeEntry();
                } else {
                    if (!fpath.exists()){
                        fpath.mkdirs();
                    }
                    fouts = new FileOutputStream(zfile);
                    int byts = 0;
                    while ((byts = zins.read()) != -1) {
                        fouts.write(byts);
                    }
                    zins.closeEntry();
                    fouts.close();
                }
            }
            Log.e("EMOP", " END ECTRACT ZIP DATABASE FILE .............................");
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            CloseUtils.closeInputStream(inputstream);
            CloseUtils.closeInputStream(zins);
            CloseUtils.closeOutputStream(fouts);
        }
	}
	
	
	private static final Map<String,String> MIME_MAP = new HashMap<String, String>();
	
	 static{
	    MIME_MAP.put(".3gp",   "video/3gpp");
	    MIME_MAP.put(".apk",   "application/vnd.android.package-archive");
        MIME_MAP.put(".asf",    "video/x-ms-asf");
        MIME_MAP.put(".avi",    "video/x-msvideo");
        MIME_MAP.put(".bin",    "application/octet-stream");
        MIME_MAP.put(".bmp",    "image/bmp");
        MIME_MAP.put(".c",      "text/plain");
        MIME_MAP.put(".class",  "application/octet-stream");
        MIME_MAP.put(".conf",   "text/plain");
        MIME_MAP.put(".cpp",    "text/plain");
        MIME_MAP.put(".doc",    "application/msword");
        MIME_MAP.put(".docx",    "application/msword");
        MIME_MAP.put(".exe",    "application/octet-stream");
        MIME_MAP.put(".gif",    "image/gif");
        MIME_MAP.put(".gtar",   "application/x-gtar");
        MIME_MAP.put(".gz",     "application/x-gzip");
        MIME_MAP.put(".h",      "text/plain");
        MIME_MAP.put(".htm",    "text/html");
        MIME_MAP.put(".html",   "text/html");
        MIME_MAP.put(".jar",    "application/java-archive");
        MIME_MAP.put(".java",   "text/plain");
        MIME_MAP.put(".jpeg",   "image/jpeg");
        MIME_MAP.put(".jpg",    "image/jpeg");
        MIME_MAP.put(".js",     "application/x-javascript");
        MIME_MAP.put(".log",    "text/plain");
        MIME_MAP.put(".m3u",    "audio/x-mpegurl");
        MIME_MAP.put(".m4a",    "audio/mp4a-latm");
        MIME_MAP.put(".m4b",    "audio/mp4a-latm");
        MIME_MAP.put(".m4p",    "audio/mp4a-latm");
        MIME_MAP.put(".m4u",    "video/vnd.mpegurl");
        MIME_MAP.put(".m4v",    "video/x-m4v"); 
        MIME_MAP.put(".mov",    "video/quicktime");
        MIME_MAP.put(".mp2",    "audio/x-mpeg");
        MIME_MAP.put(".mp3",    "audio/x-mpeg");
        MIME_MAP.put(".mp4",    "video/mp4");
        MIME_MAP.put(".mpc",    "application/vnd.mpohun.certificate");      
        MIME_MAP.put(".mpe",    "video/mpeg");  
        MIME_MAP.put(".mpeg",   "video/mpeg");  
        MIME_MAP.put(".mpg",    "video/mpeg");  
        MIME_MAP.put(".mpg4",   "video/mp4");   
        MIME_MAP.put(".mpga",   "audio/mpeg");
        MIME_MAP.put(".msg",    "application/vnd.ms-outlook");
        MIME_MAP.put(".ogg",    "audio/ogg");
        MIME_MAP.put(".pdf",    "application/pdf");
        MIME_MAP.put(".png",    "image/png");
        MIME_MAP.put(".pps",    "application/vnd.ms-powerpoint");
        MIME_MAP.put(".ppt",    "application/vnd.ms-powerpoint");
        MIME_MAP.put(".pptx",    "application/vnd.ms-powerpoint");
        MIME_MAP.put(".prop",   "text/plain");
        MIME_MAP.put(".rar",    "application/x-rar-compressed");
        MIME_MAP.put(".rc",     "text/plain");
        MIME_MAP.put(".rmvb",   "audio/x-pn-realaudio");
        MIME_MAP.put(".rtf",    "application/rtf");
        MIME_MAP.put(".sh",     "text/plain");
        MIME_MAP.put(".tar",    "application/x-tar");   
        MIME_MAP.put(".tgz",    "application/x-compressed"); 
        MIME_MAP.put(".txt",    "text/plain");
        MIME_MAP.put(".wav",    "audio/x-wav");
        MIME_MAP.put(".wma",    "audio/x-ms-wma");
        MIME_MAP.put(".wmv",    "audio/x-ms-wmv");
        MIME_MAP.put(".wps",    "application/vnd.ms-works");
        MIME_MAP.put(".xml",    "text/xml");
        MIME_MAP.put(".xml",    "text/plain");
        MIME_MAP.put(".xls",    "application/vnd.ms-excel");
        MIME_MAP.put(".xlsx",   "application/vnd.ms-excel");
        MIME_MAP.put(".z",      "application/x-compress");
        MIME_MAP.put(".zip",    "application/zip");
        MIME_MAP.put(".vsd",    "application/vnd.visio");
    }
	
	/**
	 * 获取对应的mimetype
	 */
	public static String getMimeType(String key){
	    String obj = (String) MIME_MAP.get(key);
	    return obj!=null ? obj : "*/*";
	}
	
	/**
	 * 复制文件
	 * @param filePath
	 * @param newPath
	 */
	public static void copyFile(String filePath,String newPath){
		if (!StringUtils.isEmpty(filePath) && !StringUtils.isEmpty(newPath)) {
            BufferedOutputStream bops = null;
            BufferedInputStream bips = null;
            FileInputStream ins = null;
            File outFile = new File(newPath);
            try {
            	if(!outFile.getParentFile().isDirectory()){
            		outFile.getParentFile().mkdirs();
            		if(!outFile.exists()){
            			outFile.createNewFile();
            		}
                }
            	ins = new FileInputStream(filePath);
                bips = new BufferedInputStream(ins);
                bops = new BufferedOutputStream(new FileOutputStream(outFile));
                byte[] boxes = new byte[512];
                int byts = 0;
                while ((byts = bips.read(boxes)) != -1) {
                    bops.write(boxes,0,byts);
                }
                bops.flush();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("CREATE FILE TO [" + newPath + "] ERROR:", e);
            } finally {
            	CloseUtils.closeInputStream(ins);
                CloseUtils.closeInputStream(bips);
                CloseUtils.closeOutputStream(bops);
            }
        }
	}
	
	public static String getXczfHtml(InputStream ins){
		BufferedInputStream bips = null;
		ByteArrayOutputStream bops = null;
		StringBuffer buffer = new StringBuffer();
		try{
			bops = new ByteArrayOutputStream();
			bips = new BufferedInputStream(ins);
			int byts = 0;
            while ((byts = bips.read()) != -1) {
                bops.write(byts);
            }
            byte[] lens = bops.toByteArray();
            buffer.append(new String(lens,0,lens.length));
		}catch(Exception e){
			e.printStackTrace();
		}finally{ 
			CloseUtils.closeInputStream(bips);
			CloseUtils.closeOutputStream(bops);
		}
		return buffer.toString(); 
	}
}
