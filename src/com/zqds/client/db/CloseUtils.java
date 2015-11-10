package com.zqds.client.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CloseUtils {
	
	public CloseUtils(){}
	
	/**
	 * 关闭输入流
	 * */
	public static void closeInputStream(InputStream... ins){
		if(ins != null && ins.length > 0){
			for(InputStream in : ins){
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 关闭输出流
	 * */
	public static void closeOutputStream(OutputStream... ous){
		if(ous != null && ous.length > 0){
			for(OutputStream out : ous){
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
