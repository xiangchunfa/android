/*
 * AbsractBaseModel.java
 * classes : com.sohu.sohuvideo.models.AbsractBaseModel
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-3-24 下午5:18:53
 */
package com.zqds.client.model;

import java.io.Serializable;

/**
 * com.sohu.sohuvideo.models.AbsractBaseModel
 * 
 * @author xiangyutian <br/>
 *         create at 2014-3-24 下午5:18:53
 */
public abstract class AbstractBaseModel implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * status
     */
    private int state;

    /**
     * statusText
     */
    private String errorMsg;

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
