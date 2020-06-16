package com.licenta.android.transfile_ii.backend.transfer;

import android.os.Environment;

import com.licenta.android.transfile_ii.backend.memory.Values;

import com.licenta.android.transfile_ii.backend.cryption.Selector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.IllegalBlockingModeException;

import javax.crypto.Cipher;

public class Client extends Thread
{
    Socket socket;
    InputStream s_in;
    String host ;
    Integer port;

    File path;

    File crypt;
    File decrypt;

    String nameFile;
    FileOutputStream FOS;

    /**
     * Constructor.
     * @param port
     * @param ip
     * @param path
     */
    public Client(int port, String ip,File path)
    {
        this.host = ip;
        this.port=port;
        this.socket=null;
        this.s_in=null;
        this.path=path;
        this.decrypt=null;
        this.crypt=null;
        this.nameFile=null;
        this.FOS=null;
    }

    @Override
    public void run()
    {

        socket = this.openSocket(socket,host,port);
        System.out.println("S-a deschis socket-ul");

        s_in=this.connectInputStream(socket,s_in);
        System.out.println("S-a conectat datastream-ul de socket.");

        this.recCryptingAlgorithm(s_in);
        System.out.println("S-a primit numele algoritmului de criptare. " + Values.getAlgorithm());

        this.recCryptingKey(this.s_in);
        System.out.println("S-a primit cheia de criptare.");

        this.nameFile=this.readFileName(this.s_in);
        System.out.println("S-a citit numele fisierului: "+this.nameFile);

        crypt=this.createFilePath(this.path,"OUT.bin");
        this.FOS=this.openFileStream(FOS, crypt); //this.nameFile);
        System.out.println("S-a conectat stream-ul de fisier la fisier");


        this.doProblem(this.s_in,this.FOS);


        decrypt= this.createFilePath(path,this.nameFile);
        Selector.algorithmSelector(crypt,decrypt, Cipher.DECRYPT_MODE);
        Values.setPath(decrypt.getAbsolutePath());
        System.out.println("Fisier decriptat");

        this.closeInputStream(this.s_in);
        System.out.println("S-a inchis datastream-ul");

        this.closeFileStream(this.FOS);
        System.out.println("S-a inchis filestream-ul");

        this.closeSocket(this.socket);
        System.out.println("S-a inchis socket-ul");

        System.gc();
    }

    /**
     * Citeste din {@code is} cheia de criptare si o salveaza in {@code Values.setKey()}.
     * @param is InputStream: stream conectat la socket
     */
    private void recCryptingKey(InputStream is)
    {
        String s;
        char seq []  = new char[16];
        try
        {
            for (int i=0;i<16;i++)
                seq[i] = (char)is.read();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        s= String.valueOf(seq);
        Values.setKey(s);
    }

    private void recCryptingAlgorithm(InputStream is)
    {

        String s;
        char seq []  = new char[8];
        try
        {
            seq[0] = (char)is.read();
            seq[1] = (char)is.read();
            seq[2] = (char)is.read();
            seq[3] = (char)is.read();
            seq[4] = (char)is.read();
            seq[5] = (char)is.read();
            seq[6] = (char)is.read();
            seq[7] = (char)is.read();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        s= String.valueOf(seq);
        Values.setAlgorithm(s);
    }

    private String readFileName(InputStream is)
    {

        int length;
        String s;
        char [] array=null;
        try
        {
            length = is.read();
            array = new char[length];
            for (int i=0;i<length;i++)
            {
                array[i]=(char)is.read();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        s=String.valueOf(array);
        return s;
    }

    private void closeInputStream(InputStream IS)
    {
        try
        {
            IS.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void closeFileStream(FileOutputStream FOS)
    {
        try
        {
            FOS.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void closeSocket(Socket s)
    {
        try
        {
            if (!(s.isClosed()))
                s.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private InputStream connectInputStream(Socket socket, InputStream is)
    {

        try
        {
            is = socket.getInputStream();
        }
        catch( IllegalBlockingModeException e)
        {
            e.printStackTrace();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return is;
    }

    private Socket openSocket(Socket s,String ip, int port)
    {
        try {
            s= new Socket(ip, port);
        }
        catch(AsynchronousCloseException ace)
        {
            ace.printStackTrace();

        }
        catch (UnknownHostException uhe)
        {
            uhe.printStackTrace();

        }
        catch (SecurityException se)
        {
            se.printStackTrace();

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return s;
    }

    // verify if external storage is mounted and writeable and readable
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param FOS
     * @return
     */
    public FileOutputStream openFileStream(FileOutputStream FOS, File file)
    {

        try
        {
            FOS = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) // de fiecare data intra pe aceasta ramura
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
        return FOS;
    }

    /**
     * tine de this.openFileStream()
     * @param f
     * @param filename
     * @return
     */
    private File createFilePath(File f,String filename)
    {
        try
        {
            if (!(f.exists()))
                f.mkdir();
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }

        File file = new File(f, filename);
        try
        {
            if (file.exists())
                file.delete();

            file.createNewFile();
            // se incearca creerea unui fisier cu calea definita mai sus
        }
        catch(java.io.IOException e)// de fiecare data intra pe aceasta ramura
        {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Citeste un int din {@code IS} si il scrie in {@code FOS}.
     * @param is {@code InputStream} :
     * @param fos {@code FileOutputStream} :
     */
    public void doProblem(InputStream is, FileOutputStream fos)
    {
        // citim un byte
        byte b[]= new byte[1024];
        //int b=0;
        try
        {
            //int available = is.available();
            ///while (available !=0)
            //{ available = is.available();
            //    try
            //    {
            //        //b = is.read();
            //        is.read(b);// citim din stream
            //        fos.write(b);// scriem in fisier
            //    }
            //    catch (IOException e)
            //    {
            //    e.printStackTrace();
            //    }
            //} // end while

            int ava = is.available();
            // is.read(b, 0,b.length);
            while(ava!=0)
            {
                if (ava<1024)
                {
                    byte p[] = new byte[ava];
                    is.read(p,0,ava);
                    this.intrerrupt(10);
                    fos.write(p,0,ava);
                    // chr nerecunoscute in fisier
                }
                else
                {
                    is.read(b, 0, b.length);
                    this.intrerrupt(10);
                    fos.write(b, 0, b.length);
                }
                ava = is.available();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void intrerrupt(int milis)
    {
        try
        {
            this.sleep(milis);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public void doProblem(byte[] b, FileOutputStream FOS)
    {
        // scriem in fisier
        try
        {
            FOS.write(b);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }



}

