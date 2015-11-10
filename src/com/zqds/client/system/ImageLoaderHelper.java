package com.zqds.client.system;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zqds.client.R;

/**
 * ImageLoaderHelper
 * 
 * @author xiangyutian <br/>
 *         create at 2014-6-18 上午10:13:40
 *         
 *         update at 2014-11-12 15:18  by antony
 */
public class ImageLoaderHelper {
    /**
     * TAG
     */
    private static final String TAG = ImageLoaderHelper.class.getSimpleName();

    private static volatile ImageLoaderHelper mInstance;
    private DisplayImageOptions imgOptions;
    private ImageLoader mImageLoader;
    private ImageLoadingListener animateFirstListener;

    public static ImageLoaderHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ImageLoaderHelper.class) {
                if (mInstance == null)
                    mInstance = new ImageLoaderHelper(context);
            }
        }
        return mInstance;
    }

    private ImageLoaderHelper(Context context) {
        initImageLoader(context);
    }

    public void initImageLoader(Context context) {
        mImageLoader = ImageLoader.getInstance();
        imgOptions = getDisplayImageOptions(R.drawable.ic_launcher);
        animateFirstListener = new AnimateFirstDisplayListener();

        File cacheDir = StorageUtils.getCacheDirectory(context);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
//              .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个 
//              .taskExecutor(...)
//              .taskExecutorForCachedImages(...)
                .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径  
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量 
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) 
                .imageDownloader(new BaseImageDownloader(context)) // default
//              .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(imgOptions) // default
//              .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        mImageLoader.init(config);    
      }

    public void displayImage(String url, ImageView imageView) {
        mImageLoader.displayImage(url, imageView, animateFirstListener);
    }
    
    public void displayImage(String url,ImageView imageView,ImageLoadingListener listener){
    	mImageLoader.displayImage(url, imageView, listener);
    }
    
    public void displayImage(String url, ImageView imageView,int res){
    	mImageLoader.displayImage(url, imageView, getDisplayImageOptions(res),animateFirstListener);
    }
    
    private DisplayImageOptions getDisplayImageOptions(int res){
    	DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
											.showImageForEmptyUri(res)
											.showImageOnFail(res)
											.showImageOnLoading(res)
											.cacheInMemory(true)
											.cacheOnDisc(true)
											.considerExifParams(true)
                                            .cacheOnDisk(true)
											.imageScaleType(ImageScaleType.EXACTLY)
											.bitmapConfig(Bitmap.Config.RGB_565)
											.build();
    	return imgOptions;
    }
    
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener{
    	
    	static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
    	
    	@Override
    	public void onLoadingComplete(String imageUri, View view,
    			Bitmap loadedImage) {
    		// TODO Auto-generated method stub
    		if(loadedImage != null){
    			ImageView imageView = (ImageView) view;
    			boolean firstDisplay = !displayedImages.contains(imageUri);
    			if(firstDisplay){
    				FadeInBitmapDisplayer.animate(imageView, 500);
    				displayedImages.add(imageUri);
    			}
    		}
    	}
    }
}
