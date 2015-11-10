/**
 * 
 */
package com.zqds.client.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zqds.client.util.DateUtil;
import com.zqds.client.util.FileUtil;
import com.zqds.client.util.LogUtils;

/**
* Description : 数据库操作的助手类
* @author     : 向春发
**/
public class DatabaseHelper extends SQLiteOpenHelper { 
	/**数据库列表，所有系统使用的数据库必须在这里注册*/
	public static final Set<String> DATABASES = new HashSet<String>(); 
	public static boolean isReplaceDB = true;//是否替换本地数据库
	/**可以被当作字符串的数据类型列表*/ 
	private static final Set<String> STRING_TYPES = new HashSet<String>();
	public static final String TAG = DatabaseHelper.class.getSimpleName();
	public static final String  QDOC_DB_NAME= "qdoc.db"; //数据库名称为qdoc
	public static final String DB_PATH="mnt/sdcard/qdoc/dp/";//数据库保存路径
	public static boolean isCopyDatabaseFile = false;//是否复制数据库文件到SD卡

	/**
	 * 数据库版本号
	 */
	private static final int DATABASE_VERSION = 5;

	/**
	 * 表名称
	 */
	public static final String APP_CONSULT_GUIDE = "app_consult_guide";
    public static final String APP_CONSULT_MESSAGE_COUNT = "app_consult_message_count";
    public static final String APP_CONSULT_RECORD = "app_consult_record";//聊天记录
    public static final String APP_QUICK_PHRASES = "app_quick_phrases";//快捷短语
    public static final String APP_NEW_ARTICLEID = "app_new_articleid";//当天资讯
    public static final String APP_DRAFT_LIST = "app_draft_list";//草稿列表

	public static final String CREATE_APP_CONSULT_GUIDE =
			 "create table if not exists "+APP_CONSULT_GUIDE+
			 " (account varchar(20) NOT NULL PRIMARY KEY)";

	public static final String CREATE_CONSULT_MESSAGE_COUNT =    
			"create table if not exists " + APP_CONSULT_MESSAGE_COUNT +
			" (consult_id varchar(20) NOT NULL PRIMARY KEY," +
			" account varchar(20)," +		
			" count integer)";
	
	public static final String CREATE_QUICK_PHRASES =   
			"create table if not exists " + APP_QUICK_PHRASES +
			" (id integer PRIMARY KEY AUTOINCREMENT," +
			" phrases_text text," +		
			" update_time varchar(30))";
	
	public static final String CREATE_APP_CONSULT_RECORD = "create table if not exists " + APP_CONSULT_RECORD + "(id integer primary key autoincrement,msg_id varchar(64)," +
			"content text,update_time varchar(30),consult_id varchar(20),account varchar(20),msg_type integer)";
	
	public static final String CREATE_NEW_INFO_LIST_ARTICLEID = "create table if not exists " + APP_NEW_ARTICLEID + "(articleid varchar(20) UNIQUE)";
	public static final String CREATE_DRAFT_LIST = "create table if not exists " + APP_DRAFT_LIST + 
			"(consult_id varchar(20) NOT NULL PRIMARY KEY," +
			" draft_text text)";
	
	public static final Set<String> getStringTypes(){
		return STRING_TYPES;
	}

	public static List<String> getDefaultData(){ 
		List<String> dataList = new ArrayList<String>();
		return dataList;
	}
 
	
	public final SQLiteDatabase getSQLiteDatabase(){
		SQLiteDatabase sdb = null; 
		try{   
			sdb = this.getWritableDatabase(); 
		}catch(Exception e){  
			sdb = this.getReadableDatabase(); 
		} 
		return sdb;   
	}


	
	private DatabaseHelper(Context context, String name) {  
		super(context, name, null, DATABASE_VERSION); 
	}
	
	private static DatabaseHelper instance;  

	public static final DatabaseHelper getInstance(Context context, String name){
		FileUtil.copyFile(getDatabasesPath(context)+name,DB_PATH + name, true);
		if(instance == null) {
			instance = new DatabaseHelper(context,name);  	
		}
	    return instance;  
	}

	//复制数据库文件
	public static void copyFile(Context context,String name,boolean isReplaceDB){
		FileUtil.copyDatabaseFile(context,name,getDatabasesPath(context),isReplaceDB);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_APP_CONSULT_GUIDE);
		db.execSQL(CREATE_CONSULT_MESSAGE_COUNT);
		db.execSQL(CREATE_QUICK_PHRASES);
		db.execSQL(CREATE_NEW_INFO_LIST_ARTICLEID);
		db.execSQL(CREATE_DRAFT_LIST);
		initData(db);
		LogUtils.i(TAG, "onCreate");
	}   

	/**
	 * 初始化数据
	 */
	public static void initData(SQLiteDatabase db) {
		initQuickPhrasesData(db);
	}
	
	/**
	 * 加载快捷短语初始数据
	 */
	public static void initQuickPhrasesData(SQLiteDatabase db) {
		String time = DateUtil.getDate(DateUtil.timeFormat1);
		db.execSQL("INSERT INTO " + APP_QUICK_PHRASES + "(phrases_text,update_time) VALUES ('点击右下角可添加快捷短语','" + time + "')");
		db.execSQL("INSERT INTO " + APP_QUICK_PHRASES + "(phrases_text,update_time) VALUES ('您好。这种情况多长时间了？','" + time + "')");
		db.execSQL("INSERT INTO " + APP_QUICK_PHRASES + "(phrases_text,update_time) VALUES ('这个问题不严重，请不要担心~','" + time + "')");
		db.execSQL("INSERT INTO " + APP_QUICK_PHRASES + "(phrases_text,update_time) VALUES ('你可以发张照片来看看吗？','" + time + "')");
		db.execSQL("INSERT INTO " + APP_QUICK_PHRASES + "(phrases_text,update_time) VALUES ('不用谢，这是举手之劳','" + time + "')");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  Log.i(TAG, "oldVersion="+oldVersion+";"+"newVersion="+newVersion);
			try {
				for (int i = oldVersion; i < newVersion; i++) {
					switch (i) {
					case 1:
						DatabaseVersionManagement.upgradedVersion1To2(db);
						break;
					case 3:
						DatabaseVersionManagement.upgradedVersion3To4(db);
						break;
					case 4:
						DatabaseVersionManagement.upgradeVersion4To5(db);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static String getDatabasesPath(Context context){ 
		System.out.println("======================"+context);
		return "/data/data/" + context.getPackageName() + "/databases/";  
	}
	
	public static void close(Cursor cursor){
		try {
			cursor.close();
		} catch(Exception e){}
	}
	
	public static void close(SQLiteDatabase sdb){
		try {
			sdb.close();   
		} catch(Exception e) { 
		}
	}
}


 