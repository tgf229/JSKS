package com.jsksy.app.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

import com.jsksy.app.constant.Global;

/**
 * ����빤���� �˴������Ⱥͼ��ܷ�ʽ��Ҫ��Ӧ����
 * 
 * ��ʱ��֪�Ķ�ӦΪ�� 3DES-168 DES-56 AES-128
 * 
 * @author tgf
 * @version [1.0, 2009-12-5]
 * @since [M2M/1.0]
 */
public final class SecurityUtils
{
    
    /**
     * 3DES���ܷ�ʽ
     */
    public static final String ALGORITHM_3DES = "DESede/ECB/PKCS5Padding";
    
    /**
     * DES���ܷ�ʽ
     */
    public static final String ALGORITHM_DES = "DES";
    
    /**
     * AES���ܷ�ʽ
     */
    public static final String ALGORITHM_AES = "AES";
    
    /**
     * ��Կ����-256
     */
    public static final int KEY_LENGTH_256 = 256;
    
    /**
     * ��Կ����-256
     */
    public static final int KEY_LENGTH_128 = 128;
    
    /**
     * ��Կ����-168
     */
    public static final int KEY_LENGTH_168 = 168;
    
    /**
     * ��Կ����-56
     */
    public static final int KEY_LENGTH_56 = 56;
    
    /**
     * ��Կ����-24
     */
    public static final int KEY_LENGTH_24 = 24;
    
    private SecurityUtils()
    {
        
    }
    
    /**
     * �����ܵ�byte[]����,���ؽ��ܺ��ֽ�����
     * 
     * @param msg
     *            ������Ϣ��
     * @param srcKey
     *            �Ự��Կ
     * @return byte[] ���ܺ�byte[]
     * @throws Exception
     *             Exception
     */
    public static byte[] decode2Byte(byte[] msg, String srcKey)
        throws Exception
    {
        SecretKey sercKey = new SecretKeySpec(srcKey.getBytes(), ALGORITHM_3DES);
        
        byte[] base64Msg = decrypt(sercKey, msg, ALGORITHM_3DES);
        
        return Base64.decode(base64Msg, Base64.DEFAULT);
    }
    
    /**
     * �����ܵ��ַ�������,���ؽ��ܺ��ַ���
     * 
     * @param msg
     *            ������Ϣ��
     * @param srcKey
     *            �Ự��Կ
     * @return String ���ܺ�byte[]
     * @throws Exception
     *             Exception
     */
    public static String decode2Str(String msg, String srcKey)
        throws Exception
    {
        SecretKey sercKey = new SecretKeySpec(srcKey.getBytes(), ALGORITHM_3DES);
        
        String base64Msg = decrypt(sercKey, msg, ALGORITHM_3DES);
        
        return new String(Base64.decode(base64Msg, Base64.DEFAULT), "UTF-8");
    }
    
    /**
     * �����ַ���,���ؼ��ܺ���ֽ�����
     * 
     * @param msg
     *            �ַ���
     * @param srcKey
     *            �Ự��Կ
     * @throws Exception
     *             Exception
     * @return byte[] �ֽ�����
     */
    public static byte[] encode2Byte(String msg, String srcKey)
        throws Exception
    {
        SecretKey sercKey = new SecretKeySpec(srcKey.getBytes(), SecurityUtils.ALGORITHM_3DES);
        
        byte[] base64Msg = Base64.encode(msg.getBytes("UTF-8"), Base64.DEFAULT);
        
        return SecurityUtils.encrypt(sercKey, base64Msg, SecurityUtils.ALGORITHM_3DES);
    }
    
