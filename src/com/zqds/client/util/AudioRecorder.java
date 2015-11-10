package com.zqds.client.util;

import java.io.File;
import java.io.IOException;
import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import com.zqds.client.util.LogUtils;

/**
 * FileName    : SoundMeter.java
 * Description : 录音工具类
 * @Copyright  : Keai Software Co.,Ltd.Rights Reserved 
 * @Company    : 可爱医生网络技术有限公司
 * @author     : 向春发
 * @version    : 1.0 
 * Create Date : 2014-12-22 
 **/

public  class AudioRecorder {
	private static int SAMPLE_RATE_IN_HZ = 8000; 
	private MediaRecorder mRecorder = null;
	public static final String TAG = AudioRecorder.class.getSimpleName(); 
    private static final String PREFIX = "voice";// 零时文件的前缀
    private static final String SUFFIX = ".amr";// 零时文件的后缀
    public static final String PATH = PREFIX + SUFFIX;
    public File mRecAudioFile;
    
	public void start(Context context) {
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			LogUtils.i(TAG, "NO_MEDIA_MOUNTED");
			return;
		}
		if (mRecorder == null) {
			LogUtils.i(TAG, "mRecorder");
			mRecorder = new MediaRecorder();
			mRecorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);//新增，解决部分手机兼容问题
			mRecorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);//新增，解决部分手机兼容问题
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			try {
				File mRecAudioPath = Environment.getExternalStorageDirectory();
				mRecAudioFile = File.createTempFile(PREFIX, SUFFIX, mRecAudioPath);
				mRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
				mRecorder.prepare();
				mRecorder.start();
			} catch (IllegalStateException e) {
				LogUtils.i(TAG, "IllegalStateException="+e.getMessage());
				Toast.makeText(context, "找不到SD卡", Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				LogUtils.i(TAG, "IOException="+e.getMessage());
				Toast.makeText(context, "找不到SD卡", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				LogUtils.i(TAG, "IOException="+e.getMessage());
				Toast.makeText(context, "找不到SD卡", Toast.LENGTH_LONG).show();
			}
		}
	}

	
	
	public void stop() {
		if (mRecorder != null) {
			try {
				LogUtils.i(TAG, "stop");
				mRecorder.stop();
				mRecorder.release();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mRecorder = null;
		}
	}

	public void pause() {
		if (mRecorder != null) {
			LogUtils.i(TAG, "pause");
			mRecorder.stop();
		}
	}

	public void start() {
		if (mRecorder != null) {
			LogUtils.i(TAG, "start");
			mRecorder.start();
		}
	}
   /**
    * 获取音频振幅大小
    * @return
    */
	public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;

	}
}