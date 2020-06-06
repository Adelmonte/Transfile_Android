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
        Values.setEnable(false); // for files
        // String p = "/TransFileUpload/SEC.pdf";
        //
        //openActivity();
        Values.setAlgorithm("NOTHING ");

        Values.setKey(KeyCryption.cryptKey(Values.getAlgorithm()));




        String j; //= Environment.getExternalStorageDirectory().getAbsolutePath() + p;
        j=Values.getPath(); // se preia path de la alegeea fisierului
        File F = new File(j);
        // /*
        //String alg = "NOTHING ";//"DES     ";  "AES     ";  "BLOWFISH";
        //Values.setAlgorithm(alg);

        String extension = Values.extension(F);
        String s=F.getName();
        String path = F.getParent();
        F.renameTo(new File(path + "/a"+s));
        File M = new File(path + "/a"+s);

        File OUT =new File(path+"/"+s);

        // File OUT = new File(F.getParent()+"/OUT"+extension);
        try
        {
            OUT.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Selector.algorithmSelector(M, OUT, Cipher.ENCRYPT_MODE);
//  */
        int port = 20000;
        //TransferServer TS = new TransferServer(4000,F);
        //TS.execute("");
        Server TS = new Server(port, OUT);
        TS.start();
        try
        {
            TS.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //TS.run();
        OUT.delete();
        M.renameTo(new File(path + "/"+s));
    }

    public static void clientCall()
    {
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TransFileDownload");
        //TransferFile TF = new TransferFile(5001,"192.168.0.104", path);
        //TF.execute("");

        Client TC = new Client(5001, "192.168.0.108", path);
        TC.start();
    }

    public static int clientSettings(String ipv4, String port)
    {
        int err = 0;
        err=Values.setIpv4(ipv4);
        err=Values.setPort(Integer.parseInt(port));
        return err;
    }

    public static void serverSettings(String prot, String alg)
    {
        prot="FTP";
        if (prot.equals("FTP"))
        {
            Values.setAlgorithm("NOTHING");
        }
        else
            if (prot.equals("FTPS"))
            {
                Values.setAlgorithm(alg);
            }
    }

    public static void setServerPath(String path)
    {
        Values.setPath(path);
    }

    public static String adjustFilePath(String path)
    {
        String cale;
        cale = Values.filePathAdjusted(path);
        return cale;
    }
}
