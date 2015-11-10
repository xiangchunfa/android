package com.zqds.client.db;

import com.zqds.client.util.LogUtils;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Description : 数据库版本管理
 * @author     : 向春发
 **/
public class DatabaseVersionManagement {
	private static String TAG = DatabaseVersionManagement.class.getSimpleName();
	/**
	 * 数据库版本升级：1 to 2
	 * 
	 * */
	public static void upgradedVersion1To2(SQLiteDatabase db) {
		try {
			db.execSQL("alter table app_consult_guide rename to temp_app_consult_guide");
			db.execSQL(DatabaseHelper.CREATE_APP_CONSULT_GUIDE);
			db.execSQL("insert into app_consult_guide select account from temp_app_consult_guide");
			db.execSQL("drop table if exists temp_app_consult_guide");
			db.execSQL(DatabaseHelper.CREATE_CONSULT_MESSAGE_COUNT);
			LogUtils.i(TAG, "UpgradedVersion1To2");
		} catch (Exception e) {
			LogUtils.i(TAG, e.getMessage());
		}
	}
	
	/**
	 * 数据库版本升级：3 to 4
	 * 
	 * */
	public static void upgradedVersion3To4(SQLiteDatabase db) {
		try {
			db.execSQL(DatabaseHelper.CREATE_QUICK_PHRASES); 
			DatabaseHelper.initQuickPhrasesData(db);
			LogUtils.i(TAG, "UpgradedVersion3To4");
		} catch (Exception e) {
			LogUtils.i(TAG, e.getMessage());
		}
	}
	
	/**
	 * 数据库版本升级：4 to 5
	 * @param db
	 */
	public static void upgradeVersion4To5(SQLiteDatabase db){
		try {
			db.execSQL(DatabaseHelper.CREATE_NEW_INFO_LIST_ARTICLEID);
			db.execSQL(DatabaseHelper.CREATE_DRAFT_LIST);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
