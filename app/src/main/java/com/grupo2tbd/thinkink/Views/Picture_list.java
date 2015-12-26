package com.grupo2tbd.thinkink.Views;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Utilities.GalleryAdapter;
import com.grupo2tbd.thinkink.Utilities.GalleryAdapterRV;

import java.util.ArrayList;


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

        ArrayList<String> ar = new ArrayList<>();
        ar.add("Pedro");
        ar.add("Juan");
        ar.add("Diego");
        ar.add("Pedro");
        ar.add("Juan");
        ar.add("Diego");
        ar.add("Pedro");
        ar.add("Juan");
        ar.add("Diego");
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        //LinearLayoutManager llm = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recList.setLayoutManager(llm);
        recList.setAdapter(new GalleryAdapterRV(ar, getContext()));

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
    public static Picture_list newInstance(){
        Picture_list pl = new Picture_list();
        Bundle b = new Bundle();
        pl.setArguments(b);
        return pl;
    }

    public void setString(String string) {
        this.URL_GET = string;
    }
}
