/*
 * TransitionPersonalConfig.java
 * classes : com.qdoc.client.config.TransitionPersonalConfig
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午8:46:09
 */
package com.zqds.client.config;

import java.io.File;

import android.text.TextUtils;

import com.zqds.client.account.AccountUtils;
import com.zqds.client.system.Common;
import com.zqds.client.util.LogUtils;

/**
 * com.qdoc.client.config.TransitionPersonalConfig
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午8:46:09
 */
public class TransitionPersonalConfig extends UtilConfig {
    private static final String TAG = TransitionPersonalConfig.class.getSimpleName();

    @Override
    protected String getConfigName() {
        return (TextUtils.isEmpty(AccountUtils.getInstance().getAccountInfo()) ? "" : AccountUtils.getInstance()
                .getAccountInfo()) + Common.CONFIG_FILENAME;
    }

    /**
     * @see com.baidu.netdisk.util.config.UtilConfig#loadProperties()
     */
    @Override
    protected boolean loadProperties() {
        // // 保证每次请求都有uid
        // String accountInfo = AccountUtils.getInstance().getAccountInfo();
        // String bduss = AccountUtils.getInstance().getBduss();
        // if (TextUtils.isEmpty(bduss) || TextUtils.isEmpty(accountInfo)) {
        // LogUtils.e(TAG, "account info=" + accountInfo + " bduss=" + bduss);
        // return false;
        // }
        return super.loadProperties();
    }

    /**
     * <tr>
     * 1、判断是否有uid
     * </tr>
     * <tr>
     * 2、如果有uid，看是否有uid关联的配置文件，如果不存在则看是否有username关联的配置文件
     * </tr>
     * <tr>
     * 3、</td>
     */
    @Override
    protected void compatibleBeforeV34() {
        /** 没有uid的情况 **/
        LogUtils.d(TAG, "uid=" + AccountUtils.getInstance().getUid());

        /** 存在uid判断是否有uid的配置文件 ***/
        File file = new File(PROPERTY_FILE_PATH, getConfigName());
        if (file.exists()) {
            return;
        }

        /** 有Username绑定的配置文件,删除 **/
        final String userName = AccountUtils.getInstance().getUserName();

        File userNameFile = new File(PROPERTY_FILE_PATH, userName + Common.CONFIG_FILENAME);

        if (userNameFile.exists()) {
            userNameFile.delete();
        }
    }

}
