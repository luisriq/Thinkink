package com.grupo2tbd.thinkink.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by cris_ on 14/12/2015.
 * Clase que se utiliza para obtener información del sistema
 */
public class SystemUtilities {

    Context context;

    /**
     * Constructor
     */
    public SystemUtilities(Context context) {
        this.context = context;
    }// SystemUtilities(Context context)

    /**
     * Método que consulta el estado de la conexión a Internet
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }// isNetworkAvailable()

}// SystemUtilities
