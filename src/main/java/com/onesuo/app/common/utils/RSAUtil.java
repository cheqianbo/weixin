package com.onesuo.app.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

/**
 * RSA算法加密/解密工具类V2
 */
@Slf4j
public class RSAUtil {
    /** 算法名称 */
    private static final String ALGORITHM =  "RSA";
    /** 默认密钥大小 */
    private static final int KEY_SIZE = 1024;
    /** 用来指定保存密钥对的文件名和存储的名称 */
    private static final String PUBLIC_KEY_NAME = "publicKey";
    private static final String PRIVATE_KEY_NAME = "privateKey";
    private static final String PUBLIC_FILENAME = "publicKey.properties";
    private static final String PRIVATE_FILENAME = "privateKey.properties";
    /** 密钥对生成器 */
    private static KeyPairGenerator keyPairGenerator = null;

    private static KeyFactory keyFactory = null;
    /** 缓存的密钥对 */
    private static KeyPair keyPair = null;
    /** Base64 编码/解码器 JDK1.8 */
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();
    /** 初始化密钥工厂 */
    static{
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException",e);
        }
    }
    /** 私有构造器 */
    private RSAUtil(){

    }

    /**
     * 获取密钥字符串
     * @param keyName 需要获取的密钥名
     * @param fileName 密钥所在文件
     * @return Base64编码的密钥字符串
     */
    private static String getKeyString(String keyName,String fileName){
        try(InputStream in =RSAUtil.class.getClassLoader().getResourceAsStream(fileName)){
            Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty(keyName);
        } catch (IOException e) {
            log.error("IOException",e);
        }
        return  null;
    }

    /**
     * 从文件获取RSA公钥
     *
     * @return RSA公钥
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        try {
            byte[] keyBytes = decoder.decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            log.error("InvalidKeySpecException", e);
        }
        return null;
    }

    /**
     * 从文件获取RSA私钥
     * @return RSA私钥
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey getPrivateKey(){
        try {
            byte[] keyBytes = decoder.decode(getKeyString(PRIVATE_KEY_NAME,PRIVATE_FILENAME));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            return (RSAPrivateKey)keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            log.error("InvalidKeySpecException",e);
        }
        return null;
    }


    /**
     * 根据限定的每组字节长度，将字节数组分组
     * @param bytes 等待分组的字节组
     * @param splitLength 每组长度
     * @return 分组后的字节组
     */
    public static byte[][] splitBytes(byte[] bytes,int splitLength){
        //bytes与splitLength的余数
        int remainder = bytes.length % splitLength;
        //数据拆分后的组数，余数不为0时加1
        int quotient = remainder != 0 ? bytes.length / splitLength + 1:bytes.length / splitLength;
        byte[][] arrays = new byte[quotient][];
        byte[] array = null;
        for (int i =0;i<quotient;i++){
            //如果是最后一组（quotient-1）,同时余数不等于0，就将最后一组设置为remainder的长度
            if (i == quotient -1 && remainder != 0){
                array = new byte[remainder];
                System.arraycopy(bytes,i * splitLength,array,0,remainder);
            } else {
                array = new byte[splitLength];
                System.arraycopy(bytes,i*splitLength,array,0,splitLength);
            }
            arrays[i] = array;
        }
        return arrays;
    }

    /**
     * 将字节数组转换成16进制字符串
     * @param bytes 即将转换的数据
     * @return 16进制字符串
     */
    public static String bytesToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer(bytes.length);
        String temp = null;
        for (int i = 0;i< bytes.length;i++){
            temp = Integer.toHexString(0xFF & bytes[i]);
            if(temp.length() <2){
                sb.append(0);
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     * @param hex 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hex){
        int len = (hex.length() / 2);
        hex = hex.toUpperCase();
        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i= 0;i<len;i++){
            int pos = i * 2;
            result[i] = (byte)(toByte(chars[pos]) << 4 | toByte(chars[pos + 1]));
        }
        return result;
    }


    /**
     * 将char转换为byte
     * @param c char
     * @return byte
     */
    private static byte toByte(char c){
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    /**
     * RSA公钥加密
     * @param content 等待加密的数据
     * @param publicKey RSA 公钥 if null then getPublicKey()
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPublic(byte[] content,PublicKey publicKey){
        if (publicKey == null){
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPublicKey)publicKey).getModulus().bitLength() / 8 -11;
            byte[][] arrays = splitBytes(content,splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte[] array : arrays){
                stringBuffer.append(bytesToHexString(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException",e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException",e);
        }
        return null;
    }


    /**
     * RSA公钥解密
     * @param content 等待解密的数据
     * @param publicKey RSA 公钥 if null then getPublicKey()
     * @return 解密后的明文
     */
    public static String decryptByPublic(String content,PublicKey publicKey){
        if (publicKey == null){
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,publicKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPublicKey)publicKey).getModulus().bitLength() / 8;
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes,splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            String sTemp = null;
            for (byte[] array : arrays){
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException",e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA私钥加密
     * @param content 等待加密的数据
     * @param privateKey RSA 私钥 if null then getPrivateKey()
     * @return 加密后的密文(16进制的字符串)
     */
    public static String encryptByPrivate(byte[] content,PrivateKey privateKey){
        if (privateKey == null){
            privateKey = getPrivateKey();
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,privateKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPrivateKey)privateKey).getModulus().bitLength() / 8 -11;
            byte[][] arrays = splitBytes(content,splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            for(byte[] array : arrays){
                stringBuffer.append(bytesToHexString(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            log.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            log.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            log.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            log.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }

    /**
     * RSA私钥解密
     * @param content 等待解密的数据
     * @param privateKey RSA 私钥 if null then getPrivateKey()
     * @return 解密后的明文
     */
    public static String decryptByPrivate(String content,PrivateKey privateKey){
        if (privateKey == null){
            privateKey = getPrivateKey();
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            //该密钥能够加密的最大字节长度
            int splitLength = ((RSAPrivateKey)privateKey).getModulus().bitLength() / 8;
            byte[] contentBytes = hexStringToBytes(content);
            byte[][] arrays = splitBytes(contentBytes,splitLength);
            StringBuffer stringBuffer = new StringBuffer();
            String sTemp = null;
            for (byte[] array : arrays){
                stringBuffer.append(new String(cipher.doFinal(array)));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt()#NoSuchAlgorithmException",e);
        } catch (NoSuchPaddingException e) {
            log.error("encrypt()#NoSuchPaddingException",e);
        } catch (InvalidKeyException e) {
            log.error("encrypt()#InvalidKeyException",e);
        } catch (BadPaddingException e) {
            log.error("encrypt()#BadPaddingException",e);
        } catch (IllegalBlockSizeException e) {
            log.error("encrypt()#IllegalBlockSizeException",e);
        }
        return null;
    }


//    /**
//     * demo
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//
//        String  mingwen="test";
//
//        String miwen=encryptByPublic(mingwen.getBytes(),getPublicKey());
//
//        System.out.println(miwen);
//
//        System.out.println(decryptByPrivate(miwen,getPrivateKey()));
//
//        miwen=encryptByPrivate(mingwen.getBytes(),getPrivateKey());
//
//        System.out.println(miwen);
//
//        System.out.println(decryptByPublic(miwen,getPublicKey()));
//
//    }

}