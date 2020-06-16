/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa DES
 * Folosită pentru criptarea și decriptarea datelor folosind
 * algoritmul DES.
 */

package com.licenta.android.transfile_ii.backend.cryption;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES
{
    /**
     * Cheia criptarii de lungime 48 bit.
     */
    private static byte[] KEY= {14,101,-56,92,-101,-66,-23,-90}; // 48bit key

    public static void setKey(String key)
    {
        DES.KEY=key.getBytes();
    }
    /**
     * Cripteaza sau decriptaeaza pe baza {@code cipherMode} fisierul de intrare {@code IN}, rezultatul salvandu-l in fisierul {@code OUT}.
     * @param cipherMode cipherMode functia cripteaza pentru {@code Cipher.ENCRYPT_MODE} si decripteaza pentru {@code Cipher.DECRYPT_MODE}
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

        DESKeySpec DespKS = new DESKeySpec(KEY);

        SecretKeyFactory SKFf = SecretKeyFactory.getInstance("DES");
        SecretKey SKf = SKFf.generateSecret(DespKS);

        Cipher Ci = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // AES/CBC/PKC5Padding

        if (Cipher.ENCRYPT_MODE == cipherMode)
        {
            Ci.init(Cipher.ENCRYPT_MODE, SKf, SecureRandom.getInstance("SHA1PRNG"));
            CipherInputStream CIS = new CipherInputStream(FIS,Ci);
            write(CIS,FOS);
        }
        else
        if (Cipher.DECRYPT_MODE == cipherMode)
        {
            Ci.init(Cipher.DECRYPT_MODE, SKf, SecureRandom.getInstance("SHA1PRNG"));
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



