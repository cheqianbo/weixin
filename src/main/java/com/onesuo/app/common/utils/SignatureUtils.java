package com.onesuo.app.common.utils;


import java.security.*;

public final class SignatureUtils

{
    private SignatureUtils()
    {
        
    }
    
    public static byte[] encodeHash(byte[] data, String algorithm,String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm,provider);
        messageDigest.update(data);
        
        return messageDigest.digest();
    }
    
    public static byte[] encodeHashByMD5(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "MD5", provider);
    }
    
    public static byte[] encodeHashBySHA1(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "SHA-1", provider);
    }
    
    public static byte[] encodeHashBySHA256(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "SHA-256", provider);
    }
    
    public static byte[] encodeHashBySHA384(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "SHA-384", provider);
    }
    
    public static byte[] encodeHashBySHA512(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "SHA-512", provider);
    }
    
    public static byte[] encodeHashBySM3(byte[] data, String provider)throws NoSuchAlgorithmException, NoSuchProviderException
    {
        return encodeHash(data, "SM3", provider);
    }
    
    public static byte[] signatureByHash(byte[] hash, String algorithm, String provider, PrivateKey privateKey)throws NoSuchAlgorithmException, NoSuchProviderException,
                                                                                                                      InvalidKeyException, SignatureException
    {
        Signature signature = Signature.getInstance(algorithm, provider);
        signature.initSign(privateKey);
        signature.update(hash);
        
        return signature.sign();
    }
    
    public static byte[] signatureByMD5withRSA(byte[] hash, String provider,PrivateKey privateKey)throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                  NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "MD5withRSA", provider, privateKey);
    }
    
    public static byte[] signatureBySHA1withRSA(byte[] hash, String provider,PrivateKey privateKey)throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                   NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "SHA1withRSA", provider, privateKey);
    }
    
    public static byte[] signatureBySHA256withRSA(byte[] hash, String provider,PrivateKey privateKey) throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                      NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "SHA256withRSA", provider, privateKey);
    }
    
    public static byte[] signatureBySHA384withRSA(byte[] hash, String provider,PrivateKey privateKey)throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                     NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "SHA384withRSA", provider, privateKey);
    }
    
    public static byte[] signatureBySHA512withRSA(byte[] hash, String provider,PrivateKey privateKey)throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                     NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "SHA512withRSA", provider, privateKey);
    }
    
    public static byte[] signatureBySM3withSM2(byte[] hash, String provider,PrivateKey privateKey)throws InvalidKeyException, NoSuchAlgorithmException,
                                                                                                  NoSuchProviderException, SignatureException
    {
        return signatureByHash(hash, "SM3withSM2", provider, privateKey);
    }
    
    public static boolean verifySignature(String algorithm, String provider,byte[] originalValue,
                                          byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                       NoSuchProviderException, 
                                                                                       InvalidKeyException, 
                                                                                       SignatureException
    {
        Signature verify = Signature.getInstance(algorithm,provider);
        verify.initVerify(publicKey);
        verify.update(originalValue);
        
        return verify.verify(signValue); 
    }
    
    public static boolean verifySignatureByMD5withRSA(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException,
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("MD5withRSA", provider, originalValue, signValue, publicKey);
    }
    
    public static boolean verifySignatureBySHA1withRSA(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException,
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("SHA1withRSA", provider, originalValue, signValue, publicKey);
    }
    
    public static boolean verifySignatureBySHA256withRSA(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException,
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("SHA256withRSA", provider, originalValue, signValue, publicKey);
    }
    
    public static boolean verifySignatureBySHA384withRSA(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException,
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("SHA384withRSA", provider, originalValue, signValue, publicKey);
    }
    
    public static boolean verifySignatureBySHA512withRSA(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException,
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("SHA512withRSA", provider, originalValue, signValue, publicKey);
    }
    
    public static boolean verifySignatureBySM3withSM2(String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws NoSuchAlgorithmException, 
                                                                                                   NoSuchProviderException, 
                                                                                                   InvalidKeyException, 
                                                                                                   SignatureException
    {
        return verifySignature("SM3withSM2", provider, originalValue, signValue, publicKey); 
    }
    
    public static boolean verifySignatureBySigAlgOID(String sigAlgOID,String provider,byte[] originalValue,
                                                      byte[] signValue,PublicKey publicKey) throws Exception {
        //md5RSA
        if ("1.2.840.113549.1.1.4".equals(sigAlgOID))
        {
            return verifySignatureByMD5withRSA(provider, originalValue, signValue, publicKey);
        }
        //sha1RSA
        else if ("1.2.840.113549.1.1.5".equals(sigAlgOID))
        {
            return verifySignatureBySHA1withRSA(provider, originalValue, signValue, publicKey);
        }
        //sha256RSA
        else if ("1.2.840.113549.1.1.11".equals(sigAlgOID))
        {
            return verifySignatureBySHA256withRSA(provider, originalValue, signValue, publicKey);
        }
        //sha384RSA
        else if ("1.2.840.113549.1.1.12".equals(sigAlgOID))
        {
            return verifySignatureBySHA384withRSA(provider, originalValue, signValue, publicKey);
        }
        //sha512RSA
        else if ("1.2.840.113549.1.1.13".equals(sigAlgOID))
        {
            return verifySignatureBySHA512withRSA(provider, originalValue, signValue, publicKey);
        }
        //SM3SM2
        else if ("1.2.156.10197.1.501".equals(sigAlgOID))
        {
            return verifySignatureBySM3withSM2(provider, originalValue, signValue, publicKey);
        }
        else
        {
            throw new Exception(sigAlgOID + " is not supported by the OID");
        }
    }
    
    
    public static String getHashAlgorithmOID(String sigAlgOID)
    {
        //md5RSA
        if("1.2.840.113549.1.1.4".equals(sigAlgOID))
        {
            return "1.2.840.113549.2.5";
        }
        //sha1RSA
        else if("1.2.840.113549.1.1.5".equals(sigAlgOID))
        {
            return "1.3.14.3.2.26";
        }
        //sha256RSA
        else if("1.2.840.113549.1.1.11".equals(sigAlgOID))
        {
            return "2.16.840.1.101.3.4.2.1";
        }
        //sha384RSA
        else if("1.2.840.113549.1.1.12".equals(sigAlgOID))
        {
            return "2.16.840.1.101.3.4.2.2";
        }
        //sha512RSA
        else if("1.2.840.113549.1.1.13".equals(sigAlgOID))
        {
            return "2.16.840.1.101.3.4.2.3";
        }
        //SM3SM2
        else if("1.2.156.10197.1.501".equals(sigAlgOID))
        {
            return "1.2.156.10197.1.401";
        }
        
        return sigAlgOID;
    }
}
