package com.huwei.neteasemusic.util.security;

import android.util.Base64;

import com.huwei.neteasemusic.util.JsonUtils;
import com.huwei.neteasemusic.util.LogUtils;
import com.huwei.neteasemusic.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author jerry
 * @date 2016/07/09
 */
public class SecuityRequest {
    public static final String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
    public static String nonce = "0CoJUm6Qyw8W8jud";
    public static final String e = "010001";


    public static final String TAG = "SecuityRequest";

    /**
     * 加密参数
     *
     * @param object
     * @return payload = {
     * 'params': enc_text,
     * 'encSecKey': enc_aes_key,
     * }
     */
    public static String[] encrypt_request(Object object) {
        String text = JsonUtils.toJSONString(object);

        //// TODO: 16-7-9
        text = "{\"br\": 320000, \"ids\": [26117507], \"csrf_token\": null}";

        String second_aes_key = create_aes_key(16);

        String tempAesText;
        final String enc_text = aes_encrypt(
                tempAesText = aes_encrypt(text, nonce), second_aes_key);

        final String enc_aes_key = rsa_encrypt(second_aes_key);

        LogUtils.d(TAG, "tempAesText:" + tempAesText + "     enc_text:" + enc_text);

        return new String[]{enc_text, enc_aes_key};
    }

    public static String create_aes_key(int size) {
        StringBuilder builder = new StringBuilder();
//        return StringUtils.randomAZ09(size);
//        String randStr =  StringUtils.randomAZ09(size);
//                for (int i = 0; i < randStr.length(); i++) {
//            String b = randStr.substring(i, i + 1);
//            builder.append(CHexConver.str2HexStr(b).substring(2));
//        }
//        return builder.substring(0, 16);

        return "0123456789abcdef";
    }

    public static String aes_encrypt(String text, String key) {
        int pad = 16 - text.length() % 16;
        StringBuilder last = new StringBuilder();
        for (int i = 0; i < pad; i++) {
            last.append((char) pad);
        }

        text = text + last.toString();
        String enc_text = AESUtils.encrypt4AES(text, key, "0102030405060708".getBytes());

//        return enc_text;

//        String enc_text_encode = Base64Utils.encode(enc_text.getBytes());
        return enc_text;
    }


    public static String rsa_encrypt(String text) {
        String e = "010001";
        String n = modulus;
        String revertText = StringUtils.reverse(text);

        try {
            PublicKey publicKey = RSAUtils.getPublicKey(modulus, e);

            BigInteger bigM = new BigInteger(CHexConver.str2HexStr(revertText), 16);
            BigInteger bigN = new BigInteger(n,16);
            BigInteger bigE = new BigInteger(e,16);

            BigInteger bigC = bigM.modPow(bigE,bigN);

            return  StringUtils.flushRight('0',256,bigC.toString(16).toLowerCase());

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
        }

        return "";
    }
}
