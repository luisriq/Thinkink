package com.grupo2tbd.thinkink.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
 * Created by cris_ on 21/12/2015.
 */
public class InformacionPerfilFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

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
        MapView mapView = (MapView) v.findViewById(R.id.map_view);
        mapView.onCreate(null);
        // Set the map ready callback to receive the GoogleMap object
        mapView.getMapAsync(this);

        return v;
        }

    public static InformacionPerfilFragment newInstance(int id){
        InformacionPerfilFragment ipf = new InformacionPerfilFragment();
        Bundle b = new Bundle();
        b.putInt("id", id);
        ipf.setArguments(b);
        return ipf;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng loc =  new LatLng(-33.4445011, -70.650879);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f));

        googleMap.addMarker(new MarkerOptions().position(loc));

    }
}
