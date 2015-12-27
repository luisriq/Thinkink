package com.grupo2tbd.thinkink.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupo2tbd.thinkink.R;

import org.w3c.dom.Text;

/**
 * Created by cris_ on 21/12/2015.
 */
public class InformacionPerfilFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.info_tatuador, container,false);
        TextView info = (TextView) v.findViewById(R.id.textViewInformacion);





        info.setText("");

        return v;
    }
    public static InformacionPerfilFragment newInstance(){
        InformacionPerfilFragment ipf = new InformacionPerfilFragment();
        Bundle b = new Bundle();
        ipf.setArguments(b);
        return ipf;
    }
}
