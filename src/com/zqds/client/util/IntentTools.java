/*

 * IntentTools.java
 * classes : com.ledu.ledubuyer.util.IntentTools
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-7-2 下午11:32:31
 */
package com.zqds.client.util;

import com.zqds.client.ui.BindingAppActivity;

import android.content.Context;
import android.content.Intent;


/**
 * 意图工具类 
 * @author 向春发
 */
public class IntentTools {

    public static final String EXTRA_ELEMENT_MODEL = "EXTRA_ELEMENT_MODEL";
    
    public static Intent getBindingAppIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BindingAppActivity.class);
        return intent;
    }
}
