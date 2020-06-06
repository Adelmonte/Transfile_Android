package com.licenta.android.transfile_ii.backend.cryption;


import com.licenta.android.transfile_ii.backend.memory.Values;
import java.util.Random;

public class KeyCryption
{

    private static String KEYAES;

    public static String getKeyAes()
    {
        return KeyCryption.
                KEYAES;
    }

    private static String KEYDES;

    public static String getKeyDes()
    {
        return KeyCryption.KEYDES;
    }

    private static String KEYBLOWFISH;

    public static String getKeyBlowfish()
    {
        return KeyCryption.KEYBLOWFISH;
    }

    public static String cryptKey(String selector)
    {
        String key="";
        if (selector.equals("NOTHING "))
        {
            key="0000000000000000";
        }
        else
        if (selector.equals("AES     "))
        {
            //16
            byte[] AES = KeyCryption.genKey(16);
            KeyCryption.KEYAES= AES.toString();
            AES = KeyCryption.mixture16(AES);
            key=AES.toString();
        }
        else
        if (selector.equals("DES     "))
        {
            //8
            byte [] DES =KeyCryption.genKey(8);
            KeyCryption.KEYDES= DES.toString();
            DES = KeyCryption.mixture8(DES);
            key=DES.toString();
        }
        else
        if (selector.equals("BLOWFISH"))
        {
            //12
            byte BF[] = KeyCryption.genKey(12);
            KeyCryption.KEYAES= BF.toString();
            BF = KeyCryption.mixture12(BF);
            key=BF.toString();
        }
        return key;

    }

    public static void decryptKey(String selector,String key)
    {
        if (selector.equals("NOTHING "))
        {
            ;
        }
        else
        if (selector.equals("AES     "))
        {
            // 16
            byte[] aAES = key.getBytes();
            aAES=mixture16(aAES);
            AES.setKey(aAES.toString());
        }
        else
        if (selector.equals("DES     "))
        {
            //8
            key= Values.unpadding(key, 8);
            byte[] dDES = key.getBytes();
            dDES=mixture8(dDES);
            DES.setKey(dDES.toString());
        }
        else
        if (selector.equals("BLOWFISH"))
        {
            //12
            key=Values.unpadding(key, 12);
            byte[] BF = key.getBytes();
            BF=mixture12(BF);
            Blowfish.setKey(BF.toString());
        }
    }


    private static byte[] genKey(int nr)
    {
        byte[] p= new byte[nr];
        Random r = new Random();
        r.nextBytes(p);
        return p;
    }

    private static byte[] mixture16(byte[] seq)
    {
        byte[] front = {seq[0],seq[1],seq[2],seq[3],seq[4],seq[5],seq[6],seq[7]};
        front = KeyCryption.mixture8(front);
        byte[] back = {seq[8],seq[9],seq[10],seq[11],seq[12],seq[13],seq[14],seq[15]};
        back = KeyCryption.mixture8(back);
        for (int i=0;i<seq.length;i++)
        {
            if (i<8)
            {
                seq[i]=back[i];
            }
            else
            {
                seq[i]=front[i-8];
            }
        }
        return seq;
    }

    private static byte[] mixture12(byte[] seq)
    {
        byte[] front = {seq[0],seq[1],seq[2],seq[3]};
        byte[] middle = {seq[4],seq[5],seq[6],seq[7]};
        byte[] back = {seq[8],seq[9],seq[10],seq[11]};
        for (int i=0;i<seq.length;i++)
        {
            if (i<4)
            {
                seq[i]=back[i];
            }
            else
            if (i<8)
            {
                seq[i]=middle[i-4];
            }
            else
            {
                seq[i]=front[i-8];
            }
        }
        return seq;
    }

    private static byte[] mixture8(byte[] seq)
    {
        byte[] front = {seq[0],seq[1],seq[2],seq[3]};

        byte[] back = {seq[4],seq[5],seq[6],seq[7]};

        for (int i=0;i<seq.length;i++)
        {
            if (i<4)
            {
                seq[i]=back[i];
            }
            else
            {
                seq[i]=front[i-4];
            }
        }
        return seq;
    }
}



