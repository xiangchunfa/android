package com.zqds.client.db;



import java.util.ArrayList;
import java.util.List;

import com.zqds.client.util.StringUtils;

/**
 * Description : 生成数据同步提交SQL的帮助类
 * @author     : 向春发
 **/
public class SQLHelper {

	public static final String TAG = "[SQLHelper]";
	/**表名*/
	private String tableName;
	/**列的集合*/
	private List<String> columns = new ArrayList<String>();
	/**具体的列的值*/
	private List<String> values = new ArrayList<String>();
	/**生成更新语句的where 对应的字段*/
	private List<String> whereColumns = new ArrayList<String>();
	
	public SQLHelper(){
		this.clear();
	}
	
	public SQLHelper(String tableName){
		this();
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 *添加SQL语句参数 
	 */
	public void addParameter(String key,String value,boolean iswhere){
		if(!columns.contains(key)){
			columns.add(key);
			if(StringUtils.isEmpty(value))
				value="";
			values.add("'"+value+"'");
			if(iswhere && !whereColumns.contains(key)){
				whereColumns.add(key);
			}
		}
	}
   
	public void addParameter(String key,String value){
	   addParameter(key, value, false);
	}
	
	/**
	 *修改字段值
	 */
	public void setParameter(String key,String value){
		if(columns.contains(key)){
			int index = columns.indexOf(key);
			values.set(index, "'"+value+"'");
		}
	}
	
	/**
	 *清除设置数据
	 */
	public void clear(){
		columns.clear();
		values.clear();
		whereColumns.clear();
	}
	
	/**
	 *创建修改语句 
	 */
	public String createUpdateSQL(){
		StringBuffer sql = new StringBuffer("UPDATE "+tableName+" SET ");
		List<String> sets = new ArrayList<String>();
		List<String> where = new ArrayList<String>();
 		for(int i = 0;i<columns.size();i++){
			String tempCol = columns.get(i);
			String appendSQL = " "+tempCol+"="+values.get(i);
			if(!whereColumns.contains(tempCol)){
				//加了更新列表
				sets.add(appendSQL);
			}else{
				//查询列表
				where.add(appendSQL);
			}
		}
		sql.append(BeanUtils.join(sets,","));
		sql.append(" WHERE ");
		sql.append(BeanUtils.join(where," AND "));
		return sql.toString();
	}
	
	/**
	 *创建插入语句 
	 */
	public String createInsertSQL(){
		StringBuffer sql = new StringBuffer("INSERT INTO "+tableName+"  ");
		sql.append("("+BeanUtils.join(columns,",")+")");
		sql.append(" VALUES ");
		sql.append("("+BeanUtils.join(values,",")+")");
		return sql.toString();
	}
	
	/**
	 *创建删除语句 
	 */
	public String createDeleteSQL(){
		StringBuffer sql = new StringBuffer("DELETE FROM "+tableName+" ");
		List<String> where = new ArrayList<String>();
 		for(int i = 0;i<columns.size();i++){
			String tempCol = columns.get(i);
			String appendSQL = " "+tempCol+"="+values.get(i);
			if(whereColumns.contains(tempCol)){
				//查询列表
				where.add(appendSQL);
			}
		}
 		if(where.size()>0){
		sql.append(" WHERE ");
		sql.append(BeanUtils.join(where," AND "));
 		}
		return sql.toString();
	}
	
	/**
	 *获取指定字段的值 
	 */
	protected String getValue(String params){
		int index = columns.indexOf(params);
		return (String) values.get(index);
	}
	
	
	
}
