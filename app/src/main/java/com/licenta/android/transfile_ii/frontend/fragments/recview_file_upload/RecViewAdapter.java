package com.licenta.android.transfile_ii.frontend.fragments.recview_file_upload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.licenta.android.transfile_ii.R;

import java.util.ArrayList;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {

    private ArrayList<CardContent> listItems;
    private Context context;

    public RecViewAdapter(ArrayList<CardContent> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup ViewGroup, int i) {
        // apelata cand se ceaza viewHolder
        // trimitem layout-ul la adapter
        View v = LayoutInflater.from(ViewGroup.getContext()).inflate(R.layout.recview_list, ViewGroup, false);
        ViewHolder vh = new ViewHolder( v );
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder ViewHolder, int i) {
        //se apeleaza dupa prima
        //trimitem valori catre REcyclerView cu aceasta
        //will show in View the data from ViewHolder
        CardContent lai = listItems.get(i); //list actual item
        ViewHolder.textViewName.setText(lai.getName());
        ViewHolder.textViewSize.setText(String.valueOf(lai.getSize()));
        ViewHolder.imageViewIcon.setImageBitmap(lai.getIcon());
    }

    @Override
    public int getItemCount()
    {
        return this.listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // aici se pun instantele din CardView
        public TextView textViewName;
        public TextView textViewSize;
        public ImageView imageViewIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.TextViewHead);
            textViewSize = itemView.findViewById(R.id.TextViewDescription);
            imageViewIcon = itemView.findViewById(R.id.Icon);
        }
    }
}
