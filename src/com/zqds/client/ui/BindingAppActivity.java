package com.zqds.client.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zqds.client.R;
import com.zqds.client.ui.fragment.BaseFragment;
import com.zqds.client.ui.fragment.BindingMobileFragment;
import com.zqds.client.util.IntentTools;

/**
 * 绑定手机号界面
 * @author 向春发
 *
 */
public class BindingAppActivity extends BaseActivity implements OnClickListener{
	  /**
     * TAG
     */
    private static final String TAG = BindingAppActivity.class.getSimpleName();

    /**
     * view
     */
    
    /**
     * params
     */
	private BaseFragment mBindingMobileFrgment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		initView();
		initData();
		initListener();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void initView() {
	    mBindingMobileFrgment = BindingMobileFragment.newInstance(null);
    	addContent(R.id.fragment_base,mBindingMobileFrgment, TAG);
	}
				

	@Override
	protected void initListener() {
	}
	
    OnClickListener actionBarLeftBtnListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
        	finish();
        }
    };
	
	public static void startActivity(Context context){
		if(context==null)
			return;
		context.startActivity(IntentTools.getBindingAppIntent(context));
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
