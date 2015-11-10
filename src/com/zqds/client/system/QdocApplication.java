/*
 * LegouBuyerApplication.java
 * classes : com.ledu.ledubuyer.system.LegouBuyerApplication
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午2:06:37
 */
package com.zqds.client.system;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * com.ledu.ledubuyer.system.LegouBuyerApplication
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午2:06:37
 */
public class QdocApplication extends Application {
	/**
	 * TAG
	 */
	private static final String TAG = QdocApplication.class.getSimpleName();
     
	/**
	 * param
	 */
	private static QdocApplication instance = null;
	private Handler handler = new Handler();
	private Thread mUiThread;

	public QdocApplication() {
		super();
		instance = this;
	}

	public static QdocApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mUiThread = Thread.currentThread();
		initImageLoaderConfig();
	}

	/**
	 * 配置图片属性
	 * 
	 * @author xiangyutian create at 2014-6-17 下午8:01:42
	 */
	private void initImageLoaderConfig() {
		// TODO Auto-generated method stub
		ImageLoaderHelper.getInstance(this);
		initImageLoader(getApplicationContext());
	}

	private void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			handler.post(action);
		} else {
			action.run();
		}
	}

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * @param handler
	 *            the handler to set
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}


}
