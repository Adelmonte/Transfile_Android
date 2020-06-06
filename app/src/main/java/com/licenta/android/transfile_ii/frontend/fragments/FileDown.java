package com.licenta.android.transfile_ii.frontend.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;



public class FileDown extends Fragment implements View.OnClickListener
{

    View view;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_file_download, container, false);
        btn =  view.findViewById(R.id.butcl);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        Link.clientCall();
    }

   // @Nullable
  // @Override
    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //    return inflater.inflate(R.layout.fragment_file_download,container,false);
    //}
}
