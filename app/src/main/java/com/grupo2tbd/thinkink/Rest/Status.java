package com.grupo2tbd.thinkink.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Response;

/**
 * Created by luis on 12/22/15.
 */
public class Status {
    String respuesta;
    public static Status parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Response respuesta = gson.fromJson(response, Response.class);
        Status s = new Status();
        s.respuesta = respuesta.toString();
        return s;
    }
}