package com.grupo2tbd.thinkink.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grupo2tbd.thinkink.R;

import java.util.ArrayList;

/**
 * Created by cris_ on 14/12/2015.
 */
public class GalleryAdapter extends BaseAdapter {
    ArrayList<String> listaNombres = new ArrayList<>();
    Context c;
    LayoutInflater li;

    public GalleryAdapter(Context c){
        this.c = c;
        // convierte xml en view
        this.li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = li.inflate(R.layout.item_galery, null);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.nombre_usuario);
        tv.setText("Holi");
        //ImageView iv = (ImageView)convertView.findViewById(R.id.imagenCard);
        //Picasso.with(c).load(R.drawable.fondoamarillo).fit().centerInside().into(iv);


        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imagenCard);

        //Glide.with(c).load(R.drawable.imagenlarga).crossFade().into(imageView);
        Glide.with(c)
                .load(R.drawable.fondoamarillo)
                .centerCrop()
                .fitCenter()
                .into(imageView);
        return convertView;
    }
}
