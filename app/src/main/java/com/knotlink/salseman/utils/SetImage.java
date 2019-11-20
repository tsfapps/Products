package com.knotlink.salseman.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Objects;


public class SetImage {

    public static void setGalleryImage(Context tContext, ImageView imageView, Intent data) {
        Uri contentURI = data.getData();
        try {
            Bitmap bitmap = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(tContext).getContentResolver(), contentURI);
            }
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCameraImage(ImageView imageView, Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }

}
