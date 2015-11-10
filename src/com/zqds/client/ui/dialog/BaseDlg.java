package com.zqds.client.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public  class BaseDlg extends Dialog {

	private Window window;
	private Context context;
	private String TAG="MessageDlg";

	public BaseDlg(Context context) {
		super(context);
		this.context=context;
		
	}

 	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置dialog圆角效果
		window = getWindow(); // 得到对话框
		WindowManager.LayoutParams wl = window.getAttributes();
	    DisplayMetrics dm = new DisplayMetrics();  
	    dm =context.getResources().getDisplayMetrics();
	    int densityDPI=dm.densityDpi;
	    if(densityDPI==120||densityDPI==160)
		    wl.width = dm.widthPixels - 150;
	    else if (densityDPI==240 || densityDPI==320)
	     	wl.width = dm.widthPixels - 300; // 设置宽度
	    else 
	    	wl.width = dm.widthPixels - 450;
	    window.setAttributes(wl);
	}
}
