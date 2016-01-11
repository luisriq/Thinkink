package com.grupo2tbd.thinkink.Views;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.UploadImage;
import com.grupo2tbd.thinkink.Rest.Usuario;
import com.grupo2tbd.thinkink.Utilities.GalleryAdapterRV;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by cris_ on 14/12/2015.
 * Clase Fragmeto que se utilizará para mostrar la galería de imágenes
 */
public class Picture_list extends android.support.v4.app.Fragment{

    public static final int IMAGE_PICKER_SELECT = 100;
    private BroadcastReceiver br = null;
    private String URL_GET;
    private View v;
    private RecyclerView recList = null;
    private ProgressDialog progressDialog;

    /**
     * Constructor
     */
    public Picture_list(){
    }// Picture_list()

    /**
     * Método que crea la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.gallery, container, false);
        //ListView lista = (ListView) v.findViewById(R.id.galeryListView);
        //lista.setAdapter(new GalleryAdapter(getContext()));
        recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        //LinearLayoutManager llm = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recList.setLayoutManager(llm);

        FloatingActionButton subir = (FloatingActionButton) v.findViewById(R.id.subirFoto);

        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(e, IMAGE_PICKER_SELECT);
            }
        });
        Usuario loginService = ServiceGeneratorRest.createService(Usuario.class);
        Galeria g = new Galeria("SUBIDA", getArguments().getInt("id"));

        Call<ArrayList<Galeria.Foto>> call = loginService.galeria(g);
        call.enqueue(new Callback<ArrayList<Galeria.Foto>>() {
            @Override
            public void onResponse(Response<ArrayList<Galeria.Foto>> response, Retrofit retrofit) {
                for (Galeria.Foto d : response.body()) {
                    Log.e("Lista", ""+d.idFoto);
                }
                recList.setAdapter(new GalleryAdapterRV(response.body(), getContext()));

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload", t.getMessage());
                Toast.makeText(Picture_list.this.getContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)



    /**
     * Método que se llama una vez que se ha creado la actividad que contiene al fragmento
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //URL_GET = getString(R.string.ip)+"actors";
    }// onActivityCreated(Bundle savedInstanceState)

    /**
     * Método que escucha los click en los elementos de la lista
     */
    /**@Override
    *public void onListItemClick(ListView l, View v, int position, long id){
    *
    *}
    */

    /**
     * Método que se ejecuta cuando el fragmento es creado o restaurado
     */
    @Override
    public void onResume(){




        super.onResume();
    }
    public static Picture_list newInstance(int id){
        Picture_list pl = new Picture_list();
        Bundle b = new Bundle();
        b.putInt("id", id);
        pl.setArguments(b);
        return pl;
    }

    public void setString(String string) {
        this.URL_GET = string;
    }

    public void agregarFoto(Galeria.Foto foto) {
        ((GalleryAdapterRV)recList.getAdapter()).listaFotos.add(0, foto);
        recList.getAdapter().notifyItemInserted(0);
        recList.scrollToPosition(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Subiendo Imagen....");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            String path = getPathFromCameraData(data, getContext());
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {
                UploadImage service =
                        ServiceGenerator.createService(UploadImage.class);

                File file = new File(path);

                RequestBody requestBody =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                int idUsuario = getArguments().getInt("id", -1);
                Log.e("IDDD", ""+idUsuario);

                Call<Galeria.Foto> call = service.upload(requestBody, idUsuario);
                call.enqueue(new Callback<Galeria.Foto>() {
                    @Override
                    public void onResponse(Response<Galeria.Foto> response, Retrofit retrofit) {
                        progressDialog.dismiss();
                        //Actualizar lista

                        agregarFoto(response.body());

                        Toast.makeText(getContext(), "Imagen Subida correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Upload", t.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error al subir imagen", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
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
}
