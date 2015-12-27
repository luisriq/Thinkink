package com.grupo2tbd.thinkink.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 12/26/15.
 */

public class GalleryAdapterRV extends RecyclerView.Adapter<GalleryAdapterRV.FotoViewHolder> {

    private final Context c;
    private ArrayList<Galeria.Foto> listaFotos;

    public GalleryAdapterRV(ArrayList<Galeria.Foto> listaFotos, Context c) {
        this.listaFotos = listaFotos;
        this.c = c;
    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    @Override
    public void onBindViewHolder(FotoViewHolder contactViewHolder, int i) {
        Galeria.Foto ci = listaFotos.get(i);
        contactViewHolder.vName.setText(ci.nombre);
        contactViewHolder.vFecha.setText(ci.fecha);
        String url = "http://192.168.0.3:8080/Think-INK/verFoto/"+ci.idFoto;
        //Picasso.with(c).load(R.drawable.fondoamarillo).resize(600, 200).centerInside().into(contactViewHolder.foto);
        Picasso.with(c).load(url).resize(600, 200).centerInside().into(contactViewHolder.foto);

        /*Glide.with(c)
                .load(R.drawable.tatuaje).thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(contactViewHolder.foto);*/
        /*Glide.with(c)
                .load(R.drawable.fondoamarillo)
                .centerCrop()
                .fitCenter()
                .into(contactViewHolder.foto);*/
    }

    @Override
    public FotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_galery, viewGroup, false);

        return new FotoViewHolder(itemView);
    }

    public static class FotoViewHolder extends RecyclerView.ViewHolder {


        protected TextView vFecha;
        protected ImageView foto;
        protected TextView vName;

        public FotoViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.nombre_usuario);
            vFecha = (TextView) v.findViewById(R.id.textView2);
            foto = (ImageView) v.findViewById(R.id.imagenCard);

        }
    }
}