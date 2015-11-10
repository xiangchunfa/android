package com.zqds.client.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 避免出现多个toast显示的case
 * 
 * @author antony <br/>
 *         update at 2014-12-05 上午10:32
 */
public class ToastUtils {
	  /**
     * TAG
     */
    public static final String TAG = ToastUtils.class.getSimpleName();
 
    private static Toast toast;
    private static int version = android.os.Build.VERSION.SDK_INT;
    private static final int MAX_NEED_CANCEL_VERSION = 10;
    private static String oldToastMsg;
    private static long oldTime=0;  
    private static long newTime=0;
    
    public static void show(Context context, String s,int gravity, int xOffset, int yOffset, int duration){
        if(toast==null){
        	toast =Toast.makeText(context, s, duration);
        	toast.setGravity(gravity, xOffset, yOffset);
        	toast.show();  
        	oldTime=System.currentTimeMillis();  
        }else{  
        	newTime=System.currentTimeMillis();  
            if(s.equals(oldToastMsg)){ 
                if(newTime-oldTime>duration){ 
                	toast.setGravity(gravity, xOffset, yOffset);
                	toast.show();  
                }  
            }else{
            	oldToastMsg = s;  
                toast.setText(s);
                toast.setGravity(gravity, xOffset, yOffset);
                toast.show();
            }         
        }  
        oldTime=newTime;  
    }
    
    public static void show(Context context, int resId,int gravity, int xOffset, int yOffset,int duration){     
        show(context, context.getString(resId),gravity,xOffset,yOffset,duration);  
    }
    
    public static void show(Context context,String s,int duration){
    	if(TextUtils.isEmpty(s))
    		s = "";
    	if(toast==null){
    		toast = Toast.makeText(context, s, duration);
    		toast.show();
    		oldTime=System.currentTimeMillis();  
    	}else{
    		newTime=System.currentTimeMillis();
    		if(s.equals(oldToastMsg)){
                if(newTime-oldTime>duration){
                	toast.show();  
                }  
    		}else{
            	oldToastMsg = s;  
                toast.setText(s);  
                toast.show();
    		}
    	}
    }
    
    public static void show(Context context,int resoureId,int duration){
    	if(null!=context && 0!=resoureId){
        	String msg = context.getString(resoureId);
        	show(context, msg, duration);
    	}
    }

    /**
     * 显示toast,Toast.LENGTH_SHORT
     * 
     * @param context
     * @param resId
     */
    public static void ToastShort(Context context, int resId) {
        /*initToast(context, resId);
        if (toast == null) {
            return;
        }
        toast.show();*/
    	show(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast,Toast.LENGTH_SHORT
     * 
     * @param context
     * @param resId
     */
    public static void ToastShort(Context context, String text) {
        /*initToast(context, text);
        if (toast == null) {
            return;
        }
        toast.show();*/
    	show(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast,Toast.LENGTH_SHORT
     * 
     * @param context
     * @param resId
     */
    public static void ToastShort(Context context, String text, int gravity) {
        /*initToast(context, text);
        if (toast == null) {
            return;
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();*/
    	show(context, text, gravity, 0, 0, Toast.LENGTH_SHORT);
    }
    
    /**
     * 显示toast,Toast.LENGTH_LONG
     * 
     * @param context
     * @param resId
     */
    public static void ToastLong(Context context, int resId) {
        /*initToast(context, resId);
        if (toast == null) {
            return;
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();*/
    	show(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * 
     * 显示toast,Toast.LENGTH_LONG
     * 
     * @param context
     * @param resId
     */
    public static void ToastLong(Context context, String text) {
        /*initToast(context, text);
        if (toast == null) {
            return;
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();*/
    	show(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 隐藏toast
     */
    public static void hide() {
        if (toast == null) {
            return;
        }
        toast.cancel();
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     * 
     * @param resId 需要显示的String 资源ID
     */
    private static void initToast(Context context, int resId) {
        initToast(context, context.getResources().getString(resId));
    }

    /**
     * 初始化Toast ，替换为统一样式， R.layout.toast
     * 
     * @param text 需要显示的Toast 文字
     */
    @SuppressLint("ShowToast")
    private static void initToast(Context context, String text) {
        try {
			    if (context != null) {
			        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			    	toast.setText(text);
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 LogUtils.e(TAG, "initToast", e);
		}
    }

}
