package com.zqds.client.util;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

	/**
	 * 对齐输出，左边补0
	 * @param num
	 * @param value
	 * @return
	 */
	public static String toAligning(int num, int value) {
		String result = Integer.valueOf(value).toString();
		while (num > result.length()) {
			result = "0" + result;
		}
		return result;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static String getRandomNumber() {
		return String.valueOf(new Date().getTime()+(new Random().nextInt(89)+10));
	}
}
