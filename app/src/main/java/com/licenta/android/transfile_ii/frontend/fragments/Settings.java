package com.licenta.android.transfile_ii.frontend.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

public class Settings extends Fragment implements View.OnClickListener
{

    View view;
    TextView error;
    Button btn;
    RadioButton rb;
    String protocol;
    String algorithm;
    int err;
    // partea client

    EditText ip;
    EditText port;

    // partea server

    RadioGroup prot;
    RadioGroup alg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        btn =  view.findViewById(R.id.floatActBtn);
        error = view.findViewById(R.id.err);
        ip = view.findViewById(R.id.ipv4);
        port = view.findViewById(R.id.port);

        alg = view.findViewById(R.id.protocol);
        int radioint = alg.getCheckedRadioButtonId();
        rb = view.findViewById(radioint);
        algorithm = rb.getText().toString();

        prot = view.findViewById(R.id.algorithm);
        radioint = prot.getCheckedRadioButtonId();
        rb = view.findViewById(radioint);
        protocol = rb.getText().toString();

        btn.setOnClickListener(this);

        if (err!=0)
        {
           error.setText(R.string.error);
        }
        return view;
    }

    @Override
    public void onClick(View v)
    {
        err=0;
        err=Link.clientSettings(ip.getText().toString(),port.getText().toString());
        Link.serverSettings(protocol,algorithm);
    }
}
