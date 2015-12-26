package com.grupo2tbd.thinkink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.grupo2tbd.thinkink.Views.Login;
import com.grupo2tbd.thinkink.Views.Picture_list;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences(PerfilTatuador.Preferencias, Context.MODE_PRIVATE);
        String id = sharedPref.getString("idUsuario",null);
        if(id != null){
            Intent i = new Intent(this, PerfilTatuador.class);
            i.putExtra("id",id);
            startActivity(i);
            finish();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = new Login();
        fm.beginTransaction().replace(R.id.containerInicio, fragment).commit();


    }

}
