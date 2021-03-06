package com.zqds.client.http;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.android.volley.center.AsyncRequestCenter;
import com.android.volley.center.AsyncRequestCenter.RequestListener;
import com.android.volley.center.RequestParams;
import com.zqds.client.http.listener.IResultDataParser;
import com.zqds.client.http.listener.IResultDbProcess;
import com.zqds.client.http.listener.IResultReceiver;
import com.zqds.client.system.QdocApplication;
import com.zqds.client.util.LogUtils;
import com.zqds.client.util.NetworkUtils;

/**
 * 异步任务的帮助类，負責处理具体业务<br/>
 * 代理UI层发起的网络请求，将任务委派到{@link HttpTaskManager} 使用静态方法发起服务，
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-20 下午2:20:35
 */
public class HttpTaskManager {

    /**
     * TAG
     */
    private static final String TAG = HttpTaskManager.class.getSimpleName();

    static {
        AsyncRequestCenter.getInstance().initiate(QdocApplication.getInstance().getApplicationContext());
       
    }

    /**
     * volley的StringRequest类型请求
     * 
     * @param requestParams
     * @param parserObj
     * @param resultReceiver
     */
    public static <T> void startStringRequest(final RequestParams requestParams, final IResultDataParser<T> parserObj,
            final IResultReceiver resultReceiver) {

        if (requestParams == null) {
            LogUtils.e(TAG, "startStringRequest error!!! requestParams is null!");
            return;
        }

        boolean isNetAvailable = NetworkUtils.isOnline(QdocApplication.getInstance());
        // cache无效，可判断网络状况
        if (requestParams.getCacheTimeoutMs() <= 0) {
            // 判断网络是否可用
            if (!isNetAvailable) {
                if (resultReceiver == null) {
                    return;
                }
                resultReceiver.onReceiveResult(ServerErrorCode.NO_CONNECTION_ERROR, null);
                return;
            }
        }
        // 开启网络请求
        AsyncRequestCenter.getInstance().startStringRequest(requestParams, new RequestListener<String>() {

            @Override
            public void onSuccess(String result) {
            	// 打印请求与响应参数
            	   LogUtils.i(TAG, (String)requestParams.getTag()+">>requestParams："+requestParams.toString());
            	   LogUtils.i(TAG, (String)requestParams.getTag()+">>responseParams："+result);
                // 解析器或者结果回调器为空则返回，不进行任何网络请求操作
                if ((parserObj == null) || (resultReceiver == null)) {
                    LogUtils.e(TAG, "parserObj or resultReceiver is null...");
                    return;
                }

                // 判断数据返回结果是否为空
                if (TextUtils.isEmpty(result)) {
                    resultReceiver.onReceiveResult(ServerErrorCode.STATUS_EMPTY, null);
                    return;
                }

                // 数据处理逻辑过程
                onContentProviderProcess(result, parserObj, null, resultReceiver);
            }

            @Override
            public void onFailure(int errorCode) {
                // 转换至UI线程
                runUiThread(resultReceiver, errorCode, null);
                LogUtils.i(TAG, (String)requestParams.getTag()+">>requestParams："+requestParams.toString());
                LogUtils.i(TAG, (String)requestParams.getTag()+">>responseParams："+errorCode);
            }
        }, isNetAvailable);
    }

    /**
     * volley的StringRequest类型请求
     * 
     * @author xiangyutian
     * @param bundle 网络请求数据集
     * @param parserObj 解析接口
     * @param dbProcess 数据库操作接口
     * @param resultReceiver 结果回传接口 create at 2014-3-21 上午10:44:44
     */
    @Deprecated
    public static <T> void startStringRequest(final RequestParams requestParams, final IResultDataParser<T> parserObj,
            final IResultDbProcess dbProcess, final IResultReceiver resultReceiver) {
        boolean isNetAvailable = NetworkUtils.isOnline(QdocApplication.getInstance());
        // 判断网络是否可用
        if (!isNetAvailable) {
            if (resultReceiver == null) {
                return;
            }

            resultReceiver.onReceiveResult(ServerErrorCode.NO_CONNECTION_ERROR, null);
            return;
        }

        // 开启网络请求
        AsyncRequestCenter.getInstance().startStringRequest(requestParams, new RequestListener<String>() {

            @Override
            public void onSuccess(String result) {
                // 解析器或者结果回调器为空则返回，不进行任何网络请求操作
                if ((parserObj == null) || (resultReceiver == null)) {
                    LogUtils.e(TAG, "parserObj or resultReceiver is null...");
                    return;
                }

                // 判断数据返回结果是否为空
                if (TextUtils.isEmpty(result)) {
                    resultReceiver.onReceiveResult(ServerErrorCode.STATUS_EMPTY, null);
                    return;
                }

                // 数据处理逻辑过程
                onContentProviderProcess(result, parserObj, dbProcess, resultReceiver);
            }

            @Override
            public void onFailure(int errorCode) {
                // 转换至UI线程
                runUiThread(resultReceiver, errorCode, null);
            }
        }, isNetAvailable);
    }

