package com.zqds.client.ui.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

/**
 * create at 2015-02-04 
 * @author antony
 * 登录界面手机号码编辑框 
 *
 */
public class PhoneClearEditText extends EditText implements TextWatcher,OnFocusChangeListener {
	
	private static final String TAG = PhoneClearEditText.class.getSimpleName();
	
	/**
	 * 左右两侧图片资源
	 */
	private Drawable left, right;
	/**
	 * 是否获取焦点，默认没有焦点
	 */
	private boolean hasFocus = false;
	/**
	 * 手指抬起时的X坐标
	 */
	private int xUp = 0;
	/**
	 * 
	 */
	private int beforeLen = 0;
	/**
	 * 
	 */
    private int afterLen = 0;
    /**
     * 
     */
	private Context mContext;
	/**
	 *验证数字与字母 
	 */
	@SuppressLint("ParserError")
	private boolean isOpenVerify;
	
	
	public PhoneClearEditText(Context context) {
		this(context, null);
		this.mContext = context;
	}
	
	public PhoneClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
		this.mContext = context;
	}

	public PhoneClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWedgits(attrs);
		this.mContext = context;
	}
	
	/**
	 * 初始化各组件
	 * @param attrs
	 *            属性集
	 */
	private void initWedgits(AttributeSet attrs) {
		try {
			left = getCompoundDrawables()[0];
			right = getCompoundDrawables()[2];
			initDatas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据
	 */
	private void initDatas() {
		try {
			// 第一次显示，隐藏删除图标
			setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
			addListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加事件监听
	 */
	private void addListeners() {
		try {
			setOnFocusChangeListener(this);
			addTextChangedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPhoneNumber(){
		String phoneNumber = getText().toString();
		return phoneNumber.replaceAll("-", "");
	}
	
	 /**
	  *是否需要空格 
	  */
	 public boolean isSpace(int length){
		 if(length==4)
			 return true;
		 if(length==9)
			 return true;
		 return false;
	 }

	@Override
	public void afterTextChanged(Editable s) {
		String txt = this.getText().toString();
		afterLen = txt.length();
        if (afterLen > beforeLen) {  
            if (isSpace(afterLen)) {  
                this.setText(new StringBuffer(txt).insert(  
                        txt.length() - 1, "-").toString());  
                this.setSelection(this.getText()  
                        .length());
                Log.d(TAG, "selection = " +this.getText()  
                        .length());
            }  
        } else {
            if (txt.startsWith("-")) {  
                this.setText(new StringBuffer(txt).delete(  
                        afterLen - 1, afterLen).toString());  
                this.setSelection(this.getText()  
                        .length());  
                Log.d(TAG, "else start space");
            }  
        }
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		beforeLen = s.length();
	}

	@Override
	public void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		if (hasFocus) {
			if (TextUtils.isEmpty(text)) {
				// 如果为空，则不显示删除图标
				setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
			} else {
				if(!isNumberAndLetter(text.toString())&&isOpenVerify){
//				   setShakeAnimation();
				   this.setText("");
//				   Toast.makeText(mContext, "请输入数字或者字母", Toast.LENGTH_LONG).show();
				   return;
				}
				// 如果非空，则要显示删除图标
				if (null == right) {
					right = getCompoundDrawables()[2];
				}
				setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
			}
		}
	}
	
	/**
	 * 判断是否为数字或者字母 
	 */     
	public boolean isNumberAndLetter(String inputText){
		 if(TextUtils.isEmpty(inputText)){
			 return false;
		 }
		 
		 String text=String.valueOf(inputText.charAt(inputText.length()-1));
		 Pattern p = Pattern.compile("[0-9]*"); 
	     Matcher m = p.matcher(text); 
	     if(m.matches() ){
	       return true;
	     } 
	     p=Pattern.compile("[a-zA-Z]");
	     m=p.matcher(text);
	     if(m.matches()){
	    	return true;
	     }
	  return false;
   }

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			String text = getText().toString();
			if(!TextUtils.isEmpty(text)){
				if (null == right) {
					right = getCompoundDrawables()[2];
				}
				setCompoundDrawablesWithIntrinsicBounds(left, null, right, null);
			}
		}
		try {
			this.hasFocus = hasFocus;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				// 获取点击时手指抬起的X坐标
				xUp = (int) event.getX();
				// 当点击的坐标到当前输入框右侧的距离小于等于getCompoundPaddingRight()的距离时，则认为是点击了删除图标
				// getCompoundPaddingRight()的说明：Returns the right padding of the view, plus space for the right Drawable if any.
				if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
					if (!TextUtils.isEmpty(getText().toString())) {
						setText("");
					}
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onTouchEvent(event);
	}
}
