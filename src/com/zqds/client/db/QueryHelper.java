package com.zqds.client.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zqds.client.util.PlatformException;
import com.zqds.client.util.StringUtils;

/**
 * Description : 数据库操作帮助类
 * @author : 向春发
 **/
public class QueryHelper {

	private String database;
	private String sql;
	private List<String> parameters = new ArrayList<String>();
	private SQLiteDatabase db;
    private Context context;
	public QueryHelper() {

	}

	public QueryHelper(String database, String sql, String[] args) {
		this.database = database;
		this.sql = sql;
		if (!BeanUtils.emptyArray(args)) {
			this.parameters = Arrays.asList(args);
		}
	}

	public QueryHelper(String database, String sql) {
		this.database = database;
		this.sql = sql;
	}

	public QueryHelper(SQLiteDatabase db, String sql) {
		this.db = db;
		this.sql = sql;
	}

	public QueryHelper(String sql) {
		this.sql = sql;
	}
	
	public void clear() {
		this.database = "";
		this.sql = "";
		this.parameters.clear();

	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void addParameters(List<String> args) {
		if (!BeanUtils.emptyCollection(args)) {
			this.parameters.addAll(args);
		}
	}

	public void addParameters(String[] args) {
		if (!BeanUtils.emptyArray(args)) {
			this.parameters.addAll(Arrays.asList(args));
		}
	}

	public void addParameter(String args) {
		parameters.add(args);
	}

	/**
	 * 执行更新语句
	 */
	public void executeSQL(Context context) {
		SQLiteDatabase sdb = DatabaseHelper.getInstance(context, database)
				.getReadableDatabase();
		try {
			if (StringUtils.isEmpty(sql)) {
				throw new PlatformException("NO SET SQL");
			}
			String[] args = new String[] {};
			if (!BeanUtils.emptyCollection(this.parameters)) {
				args = (String[]) this.parameters
						.toArray(new String[this.parameters.size()]);
			}
			sdb.execSQL(this.sql, args);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sdb.close();
		}
	}

	/**
	 * 执行带数据库的更新语句
	 * 
	 * @param sdb
	 */
	public void executeSQL(SQLiteDatabase sdb) {
		try {
		if (StringUtils.isEmpty(sql)) {
			throw new PlatformException("NO SET SQL");
		}
		String[] args = new String[] {};
		if (!BeanUtils.emptyCollection(this.parameters)) {
			args = (String[]) this.parameters
					.toArray(new String[this.parameters.size()]);
		}
		sdb.execSQL(this.sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 执行单个查询
	 * 
	 * @return
	 */
	public JSONObject getSingleValue(Context context) {
		JSONObject obj = null;
		SQLiteDatabase sdb = DatabaseHelper.getInstance(context,
				database).getReadableDatabase();
		Cursor cursor = null;
		try {
			if (StringUtils.isEmpty(sql)) {
				throw new PlatformException("NO SET SQL");
			}
			String[] args = new String[] {};
			if (!BeanUtils.emptyCollection(this.parameters)) {
				args = (String[]) this.parameters
						.toArray(new String[this.parameters.size()]);
			}
			cursor = sdb.rawQuery(this.sql, args);
			if (cursor.getCount() > 0) {
				obj = BeanUtils.convertCusorJsonObj(cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseHelper.close(cursor);
			sdb.close();
		}
		return obj;
	}
}
