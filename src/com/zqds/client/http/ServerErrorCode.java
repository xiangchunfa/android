/*
 * ServerErrorCode.java
 * classes : com.sh.shvideo.task.ServerErrorCode
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-20 下午6:48:30
 */
package com.zqds.client.http;

import com.android.volley.center.AsyncRequestCenter.ErrorCode;

/**
 * 错误码集合
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-20 下午6:48:30
 */
public class ServerErrorCode implements ErrorCode {
    public static final String TAG = "ServerErrorCode";

    /**
     * Http请求成功
     */
    public static final int STATUS_SUCCESS = 200;

    /**
     * Http请求失败
     */
    public static final int STATUS_EMPTY = 201;
    /**
     * 接口请求成功
     */
    public static final int INTERFACE_SUCCESS = 1;

    /**
     * 接口请求失败
     */
    public static final int INTERFACE_FAIL = 0;
    /**
     * token失效
     */
    public static final int TOKEN_INVALID = -1;
}
