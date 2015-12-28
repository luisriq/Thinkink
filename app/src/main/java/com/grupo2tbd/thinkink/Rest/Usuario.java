package com.grupo2tbd.thinkink.Rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by luis on 12/20/15.
 */
public interface Usuario {
    @Headers( "Content-Type: application/json" )
    @POST("login")
    Call<HashMap<String, String>> logear( @Body HashMap<String, String> sJsonBody ) ;


    @Headers( "Content-Type: application/json" )
    @POST("registro")
    Call<HashMap<String, String>> registrar( @Body HashMap<String, String> sJsonBody ) ;

    @Headers( "Content-Type: application/json" )
    @POST("verGaleria")
    Call<ArrayList<Galeria.Foto>> galeria(@Body Galeria sJsonBody) ;

    @Headers( "Content-Type: application/json" )
    @POST("verPerfil")
    Call<Galeria.Usuario> perfil(@Body HashMap<String, Integer> sJsonBody) ;
}
