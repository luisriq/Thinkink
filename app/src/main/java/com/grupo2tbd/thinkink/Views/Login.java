package com.grupo2tbd.thinkink.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        TextView tx = (TextView)v.findViewById(R.id.textViewNoCuentaButton);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = Login.this.getActivity().getSupportFragmentManager();
                android.support.v4.app.Fragment fragment = new Registro(Login.this.getContext());
                fm.beginTransaction().replace(R.id.containerInicio, fragment).commit();

            }
        });
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
