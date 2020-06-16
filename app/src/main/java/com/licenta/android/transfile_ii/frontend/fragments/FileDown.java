/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa FileDown
 * Folosită ca parte automatizată a resursei filedown_fragment.xml.
 */

package com.licenta.android.transfile_ii.frontend.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

import java.io.File;


public class FileDown extends Fragment
{

    View view;
    Button btn;
    Button btn2;
    boolean state;
    TextView tw;
    String path;
    String text;
    TextView tw1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_file_download, container, false);
        state=false;

        btn =  view.findViewById(R.id.but_cl);
        btn2 = view.findViewById(R.id.but_op);
        btn2.setEnabled(state);
        tw= view.findViewById(R.id.path);
        tw1= view.findViewById(R.id.opo);
        tw1.setText("");
        text="Fișierul este salvat la locația:";


        // start client
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                state = Link.clientCall();
                path=Link.getClientFilePath();
                tw.setText(path);
                if (state)
                {
                    tw1.setText(text);
                    Toast.makeText(getContext(),R.string.file_rec, Toast.LENGTH_SHORT).show();
                }
                btn2.setEnabled(state);
            }
        });

        // viz. fisierul primit
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (state)
                {
                    File recfile = new File(path);
                    openFile(recfile);
                }
            }
        });
        return view;
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
