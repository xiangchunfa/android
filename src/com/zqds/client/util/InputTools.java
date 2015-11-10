package com.zqds.client.util;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * FileName    : InputTools.java
 * Description : 输入法工具类
 * @Copyright  : Keai Software Co.,Ltd.Rights Reserved 
 * @Company    : 可爱医生网络技术有限公司
 * @author     : 向春发
 * @version    : 1.0 
 * Create Date : 2014-10-29 
 **/
public class InputTools {
      public static final int OPEN=1;//打开状态
      public static final int CLOSE=2;//关闭状态
      
      
      /**
       *隐藏虚拟键盘 
       *@param v:接受软键盘输入的视图 
       */
      public static void HideKeyboard(View v)
      {
          InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
        if ( imm.isActive( ) ) {     
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );   
        }    
      }
      
      /**
       *显示虚拟键盘 
       *@param v:接受软键盘输入的视图 
       */
      public static void ShowKeyboard(View v)
      {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);    
      }
      
      /**
       *强制显示或者关闭系统键盘
       *@param v:接受软键盘输入的视图 
       */
      public static void KeyBoard(final EditText txtSearchKey,final int status)
      {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        @Override
        public void run()
        {
            InputMethodManager m = (InputMethodManager)
            txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
             if(status==OPEN)
             {
                 m.showSoftInput(txtSearchKey,InputMethodManager.SHOW_FORCED); 
             }
             else
             {
                 m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0); 
             }
         }  
         }, 300);
       }
      
      
      /**
       *输入法是否显示着
       *@param v:接受软键盘输入的视图 
       */
      public static boolean isShowKeyBoard(Context context)
      {
          boolean bool = false;
          InputMethodManager imm = ( InputMethodManager ) context.getSystemService( Context.INPUT_METHOD_SERVICE );     
        if ( imm.isActive( ) )
        {     
           bool = true; 
        }    
        return bool;
          
      }
}