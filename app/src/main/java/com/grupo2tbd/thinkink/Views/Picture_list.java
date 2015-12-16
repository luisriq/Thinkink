package com.grupo2tbd.thinkink.Views;


import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Utilities.GalleryAdapter;


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
        ListView lista = (ListView) v.findViewById(R.id.galeryListView);
        lista.setAdapter(new GalleryAdapter(getContext()));

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

    public void setString(String string) {
        this.URL_GET = string;
    }
}
