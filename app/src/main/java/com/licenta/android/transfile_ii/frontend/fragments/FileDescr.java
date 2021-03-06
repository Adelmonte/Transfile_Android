/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa FileDescr
 * Folosită ca parte automatizată a resursei filedescr_fragment.xml.
 */

package com.licenta.android.transfile_ii.frontend.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

import java.io.File;


public class FileDescr extends Fragment {
    View view;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    TextView tw;
    Intent thisIntent;

    public FileDescr() { }

    public void setIntent(Intent intent)
    {
        this.thisIntent = intent;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_filedescr, container, false);

        tw = view.findViewById(R.id.textView);

        btn1 = view.findViewById(R.id.button2); //selecteaza fisier
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // afiseaza in tw path-ul fisierului
                String path = thisIntent.getData().getPath();
                path = Link.adjustFilePath(path);
                tw.setText(path);
                Link.setServerPath(path);
            }
        });

        btn2 = view.findViewById(R.id.button3); // deschide fisier
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deschide in aplicatie externa fisierul
                String path = thisIntent.getData().getPath();
                path = Link.adjustFilePath(path);
                File file = new File(path);

                openFile(file);
            }
        });

        btn3 = view.findViewById(R.id.rsf); //inapoi
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    getFragmentManager().
                            beginTransaction().
                                replace(R.id.fragment_container, new FileUp()).
                                    commit();
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }
        });

        btn4 = view.findViewById(R.id.caf); //alt fisier
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent fileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                // folder, permite vizualizea doar a folderelor

                startActivityForResult(fileIntent,10);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data)
    {
        switch (requestCode) {
            case 10: {
                if (resultCode == Activity.RESULT_OK)
                {

                    FileDescr fileDescr = new FileDescr();
                    fileDescr.setIntent(data);
                    try {
                        getActivity().
                                getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.fragment_container, fileDescr).
                                commit();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    private void openFile(File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (url.toString().contains(".doc") || url.toString().contains(".docx"))
            {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            }
            else
                if (url.toString().contains(".pdf"))
                {
                    // PDF file
                    intent.setDataAndType(uri, "application/pdf");
                }
                else
                    if (url.toString().contains(".ppt") || url.toString().contains(".pptx"))
                    {
                        // Powerpoint file
                        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                    }
                    else
                        if (url.toString().contains(".xls") || url.toString().contains(".xlsx"))
                        {
                            // Excel file
                            intent.setDataAndType(uri, "application/vnd.ms-excel");
                        }
                        else
                            if (url.toString().contains(".zip"))
                            {
                                // ZIP file
                                intent.setDataAndType(uri, "application/zip");
                            }
                            else
                                if (url.toString().contains(".rar"))
                                {
                                    // RAR file
                                    intent.setDataAndType(uri, "application/x-rar-compressed");
                                }
                                else
                                    if (url.toString().contains(".rtf"))
                                    {
                                        // RTF file
                                        intent.setDataAndType(uri, "application/rtf");

                                    }
                                    else
                                        if (url.toString().contains(".wav") || url.toString().contains(".mp3"))
                                        {
                                            // WAV audio file
                                            intent.setDataAndType(uri, "audio/x-wav");
                                        }
                                        else
                if (url.toString().contains(".gif"))
                {
                    // GIF file
                    intent.setDataAndType(uri, "image/gif");
                }
                else
                    if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png"))
                    {
                        // JPG file
                        intent.setDataAndType(uri, "image/jpeg");
                    }
                    else
                        if (url.toString().contains(".txt"))
                        {
                            // Text file
                            intent.setDataAndType(uri, "text/plain");
                        }
                        else
                            if (url.toString().contains(".3gp") || url.toString().contains(".mpg") ||
                    url.toString().contains(".mpeg") || url.toString().contains(".mpe") ||
                    url.toString().contains(".mp4") || url.toString().contains(".avi"))
                            {
                                // Video files
                                intent.setDataAndType(uri, "video/*");
                            }
                            else
                                {
                                    intent.setDataAndType(uri, "*/*");
                                }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(getContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }
}