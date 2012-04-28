package com.jl.common.security3a;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
     * ָ���㷨ΪMD5��MessageDigest
     */
    private static MessageDigest messageDigest = null;
    
    /**
     * 16�����ַ���
     */
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * ��ʼ��messageDigest�ļ����㷨ΪMD5
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
     * �������ֽ���ת����16�����ַ���
     * @param bt Ŀ���ֽ�
     * @return ת�����
     */
    private static String byteToHex(byte bt)
    {
        return HEX_DIGITS[(bt & 0xf0) >> 4] + "" + HEX_DIGITS[bt & 0xf];
    }
    
    
    /**
     * ���ֽ�������ָ�������������ת����16�����ַ���
     * @param bytes Ŀ���ֽ�����
     * @param start ��ʼλ�ã�������λ�ã�
     * @param end ����λ�ã���������λ�ã�
     * @return ת�����
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
     * ���ֽ�����ת����16�����ַ���
     * @param bytes Ŀ���ֽ�����
     * @return ת�����
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
    	
        //����Դ����
        md5 = "12345678";  
        // ��ȡ�ֽ������ɱ��룩
        b = md5.getBytes();
        // MD5 hash
        b = messageDigest.digest(b);
        // ת����16�����ַ�
        md5 = bytesToHex(b);
        System.out.println(md5);
        
        //����Դ����
        md5 = "abc111";  
        // ��ȡ�ֽ������ɱ��룩
        b = md5.getBytes("UTF-16LE");
        // MD5 hash
        b = messageDigest.digest(b);
        // ת����16�����ַ�
        md5 = bytesToHex(b);
        System.out.println(md5);
    }
    
    public static void main(String[] args) throws IOException
    {
    	test();
    	System.out.println(MD5_UTF16LE("12345678"));
    }
    
}