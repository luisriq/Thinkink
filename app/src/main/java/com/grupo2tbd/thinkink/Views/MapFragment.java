package com.grupo2tbd.thinkink.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Usuario;

import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by luis on 1/8/16.
 */
public class MapFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.info_tatuador, container,false);


        return v;
    }

    public static InformacionPerfilFragment newInstance(int id){
        InformacionPerfilFragment ipf = new InformacionPerfilFragment();
        Bundle b = new Bundle();
        b.putInt("id", id);
        ipf.setArguments(b);
        return ipf;
    }
}
