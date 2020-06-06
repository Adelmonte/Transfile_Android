package com.licenta.android.transfile_ii.frontend.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity.*;
import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

    public class FileUp extends Fragment
{

    View view;
    Button btn;
    Button btn2;
    Intent fileIntent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_file_upload, container, false);
        btn =  view.findViewById(R.id.butsv);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Link.serverCall();
            }
        });

        btn2 = view.findViewById(R.id.pick);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("folder/*");
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

                    System.out.println("Ajunge aici");
                    FileDescr fileDescr = new FileDescr();
                    fileDescr.setIntent(data);
                    getActivity().
                            getSupportFragmentManager().
                                beginTransaction().
                                    replace(R.id.fragment_container, fileDescr).
                                        commit();
                }
            }
            break;
        }
    }
}
