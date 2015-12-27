package com.grupo2tbd.thinkink.Views;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Usuario;
import com.grupo2tbd.thinkink.Utilities.GalleryAdapterRV;

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

    private BroadcastReceiver br = null;
    private String URL_GET;
    private View v;


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
        final RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        //LinearLayoutManager llm = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recList.setLayoutManager(llm);


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
}
