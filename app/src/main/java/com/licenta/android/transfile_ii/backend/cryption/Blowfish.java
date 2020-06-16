/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa Blowfish
 * Folosită pentru criptarea și decriptarea datelor folosind
 * algoritmul Blowfish.
 */

package com.licenta.android.transfile_ii.backend.cryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Blowfish
{

    private static final String ALGORITHM = "Blowfish";
    private static String keyString = "M_A_M_185836";

    public static void setKey(String key)
    {
        Blowfish.keyString=key;
    }

    public static void criptare(File IN, File OUT) throws Exception
    {
        executa(Cipher.ENCRYPT_MODE, IN, OUT);
        System.out.println("Fisier criptat!");
    }

    public static void decriptare(File IN,File OUT) throws Exception
    {
        executa(Cipher.DECRYPT_MODE, IN, OUT);
        System.out.println("Fisier decriptat!");
    }

    private static void executa(int cipherMode, File InFile,File OutFile) throws Exception
    {
        Key secretKey = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(cipherMode, secretKey);

        FileInputStream InStream = new FileInputStream(InFile);
        FileOutputStream OutStream = new FileOutputStream(OutFile);


        byte[] inBytes = new byte[(int) InFile.length()];
        InStream.read(inBytes);

        byte[] outBytes = cipher.doFinal(inBytes); // aici se intampla magia
        OutStream.write(outBytes);

        InStream.close();
        OutStream.close();
    }
}


