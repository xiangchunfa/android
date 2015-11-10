package com.zqds.client.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqds.client.R;
import com.zqds.client.ui.fragment.BaseFragment;
import com.zqds.client.ui.fragment.HomeFragment;
import com.zqds.client.ui.fragment.MeFragment;
import com.zqds.client.ui.fragment.MsgFragment;

/**
 * 主Activity
 * 
 * @author 向春发
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	// 三个tab布局
	private RelativeLayout HomeLayout, msgLayout, meLayout;
	// 底部标签切换的Fragment
	private BaseFragment HomeFragment, msgFragment, meFragment, currentFragment;
	// 底部标签图片
	private ImageView iv_home, iv_msg, iv_me;
	// 底部标签的文本
	private TextView tv_home, tv_msg, tv_me;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
		initTab();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		HomeLayout = (RelativeLayout) findViewById(R.id.rl_home);
		msgLayout = (RelativeLayout) findViewById(R.id.rl_msg);
		meLayout = (RelativeLayout) findViewById(R.id.rl_me);
		HomeLayout.setOnClickListener(this);
		msgLayout.setOnClickListener(this);
		meLayout.setOnClickListener(this);

		iv_home = (ImageView) findViewById(R.id.iv_home);
		iv_msg = (ImageView) findViewById(R.id.iv_msg);
		iv_me = (ImageView) findViewById(R.id.iv_me);
		tv_home = (TextView) findViewById(R.id.tv_home);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		tv_me = (TextView) findViewById(R.id.tv_me);

	}

	/**
	 * 初始化底部标签
	 */
	private void initTab() {
		if (HomeFragment == null) {
			HomeFragment = new HomeFragment();
		}
		if (!HomeFragment.isAdded()) {
			// 提交事务
			getSupportFragmentManager().beginTransaction().add(R.id.content_layout, HomeFragment).commit();
			// 记录当前Fragment
			currentFragment = HomeFragment;
			// 设置图片文本的变化
			iv_home.setImageResource(R.drawable.footer_home_selected);
			tv_home.setTextColor(getResources().getColor(R.color.bottomtab_press));
			iv_msg.setImageResource(R.drawable.footer_msg_normal);
			tv_msg.setTextColor(getResources().getColor(R.color.bottomtab_normal));
			iv_me.setImageResource(R.drawable.footer_me_normal);
			tv_me.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_home: // 赚钱大师
			clickTab1Layout();
			break;
		case R.id.rl_msg: // 消息
			clickTab2Layout();
			break;
		case R.id.rl_me: // 我
			clickTab3Layout();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击第一个tab
	 */
	private void clickTab1Layout() {
		if (HomeFragment == null) {
			HomeFragment = new HomeFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), HomeFragment);
		// 设置底部tab变化
		iv_home.setImageResource(R.drawable.footer_home_selected);
		tv_home.setTextColor(getResources().getColor(R.color.bottomtab_press));
		iv_msg.setImageResource(R.drawable.footer_msg_normal);
		tv_msg.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iv_me.setImageResource(R.drawable.footer_me_normal);
		tv_me.setTextColor(getResources().getColor(R.color.bottomtab_normal));
	}

	/**
	 * 点击第二个tab
	 */
	private void clickTab2Layout() {
		if (msgFragment == null) {
			msgFragment = new MsgFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), msgFragment);
		iv_home.setImageResource(R.drawable.footer_home_normal);
		tv_home.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iv_msg.setImageResource(R.drawable.footer_msg_selected);
		tv_msg.setTextColor(getResources().getColor(R.color.bottomtab_press));
		iv_me.setImageResource(R.drawable.footer_me_normal);
		tv_me.setTextColor(getResources().getColor(R.color.bottomtab_normal));
	}

	/**
	 * 点击第三个tab
	 */
	private void clickTab3Layout() {
		if (meFragment == null) {
			meFragment = new MeFragment();
		}
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
		iv_home.setImageResource(R.drawable.footer_home_normal);
		tv_home.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iv_msg.setImageResource(R.drawable.footer_msg_normal);
		tv_msg.setTextColor(getResources().getColor(R.color.bottomtab_normal));
		iv_me.setImageResource(R.drawable.footer_me_selected);
		tv_me.setTextColor(getResources().getColor(R.color.bottomtab_press));
	}

	/**
	 * 添加或者显示碎片
	 * 
	 * @param transaction
	 * @param fragment
	 */
	private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
		if (currentFragment == fragment)
			return;
		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}
		currentFragment = (BaseFragment) fragment;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		
	}
}
