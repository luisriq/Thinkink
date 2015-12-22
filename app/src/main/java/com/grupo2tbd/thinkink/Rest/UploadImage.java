package com.grupo2tbd.thinkink.Rest;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by luis on 12/20/15.
 */
public interface UploadImage {
    @Multipart
    @POST("fileupload")
    Call<String> upload(
            @Part("file") RequestBody file,
            @Part("description") String description);
}