    /**
     * �����ַ��� �����ַ���
     * 
     * @param json
     *            �ַ���
     * @param srcKey
     *            �Ự��Կ
     * @return ���ܺ���ַ���
     * @throws Exception
     *             �쳣
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String encode2Str(String msg)
        throws Exception
    {
        String srcKey = Global.getKey();
        
        SecretKey sercKey = new SecretKeySpec(srcKey.getBytes(), ALGORITHM_3DES);
        
        String base64Msg = Base64.encodeToString(msg.getBytes("UTF-8"), Base64.DEFAULT);
        
        return encrypt(sercKey, base64Msg, ALGORITHM_3DES);
        
    }
    
    /**
     * ����
     * 
     * @param secretKey
     *            ��Կ
     * @param msg
     *            ����
     * @param algorithm
     *            ���ܷ�ʽ
     * @return ����
     * @throws UtilException
     *             UtilException
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String decrypt(SecretKey secretKey, String msg, String algorithm)
        throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] arr = Base64.decode(stringToBytes(msg), Base64.DEFAULT);
            byte[] src = cipher.doFinal(arr);
            return new String(src, "UTF-8");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(e);
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(e);
        }
        catch (InvalidKeyException e)
        {
            throw new Exception(e);
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception(e);
        }
        catch (BadPaddingException e)
        {
            throw new Exception(e);
        }
        
    }
    
    /**
     * ����
     * 
     * @param secretKey
     *            ��Կ
     * @param msg
     *            ����
     * @param algorithm
     *            ���ܷ�ʽ
     * @return ����
     * @throws UtilException
     *             UtilException
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] decrypt(SecretKey secretKey, byte[] msg, String algorithm)
        throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(msg);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(e);
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(e);
        }
        catch (InvalidKeyException e)
        {
            throw new Exception(e);
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception(e);
        }
        catch (BadPaddingException e)
        {
            throw new Exception(e);
        }
    }
    
    /**
     * ����
     * 
     * @param secretKey
     *            ��Կ
     * @param msg
     *            ����
     * @param algorithm
     *            ���ܷ�ʽ
     * @return ����
     * @throws UtilException
     *             UtilException
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String encrypt(SecretKey secretKey, String msg, String algorithm)
        throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] base64 = Base64.encode(cipher.doFinal(stringToBytes(msg)), Base64.DEFAULT);
            return bytesToSting(base64);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(e);
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(e);
        }
        catch (InvalidKeyException e)
        {
            throw new Exception(e);
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception(e);
        }
        catch (BadPaddingException e)
        {
            throw new Exception(e);
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
        
    }
    
    /**
     * ����
     * 
     * @param secretKey
     *            ��Կ
     * @param msg
     *            ����
     * @param algorithm
     *            ���ܷ�ʽ
     * @return ����
     * @throws UtilException
     *             UtilException
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] encrypt(SecretKey secretKey, byte[] msg, String algorithm)
        throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(msg);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Exception(e);
        }
        catch (NoSuchPaddingException e)
        {
            throw new Exception(e);
        }
        catch (InvalidKeyException e)
        {
            throw new Exception(e);
        }
        catch (IllegalBlockSizeException e)
        {
            throw new Exception(e);
        }
        catch (BadPaddingException e)
        {
            throw new Exception(e);
        }
    }
    
    /**
     * byteת��String
     * 
     * @param arr
     *            src
     * @return String
     * @see [�ࡢ��#��������#��Ա]
     */
    public static String bytesToSting(byte[] arr)
    {
        try
        {
            return new String(arr, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // ���쳣���ᷢ��
            
            return "";
        }
    }
    
    /**
     * ���ַ���ת����byte����
     * 
     * @param str
     *            �ַ���
     * @return ת�����byte����
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] stringToBytes(String str)
    {
        try
        {
            if (str != null && str.length() > 0)
            {
                return str.getBytes("UTF-8");
            }
            else
            {
                return new byte[0];
            }
        }
        catch (Exception e)
        {
            return new byte[0];
        }
    }
    
    public final static String get32MD5Str(String str)
    {
        if (GeneralUtils.isNullOrZeroLenght(str))
        {
            return str;
        }
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }
    
}
