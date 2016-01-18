package com.grupo2tbd.thinkink.Views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.ErrorHandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import retrofit.http.POST;

/**
 * Created by cris_ on 14/01/2016.
 */
public class LocalDialogFragment extends android.support.v4.app.DialogFragment implements OnMapReadyCallback {
    private MapView mapView;
    private LatLng loc;
    private RequestQueue requestQueue;
    private InformacionPerfilFragment.MyDialogCloseListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final Double lat = getArguments().getDouble("lat");
        final Double lng = getArguments().getDouble("lng");
        loc = new LatLng(lat,lng);
        final View v = inflater.inflate(R.layout.dialog_local, null);
        mapView = (MapView) v.findViewById(R.id.map_view);
        mapView.onCreate(null);
        // Set the map ready callback to receive the GoogleMap object
        mapView.getMapAsync(this);
        builder.setView(v)

                .setPositiveButton(R.string.localAcept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final JSONObject jo= new JSONObject();
                        try {
                            jo.put("idUsuario", getArguments().getInt("idUsuario"));
                            EditText et = (EditText) v.findViewById(R.id.nombre_local);
                            jo.put("nombreLocal",et.getText());
                            jo.put("latitud", String.valueOf(lat));
                            jo.put("longitud", String.valueOf(lng));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            listener.acpetar(jo.getString("nombreLocal"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(jo.toString(),"ms");
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                ServiceGenerator.IP+":8080/Think-INK/trabajo/crearTrabajo",
                                jo,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                        );
                        requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(jsonObjReq);
                    }
                })
                .setNegativeButton(R.string.localCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f));

        googleMap.addMarker(new MarkerOptions().position(loc));
    }

    public void setOnCloseListener(InformacionPerfilFragment.MyDialogCloseListener listener) {
        this.listener = listener;
    }
}