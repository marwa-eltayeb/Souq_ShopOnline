package com.marwaeltayeb.souq.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public class ImageUtils {

    private ImageUtils(){}

    // And to convert the image URI to the direct file system path of the image file
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        // can post image
        String [] projection ={MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query( contentUri,
                projection , // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String result = cursor.getString(columnIndex);
        cursor.close();

        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri getImageUriQ(Context context, Bitmap bitmap) {

        String filename = "IMG_$"+ System.currentTimeMillis()+".jpg";

        OutputStream fos = null;
        Uri imageUri;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 1);

        ContentResolver contentResolver = context.getContentResolver();

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
            fos = contentResolver.openOutputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
        contentValues.clear();
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);

        contentResolver.update(imageUri, contentValues, null, null);

        return imageUri;
    }

    public static Uri getImageUriBeforeQ(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Uri getImageUri(Context context, Bitmap photo){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getImageUriQ(context, photo);
        } else {
            return getImageUriBeforeQ(context, photo);
        }
    }




}
