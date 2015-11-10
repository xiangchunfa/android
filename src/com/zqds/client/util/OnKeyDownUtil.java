/*
 * OnKeyDownUtil.java
 * classes : com.qdoc.client.util.OnKeyDownUtil
 * @author xiangyutian
 * V 1.0.0
 * Create at 2014-8-27 下午10:31:32
 */
package com.zqds.client.util;

/**
 * com.qdoc.client.util.OnKeyDownUtil
 * 
 * @author xiangyutian <br/>
 *         create at 2014-8-27 下午10:31:32
 */
public class OnKeyDownUtil {
    /**
     * TAG
     */
    private static final String TAG = OnKeyDownUtil.class.getSimpleName();

    private static long lastClickTime;
    private static long lastVertifyClickTime;
    private long lastClickTime1;//最后点击的时间

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isValidClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastVertifyClickTime;
        if (0 < timeD && timeD < 1000) {
            return false;
        }
        lastVertifyClickTime = time;
        return true;
    }
    
    /**
     * 按钮连续点击控制，连续点击间隔小于等于1秒为无效请求
     * @return
     */
    public boolean isInValidBtnClick(){
    	if(System.currentTimeMillis() - lastClickTime1 <= 1000)  
        {  
           return false;  
        }  
    	lastClickTime1 = System.currentTimeMillis();      	
    	return true;
    }
}
