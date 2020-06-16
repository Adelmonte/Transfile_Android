package com.licenta.android.transfile_ii.backend.memory;

import java.io.File;

public class Values
{
    // path of the file; used in FileBrowser and MainActivity
    private static String path;

    public static String getPath()
    {
        return Values.path;
    }

    public static void setPath(String cale)
    {
        Values.path=cale;
    }

    // used for enabling or not the choosing of a file or a folder
    private static boolean enable;

    // enable == true -> folder
    // enable == false -> file
    public static void setEnable(boolean enable)
    {
        Values.enable = enable;
    }

    public static boolean getEnable()
    {
        return Values.enable;
    }




    /***/
    /**
     * Adauga lui {@code s} caracterul {@code regex} pana cand are lungimea {@code nr}.
     * @param s string: string-ul de completat
     * @param regex string: caracterul de completare
     * @param nr int: lungimea stringului dorit
     * @return string-ul rezultat
     */
    public static String padding(String s,String regex, int nr)
    {
        while (s.length()!=nr)
        {
            s=s+regex;
        }
        return s;
    }

    /**
     * Selecteaza primele {@code nr} caractere din {@code s}
     * @param s String: string-ul de operat
     * @param nr int: numarul de caractere al string-ului returnat
     * @return
     */
    public static String unpadding(String s, int nr)
    {
        s=s.substring(0, nr);
        return s;
    }

    // Networking

    /***/
    private static int PORTClient;

    /**
     *
     * @param prt a number from 1 to 65536
     * @return 1 if port exists
     * 			0 if not
     */
    public static boolean setPortClient(String prt)
    {
        boolean err=false;
        int port=-1;
        try {
            port = Integer.parseInt(prt);
        }
        catch(NumberFormatException e)
        {
            err=true;
        }
        if ((port>0) && (port <65536))
        {
            Values.PORTClient = port;
        }
        else
            err=true;
            return err;
    }

    public static int getPortClient()
    {
        return Values.PORTClient;
    }


    /***/
    private static int PORTServer;

    /**
     *
     * @param prt a number from 1 to 65536
     * @return 1 if port exists
     * 			0 if not
     */
    public static boolean setPortServer(String prt)
    {
        boolean err=false;
        int port=-1;
        try {
             port = Integer.parseInt(prt);
        }
        catch(NumberFormatException e)
        {
            err=true;
        }
        if ((port>0) && (port <65536))
        {
            Values.PORTServer = port;

        }
        else
            err=true;
        return err;
    }

    public static int getPortServer()
    {
        return Values.PORTServer;
    }

    /***/

    private static String thisIP;

    public static String getThisIP()
    {
        return Values.thisIP;
    }

    public static void setThisIP(String ip)
    {
        Values.thisIP= ip;
    }
    /***/
    private static String IPv4;

    /**
     *
     * @param ip a string contains IPv4 adress
     * @return 1 if IPv4 adress is valid
     * 			0 if not
     */
    public static int setIpv4(String ip)
    {
        if (convergence4(ip))
        {
            Values.IPv4 = ip;
            return 0;
        }
        else
            return 1;
    }

    public static String getIpv4()
    {
        return Values.IPv4;
    }


    /**
     * Testeaza daca adresa Ipv4 e una valida.
     * @param ipv4
     * @return daca e sau nu valida adresa IPv4
     */
    public static boolean convergence4(String ipv4)
    {
        boolean state = true;
        System.out.print(ipv4);
        System.out.println();

        //String[] component  = ipv4.split(".");
        if (ipv4.length()<=15)
        {
            String[] component = new String[4];
            component = splitv(ipv4,'.',component);
            for (int i=0;i<4;i++)
            {
                int number = Integer.parseInt(component[i]);
                if (number<0)
                {
                    state = false;
                }
                else
                if (number>255)
                {
                    state=false;
                }
                else
                {
                    state=true;
                }
            }
        }
        else
            state=false;
        return state;
    }

    private static String[] splitv( String split, char regex,String[] array)
    {
        String loc="";
        int l=array.length;
        for (int i=0;i<=split.length();i++)
        {
            if (i==13)
            {
                array[l-1]=loc;
            }
            else
            {
                char c=split.charAt(i);
                if (c!=regex)
                {
                    loc=loc+c;
                }
                else
                {
                    array[l-1]=loc;
                    loc="";
                    l--;
                }
            }
        }

        return array;
    }

    /***/

    private static File FILEPATH;

    public static File getFilePath()
    {
        return FILEPATH;
    }

    public static void setFilePath(File s)
    {
        FILEPATH=s;
    }

    /***/

    /**
     * Stocheaza algoritmul de criptare folosit.
     */
    private static String Algorithm="";

    public static void setAlgorithm(String alg)
    {
        Values.Algorithm = alg;
    }

    public static String getAlgorithm()
    {
        return Values.Algorithm;
    }

    private static String KEY;

    public static void setKey(String key)
    {
        Values.KEY = key;
    }

    public static String getKey()
    {
        return Values.KEY;
    }

    /***/

    /**
     * Extrage si returneaza extensia cu punct a fisierului.
     * @param file fisierul in cauza
     * @return extensia fisierului {@code file}
     */
    public static String extension(File file)
    {
        String extension = ".";

        int i = file.getName().lastIndexOf('.');
        if (i > 0)
        {
            extension = extension + file.getName().substring(i+1);
        }
        return extension;
    }

    public static String filePathAdjusted(String path)
    {
        String is ="/document/primary:";
        String sdCardS = "/document/62A2-4A17:";
        String manevra="";
        int infBound = path.indexOf(':');
        if (path.contains(is))
        {
            manevra="/storage/emulated/0/";
            int supBound = path.length();
            for (int i=infBound+1;i<supBound;i++)
            {
                manevra = manevra + path.charAt(i);
            }
        }
        else
            if (path.contains(sdCardS))
            {
                manevra="/storage/62A2-4A17/";
                int supBound = path.length();
                for (int i=infBound;i<supBound;i++)
                {
                    manevra = manevra + path.charAt(i);
                }
            }
        return manevra;
    }
}

