package com.zqds.client.system;


import android.view.View;

public interface ItemOnclickListener {
	public void onButtonClick(Object obj,View view1,View view2,int position);
    public void onButtonClick(int type, Object obj, View view1, View view2);
    public void onButtonClick(int type, Object obj, View view1, View view2,int position);
    public void onReplyClick(int type, Object obj,View view);
}
