package com.zqds.client.db;

import android.content.Context;

/**
 * Description : 数据库工具类
 * @author     : 向春发
 **/
public class DatabaseService {
	private static String TAG = "DatabaseService";
    private DatabaseHelper dbOpenHelper;
    private static DatabaseService instance = null;

    public DatabaseService(Context context) {
        this.dbOpenHelper = DatabaseHelper.getInstance(context, DatabaseHelper.QDOC_DB_NAME);
    }

    public synchronized static DatabaseService getInstance(Context ctx) {
        if (null == instance) {
            instance = new DatabaseService(ctx);
        }
        return instance;
    }
}