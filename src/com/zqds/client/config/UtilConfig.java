/*
 * UtilConfig.java
 * classes : com.qdoc.client.config.UtilConfig
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午8:39:53
 */
package com.zqds.client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.zqds.client.system.Common;
import com.zqds.client.util.LogUtils;

/**
 * com.qdoc.client.config.UtilConfig
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午8:39:53
 */
abstract class UtilConfig {

    private static final String TAG = UtilConfig.class.getSimpleName();

    /**
     * Property
     */
    protected Properties mProperties = new Properties();

    /** 配置文件路径 **/
    protected final String PROPERTY_FILE_PATH = "/data/data/" + Common.PACKAGE_NAME + "/shared_prefs/";

    public UtilConfig() {
        loadProperties();
    }

    protected abstract String getConfigName();

    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        if (loadProperties()) {
            return mProperties.getProperty(key, defValue);
        } else {
            return defValue;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        // 由于Property只支持String，需要转换
        return Boolean.parseBoolean(getString(key, String.valueOf(defValue)));
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public int getInt(String key, Integer defValue) {
        return Integer.parseInt(getString(key, String.valueOf(defValue)));
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Float.parseFloat(tmpStr);
    }

    public long getLong(String key, long defValue) {
        String tmpStr = getString(key, String.valueOf(defValue));
        return Long.parseLong(tmpStr);
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * author:cyq 20121211,如果加载资源失败返回空的默认值
     * 
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        // 防止为空或未加载，只需一次

        if (value == null) {
            return;
        }
        // if (loadProperties()) {
        mProperties.put(key, value);
        // } else {
        // mProperties.clear();
        // }
    }

    public void putBoolean(String key, boolean value) {
        putString(key, String.valueOf(value));
    }

    public void putInt(String key, Integer value) {
        putString(key, String.valueOf(value));
    }

    public void putFloat(String key, Float value) {
        putString(key, String.valueOf(value));
    }

    public void putLong(String key, long value) {
        putString(key, String.valueOf(value));
    }

    /**
     * 是否包含该key
     * 
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        // 防止为空或未加载，只需一次
        // loadProperties();
        if (mProperties == null || key == null) {
            return false;
        }
        return mProperties.containsKey(key);
    }

    // 移除键
    public void remove(String key) {
        // 防止为空或未加载，只需一次
        // if (loadProperties()) {
        mProperties.remove(key);
        // } else {
        // mProperties.clear();
        // }
    }

    /**
     * 提交，在put value过后必须调用 getConfigName()，由于可能之前没有uid，所以这里不能一开始就初始化configname,
     * 需要后续过程中再次获取configname
     */
    public void commit() {
        String configName = getConfigName();
        FileOutputStream fos = null;
        try {
            // 同步修改
            synchronized (mProperties) {
                fos = new FileOutputStream(PROPERTY_FILE_PATH + configName);
                mProperties.store(fos, "");
            }
        } catch (FileNotFoundException ex) {
            LogUtils.e(TAG, "", ex);
        } catch (IOException ex) {
            LogUtils.e(TAG, "", ex);
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    LogUtils.e(TAG, "", ex);
                }
            }
        }
    }

    protected abstract void compatibleBeforeV34();

    /**
     * 初始化Properties
     */
    protected boolean loadProperties() {
        if (null == mProperties || mProperties.size() == 0) {

            FileInputStream fis = null;
            try {
                File dir = new File(PROPERTY_FILE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = null;
                LogUtils.d(TAG, "config name=" + getConfigName());
                file = new File(PROPERTY_FILE_PATH, getConfigName());
                /** 处理配置文件的变化 **/
                compatibleBeforeV34();
                if (!file.exists()) {
                    file.createNewFile();
                }
                fis = new FileInputStream(file);
                mProperties.load(fis);
                return true;
            } catch (FileNotFoundException ex) {
                LogUtils.e(TAG, "", ex);
                return false;
            } catch (IOException ex) {
                LogUtils.e(TAG, "", ex);
                return false;
            } finally {
                if (null != fis) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        LogUtils.e(TAG, "", ex);
                    }
                }
            }
        }
        return true;
    }
}
