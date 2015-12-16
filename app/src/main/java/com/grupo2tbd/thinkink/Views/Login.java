package com.grupo2tbd.thinkink.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grupo2tbd.thinkink.R;

/**
 * Created by cris_ on 16/12/2015.
 */
public class Login extends android.support.v4.app.Fragment {

    private LayoutInflater li;
    private View v;

    /**
     * Constructor
     */
    public Login(Context c){
    }//Login()

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.v = inflater.inflate(R.layout.login, container, false);
        ImageView fondo = (ImageView) v.findViewById(R.id.imageFondoLogo);
        return v;
    }
}
