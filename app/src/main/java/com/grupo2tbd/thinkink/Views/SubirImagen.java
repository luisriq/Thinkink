package com.grupo2tbd.thinkink.Views;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.grupo2tbd.thinkink.Rest.UploadImage;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cris_ on 16/12/2015.
 */
public class SubirImagen extends android.support.v4.app.Fragment {

    private LayoutInflater li;
    private View v;
    private int IMAGE_PICKER_SELECT = 100;
    /**
     * Constructor
     */
    public SubirImagen(){
    }//SubirImagen()



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.getActivity());
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {
                /*SendFoto send = new SendFoto(getContext(), path);
                send.execute();*/

                UploadImage service =
                        ServiceGenerator.createService(UploadImage.class);

                String description = "hello, this is description speaking";
                File file = new File(path);

                RequestBody requestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                Call<String> call = service.upload(requestBody, description);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        Log.v("Upload", "success"+response.message()+response.code());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Upload", t.getMessage());
                    }
                });



            }
        }
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.login, container, false);


        IntentFilter intentFilter = new IntentFilter("imagen-resultado");
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Enviado", Toast.LENGTH_LONG).show();
            }
        };
        getActivity().registerReceiver(br, intentFilter);
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICKER_SELECT);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
