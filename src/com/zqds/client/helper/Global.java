package com.zqds.client.helper;
 
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zqds.client.R;
import com.zqds.client.config.PersonalConfig;
import com.zqds.client.config.PersonalConfigKey;
import com.zqds.client.util.LogUtils;
import com.zqds.client.util.MyLogger;
import com.zqds.client.util.NumberUtil;
import com.zqds.client.util.StringUtils;
import com.zqds.client.util.Tools;

public class Global {
	private static final String TAG = Global.class.getSimpleName();
	 /**
     *默认设备号
     */
	public static final String DEVICE_NUMBER="0123456789abcde";

	public static boolean debug = false;//测试模式开关
	public static int displayWidth; // 屏幕宽度
	public static int displayHeight; // 屏幕高度
	public static int titleHeight;// 标题栏高度
	public static int statusBarHeight;// 状态栏高度
	public static float displayDensity;
	public static float displayScaledDensity;
	public static String  resolution;//分辨率
	public static boolean isFirstIn = true;// 是否首次安装
	public static int APP_VERSION_CODE;//软件版本号
	public static String APP_VERSION_NAME;//软件版本名称
	public static String OS_VERSION;//Android系统版本 
	public static String PHONE_MODEL;//手机型号
	public static int displayDPI; // 屏幕DPIR
	public static String IMEI;// 设备唯一号
	public static String IMSI;
	public static String localeLanguage;//本地显示的语言
	public static Context context = null;
	public static MyLogger mylogger = MyLogger.getLogger();
	public static int loginStatus;//登录状态
	
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			// mylogger.i("Global",
			// "正在运行的服务"+serviceList.get(i).service.getClassName());
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	
    /**
     * 判断Activity是否正在前台显示
     */
	public static boolean isActProsceniumShow(Context mContext, String className) {
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
        if(serviceList.size() > 0 && serviceList.get(1).service.getClassName().equals(className) == true){
        	return true;
        }
        return false;
	}
	
	/**
	 *获取网络状态（2G/3G/WIFI） 
	 */
	public static String getNetworkState(){
    	return Tools.getNetworkState(context);
	}
	
    /**
     * 初始化参数
     */
	public static void inits(Context aContext){
		context=aContext;
		//在 Java 语言中，此方法以小写 ISO 639 代码形式返回区域设置的语言。
		localeLanguage = aContext.getResources().getConfiguration().locale.getLanguage();
		if(PersonalConfig.hasKey(PersonalConfigKey.EXTRA_TOKEN))
		  loadConfig();
		Global.statusBarHeight = Global.getStatusBarHeight(aContext);
		TelephonyManager mTelephonyMgr = (TelephonyManager)aContext.getSystemService(Context.TELEPHONY_SERVICE);
		Global.IMSI = Global.getImsi(aContext);
		Global.IMEI = mTelephonyMgr.getDeviceId();
		APP_VERSION_NAME = getVerName(aContext);
		APP_VERSION_CODE = getVerCode(aContext);
		saveConfig();
	}

	/**
	 * 下载安装路径
	 */
	public static String downLoadPath(Context context){
		String APP_FILE= Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/";
		return APP_FILE + "Temp/";
	}
	
    /**
     *初始化手机相关参数
     */
	public static void initMetrics(Activity aContext){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		aContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		displayWidth = displayMetrics.widthPixels;
		displayHeight = displayMetrics.heightPixels;
	    displayDensity = displayMetrics.density;
		displayDPI = displayMetrics.densityDpi;
		displayScaledDensity = displayMetrics.scaledDensity;
		resolution = displayWidth+"X" + displayHeight;
	}
	
	/*加载初始化配置*/
	public static void loadConfig() {
	}
	
	/*保存初始化配置*/
	public static void saveConfig() {
	} 
   
	/**
	 * 退出账号
	 */
	public static void quitAccount(){
		cleanCache();
	}

	
	/**
	 * 获得版本名称
	 * 
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo("com.qdoc.client", 0).versionName;
		} catch (NameNotFoundException e) {
			mylogger.e(e.getMessage());
		}
		return verName;
	}

	/**
	 * 获得版本代码
	 * 
	 * @return
	 */
	public static  int getVerCode(Context context) {
		int verCode = 0;
		try {
			verCode = context.getPackageManager().getPackageInfo("com.qdoc.client", 0).versionCode;
		} catch (NameNotFoundException e) {
			mylogger.e(e.getMessage());
		}
		return verCode;
	}
	
