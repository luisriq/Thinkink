package com.grupo2tbd.thinkink.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Usuario;

import org.w3c.dom.Text;

import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cris_ on 21/12/2015.
 */
public class InformacionPerfilFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.info_tatuador, container,false);
        final TextView info = (TextView) v.findViewById(R.id.textViewInformacion);
        Usuario loginService = ServiceGeneratorRest.createService(Usuario.class);
        HashMap<String, Integer> user = new HashMap<>();

        user.put("idUsuario", getArguments().getInt("id"));
        Call<Galeria.Usuario> call = loginService.perfil(user);
        call.enqueue(new Callback<Galeria.Usuario>() {

            @Override
            public void onResponse(Response<Galeria.Usuario> response, Retrofit retrofit) {
                if(response.body().descripcion!=null){
                    info.setText(response.body().descripcion);
                }
                ((PerfilTatuador)getActivity()).collapsingToolbarLayout.setTitle(response.body().nombreUsuario);


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


            info.setText("");

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
