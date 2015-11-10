package com.zqds.client.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigService {
	public final String PASSWORD="Password";
	public final String USER_NAME="UserName";
	public final String MSISDN="MSISDN";
	public final String IMSI="IMSI";
	public final String ICCID="ICCID";
	public final String AUTO_LOGIN="autoLogin";
	public final String NEXT_UPGRADE_TIME="NextUpgradeTime";
	public final String FIRST_RUN = "FIRST_RUN";
	public final String APP_VERSION_CODE = "APP_VERSION_CODE";//app版本号
   public  final String PAY_WAY_VERSION_CODE = "PAY_WAY_VERSION_CODE";// 支付方式
	public  final String PAY_TYPE_VERSION_CODE = "PAY_TYPE_VERSION_CODE";// 支付类型
	
	
	private Context iContext = null;
	private SharedPreferences iPreferences = null;
	
	public ConfigService(Context aContext) {
		iContext = aContext;
		iPreferences = iContext.getSharedPreferences("SystemConfig", 
				Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE);
	}
	
	public boolean setString(String key, String value) {
		if(StringUtils.isEmpty(key))
			return false;
		
		Editor editor = iPreferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
		return true;
	}
	
	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String defaultValue) {
		return  iPreferences.getString(key, defaultValue);
	}
	
	public boolean setInteger(String key, int value) {
		if(StringUtils.isEmpty(key))
			return false;
		Editor editor = iPreferences.edit();
		editor.remove(key);
		editor.putInt(key, value);
		editor.commit();
		return true;		
	}
	
	public boolean setLong(String key, long value) {
		if(StringUtils.isEmpty(key))
			return false;
		Editor editor = iPreferences.edit();
		editor.remove(key);
		editor.putLong(key, value);
		editor.commit();
		return true;		
	}
	public long getLong(String key) {
		return  getLong(key, 0);		
	}
	
	public long getLong(String key, int defaultValue) {
		return  iPreferences.getLong(key, defaultValue);		
	}
	public int getInteger(String key) {
		return  getInteger(key, 0);		
	}
	
	public int getInteger(String key, int defaultValue) {
		return  iPreferences.getInt(key, defaultValue);		
	}
	
	public boolean setBoolean(String key, boolean value) {
		if(StringUtils.isEmpty(key))
			return false;
		
		Editor editor = iPreferences.edit();
		editor.remove(key);
		editor.putBoolean(key, value);
		editor.commit();
		return true;			
	}
	
	public boolean getBoolean(String key) {
		return  getBoolean(key, false);		
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return  iPreferences.getBoolean(key, defaultValue);		
	}
	
	public boolean remove(String key) {
		if(StringUtils.isEmpty(key))
			return false;
		
		Editor editor = iPreferences.edit();
		editor.remove(key).commit();
		return true;
	}
	
	public void clear() {		
		Editor editor = iPreferences.edit();
		editor.clear().commit();
	}
	public Editor getEditor(){
		Editor editor = iPreferences.edit();
		return editor;
	
	}
	public SharedPreferences getSharedPreferences(){
		return iPreferences;
	}
}
