package com.zqds.client.util;
import java.lang.reflect.Method;
import java.util.Vector;
import android.telephony.TelephonyManager;

public class PhoneUtil {
	/**
	 * 判断电话号码字符是否有效
	 * @param mobile
	 * @return
	 */
	public static boolean isValidNumber(String mobile){
		if(mobile == null)
			return false;
		mobile = mobile.trim();
		if("".equals(mobile))
			return false;
		if(mobile.length() < 5)
			return false;
		String rule = "+0123456789";
		for(int i=0;i<mobile.length();i++) {
			if(rule.indexOf(mobile.charAt(i)) == -1)
				return false;
		}
		return true;
	}
	
	public static boolean isValidIMEI(String IMEI){
		if(IMEI == null)
			return false;
		IMEI = IMEI.trim();
		if("".equals(IMEI.trim()))
			return false;
		if(!NumberUtil.isNumeric(IMEI))
			return false;
		if(IMEI.length() < 15)
			return false;
		return true;
	}

	public static boolean isValidIMSI(String IMSI){
		if(IMSI == null)
			return false;
		IMSI = IMSI.trim();
		if("".equals(IMSI.trim()))
			return false;
		if(!NumberUtil.isNumeric(IMSI))
			return false;
		if(IMSI.length() < 15)
			return false;
		return true;
	}
	
	public static String getMCC(String aIMSI) {
		if(StringUtils.isEmpty(aIMSI))
			return "";
		return aIMSI.substring(0, 3);
	}
	
	public static String getMNC(String aIMSI) {
		if(StringUtils.isEmpty(aIMSI))
			return "";
		return aIMSI.substring(3, 5);
	}
	
	/**
	 * 是否特殊的号码
	 * @param mobile
	 * @return
	 */
	public static boolean isPeculiarNumber(String mobileNumber) {
		if(!isValidNumber(mobileNumber))
			return true;
		if(mobileNumber.startsWith("+"))
			mobileNumber = "00"+mobileNumber.substring(1);
		if(mobileNumber.startsWith("000"))
			return true;
		
		if(mobileNumber.startsWith("0086")) {
			Vector<String> list = new Vector<String>();
			list.addElement("00861[^034589]");
			list.addElement("008610(0|1|9)");
			list.addElement("00862\\d{1}(0|1|9)");
			list.addElement("0086[^12]\\d{2}(0|1|9)");
			boolean bRet = RegexUtil.contains(mobileNumber, list);
			if(bRet) return bRet;
			list = new Vector<String>();
			list.addElement("00861(3|4|5|8|9)\\d{9}$");
			list.addElement("008610\\d{8}$");
			list.addElement("00862\\d{9}$");
			list.addElement("0086(3|4|5|6|7|8|9)\\d{9,10}$");
			return (!RegexUtil.contains(mobileNumber, list));
		}
		return false;
	}
	
	/**
	 * 是否中国的手机号
	 * @param phoneNumber
	 * @param ignorePeculiar
	 * @return
	 */
	public static boolean isChinaMobile(String phoneNumber) {
		String number = PhoneUtil.parseChinaNumber(phoneNumber);
		if(isPeculiarNumber(number))
			return false;
		if(number.startsWith("00861") && !number.startsWith("008610"))
			return true;
		return false;
	}
	
	/**
	 * 解析为简写的中国手机号，非（中国）手机返回空
	 * @param phoneNumber 输入号码参数
	 * @return 成功返回：139260xxxxx，失败返回null
	 */
	public static String parseChinaMobile(String phoneNumber) {
		String number = PhoneUtil.parseChinaNumber(phoneNumber);
		if(isPeculiarNumber(number))
			return null;
		if(!number.startsWith("00861"))
			return null;
		if(number.startsWith("008610"))
			return null;
		try {
			return number.substring(4);
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * 是否中国的号码
	 * @param phoneNumber
	 * @param ignorePeculiar 是否忽略特殊号码 et.075595555、075510086这类号码
	 * @return
	 */
	public static boolean isChinaNumber(String phoneNumber, boolean ignorePeculiar) {
		String number = PhoneUtil.parseChinaNumber(phoneNumber, ignorePeculiar);
		if(!isValidNumber(number))
			return false;
		if(number.startsWith("0086"))
			return true;
		return false;
	}
	
	/**
	 * 由完整的中国号码，反向解析
	 * @param phoneNumber 电话号码 et. mobile:008613xxxxxxxx telphone:00867558xxxxxxx
	 * @return 成功返回 et. mobile:139xxxxxxxx telphone:07558xxxxxxx
	 * 			失败返回null
	 */
	public static String reverseChinaNumber(String phoneNumber) {
		if(StringUtils.isEmpty(phoneNumber))
			return null;
		if(!isValidNumber(phoneNumber))
			return null;
		if(!phoneNumber.startsWith("0086"))
			return null;

		phoneNumber = phoneNumber.substring(4);
		if(!phoneNumber.startsWith("1") || phoneNumber.startsWith("10"))
			phoneNumber = "0"+phoneNumber;
		return phoneNumber;
	}
	
	/**
	 * 解释中国号码
	 * @param phoneNumber 电话号码
	 * @param ignorePeculiar 是否忽略特殊号码 et.075595555、075510086这类号码
	 * @return 成功返回 et. mobile:008613xxxxxxxx telphone:00867558xxxxxxx
	 * 			失败返回null
	 */
	public static String parseChinaNumber(String phoneNumber, boolean ignorePeculiar) {
		if(ignorePeculiar && isPeculiarNumber(phoneNumber))
			return null;
		return parseChinaNumber(phoneNumber);
	}
	
	/**
	 * 解析中国完整的号码
	 * @param phoneNumber
	 * @return 成功返回 et. mobile:008613xxxxxxxx telphone:00867558xxxxxxx
	 * 			失败返回null
	 */
	public static String parseChinaNumber(String phoneNumber) {
		if(!isValidNumber(phoneNumber))
			return null;
		if(phoneNumber.startsWith("000"))
			return null;
		if(phoneNumber.startsWith("+"))
			phoneNumber = "00"+phoneNumber.substring(1);
		
		String result = null;
		if(phoneNumber.startsWith("00")) {
			if(!phoneNumber.startsWith("0086"))
				return null;
			result = phoneNumber;
		} else {
			if(phoneNumber.startsWith("0"))
				result = "0086"+phoneNumber.substring(1);
			else
				result = "0086"+phoneNumber;
		}
		return result;
	}
	
	public static void main(String[] args) {		
		System.out.println(PhoneUtil.parseChinaNumber("075595599", true));
	}
	
	/**
	 * 是否为正确的手机号码
	 */
	public static void isRightMobile() {
		
	}
}
