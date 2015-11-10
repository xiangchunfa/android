/*
 * ThreadTools.java
 * classes : com.ledu.ledubuyer.system.ThreadTools
 * @author xiangyutian
 * V 4.5.0
 * Create at 2014-5-28 下午9:02:56
 */
package com.zqds.client.system;

/**
 * com.ledu.ledubuyer.system.ThreadTools
 * 
 * @author xiangyutian <br/>
 *         create at 2014-5-28 下午9:02:56
 */
public final class ThreadTools {
    /**
     * TAG
     */
    private static final String TAG = ThreadTools.class.getSimpleName();

    public static Thread startNormalThread(Runnable action) {
        Thread thread = new Thread(action);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        return thread;
    }
}
