/*
 * IResultParser.java
 * classes : com.sohu.sohuvideo.task.listener.IResultParser
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-20 下午5:58:32
 */
package com.zqds.client.http.listener;

import java.io.IOException;

import android.os.RemoteException;

import org.apache.http.ParseException;
import org.json.JSONException;

import com.zqds.client.http.exception.ZqdsApiException;

/**
 * 解析器基类
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-20 下午5:58:32
 */
public interface IResultDataParser<T> {

    /**
     * 解析服务器响应
     * 
     * @param  response 服务器响应的内容字符串
     * @return T 解析response后得到的数据类型
     * @throws JSONException 解析服务器响应的JSON的异常
     * @throws IOException 获取服务器响应异常
     * @throws Exception 响应异常
     */
    T parse(Object response) throws JSONException, IOException, Exception;
}
