package com.huwei.neteasemusic.util.security;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author carlos carlosk@163.com
 * @version 创建时间：2012-5-17 上午9:48:35 类说明
 */

public class AESUtils {
    public static final String TAG = "AESUtils";

    public static String encrypt4AES(String source, String key,byte[] iv) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key1 = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key1, zeroIv);
            byte[] encryptedData = cipher.doFinal(source.getBytes());
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }
}