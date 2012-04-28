package com.jl.common.security3a;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
     * 指定算法为MD5的MessageDigest
     */
    private static MessageDigest messageDigest = null;
    
    /**
     * 16进制字符集
     */
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 初始化messageDigest的加密算法为MD5
     */
    static
    {
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 将单个字节码转换成16进制字符串
     * @param bt 目标字节
     * @return 转换结果
     */
    private static String byteToHex(byte bt)
    {
        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
    }
    
    
    /**
     * 将字节数组中指定区间的子数组转换成16进制字符串
     * @param bytes 目标字节数组
     * @param start 起始位置（包括该位置）
     * @param end 结束位置（不包括该位置）
     * @return 转换结果
     */
    private static String bytesToHex(byte bytes[], int start, int end)
    {
        StringBuilder sb = new StringBuilder();


        for(int i = start; i < start + end; i++)
        {
            sb.append(byteToHex(bytes[i]));
        }


        return sb.toString();
    }
    
    /**
     * 将字节数组转换成16进制字符串
     * @param bytes 目标字节数组
     * @return 转换结果
     */
    private static String bytesToHex(byte bytes[])
    {
        return bytesToHex(bytes, 0, bytes.length);
    }
    
    private static String MD5(String s,String charset) throws UnsupportedEncodingException
    {
    	return bytesToHex(messageDigest.digest(s.getBytes(charset)));
    }
    
    public static String MD5_UTF16LE(String s) throws UnsupportedEncodingException
    {
    	return MD5(s,"UTF-16LE");
    }
    
    public static void test() throws IOException
    {
    	String md5;
    	byte[] b;
    	
        //加密源密码
        md5 = "12345678";  
        // 获取字节流（可编码）
        b = md5.getBytes();
        // MD5 hash
        b = messageDigest.digest(b);
        // 转化成16进制字符
        md5 = bytesToHex(b);
        System.out.println(md5);
        
        //加密源密码
        md5 = "abc111";  
        // 获取字节流（可编码）
        b = md5.getBytes("UTF-16LE");
        // MD5 hash
        b = messageDigest.digest(b);
        // 转化成16进制字符
        md5 = bytesToHex(b);
        System.out.println(md5);
    }
    
    public static void main(String[] args) throws IOException
    {
    	test();
    	System.out.println(MD5_UTF16LE("12345678"));
    }
    
}