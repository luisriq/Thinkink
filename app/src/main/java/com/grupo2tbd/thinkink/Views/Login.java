package com.grupo2tbd.thinkink.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupo2tbd.thinkink.PerfilTatuador;
import com.grupo2tbd.thinkink.R;
import com.grupo2tbd.thinkink.Rest.ServiceGenerator;
import com.grupo2tbd.thinkink.Rest.ServiceGeneratorRest;
import com.grupo2tbd.thinkink.Rest.Status;
import com.grupo2tbd.thinkink.Rest.UploadImage;
import com.grupo2tbd.thinkink.Rest.Usuario;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by cris_ on 16/12/2015.
 */
public class Login extends android.support.v4.app.Fragment {

    private LayoutInflater li;
    private View v;
    private ProgressDialog progressDialog;

    /**
     * Constructor
     */
    public Login(){
    }//Login()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.login, container, false);

        TextView tx = (TextView)v.findViewById(R.id.textViewNoCuentaButton);
        final EditText correo = (EditText) v.findViewById(R.id.editTextEmail);
        final EditText pass = (EditText) v.findViewById(R.id.editTextPassword);


        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = Login.this.getActivity().getSupportFragmentManager();
                android.support.v4.app.Fragment fragment = new Registro();
                fm.beginTransaction().replace(R.id.containerInicio, fragment).addToBackStack(null).commit();

            }
        });
        final Button btnLogin = (Button) v.findViewById(R.id.button_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                //LLamada Login
                Usuario loginService = ServiceGeneratorRest.createService(Usuario.class);
                HashMap<String, String> user = new HashMap<>();

                user.put("correo", correo.getText().toString());
                user.put("pass", pass.getText().toString());
                Call<HashMap<String, String>> call = loginService.logear(user);
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Conectando....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                call.enqueue(new Callback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(Response<HashMap<String, String>> response, Retrofit retrofit) {
                        for (Map.Entry<String, String> entry : response.body().entrySet()) {
                            Log.e(""+entry.getKey(), entry.getValue());

                        }
                        if(response.body().containsKey("ERROR")){
                            Toast.makeText(Login.this.getContext(), "Error al ingresar\n"+response.body().get("ERROR"), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            btnLogin.setEnabled(true);
                            return;
                        }

                        Intent i = new Intent(getActivity(), PerfilTatuador.class);
                        i.putExtra("id", response.body().get("idUsuario"));
                        //Se guarda el id para mantener la sesion iniciada
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PerfilTatuador.Preferencias, Context.MODE_PRIVATE).edit();
                        editor.putString("idUsuario", response.body().get("idUsuario"));
                        editor.commit();
                        startActivity(i);
                        progressDialog.dismiss();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Upload", t.getMessage());
                        Toast.makeText(Login.this.getContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        btnLogin.setEnabled(true);
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
