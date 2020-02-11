package com.blz.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * 密码MD5 加密
 */
public class MD5Util {

    //对密码MD5加密
    public static String EncoderPwdByMd5(String str)throws NoSuchAlgorithmException,UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(str.getBytes("utf-8")))+"flag=blz";
        //return base64en.decodeBuffer(md5.digest(str.getBytes("utf-8")));
    }
}