package com.grupo2tbd.thinkink;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by cris_ on 16/12/2015.
 */
public class InicialActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.login);
    }
}
