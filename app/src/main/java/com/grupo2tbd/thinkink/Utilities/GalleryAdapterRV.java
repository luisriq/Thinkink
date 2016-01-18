package com.grupo2tbd.thinkink.Utilities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 12/26/15.
 */

public class GalleryAdapterRV extends RecyclerView.Adapter<GalleryAdapterRV.FotoViewHolder> {

    private final Context c;
    public ArrayList<Galeria.Foto> listaFotos;
    private RequestQueue requestQueue;

    public GalleryAdapterRV(ArrayList<Galeria.Foto> listaFotos, Context c) {
        this.listaFotos = listaFotos;
        this.c = c;
    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    @Override
    public void onBindViewHolder(final FotoViewHolder contactViewHolder, int i) {
        final Galeria.Foto ci = listaFotos.get(i);
        contactViewHolder.cantidadLikes.setText(""+ci.cantidadMegusta);
        contactViewHolder.vName.setText(ci.nombre);
        contactViewHolder.vFecha.setText(ci.fecha);

        if (ci.likeAble){
            Glide.with(c)
                    .load(R.drawable.like_gris)
                    .centerCrop()
                    .fitCenter()
                    .into(contactViewHolder.like);
        }
        else {
            Glide.with(c)
                    .load(R.drawable.like_333)
                    .centerCrop()
                    .fitCenter()
                    .into(contactViewHolder.like);
        }

        String url = ServiceGenerator.IP+":8080/Think-INK/verFoto/"+ci.idFoto;
        //Picasso.with(c).load(R.drawable.fondoamarillo).resize(600, 200).centerInside().into(contactViewHolder.foto);
        //Picasso.with(c).load(url).resize(600, 200).centerInside().placeholder(R.drawable.placeholder).into(contactViewHolder.foto);
        contactViewHolder.foto.setMaxHeight(500);
        contactViewHolder.foto.setMinimumHeight(500);
        contactViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                JSONObject jo = new JSONObject();
                try {
                    jo.put("idUsuario", ci.idUsuario);
                    jo.put("idFoto", ci.idFoto);

                    Log.e(jo.toString(), "ms");
                    String e = "layout_height";
                    ObjectAnimator anim = ViewPropertyObjectAnimator
                            .animate(v)
                            .withLayer()
                            .scaleX(1.5f)
                            .scaleY(1.5f)
                            .setDuration(300)
                            .get();
                    anim.start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {}

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ViewPropertyObjectAnimator
                                    .animate(v)
                                    .withLayer()
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setDuration(300)
                                    .get().start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {}

                        @Override
                        public void onAnimationRepeat(Animator animation) {}
                    });
                    if (ci.likeAble) {
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                ServiceGenerator.IP + ":8080/Think-INK/megusta/guardar",
                                jo,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        ci.cantidadMegusta = ci.cantidadMegusta + 1;
                                        contactViewHolder.like.setClickable(false);
                                        contactViewHolder.cantidadLikes.setText("" + (ci.cantidadMegusta));
                                        Glide.with(c)
                                                .load(R.drawable.like_333)
                                                .centerCrop()
                                                .fitCenter()
                                                .into(contactViewHolder.like);
                                        Log.e("post ", "like guardado");
                                        Log.e("post ", response.toString());
                                        ci.likeAble = false;
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                        );
                        requestQueue = Volley.newRequestQueue(c);
                        requestQueue.add(jsonObjReq);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(c,"No se pudo completar el like/dislike", Toast.LENGTH_LONG).show();
                }
            }
        });
        Glide.with(c)
                .load(url).thumbnail(0.5f)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(contactViewHolder.foto);

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
        protected ImageButton like;
        protected TextView cantidadLikes;

        public FotoViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.nombre_usuario);
            vFecha = (TextView) v.findViewById(R.id.textView2);
            foto = (ImageView) v.findViewById(R.id.imagenCard);
            like = (ImageButton) v.findViewById(R.id.btn_like);
            cantidadLikes = (TextView) v.findViewById(R.id.cantidadLikes);
        }

    }
}