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
 * 编解码工具类 此处，长度和加密方式需要对应起来
 * 
 * 暂时可知的对应为： 3DES-168 DES-56 AES-128
 * 
 * @author tgf
 * @version [1.0, 2009-12-5]
 * @since [M2M/1.0]
 */
public final class SecurityUtils
{
    
    /**
     * 3DES加密方式
     */
    public static final String ALGORITHM_3DES = "DESede/ECB/PKCS5Padding";
    
    /**
     * DES加密方式
     */
    public static final String ALGORITHM_DES = "DES";
    
    /**
     * AES加密方式
     */
    public static final String ALGORITHM_AES = "AES";
    
    /**
     * 密钥长度-256
     */
    public static final int KEY_LENGTH_256 = 256;
    
    /**
     * 密钥长度-256
     */
    public static final int KEY_LENGTH_128 = 128;
    
    /**
     * 密钥长度-168
     */
    public static final int KEY_LENGTH_168 = 168;
    
    /**
     * 密钥长度-56
     */
    public static final int KEY_LENGTH_56 = 56;
    
    /**
     * 密钥长度-24
     */
    public static final int KEY_LENGTH_24 = 24;
    
    private SecurityUtils()
    {
        
    }
    
    /**
     * 将加密的byte[]解密,返回解密后字节数组
     * 
     * @param msg
     *            加密消息体
     * @param srcKey
     *            会话密钥
     * @return byte[] 解密后byte[]
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
     * 将加密的字符串解密,返回解密后字符串
     * 
     * @param msg
     *            加密消息体
     * @param srcKey
     *            会话密钥
     * @return String 解密后byte[]
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
     * 加密字符串,返回加密后的字节数组
     * 
     * @param msg
     *            字符串
     * @param srcKey
     *            会话密钥
     * @throws Exception
     *             Exception
     * @return byte[] 字节数组
     */
    public static byte[] encode2Byte(String msg, String srcKey)
        throws Exception
    {
        SecretKey sercKey = new SecretKeySpec(srcKey.getBytes(), SecurityUtils.ALGORITHM_3DES);
        
        byte[] base64Msg = Base64.encode(msg.getBytes("UTF-8"), Base64.DEFAULT);
        
        return SecurityUtils.encrypt(sercKey, base64Msg, SecurityUtils.ALGORITHM_3DES);
    }
    
    /**
     * 加密字符串 返回字符串
     * 
     * @param json
     *            字符串
     * @param srcKey
     *            会话密钥
     * @return 加密后的字符串
     * @throws Exception
     *             异常
     * @see [类、类#方法、类#成员]
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
     * 解码
     * 
     * @param secretKey
     *            密钥
     * @param msg
     *            密文
     * @param algorithm
     *            加密方式
     * @return 明文
     * @throws UtilException
     *             UtilException
     * @see [类、类#方法、类#成员]
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
     * 解码
     * 
     * @param secretKey
     *            密钥
     * @param msg
     *            密文
     * @param algorithm
     *            加密方式
     * @return 明文
     * @throws UtilException
     *             UtilException
     * @see [类、类#方法、类#成员]
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
     * 加密
     * 
     * @param secretKey
     *            密钥
     * @param msg
     *            明文
     * @param algorithm
     *            加密方式
     * @return 密文
     * @throws UtilException
     *             UtilException
     * @see [类、类#方法、类#成员]
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
     * 加密
     * 
     * @param secretKey
     *            密钥
     * @param msg
     *            明文
     * @param algorithm
     *            加密方式
     * @return 密文
     * @throws UtilException
     *             UtilException
     * @see [类、类#方法、类#成员]
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
     * byte转成String
     * 
     * @param arr
     *            src
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String bytesToSting(byte[] arr)
    {
        try
        {
            return new String(arr, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // 该异常不会发生
            
            return "";
        }
    }
    
    /**
     * 讲字符串转换成byte数组
     * 
     * @param str
     *            字符串
     * @return 转换后的byte数组
     * @see [类、类#方法、类#成员]
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
