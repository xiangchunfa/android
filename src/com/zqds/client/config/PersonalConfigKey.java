/*
 * PersonalConfigKey.java
 * classes : com.qdoc.client.config.PersonalConfigKey
 * @author xiangyutian
 * V 1.0.0
 * Create at 2014-7-15 上午12:51:19
 */
package com.zqds.client.config;

/**
 * com.qdoc.client.config.PersonalConfigKey
 * 
 * @author xiangyutian <br/>
 *         create at 2014-7-15 上午12:51:19
 */
public class PersonalConfigKey {
    /**
     * TAG
     */
    private static final String TAG = PersonalConfigKey.class.getSimpleName();

    /**
     * token
     */
    public static final String EXTRA_TOKEN = "EXTRA_TOKEN";

    /**
     * device token
     */
    public static final String EXTRA_DEVICE_TOKEN = "EXTRA_DEVICE_TOKEN";

    /**
     * consult toggle
     */
    public static final String EXTRA_CONSULT_TOGGLE = "EXTRA_CONSULT_TOGGLE";

    /**
     * account
     */
    public static final String EXTRA_ACCOUNT_HINT = "EXTRA_ACCOUNT_HINT";

    /**
     * pwd
     */
//  public static final String EXTRA_PWD_HINT = "EXTRA_PWD_HINT";
    /**
     * 用户状态显示标示
     */
    public static final String EXTAR_USER_STATUS_FLAG = "EXTAR_USER_STATUS_FLAG";
    /**
     * 用户状态显示标示
     */
    public static final String EXTAR_OBLIGATION_DIAGNOSE_TOGGLE = "EXTAR_OBLIGATION_DIAGNOSE_TOGGLE";
    /**
     * 当天是否调用登陆标示
     */
    public static final String EXTAR_LOGIN_FLAG = "EXTAR_LOGIN_FLAG";
    /**
     * 用户登陆状态
     */
    public static final String EXTAR_LOGIN_STATUS = "EXTAR_LOGIN_STATUS";
    /**
     * 当天是否调用app日志接口
     */
    public static final String EXTAR_CURRENT_DAY = "EXTAR_CURRENT_DAY";
    /**
     * 每天是否调用版本更新接口(AboutmeFragment)
     */
    public static final String EXTAR_UPGRADE_DAY_ABOUTMEFRAGMENT = "extar_upgrade_day";
    /**
     * 存放服务器端的最新版本号
     */
    public static final String EXTAR_ROMATE_VERSION_NAME = "extar_romate_version_name";
    /**
     * 当天是否调用版本升级接口
     */
    public static final String EXTAR_VERSION_UPGRADE = "EXTAR_VERSION_UPGRADE";
    /**
     * 版本名称
     */
    public static final String EXTAR_VERSION_NAME = "EXTAR_VERSION_NAME";
    /**
     * 认证状态
     */
    public static final String EXTAR_AUTH_STATUS = "EXTAR_AUTH_STATUS";
    /**
     * 抢单铃声开关
     */
    public static final String EXTAR_CONSULT_RING = "extar_consult_ring";
    /**
     * 显示手势引导
     */
    public static final String EXTAR_GESTURE_GUIDE = "EXTAR_GESTURE_GUIDE";
    /**
     * 门诊时间   
     */
    public static final String EXTAR_CLINIC_TIME = "EXTAR_CLINIC_TIME";
    /**
     * 是否需要调用门诊时间   
     */
    public static final String EXTAR_CALL_CLINIC_TIME = "EXTAR_CALL_CLINIC_TIME";
    
    /**
     * 用户模式
     */
    public static final String EXTAR_USER_STATUS = "EXTAR_USER_STATUS"; 
    
    /**
     * 认证状态
     */
    public static final String EXTAR_USER_CERTIFICATION_STATUS = "user_certification_status";
    
    /**
     * 我的页面app注册引导
     */
    public static final String EXTAR_ME_APP_REGISTER_GUIDE = "EXTAR_ME_APP_REGISTER_GUIDE";
    /**
     * 我的页面后台注册引导
     */
    public static final String EXTAR_ME_BACKGROUND_REGISTER_GUIDE = "EXTAR_ME_BACKGROUND_REGISTER_GUIDE";
    /**
     * 设置页面注册引导
     */
    public static final String EXTAR_SETTING_REGISTER_GUIDE = "EXTAR_SETTING_REGISTER_GUIDE";
    /**
     * 历史咨询记录引导
     */
    public static final String EXTAR_HISTORY_CONSULT_LIST_GUIDE = "EXTAR_HISTORY_CONSULT_LIST_GUIDE";
    /**
     * 咨询内容页引导
     */
    public static final String EXTAR_CONSULT_DETAIL_GUIDE = "EXTAR_CONSULT_DETAIL_GUIDE";
}
