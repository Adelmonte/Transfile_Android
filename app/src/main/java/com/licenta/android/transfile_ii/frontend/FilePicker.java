package com.licenta.android.transfile_ii.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.licenta.android.transfile_ii.R;
import com.licenta.android.transfile_ii.middleend.Link;

import java.io.File;

public class FilePicker extends AppCompatActivity
{
    Button btn1;
    Button btn2;
    TextView tw;
    Intent fileIntent;

    public FilePicker(Intent intent)
    {
        fileIntent=intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fileIntent=new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("*/*");
        startActivityForResult(fileIntent,10);

        setContentView(R.layout.fragment_filedescr);

        tw = findViewById(R.id.textView);

        btn1 = findViewById(R.id.button2); //pick file
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // afiseaza in tw path-ul fisierului
                String path = fileIntent.getData().getPath();
                tw.setText(path);
                // seteaza calea pentru backend
                Link.setServerPath(path);
            }
        });

        btn2 = findViewById(R.id.button3); // open file
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dechide in aplicatie externa fisierul

            }
        });
    }
}