    /**
     * volley的JsonObjectRequest类型请求
     * 
     * @author xiangyutian
     * @param RequestParams 网络请求数据集
     * @param parserObj 解析接口
     * @param dbProcess 数据库操作接口
     * @param resultReceiver 结果回传接口 create at 2014-3-21 上午10:44:44
     */
    @Deprecated
    public static <T> void startJsonObjectRequest(final RequestParams requestParams,
            final IResultDataParser<T> parserObj, final IResultDbProcess dbProcess, final IResultReceiver resultReceiver) {
        // 判断网络是否可用
        if (!NetworkUtils.isOnline(QdocApplication.getInstance())) {
            if (resultReceiver == null) {
                return;
            }
            resultReceiver.onReceiveResult(ServerErrorCode.NO_CONNECTION_ERROR, null);
            return;
        }

        // 开启网络请求
        AsyncRequestCenter.getInstance().startJsonObjectRequest(requestParams, new RequestListener<JSONObject>() {

            @Override
            public void onSuccess(JSONObject result) {
                // 解析器或者结果回调器为空则返回，不进行任何网络请求操作
                if ((parserObj == null) || (resultReceiver == null)) {
                    LogUtils.e(TAG, "parserObj or resultReceiver is null...");
                    return;
                }

                // 判断数据返回结果是否为空
                if (result == null) {
                    resultReceiver.onReceiveResult(ServerErrorCode.STATUS_EMPTY, null);
                    return;
                }

                // 数据处理逻辑过程
                onContentProviderProcess(result, parserObj, dbProcess, resultReceiver);
            }

            @Override
            public void onFailure(int errorCode) {
                // 转换至UI线程
                runUiThread(resultReceiver, errorCode, null);
            }
        });
    }

    /**
     * volley的JsonObjectRequest类型请求
     * 
     * @author xiangyutian
     * @param bundle 网络请求数据集
     * @param parserObj 解析接口
     * @param dbProcess 数据库操作接口
     * @param resultReceiver 结果回传接口 create at 2014-3-21 上午10:44:44
     */
    @Deprecated
    public static <T> void startJsonArrayRequest(final RequestParams requestParams,
            final IResultDataParser<T> parserObj, final IResultDbProcess dbProcess, final IResultReceiver resultReceiver) {
        // 判断网络是否可用
        if (!NetworkUtils.isOnline(QdocApplication.getInstance())) {
            if (resultReceiver == null) {
                return;
            }

            resultReceiver.onReceiveResult(ServerErrorCode.NO_CONNECTION_ERROR, null);
            return;
        }

        // 开启网络请求
        AsyncRequestCenter.getInstance().startJsonArrayRequest(requestParams, new RequestListener<JSONArray>() {

            @Override
            public void onSuccess(JSONArray result) {
                // 解析器或者结果回调器为空则返回，不进行任何网络请求操作
                if ((parserObj == null) || (resultReceiver == null)) {
                    LogUtils.e(TAG, "parserObj or resultReceiver is null...");
                    return;
                }

                // 判断数据返回结果是否为空
                if (result == null) {
                    resultReceiver.onReceiveResult(ServerErrorCode.STATUS_EMPTY, null);
                    return;
                }

                // 数据处理逻辑过程
                onContentProviderProcess(result, parserObj, dbProcess, resultReceiver);
            }

            @Override
            public void onFailure(int errorCode) {
                // 转换至UI线程
                runUiThread(resultReceiver, errorCode, null);
            }
        });
    }

    /**
     * 数据解析和数据处理逻辑
     * 
     * @author xiangyutian
     * @param result
     * @param parserObj
     * @param dbProcess
     * @param resultReceiver create at 2014-4-9 下午3:38:30
     */
    private static <T> void onContentProviderProcess(final Object result, final IResultDataParser<T> parserObj,
            final IResultDbProcess dbProcess, final IResultReceiver resultReceiver) {
        // 进行解析
        T resultObj = null;
        int statusCode = ServerErrorCode.STATUS_SUCCESS;
        try {
            resultObj = parserObj.parse(result);
        } catch (JSONException e) {
            LogUtils.e(TAG, "JSONException break out", e);
            statusCode = ServerErrorCode.PARSE_ERROR;
        } catch (IOException e) {
            LogUtils.e(TAG, "IOException break out", e);
            statusCode = ServerErrorCode.PARSE_ERROR;
        } catch (Exception e) {
        	LogUtils.e(TAG, "Exception break out", e);
        	statusCode = ServerErrorCode.PARSE_ERROR;
		} finally {
            // 数据库逻辑操作
            if ((dbProcess != null) && (resultObj != null)) {
                dbProcess.onDbProcess(resultObj);
            }
            // 转换至UI线程
            runUiThread(resultReceiver, statusCode, resultObj);
        }
    }

    /**
     * 切换至UI线程
     * 
     * @author xiangyutian
     * @param resultReceiver
     * @param statuscode
     * @param resultObj create at 2014-3-25 上午11:58:57
     */
    private static <T> void runUiThread(final IResultReceiver resultReceiver, final int statuscode, final T resultObj) {
        QdocApplication.getInstance().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // 回调输出
                resultReceiver.onReceiveResult(statuscode, resultObj);
            }
        });
    }

    /**
     * 根据TAG,关闭对应页面http任务
     * 
     * @author xiangyutian
     * @param tag 请求标识 create at 2014-3-27 下午9:05:28
     */
    public static void stop(Object tag) {
        AsyncRequestCenter.getInstance().cancelAll(tag);
        QdocApplication.getInstance().getHandler().removeCallbacksAndMessages(null);
    }
}