	/**
	 * 获取Android操作系统版本
	 * @return
	 */
	public static String getOSVersion(){
		LogUtils.d("Global", "-----------------------系统版本号："+android.os.Build.VERSION.RELEASE+"-----------------");
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * 
	 * 获取手机型号
	 * @return
	 */
	public static String getPhoneModel(){
		LogUtils.d("Global", "------------------------手机型号："+android.os.Build.MODEL+"--------------");
		return android.os.Build.MODEL;
	}
	
    /**
     *生成设备唯一号 
     */
	public static String uniqueID(){
		if(StringUtils.isEmpty(IMEI)){
			return DEVICE_NUMBER;
		}
    	return IMEI;
	}
	
	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = null;
		try {
			connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否可以拨打电话
	 * 
	 * @param mContext
	 * @return 
	 */
	static public boolean canCallPhone(Context mContext) {
		TelephonyManager telephony = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		boolean flag = false;
		if (type == TelephonyManager.PHONE_TYPE_NONE) {
			mylogger.i("is Tablet!");
			flag = false;
		} else { 
			mylogger.i("is phone!");
			flag = true;
		}
		return flag;
	}                                                                                                                                         

	/**
	 * 判断SIM卡是否可用
	 * 
	 * @param mContext
	 * @return
	 */
	static public boolean isCanUseSim(Context mContext) {
		boolean flag = false;
		try {
			TelephonyManager mgr = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);

			flag = TelephonyManager.SIM_STATE_READY == mgr.getSimState();
			mylogger.i("SIM 卡是否可用  == " + flag);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {
		return (int) (pxValue / displayDensity + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(float dipValue) {
		return (int) (dipValue * displayDensity + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(float pxValue) {
		return (int) (pxValue / displayScaledDensity + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @return
	 */
	public static int sp2px(float spValue) {
		return (int) (spValue * displayScaledDensity + 0.5f);
	}

	/**
	 * 获取imsi
	 * 
	 * @param context
	 * @return
	 */
	public static String getImsi(Context context) {
		String imsi = "";
		try {// 普通方法获取imsi
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			// tm.getPhoneType();
			imsi = tm.getSubscriberId();
			if (imsi == null || "".equals(imsi)) {
				imsi = tm.getSimOperator();
			}
			Class<?>[] resources = new Class<?>[] { int.class };
			Integer resourcesId = new Integer(1);
			if (imsi == null || "".equals(imsi)) {
				try {// 利用反射获取 MTK手机
					Method addMethod = tm.getClass().getDeclaredMethod(
							"getSubscriberIdGemini", resources);
					addMethod.setAccessible(true);
					imsi = (String) addMethod.invoke(tm, resourcesId);
				} catch (Exception e) {
					imsi = null;
				}
			}

			if (imsi == null || "".equals(imsi)) {
				try {// 利用反射获取 展讯手机
					Class<?> c = Class
							.forName("com.android.internal.telephony.PhoneFactory");
					Method m = c.getMethod("getServiceName", String.class,
							int.class);
					String spreadTmService = (String) m.invoke(c,
							Context.TELEPHONY_SERVICE, 1);
					TelephonyManager tm1 = (TelephonyManager) context
							.getSystemService(spreadTmService);
					imsi = tm1.getSubscriberId();
				} catch (Exception e) {
					imsi = null;
				}
			}

			if (imsi == null || "".equals(imsi)) {
				try {// 利用反射获取 高通手机
					Method addMethod2 = tm.getClass().getDeclaredMethod(
							"getSimSerialNumber", resources);
					addMethod2.setAccessible(true);
					imsi = (String) addMethod2.invoke(tm, resourcesId);
				} catch (Exception e) {
					imsi = null;
				}
			}
			if (imsi == null || "".equals(imsi)) {
				imsi = "";
			}
			return imsi;
		} catch (Exception e) {
			return "";
		}
	}


	/***
	 * 设置ListView的高度 ,解决scrollview 中包含listview时,listview显示不全的问题
	 * 注意：Adapter的item最外层布局需要是LinearLayout
	 */
	public static void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 判断sdcard是否被挂载
	 */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 清除用户信息
	 */
	public static void cleanUserInfo() {
	}
	
   
   
   /**
    * 保留小数点后两位(四舍五入)
    **/
    public static double myRound(double j,int length) {
        BigDecimal bg = new BigDecimal(j);
        double f1 = bg.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
    

    
   /**
    * 将byte转换成M
    * @param 
    **/
    public static double byte2M(double number) {
        return number/(1024*1024);
    } 
    
    /**
     *判断activity是否在栈中
     */
    public boolean isExistActivity(String packageName,String className){
        Intent intent = new Intent();  
        intent.setClassName(packageName, className); 
        if(context.getPackageManager().resolveActivity(intent, 0) == null) {  
            //说明系统中不存在这个activity  
        	return true;
        }  
        return false;
    }
    
    /**
	 *调用SIM卡进行打电话 
	 */
   public static void callPhone(Context context,String customerPhone){
     if (!Global.isCanUseSim(context)) {
       String msg =context.getString(R.string.can_not_callphone);
       Builder builder = new AlertDialog.Builder(context);
       builder.setTitle(R.string.str_dialog_title);
       builder.setMessage(msg);
       builder.setPositiveButton(R.string.str_dialog_ok,
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog,
                           int which) {
 
                   }
               });
       builder.setCancelable(false);
       builder.create().show();
     } else {
       Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
               + customerPhone));
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(intent);
     }
   }
   
   /**
    * 清除缓存
    */ 
   public static void cleanCache(){
   }
   
  
   /**
    * 获取设备号
    * @return
    */
   public static String getDeviceID(Context context){
	    TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	    if(StringUtils.isBlank(Global.IMEI))   
	        Global.IMEI = mTelephonyMgr.getDeviceId();
	    String serialNumber = Global.IMEI;
		if(StringUtils.isEmpty(serialNumber)){
			//获取随机的系列号
			serialNumber = NumberUtil.getRandomNumber();
		}
	   return serialNumber;
   }
   
 
}
