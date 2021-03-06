/*
 * TransitionGlobalConfig.java
 * classes : com.qdoc.client.config.TransitionGlobalConfig
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午8:42:00
 */
package com.zqds.client.config;

import java.io.File;
import java.util.Properties;

import com.zqds.client.system.Common;

/**
 * com.qdoc.client.config.TransitionGlobalConfig
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午8:42:00
 */
public class TransitionGlobalConfig extends UtilConfig {
    private static final String TAG = TransitionGlobalConfig.class.getSimpleName();

    private static final String GLOBAL = "global";

    /**
     * Property author:cyq 20121129
     */
    protected Properties mCacheProperties = new Properties();

    /**
     * @return
     * @see com.baidu.netdisk.util.config.UtilConfig#getConfigName()
     */
    @Override
    protected String getConfigName() {
        return GLOBAL + Common.CONFIG_FILENAME;
    }

    /**
     * 处理账号的替换逻辑
     * <p>
     * 1、如果global配置文件不存在，并且原配置文件存在时进行rename
     * </p>
     * <p>
     * 2、其余情况均对global配置文件的初始化没有影响
     * </p>
     */
    @Override
    protected void compatibleBeforeV34() {
        File file = new File(PROPERTY_FILE_PATH, getConfigName());
        if (file.exists()) {
            return;
        }

        /** 3.4版本以前原有的配置文件 **/
        File oldConfigFileBeforeV34 = new File(PROPERTY_FILE_PATH, Common.CONFIG_FILENAME);

        if (oldConfigFileBeforeV34.exists()) {
            oldConfigFileBeforeV34.delete();
        }
    }
}
