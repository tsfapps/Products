package com.knotlink.salseman.utils;

import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;

public class ImageConverter {
    public static String imageToString(Bitmap image, ImageView tImageView) {

        if(null!=tImageView.getDrawable()) {
            ByteArrayOutputStream tByteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, tByteArrayOutputStream);
            byte[] b = tByteArrayOutputStream.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        }
        return "";
    }

}
