package com.licenta.android.transfile_ii.frontend.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

public class Settings extends Fragment implements View.OnClickListener
{

    View view;
    TextView error;
    FloatingActionButton btn;
    RadioButton rb;
    String protocol;
    String algorithm;
    boolean err1;
    boolean err2;
    boolean err3;

    // partea client

    EditText ip;
    EditText portClient;

    // partea server

    RadioGroup prot;
    RadioGroup alg;
    EditText portServer;
    int radioint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        btn =  view.findViewById(R.id.floatActBtn);
        error = view.findViewById(R.id.err);
        ip = view.findViewById(R.id.ipv4);
        portClient = view.findViewById(R.id.portClient);
        portServer = view.findViewById(R.id.portServer);
        alg = view.findViewById(R.id.algorithm);
        prot = view.findViewById(R.id.protocol);

        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        err1=Link.clientSettings(ip.getText().toString(),portClient.getText().toString());
        err2=Link.serverSettings(protocol,algorithm,portServer.getText().toString());

        try {
            radioint = alg.getCheckedRadioButtonId();
            rb = view.findViewById(radioint);
            algorithm = rb.getText().toString();

            radioint = prot.getCheckedRadioButtonId();
            rb = view.findViewById(radioint);
            protocol = rb.getText().toString();
        }
        catch (NullPointerException e)
        {
            err3=true;
        }

        if (err1||err2||err3)
        {
            error.setText(R.string.error);
        }
        else
            Toast.makeText(getContext(),R.string.save_sett, Toast.LENGTH_SHORT).show();

    }
}
