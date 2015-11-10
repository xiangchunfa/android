/**
 * 
 */
package com.zqds.client.util;

/**
 * Title: 异常定义
 * fileName:PlatformException.java
 * Description: 
 * @Copyright: PowerData Software Co.,Ltd. Rights Reserved.
 * @Company:可爱医生网络技术有限公司
 * @author: 向春发
 * @version:1.0 
 * create date:2010-06-03
 */
public class PlatformException extends RuntimeException {
	 
	//依赖资源尚未初始化
	public static final long NOT_INITED = 0x00001;
	
	//网络通讯异常
	public static final long NETWORK_EXCEPTION = 0x00002;
	
	/**数据库不存在*/  
	public static final long NO_SUCH_DATABASE = 0x00003;
	
    private static final long serialVersionUID = -7751597285526081427L;

	public static final long PARSE_XML_ERROR = 0x00004;
    private long code; 
     

    public long getCode() {
		return code;
	}

	public void setCode(long code) { 
		this.code = code;
	}

	/**
     * 构造一个不带参数的异常实例
     * 
     */
    public PlatformException() {
        super();
    }

    /**
     * 使用指定消息构造一个异常实例
     * 
     * @param message
     */
    public PlatformException(String message) {
        super(message);
    }
    
    /**
     * 使用指定消息构造和code一个异常实例
     * 
     * @param message
     * @param code
     */
    public PlatformException(String message,long code) {
        super(message);
        this.code = code;
    }

    /**
     * 使用指定的cause构造一个异常实例
     * 
     * @param cause
     */
    public PlatformException(Throwable cause) {
        super(cause);
    }

    /**
     * 使用指定消息和cause构造一个异常实例
     * 
     * @param message
     * @param cause
     */
    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 使用指定消息构造和code和cause一个异常实例
     * 
     * @param message
     * @param code
     */
    public PlatformException(String message,Throwable cause,long code) {
        super(message,cause);
        this.code = code;
    }
}
  