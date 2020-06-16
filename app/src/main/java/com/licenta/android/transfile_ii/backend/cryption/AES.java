/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa AES
 * Folosită pentru criptarea și decriptarea datelor folosind
 * algoritmul AES.
 */

package com.licenta.android.transfile_ii.backend.cryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES
{
    /**
     * Constanta care desemneaza cheia criptarii.
     */
    private static String KEY = "16EC96BF44FE4C64";

    public static void setKey(String key)
    {
        AES.KEY=key;
    }

    /**
     * Cripteaza sau decriptaeaza pe baza {@code cipherMode} fisierul de intrare {@code IN}, rezultatul salvandu-l in fisierul {@code OUT}.
     * @param cipherMode functia cripteaza pentru {@code Cipher.ENCRYPT_MODE} si decripteaza pentru {@code Cipher.DECRYPT_MODE}
     * @param IN fisierul de intrare
     * @param OUT fisierul rezultat
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IOException
     */
    public static void encryptDecrypt(int cipherMode, File IN, File OUT)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException
    {
        FileOutputStream FOS= new FileOutputStream(OUT);
        FileInputStream FIS= new FileInputStream(IN);

        byte[] iv = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        IvParameterSpec ivspec = new IvParameterSpec(iv);
        //.toCharArray()
        KeySpec KS = new PBEKeySpec(KEY.toCharArray(), KEY.getBytes(), 65536, 256);

        SecretKeyFactory SKF = SecretKeyFactory.getInstance("PBEwithSHAAND256BITAES-CBC-BC");

        SecretKey SK = SKF.generateSecret(KS);
        SecretKeySpec SKS = new SecretKeySpec(SK.getEncoded(),"AES");

        Cipher Ci = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        // AES/CBC/PKC5Padding

        if (Cipher.ENCRYPT_MODE == cipherMode)
        {
            //Ci.init(Cipher.ENCRYPT_MODE, SKf, SecureRandom.getInstance("SHA1PRNG"));

            try
            {
                Ci.init(Cipher.ENCRYPT_MODE, SKS,ivspec);
            }
            catch (InvalidAlgorithmParameterException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            CipherInputStream CIS = new CipherInputStream(FIS,Ci);
            write(CIS,FOS);
        }
        else
        if (Cipher.DECRYPT_MODE == cipherMode)
        {
            //Ci.init(Cipher.DECRYPT_MODE, SKf, SecureRandom.getInstance("SHA1PRNG"));

            try
            {
                Ci.init(Cipher.DECRYPT_MODE, SKS,ivspec);
            }
            catch (InvalidAlgorithmParameterException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            CipherOutputStream COS = new CipherOutputStream(FOS,Ci);
            write(FIS,COS);
        }
    }

    /**
     * Citeste un byte[2048] din {@code IS} si il scrie in {@code OS}.
     * @param IS Stream de Intrare
     * @param OS Stream de Iesire
     * @throws IOException
     */
    private static void write(InputStream IS, OutputStream OS)
            throws IOException
    {
        byte[] buffer = new byte[2048];
        int num;
        while ((num=IS.read(buffer)) != -1)
        {
            OS.write(buffer, 0,num);
        }
        OS.close();
        IS.close();
    }

}

