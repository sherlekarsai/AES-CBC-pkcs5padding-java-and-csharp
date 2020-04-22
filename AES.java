package com.ags.crypto;

 

import java.io.PrintStream;

import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;

import javax.crypto.spec.SecretKeySpec;

 

public class Encryptor

{

 

    static String key = "mynameisnotasAES";

    static String initVector = "RandomDtaaValeue";

 

    public Encryptor()

    {

    }

 

    public static void setKeys(String cipherkey, String IV_Key)

        throws BadPaddingException

    {

        if(cipherkey.length() != 16 || IV_Key.length() != 16)

        {

            throw new BadPaddingException("Invalid Key Len ,  key len should be 16 digit.");

        } else

        {

            key = cipherkey;

            initVector = IV_Key;

            return;

        }

    }

 

    public static String encrypt(String clearValue)

    {

        byte encrypted[];

        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        cipher.init(1, skeySpec, iv);

        encrypted = cipher.doFinal(clearValue.getBytes());

        return byteToHex(encrypted);

        Exception ex;

        ex;

        ex.printStackTrace(System.out);

        return null;

    }

 

    public static String decrypt(String hexEncrypted)

    {

        byte original[];

        byte encrypted[] = hextToByte(hexEncrypted);

        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        cipher.init(2, skeySpec, iv);

        original = cipher.doFinal(encrypted);

        return new String(original);

        Exception ex;

        ex;

        ex.printStackTrace(System.out);

        return null;

    }

 

    public static String byteToHex(byte value[])

    {

        String hexStr = "";

        String tempHexStr = "";

        for(int i = 0; i < value.length; i++)

        {

            tempHexStr = Integer.toHexString(value[i]);

            if(tempHexStr.length() < 2)

            {

                tempHexStr = (new StringBuilder()).append("0").append(tempHexStr).toString();

            } else

            {

                tempHexStr = tempHexStr.substring(tempHexStr.length() - 2);

            }

            hexStr = (new StringBuilder()).append(hexStr).append(tempHexStr).toString();

        }

 

        return hexStr;

    }

 

    public static byte[] hextToByte(String hexString)

    {

        int len = hexString.length() / 2;

        byte b1[] = new byte[len];

        if(len % 2 != 0)

        {

            throw new NumberFormatException((new StringBuilder()).append("Invalid Hex Len Exception :").append(len).append(" should be even number").toString());

        }

        for(int i = 0; i < len; i++)

        {

            b1[i] = (byte)Integer.parseInt(hexString.substring(0, 2), 16);

            hexString = hexString.substring(2);

        }

 

        return b1;

    }

 

    public static void main(String args[])

    {

        String str = "Value";

        String hexVal = encrypt(str);

        System.out.println((new StringBuilder()).append("Encrypted value = [").append(hexVal).append("] = ").append(hexVal.length()).toString());

        System.out.println(decrypt(hexVal));

    }

 

}