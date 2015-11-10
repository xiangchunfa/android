/*
 * DESEncryptUtils.java
 * classes : com.qdoc.client.util.DESEncryptUtils
 * @author xiangyutian
 * V 1.0.0
 * Create at 2014-8-20 下午8:20:15
 */
package com.zqds.client.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.util.Base64;

/**
 * com.qdoc.client.util.DESEncryptUtils
 * 
 * @author xiangyutian <br/>
 *         create at 2014-8-3 下午8:20:15
 */
public class DESEncryptUtils {
    private static final String DES_ALGORITHM = "DES";
    public static final String DES_PASSWORD = "123456";

    public static String encryption(String plainData) throws Exception {
        return encryption(plainData, DES_PASSWORD);
    }

    /**
     * DES加密
     * 
     * @param plainData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String encryption(String plainData, String secretKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {

        }

        try {
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
            // be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());

            return URLEncoder.encode(new String(Base64.encode(buf, Base64.DEFAULT)));

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }

    }

    public static String decryption(String secretData) throws Exception {
        return decryption(secretData, DES_PASSWORD);
    }

    /**
     * DES解密
     * 
     * @param secretData
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);

        }

        try {

            byte[] buf = cipher.doFinal(Base64.decode(URLDecoder.decode(secretData, "UTF-8"), Base64.DEFAULT));

            return new String(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * 获得秘密密钥
     * 
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Key generateKey(String secretKey) throws Exception {
        byte[] DESkey = new String(secretKey).getBytes();// 设置密钥，略去
        DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
        return keyFactory.generateSecret(keySpec);// 得到密钥对象

    }
}
