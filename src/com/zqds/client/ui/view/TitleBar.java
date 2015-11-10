/*
 * TitleBar.java
 * classes : com.ledu.ledubuyer.ui.widget.TitleBar
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-6-7 下午5:11:10
 */
package com.zqds.client.ui.view;

import com.zqds.client.R;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * com.ledu.ledubuyer.ui.widget.TitleBar
 * 
 * @author xiangyutian <br/>
 *         create at 2014-6-7 下午5:11:10
 */
public class TitleBar extends RelativeLayout {
    /**
     * TAG
     */
    private static final String TAG = TitleBar.class.getSimpleName();

    private Context mContext;

    private TextView title;
    private TextView rightTitle;
    private TextView leftTitle;
    private ImageView leftButton;
    private ImageView leftButton2;
    private ImageView rightButton;
    private ImageView rightButton2;
    private ImageView middleButton;

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写0
     */
    public void setTitleInfo(String centerTitleRes, int leftButtonResId, int rightButtonResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightButton = (ImageView) findViewById(R.id.titlebar_rightbutton);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleRes);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightButton, rightButtonResId, listener2);
    }

    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写0
     */
    public void setTitleInfo(int centerTitleResId, int leftButtonResId, int rightButtonResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightButton = (ImageView) findViewById(R.id.titlebar_rightbutton);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleResId);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightButton, rightButtonResId, listener2);
    }

    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightTitleResId 右边按钮资源id,没有写0
     */
    public void setTitleInfoWithRightText(int centerTitleResId, int leftButtonResId, int rightTitleResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar_righttv, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightTitle = (TextView) findViewById(R.id.titlebar_righttitle);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleResId);
        setTitle(rightTitle, rightTitleResId);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightTitle, rightTitleResId, listener2);
    }

    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写0
     */
    public void setTitleInfoWithRightText(String centerTitleRes, int leftButtonResId, int rightTitleResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar_righttv, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightTitle = (TextView) findViewById(R.id.titlebar_righttitle);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleRes);
        setTitle(rightTitle, rightTitleResId);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightTitle, rightTitleResId, listener2);
    }

    
    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写
     * @param leftButtonIconResId 左边按钮资源Id
     */
    public void setTitleInfoWithLeftTextAndLeftIcon(int centerTitleRes, int leftTitleTextResId,int leftButtonIconResId, int rightTitleResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor,OnClickListener listener3) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar_leftbt, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftTitle=(TextView) findViewById(R.id.titlebar_lefttitle);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightTitle = (TextView) findViewById(R.id.titlebar_righttitle);
        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleRes);
        setTitle(rightTitle, rightTitleResId);
        setTitle(leftTitle, leftTitleTextResId);
        setButton(leftButton, leftButtonIconResId, listener1);
        setButton(rightTitle, listener2);
        setButton(leftTitle, listener3);
    }
    
    
    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写0
     */
    public void setTitleInfoWithRightIcon(int centerTitleResId, int leftButtonResId, int rightButtonResId1,
            int rightButtonResId2, OnClickListener listener1, OnClickListener listener2, OnClickListener listener3,
            int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar_righticon, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightButton = (ImageView) findViewById(R.id.titlebar_righticon1);
        rightButton2 = (ImageView) findViewById(R.id.titlebar_righticon2);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleResId);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightButton, rightButtonResId1, listener2);
        setButton(rightButton2, rightButtonResId2, listener3);
    }

    /**
     * 初始化标题栏，适用于标题在中间的情况
     * 
     * @param centerTitleResId 中间标题资源id
     * @param leftButtonResId 左边按钮资源id,没有写0
     * @param rightButtonResId 右边按钮资源id,没有写0
     */
    public void setDialogTitleInfo(int centerTitleResId, int leftButtonResId, int rightButtonResId,
            OnClickListener listener1, OnClickListener listener2, int backgroundcolor) {
        LayoutInflater.from(mContext).inflate(R.layout.widget_dialog_titlebar, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        rightButton = (ImageView) findViewById(R.id.titlebar_rightbutton);

        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleResId);
        setButton(leftButton, leftButtonResId, listener1);
        setButton(rightButton, rightButtonResId, listener2);
    }
    
    /**
     * 初始化标题栏，适用于中间需要刷新图片的情况
     * @param centerTitleResId 中间标题文案资源
     * @param leftButtonResId 左边按钮文案资源
     * @param rightButtonResId1 右边按钮文案资源
     * @param middleImageViewResId 中间图片资源
     * @param leftlistener 左边监听器
     * @param rightlistener 右边监听器
     * @param middlelistener 中间监听器
     * @param backgroundcolor 标题背景图片
     */
    public void setTitleInfoWidthMiddleIcon(int centerTitleResId, int leftButtonResId, int rightButtonResId1,int middleImageViewResId,
            OnClickListener leftlistener, OnClickListener rightlistener, OnClickListener middlelistener,
            int backgroundcolor){
        LayoutInflater.from(mContext).inflate(R.layout.widget_titlebar_middlerefresh, this, true);
        title = (TextView) findViewById(R.id.titlebar_title);
        leftButton = (ImageView) findViewById(R.id.titlebar_leftbutton);
        middleButton = (ImageView) findViewById(R.id.titlebar_title_middle_refresh_iv);
        rightTitle = (TextView) findViewById(R.id.titlebar_righttitle);
        
        setBackgroundColor(backgroundcolor);
        setTitle(title, centerTitleResId);
        setButton(middleButton, middleImageViewResId, middlelistener);
        setButton(leftButton, leftButtonResId, leftlistener);
        setTitle(rightTitle, rightButtonResId1);
        setButton(rightTitle, rightlistener);
    }

    private void setTitle(TextView title, int resId) {
        if (resId != 0) {
            title.setText(resId);
        }
    }

    private void setTitle(TextView title, String res) {
        if (!TextUtils.isEmpty(res)) {
            title.setText(res);
        }
    }

    private void setButton(TextView button, int resId, OnClickListener listener) {
        if (resId != 0) {
            button.setVisibility(View.VISIBLE);
            if (listener != null) {
                button.setOnClickListener(listener);
            }
        }
    }
  
    private void setButton(TextView button,  OnClickListener listener) {
         button.setVisibility(View.VISIBLE);
         if (listener != null) {
                button.setOnClickListener(listener);
        }
    }
    
    private void setButton(ImageView button, int resId, OnClickListener listener) {
        if (resId != 0) {
            button.setImageResource(resId);
            button.setVisibility(View.VISIBLE);
            if (listener != null) {
                button.setOnClickListener(listener);
            }
        }
    }

    private void setButton(Button button, int textId,int iconId, OnClickListener listener) {
        if (textId != 0) {
            button.setCompoundDrawablesWithIntrinsicBounds(
            		iconId, 0,0, 0);
            button.setText(textId);
            button.setVisibility(View.VISIBLE);
            
            if (listener != null) {
                button.setOnClickListener(listener);
            }
        }
    }
    
    public void updateIcon(int resId) {
        if (resId != 0) {
            leftButton.setImageResource(resId);
            leftButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏或显示右上角的按钮
     */
    public void setRightButtonVisible(boolean visible){
    	if(visible)
    		rightButton.setVisibility(View.VISIBLE);
    	else
    		rightButton.setVisibility(View.GONE);
    }
}
