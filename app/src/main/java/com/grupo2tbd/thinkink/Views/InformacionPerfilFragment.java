package com.grupo2tbd.thinkink.Views;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.Galeria;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by cris_ on 21/12/2015.
 */
public class InformacionPerfilFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    static int PLACE_PICKER_REQUEST = 1;
    LatLng loc;
    private MapView mapView;
    private RequestQueue requestQueue;
    private TextView local;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.info_tatuador, container,false);
        final TextView info = (TextView) v.findViewById(R.id.textViewInformacion);
        local = (TextView) v.findViewById(R.id.tv_nombre);
        Usuario loginService = ServiceGeneratorRest.createService(Usuario.class);
        HashMap<String, Integer> user = new HashMap<>();

        JSONObject jo= new JSONObject();
        try {
            jo.put("idUsuario", getArguments().getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("holahgygygygyligyliygig",jo.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ServiceGenerator.IP+":8080/Think-INK/trabajo/obtenerTrabajo",
                jo,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(response.toString(), response.toString());
                        try {
                            String nombreLocal = response.getString("nombreLocal");
                            Double latitud = response.getDouble("latitud");
                            Double longitud = response.getDouble("longitud");
                            loc = new LatLng(latitud,longitud);
                            mapView.getMapAsync(InformacionPerfilFragment.this);
                            local.setText(nombreLocal);
                            Log.e("POS", "latitud "+latitud+" longitud "+longitud);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjReq);


        user.put("idUsuario", getArguments().getInt("id"));
        Call<Galeria.Usuario> call = loginService.perfil(user);
        call.enqueue(new Callback<Galeria.Usuario>() {

            @Override
            public void onResponse(Response<Galeria.Usuario> response, Retrofit retrofit) {
                if (response.body().descripcion != null) {
                    info.setText(response.body().descripcion);
                }
                ((PerfilTatuador) getActivity()).collapsingToolbarLayout.setTitle(response.body().nombreUsuario);


            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        info.setText("");
        mapView = (MapView) v.findViewById(R.id.map_view);

        mapView.onCreate(null);
        ImageButton editar = (ImageButton) v.findViewById(R.id.btn_editar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();//
                if (loc != null){
                builder.setLatLngBounds(new LatLngBounds(new LatLng(loc.latitude, loc.longitude - 0.05d), new LatLng(loc.latitude, loc.longitude + 0.05d)));}
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    //TODO: Generar Intent que lleve a la instalacion/actualizacion de googleplay
                    Toast.makeText(getContext(), "GooglePlayService reparable excepcion", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getContext(), "GooglePlayService no esta disponible", Toast.LENGTH_SHORT);

                    e.printStackTrace();
                }
            }
        });

        final CardView info_card = (CardView) v.findViewById(R.id.card_view);

        final ObjectAnimator animation = ObjectAnimator.ofInt(
                info,
                "maxLines", 5);

        animation.setDuration(500);
        info_card.setOnClickListener(new View.OnClickListener() {
            boolean expandido = false;

            @Override
            public void onClick(View v) {
                animation.cancel();

                if (expandido) {
                    animation.setIntValues(5);
                } else {
                    animation.setIntValues(30);
                }
                animation.start();
                Log.e("click", "" + expandido);
                expandido = !expandido;

            }
        });

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
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f));

        googleMap.addMarker(new MarkerOptions().position(loc));
        Log.e("POS MARKER", ""+loc.latitude+","+loc.longitude);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format(" %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
                LocalDialogFragment lcdf = new LocalDialogFragment();
                lcdf.setOnCloseListener(new MyDialogCloseListener() {
                    @Override
                    public String acpetar(String nombreLocal) {
                        local.setText(nombreLocal);
                        return null;
                    }

                    @Override
                    public String cancelar() {
                        return null;
                    }
                });
                loc = place.getLatLng();
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", loc.latitude);
                bundle.putDouble("lng", loc.longitude);
                bundle.putInt("idUsuario", getArguments().getInt("id"));
                lcdf.setArguments(bundle);
                lcdf.show(getFragmentManager(),"asda");
                mapView.getMapAsync(this);
            }
        }
    }


    public interface MyDialogCloseListener
    {
        public String acpetar(String nombreLocal);//or whatever args you want
        public String cancelar();
    }
}
