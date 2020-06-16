/* Lucrare de licență: Aplicație pentru transfer de fișiere
 * Student: Mihai-Alexandru Muntean
 * Aplicația Android
 * 
 * Clasa FileUp
 * Folosită ca parte automatizată a resursei fileup_fragment.xml.
 */
package com.licenta.android.transfile_ii.frontend.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

    public class FileUp extends Fragment
{

    View view;
    Button btn;
    Button btn2;
    Intent fileIntent;
    TextView tw;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_file_upload, container, false);
        tw = view.findViewById(R.id.server_file);

        // start server
        btn =  view.findViewById(R.id.butsv);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Link.serverCall();
            }
        });

        // alege fisier
        btn2 = view.findViewById(R.id.pick);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                // folder, permite vizualizea doar a folderelor

                startActivityForResult(fileIntent,10);
                tw.setText(Link.getServerFilePath());

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
                    try
                    {
                        getActivity().
                                getSupportFragmentManager().
                                    beginTransaction().
                                        replace(R.id.fragment_container, fileDescr).
                                            commit();

                    }
                    catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
}
