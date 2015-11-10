/*
 * EncodeUtils.java
 * classes : com.qdoc.client.util.EncodeUtils
 * @author xiangyutian
 * V 1.0.0
 * Create at 2014-7-12 下午7:51:46
 */
package com.zqds.client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.qdoc.client.util.EncodeUtils
 * 
 * @author xiangyutian <br/>
 *         create at 2014-7-12 下午7:51:46
 */
public class EncodeUtils {

    private static char[] DigitLower = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static char[] DigitUpper = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * MD5加密，共32位
     * 
     * @param content
     * @return
     */
    public static String encodeMD5(String content) {
        return cryptMD5(content);// HashEncrypt.encode(CryptType.MD5, content);
    }

    /**
     * MD5加密，算法由4.3及以下版本继承而来
     * 
     * @param str
     * @return
     */
    public static String cryptMD5(String str) {
        return crypt(str, "utf-8");
    }

    private static String crypt(String str, String encode) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(encode));
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (Exception e) {
            return "";
        }
        return hexString.toString();
    }

    public static String urlEncode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "utf-8").replaceAll("\\+", "%20").replaceAll("%7E", "~").replaceAll("\\*", "%2A");
    }

    public static String urlDecode(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str, "utf-8");
    }

    /**
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Lower(String srcStr) {
        String sign = "lower";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    /**
     * @param srcStr
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NullPointerException
     */
    public static String getMD5Upper(String srcStr) {
        String sign = "upper";
        try {
            return processStr(srcStr, sign);
        } catch (NoSuchAlgorithmException e) {
        } catch (NullPointerException e) {
        }
        return "";
    }

    private static String processStr(String srcStr, String sign) throws NoSuchAlgorithmException, NullPointerException {
        MessageDigest digest;
        String algorithm = "MD5";
        String result = "";
        digest = MessageDigest.getInstance(algorithm);
        digest.update(srcStr.getBytes());
        byte[] byteRes = digest.digest();

        int length = byteRes.length;

        for (int i = 0; i < length; i++) {
            result = result + byteHEX(byteRes[i], sign);
        }

        return result;
    }

    /**
     * @param bt
     * @return
     */
    private static String byteHEX(byte bt, String sign) {

        char[] temp = null;
        if (sign.equalsIgnoreCase("lower")) {
            temp = DigitLower;
        } else if (sign.equalsIgnoreCase("upper")) {
            temp = DigitUpper;
        } else {
            throw new java.lang.RuntimeException("");
        }
        char[] ob = new char[2];

        ob[0] = temp[(bt >>> 4) & 0X0F];

        ob[1] = temp[bt & 0X0F];

        return new String(ob);
    }

}
