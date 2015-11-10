/*
 * ITaskResultReceiver.java
 * classes : com.sohu.sohuvideo.task.resultlistener.ITaskResultReceiver
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-20 下午2:25:12
 */
package com.zqds.client.http.listener;

/**
 * com.sohu.sohuvideo.task.resultlistener.ITaskResultReceiver
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-20 下午2:25:12
 */
public interface IResultReceiver {

    /**
     * 返回数据接口
     * 
     * @author xiangyutian
     * @param resultCode 网络请求返回错误码和http操作过程中的错误码
     * @param resultData 解析封装好的数据模型对象 create at 2014-3-20 下午2:26:36
     */
    public void onReceiveResult(int resultCode, Object resultData);
}
