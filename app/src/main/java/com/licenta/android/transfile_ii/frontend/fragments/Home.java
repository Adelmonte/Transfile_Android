package com.licenta.android.transfile_ii.frontend.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.backend.memory.Values;

public class Home extends Fragment
{
    View view;
    TextView tx;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        Button btn;
        btn = view.findViewById(R.id.button);

       tx = view.findViewById(R.id.tx);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String p="/document/primary:event/kritle/op.png";
                p= Values.filePathAdjusted(p);
                tx.setText(p);
            }
        });
        return view;
    }
}
