/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicatia Android
 *
 * Clasa Link
 * Folosită ca o legătură între backend și frontend.
 */
package com.licenta.android.transfile_ii.middleend;

import android.os.Environment;

import com.licenta.android.transfile_ii.backend.cryption.KeyCryption;
import com.licenta.android.transfile_ii.backend.cryption.Selector;
import com.licenta.android.transfile_ii.backend.memory.Values;
import com.licenta.android.transfile_ii.backend.transfer.Client;
import com.licenta.android.transfile_ii.backend.transfer.Server;

import java.io.File;
import java.io.IOException;

import javax.crypto.Cipher;

public class Link
{
    public static void serverCall()
    {
        //start server
       // Values.setAlgorithm("NOTHING ");

        Values.setKey(KeyCryption.cryptKey(Values.getAlgorithm()));

        String j; //= Environment.getExternalStorageDirectory().getAbsolutePath() + p;
        j=Values.getSPath(); // se preia path de la alegeea fisierului
        File F = new File(j);

        //String alg = "NOTHING ";//"DES     ";  "AES     ";  "BLOWFISH";
        //Values.setAlgorithm(alg);


        String s=F.getName();
        String path = F.getParent();
        F.renameTo(new File(path + "/a"+s));
        File M = new File(path + "/a"+s);

        File OUT =new File(path+"/"+s);

        try
        {
            OUT.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Selector.algorithmSelector(M, OUT, Cipher.ENCRYPT_MODE);

        Server TS = new Server(Values.getPortServer(), OUT);
        TS.start();
        try
        {
            TS.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        OUT.delete();
        M.renameTo(new File(path + "/"+s));
    }

    public static boolean clientCall()
    {
        boolean state=false;
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TransFileDownload");

        Client TC = new Client(Values.getPortClient(), Values.getIpv4(), path);
        TC.start();
        try
        {
            TC.join();
            state=true;
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        return state;
    }

    /***/

    public static String getClientFilePath()
    {
        return Values.getCPath();
    }

    public static boolean clientSettings(String ipv4, String port)
    {
        // variabilele err sunt folosite pentru semnaliarea eventualelor erori aparute
        // pt err=true o eroare s-a intamplat
        boolean err=false,err1;
        int err2;

        err2=Values.setIpv4(ipv4);
        err1=Values.setPortClient(port);
        if (err1||(err2==1))
            err=true;
        return err;
    }

    public static String getServerPath()
    {
        return Values.getSPath();
    }

    public static boolean serverSettings(String prot, String alg,String port)
    {

       //***//
        prot="FTP";
        if (prot.equals("FTP"))
        {
            Values.setAlgorithm("NOTHING ");
        }
        else
            if (prot.equals("FTPS"))
            {
                Values.setAlgorithm(alg);
            }

         boolean err = Values.setPortServer(port);
            return err;
    }

    public static void setServerPath(String path)
    {
        Values.setSPath(path);
    }



    public static String adjustFilePath(String path)
    {
        String cale;
        cale = Values.filePathAdjusted(path);
        return cale;
    }
}
