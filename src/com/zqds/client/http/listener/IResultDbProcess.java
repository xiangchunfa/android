/*
 * IResultDbProcess.java
 * classes : com.sohu.sohuvideo.control.http.listener.IResultDbProcess
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-24 下午4:49:08
 */
package com.zqds.client.http.listener;

/**
 * 数据库处理逻辑
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-24 下午4:49:08
 */
public interface IResultDbProcess {

    /**
     * 数据库业务逻辑处理
     * 
     * @author xiangyutian
     * @param response 网络响应
     * @return create at 2014-3-21 上午10:38:57
     */
    public Object onDbProcess(Object response);
}
