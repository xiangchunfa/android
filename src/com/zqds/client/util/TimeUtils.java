package com.zqds.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.zqds.client.util.DateUtil;

/** 
 * 字符串操作工具包
 * @author ANTONY
 * @version 1.0
 * @created 2014-02-12
 */
public class TimeUtils 
{
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	/**
	 * 将字符串转位日期类型
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param preTime 上一个时间字符串
	 * @param currentTime 当前时间字符串
	 * @param pattern 时间格式
	 * @return
	 */
	public static String friendly_time(String preTime, String currentTime, String pattern) {
		Date currentDate = DateUtil.StrToDate(currentTime, pattern);
		Date preDate = DateUtil.StrToDate(preTime, pattern);
		if (preDate == null || currentDate == null) {
			return friendly_time(currentTime, pattern);
		}
		long i = (currentDate.getTime() - preDate.getTime()) / 60000;
		//发送或接收聊天内容时，判断当与上一条聊天内容时间间隔超过15min时，显示一个新的时间标签+新的聊天内容。
		if (i > 15) {
			return friendly_time(currentTime, pattern);
		} else {
			return "";
		}
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param sdate 时间字符串
	 * @return
	 */
	public static String friendly_time(String sdate) {
		return friendly_time(sdate, DateUtil.timeFormat1);
	}
	
	/**
	 * 以友好的方式显示时间
	 * @param sdate 时间字符串
	 * @param pattern 时间格式
	 * @return
	 */
	public static String friendly_time(String sdate, String pattern) {
		Date time = DateUtil.StrToDate(sdate, pattern);
		if(time == null) {
			return "";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
			return ftime;
		}
   		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if (days == 0) {
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else 
				ftime = hour+"小时前";
		} else if (days == 1) {
			ftime = "昨天 " + time.getHours() + ":" + time.getMinutes();
		} else if (days == 2) {
			ftime = "前天 " + time.getHours() + ":" + time.getMinutes();
		} else if (cal.get(Calendar.YEAR) != Integer.valueOf(DateUtil.strToStr(sdate, DateUtil.timeFormat4))) {
			ftime = DateUtil.strToStr(sdate, DateUtil.timeFormat2);
		} else {			
			ftime = time.getMonth() + 1 +"-" + time.getDate() + " " +  time.getHours() + ":" + time.getMinutes();
		}
		return ftime;
	}
	
	/**
	 * 判断给定字符串时间是否为今日
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate){
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if(time != null){
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 判断给定字符串是否空白串。
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	/**
	 * 格式化当前时间
	 * @return
	 */
	public static String formatCurrentTime(){
		Calendar cal = Calendar.getInstance();
		String currentTime = dateFormater.get().format(cal.getTime());
		return currentTime;
	}
	
	/**
	 * 计算时间字符串的时间间隔
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long timeInterval(String begin,String end){
		long interval = 0;
		try {
			Date begin_date = dateFormater.get().parse(begin);
			Date end_date = dateFormater.get().parse(end);
			interval = (end_date.getTime()-begin_date.getTime())/(1000*60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}
}
