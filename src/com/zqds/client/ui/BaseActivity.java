/*
 * BaseActivity.java
 * classes : com.ledu.ledubuyer.ui.BaseActivity
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午7:46:05
 */
package com.zqds.client.ui;

import java.util.List;
import java.util.Stack;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;

/**
 * 基础activity
 * 
 * @author 向春发
 */
public abstract  class BaseActivity extends FragmentActivity {
    /**
     * TAG
     */
    private static final String TAG = BaseActivity.class.getSimpleName();
    private static Stack<BaseActivity> sActivities = new Stack<BaseActivity>();
    private boolean mActivityPaused;
	private InputMethodManager manager = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addActivity(this);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
        super.onCreate(savedInstanceState);
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); //统计时长（友盟）
        mActivityPaused = false;
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        mActivityPaused = true;
    }

    /**
     * 判断Activity是否Paused
     * 
     * @return
     */
    public boolean isActivityPaused() {
        return mActivityPaused;
    }

    @Override
    protected void onDestroy() {
        removeActivity(this);
        super.onDestroy();
    }

    /**
     * 界面Activity入栈
     * 
     * @param activity
     */
    private static void addActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }
        sActivities.push(activity);
    }

    /**
     * 获取acitivty栈
     */
    public static Stack<BaseActivity> getActivityStack() {
        return sActivities;
    }

    /**
     * 获取栈顶界面Activity
     * 
     * @param activity
     */
    public static BaseActivity getTopActivity() {
        if (sActivities.empty()) {
            return null;
        } else {
            return sActivities.peek();
        }
    }

    /**
    * 检测某Activity是否在当前Task的栈顶
    */
    public static boolean isTopActivy(String cmdName,Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if(null != runningTaskInfos){
            cmpNameTemp=runningTaskInfos.get(0).topActivity.getClass().getSimpleName();
        }
        if(null == cmpNameTemp)
        	return false;
        return cmpNameTemp.equals(cmdName);
    }
    
    private static void removeActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }

        if (sActivities.contains(activity)) {
            sActivities.remove(activity);
        }
    }

	public static void closeApplication() {
        if (!sActivities.empty()) {
            for (BaseActivity activity : sActivities) {
                if (activity != null && !activity.isFinishing())
                    activity.finish();
            }
            sActivities.clear();
        }
    }
    
    public static void exitApplication(Context context){
    	//退出应用
    	closeApplication(); 
    	MobclickAgent.onKillProcess(context);
    	android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void refreshTopActivity(BaseActivity activity) {
        removeActivity(activity);
        addActivity(activity);
    }

    /**
     * 获取当前Activity的Context
     * 
     * @return
     */
    public Context getContext() {
        return BaseActivity.this;
    }
    
    /**
     * 添加Fragment
     * @param viewId:视图ID
     * @param fragment:碎片化管理器
     * @param tag:fragment标志
     * @param isAddToBackStack:是否返回到回退栈
     */
   public void addContent(int viewId,Fragment fragment,String tag,Boolean isAddToBackStack){
	    FragmentTransaction  fragTran = getSupportFragmentManager().beginTransaction();
        fragTran.add(viewId,fragment,tag);
        fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if(isAddToBackStack)
          fragTran.addToBackStack(null);
        fragTran.commit();
   }
   
   /**
    * 添加Fragment
    * @param viewId:视图ID
    * @param fragment:碎片化管理器
    * @param tag:fragment标签
    */
   public void addContent(int viewId,Fragment fragment,String tag){
	    addContent(viewId,fragment, tag, false);
  }
   
    /**
     * 替换当前Fragment
     * @param viewId:视图ID
     * @param fragment:碎片化管理器
     * @param tag:fragment标签
     */
   public void replaceContent(int viewId,Fragment fragment,String tag){
	    FragmentTransaction  fragTran = getSupportFragmentManager().beginTransaction();
        fragTran.replace(viewId,fragment,tag);
        fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTran.addToBackStack(null);
        fragTran.commitAllowingStateLoss();
   }
   
   /**
    * 替换当前Fragment
    * @param viewId:视图ID
    * @param fragment:碎片化管理器
    * @param tag:fragment标签
    * @param isAddtoBackStatck:是否返回到回退栈
    */
  public void replaceContent(int viewId,Fragment fragment,String tag,boolean isAddtoBackStatck){
	  FragmentTransaction  fragTran = getSupportFragmentManager().beginTransaction();
	  fragTran.replace(viewId,fragment,tag);
	  fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	  if(isAddtoBackStatck)
		  fragTran.addToBackStack(null);
	  fragTran.commitAllowingStateLoss();
  }
   
   /**
    * 移除Fragment
    * @param:isAddtoBackStatck:是否返回到回退栈
    * @param:tags:fragment标签集合
    */
   public void removeContent(boolean isAddtoBackStatck, String...tags){
      FragmentManager fragMgr = getSupportFragmentManager();
      FragmentTransaction fragTran = fragMgr.beginTransaction();
      for(String tag:tags){
         Fragment fragment = (Fragment)fragMgr.findFragmentByTag(tag);
         fragTran.remove(fragment);
      }   
      fragTran.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      if(isAddtoBackStatck)
         fragTran.addToBackStack(null);
      fragTran.commit();
   }
   
   /**
    * 移除Fragment
    * @param:tags:fragment标签集合
    */
   public void removeContent(String...tags){
	     removeContent(false, tags);
   }
   
  @Override
  public boolean onTouchEvent(MotionEvent event) {
	  // TODO Auto-generated method stub
	  if (event.getAction() == MotionEvent.ACTION_DOWN) {
	     if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
	          manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	     }  
	  }
     return super.onTouchEvent(event);
  }
  
}
