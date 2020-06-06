package com.licenta.android.transfile_ii.frontend.fragments.recview_file_upload;

import android.graphics.Bitmap;

import java.io.File;

public class CardContent
{
    // scriem ce va avea fiecare item
    private File f;

    public CardContent(File file) {
        this.f = file;
    }

    public long getSize()
    {
        return this.f.getTotalSpace();
    }

    public Bitmap getIcon()
    {
        return null;
    }

    public String getName() {
        return this.f.getName();
    }
}
