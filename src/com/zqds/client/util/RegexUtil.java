package com.zqds.client.util;

import java.util.Vector;

public class RegexUtil {

	public static boolean contains(String mobileNumber, Vector<String> list) {
		if(list == null)
			return false;
		for(int i=0;i<list.size();i++) {
			if (mobileNumber.matches(list.elementAt(i)))
				return true;
		}
		return false;
	}

}
