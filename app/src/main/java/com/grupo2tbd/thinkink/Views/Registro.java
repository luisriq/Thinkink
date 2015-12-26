package com.grupo2tbd.thinkink.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Usuario;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cris_ on 16/12/2015.
 */
public class Registro extends android.support.v4.app.Fragment {

    private LayoutInflater li;
    private View v;
    private ProgressDialog progressDialog;

    /**
     * Constructor
     */
    public Registro(Context c){
    }//Login()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.registro, container, false);
        final EditText correo = (EditText)v.findViewById(R.id.editTextEmail);
        final EditText pass = (EditText)v.findViewById(R.id.editTextPsss);
        final EditText nombre = (EditText)v.findViewById(R.id.editTextNombre);
        final EditText pass2 = (EditText)v.findViewById(R.id.editTextPassConfirmation);

        final Button buttonRegistro = (Button) v.findViewById(R.id.buttonRegistro);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass.getText().toString().equals(pass2.getText().toString())){
                    Toast.makeText(getContext(), "Ambas contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    return;
                }

                buttonRegistro.setEnabled(false);
                //LLamada Login
                Usuario loginService = ServiceGeneratorRest.createService(Usuario.class);
                HashMap<String, String> user = new HashMap<>();
                user.put("correo", correo.getText().toString());
                user.put("pass", pass.getText().toString());
                user.put("nombreUsuario", nombre.getText().toString());
                user.put("tipoUsuario", "TATUADOR");
                user.put("estadoCuenta", "ACTIVA");
                Call<HashMap<String, String>> call = loginService.registrar(user);
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Registrando....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Response<HashMap<String, String>> response, Retrofit retrofit) {
                        for (Map.Entry<String, String> entry : response.body().entrySet()) {
                            Log.e("" + entry.getKey(), entry.getValue());

                        }
                        if(response.body().containsKey("ERROR")){
                            Toast.makeText(Registro.this.getContext(), "Error al registrar\n"+response.body().get("ERROR"), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            buttonRegistro.setEnabled(true);
                            return;
                        }

                        Intent i = new Intent(getActivity(), PerfilTatuador.class);
                        i.putExtra("id", response.body().get("idUsuario"));

                        startActivity(i);
                        progressDialog.dismiss();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Upload", t.getMessage());
                        Toast.makeText(Registro.this.getContext(), "Error al logearse", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        buttonRegistro.setEnabled(false);
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
