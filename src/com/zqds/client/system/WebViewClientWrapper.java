package com.zqds.client.system;

import android.app.ProgressDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 加载页面时显示一个对话框
 * @author Administrator
 *
 */
public class WebViewClientWrapper extends WebViewClient {

	private ProgressDialog dialog;

	public WebViewClientWrapper() {
		super();
	}

	public WebViewClientWrapper(ProgressDialog dialog) {
		super();
		this.dialog = dialog;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		this.dialog.dismiss();
	}
}
