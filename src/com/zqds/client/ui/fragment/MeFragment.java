package com.zqds.client.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zqds.client.R;
import com.zqds.client.ui.BindingAppActivity;

/**
 * 我页面
 * @author 向春发
 *
 */
public class MeFragment extends BaseFragment {
	
	private RelativeLayout rly_personal_info;
	
	public static MeFragment newInstance(Bundle bundle) {
		final MeFragment fragment = new MeFragment();
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_me_fragment, container,
				false);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
		initListener();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		rly_personal_info.setOnClickListener(this);
	}

	@Override
	protected void initView(View view) {
		// TODO Auto-generated method stub
		rly_personal_info = (RelativeLayout) view.findViewById(R.id.rly_personal_info);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rly_personal_info:
			BindingAppActivity.startActivity(getContext());
			break;

		default:
			break;
		}
		
	}

	
	

}
