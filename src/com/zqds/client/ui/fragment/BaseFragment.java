package com.zqds.client.ui.fragment;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * fragment基类
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-26 下午2:08:57
 */
public abstract class BaseFragment extends Fragment implements OnClickListener{
	
	private static final String TAG = BaseFragment.class.getSimpleName();
 
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG); 
    }

    /**
     * 获取context引用
     * 
     * @author xiangyutian create at 2014-3-31 上午10:02:52
     */
    public Context getContext() {
        return getActivity();
    }

    /**
     * 销毁fragment
     * 
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    public void onDestroy() {
        super.onDestroy();
    }
   
    protected abstract void initView(View view);
    protected abstract void initData();
    protected abstract void initListener();
    
}
