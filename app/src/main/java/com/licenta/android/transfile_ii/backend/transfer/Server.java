/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa Server
 * Parte centrală a transferului de fișiere.
 */

package com.licenta.android.transfile_ii.backend.transfer;

import com.licenta.android.transfile_ii.backend.memory.Values;
import android.os.Environment;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    private ServerSocket server;
    private Socket clientSocket;
    private DataOutputStream DOS;
    private FileInputStream FIS;
    private int port;
    private File F;

    /**
     * Constructor.
     */
    public Server(int p, File f)
    {
        this.server=null;
        this.clientSocket=null;
        this.DOS=null;
        this.FIS=null;
        this.port=p;
        this.F=f;
    }

    /**
     * Functia centrala a clasei.
     *
     * @return un string
     */
    public void run()
    {
        String result="";
        System.out.println("Incepe!");

        this.DOS=this.startServer(this.port);

        this.sendCryptingAlgorihtm(Values.getAlgorithm(),this.DOS);
        System.out.println("Se trimis numele algoritmului de criptare. " +Values.getAlgorithm());

        this.sendCryptingKey(Values.getKey(),this.DOS);
        System.out.println("S-a trimis cheia de criptare" + Values.getKey());

        this.sendFileName(this.F,this.DOS);
        System.out.println("Se trimite numele fisierului.");
        this.FIS=this.connectFileStream(this.F,this.FIS);
        System.out.println("S-a conectat fisierul la Stream-ul de citire");
        this.doProblem(this.DOS, this.FIS); // citeste din fis un array si il scrie in dos
        System.out.println("S-au trimis payload-ul.");

        // result="Merge!";
        //byte[] array=result.getBytes();
        //System.out.println("Se scrie pe Stream ...");
        //try
        {
            //    this.DOS.write(array);
        }
        //catch (IOException e)
        {
            //  e.printStackTrace();
        }

        System.out.println("Se incepe inchiderea conexiunii");
        this.closeStream(DOS);
        System.out.println("S-a inchis stream-ul");
        this.closeServerSocket(server);
        System.out.println("S-a inchis server socketul");
        this.closeSocket(clientSocket);
        System.out.println("S-a inchis socketul clientului");

        System.gc();
    }

    /**
     * Scrie pe {@code dos} cheia de criptare primita prin {@code key}
     * @param key string: cheia de criptare
     * @param dos DataOutputStream: stream conectat la soket
     */
    private void sendCryptingKey(String key,DataOutputStream dos)
    {
        Values.padding(key, " ", 16);
        byte[] seq= key.getBytes();
        try
        {
            dos.write(seq);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Trimite prin {@code dos} algoritmul folosit pentru criptare.
     * @param alg
     * @param dos
     */
    private void sendCryptingAlgorihtm(String alg, DataOutputStream dos)
    {
        byte[] seq= alg.getBytes();
        try
        {
            dos.write(seq);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
    Extrage numele fisierului din path si il trimite pe DOS
     */
    private void sendFileName(File path,DataOutputStream dos)
    {
        String s = this.extractFileName(path);
        byte[] array =s.getBytes();
        try
        {
            dos.write(array.length);
            dos.write(array,0,array.length);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    /*
    Extrage numele fisierului din path si il returneaza ca Sting.
     */
    private String extractFileName(File path)
    {
        String s=null;
        s=path.getName();
        return s;
    }

    /*
    Citeste din fis un array de 1024 pozitii si il scrie in dos
     */
    private void doProblem(DataOutputStream dos, FileInputStream fis)
    {
        //byte buffer=0;
        byte[] buffer = new byte[1024];
        try
        {
            // buffer= (byte) fis.read();
            int ava = fis.available();
            while (ava!=0)
            {
                if (ava<1024)
                {
                    fis.read(buffer, 0, ava);
                    dos.write(buffer, 0, ava);
                    dos.flush();
                }
                //buffer =(byte) fis.read();
                else { // in cazul in care fisierul nu are un numar integ de 1024 de bucati
                    fis.read(buffer, 0, buffer.length);
                    dos.write(buffer, 0, buffer.length);
                    dos.flush();
                }
                ava = fis.available();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    Conecteaza fisierul f la Stram-ul fis.
     */
    private FileInputStream connectFileStream(File f,FileInputStream fis)
    {
        try
        {
            fis=new FileInputStream(f);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return fis;
    }

    /*
    Inchide stream-ul de iesire a Socket-ului.
     */
    private void closeStream(DataOutputStream dos)
    {
        try
        {
            dos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Porneste Serverul
     * @param port number of the port on which the connection is made
     * @return Stream-ul de date folosit pentru comunicarea prin portul dat ca parametru
     */
    private DataOutputStream startServer(int port)
    {

        try
        {

            //initializeaza serverul
            System.out.println("Initializez server socket ...");
            server = new ServerSocket(port);

            System.out.println("Ascult pentru conexiunea clientului ...");

            //asculta pentru conexiunile clientilor
            clientSocket = server.accept();

            System.out.println("Clientul " + clientSocket.getInetAddress().getHostName() + " conectat !");

            //creez stream-urile pentru scriere/citire mesaje
            // creare stream-uri pentru trimitere/receptionare Fisiere
            System.out.println("Se conecteaza Stream-ul de clientsocket");
            DOS =  new DataOutputStream(clientSocket.getOutputStream());


        }
        catch (IOException e)
        {
            System.out.println("Serverul nu a putut fi creat !");
            e.printStackTrace();
        }
        return DOS;
    }

    /**
     * Close the ServerSocketInstance.
     * @param ss ServerSocket to close
     */
    private void closeServerSocket(ServerSocket ss)
    {
        try
        {
            if (!ss.isClosed())
            {
                ss.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Close the Socket instance.
     * @param s Socket to close
     */
    private void closeSocket(Socket s)
    {
        try
        {
            if (!s.isClosed())
            {
                s.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    // verify if external storage is mounted and writeable and readable
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}





