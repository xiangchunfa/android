/*
 * ThankLetterFragment.java
 * classes : com.qdoc.client.ui.fragment.ThankLetterFragment
 * @author xiangyutian
 * V 1.0.0
 * Create at 2014-7-20 下午5:36:48
 */
package com.zqds.client.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.zqds.client.R;
import com.zqds.client.http.HttpTaskManager;
import com.zqds.client.ui.view.TitleBar;

/**
 * 绑定页面
 * @author 向春发
 */
public class BindingMobileFragment extends BaseFragment {
    /**
     * TAG
     */
    public static final String TAG = BindingMobileFragment.class.getSimpleName();

    /**
     * view
     */
    private TitleBar mTitleBar;

    /**
     * params
     */
    public static BindingMobileFragment newInstance(Bundle bundle) {
        final BindingMobileFragment fragment = new BindingMobileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.binding_mobile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initListener();
    }

    public void onDestroy() {
        super.onDestroy();
        HttpTaskManager.stop(TAG);
    }

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView(View view) {
		// TODO Auto-generated method stub
		mTitleBar = (TitleBar) view.findViewById(R.id.titlebar);
		mTitleBar.setTitleInfo(R.string.binding_mobile, R.drawable.icon_back, 0, actionBarLeftBtnListener, null, getResources().getColor(R.color.black));
	}
	
	OnClickListener actionBarLeftBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			getActivity().finish();
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
