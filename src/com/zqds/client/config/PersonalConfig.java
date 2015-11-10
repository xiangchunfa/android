/*
 * PersonalConfig.java
 * classes : com.qdoc.client.config.PersonalConfig
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午8:45:39
 */
package com.zqds.client.config;

/**
 * com.qdoc.client.config.PersonalConfig
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午8:45:39
 */
public class PersonalConfig {
    private static final String TAG = "PersonalConfig";

    private static UtilConfig myConfig = new TransitionPersonalConfig();;

    public static String getString(String key) {
        if (myConfig == null) {
            return "";
        }
        return myConfig.getString(key);
    }

    public static String getString(String key, String defValue) {
        if (myConfig == null) {
            return defValue;
        }
        return myConfig.getString(key, defValue);
    }

    public static boolean getBoolean(String key) {
        if (myConfig == null) {
            return false;
        }
        return myConfig.getBoolean(key);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (myConfig == null) {
            return defValue;
        }
        // 由于Property只支持String，需要转换
        return myConfig.getBoolean(key, defValue);
    }

    public static int getInt(String key) {
        if (myConfig == null) {
            return -1;
        }
        return myConfig.getInt(key);
    }

    public static int getInt(String key, Integer defValue) {
        if (myConfig == null) {
            return defValue;
        }
        return myConfig.getInt(key, defValue);
    }

    public static float getFloat(String key) {
        if (myConfig == null) {
            return -1;
        }
        return myConfig.getFloat(key);
    }

    public static float getFloat(String key, float defValue) {
        if (myConfig == null) {
            return defValue;
        }
        return myConfig.getFloat(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        if (myConfig == null) {
            return defValue;
        }
        return myConfig.getLong(key, defValue);
    }

    public static long getLong(String key) {
        if (myConfig == null) {
            return -1;
        }
        return myConfig.getLong(key);
    }

    public static void putString(String key, String value) {
        if (myConfig == null) {
            return;
        }
        myConfig.putString(key, value);
    }

    public static void putBoolean(String key, boolean value) {
        if (myConfig == null) {
            return;
        }
        myConfig.putBoolean(key, value);
    }

    public static void putInt(String key, Integer value) {
        if (myConfig == null) {
            return;
        }
        myConfig.putInt(key, value);
    }

    public static void putFloat(String key, Float value) {
        if (myConfig == null) {
            return;
        }
        myConfig.putFloat(key, value);
    }

    public static void putLong(String key, long value) {
        if (myConfig == null) {
            return;
        }
        myConfig.putLong(key, value);
    }

    /**
     * 是否包含该key
     * 
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        if (myConfig == null) {
            return false;
        }
        return myConfig.hasKey(key);
    }

    // 移除键
    public static void remove(String key) {
        if (myConfig == null) {
            return;
        }
        myConfig.remove(key);
    }

    public static void commit() {
        if (myConfig == null) {
            return;
        }
        myConfig.commit();
    }

    /**
     * 异步提交数据
     * 
     * @author libin09 2013-8-15
     */
    public static void asyncCommit() {
        new Thread(new Runnable() {

            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);
                PersonalConfig.commit();
            }
        }).start();
    }

    /**
     * 销毁myconfig对象
     */
    public synchronized static void destroyMyConfig() {
        myConfig = null;
    }

    public synchronized static void createMyConfig() {
        if (myConfig == null) {
            myConfig = new TransitionPersonalConfig();
        }
    }

}
