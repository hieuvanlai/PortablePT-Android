package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hieuit on 9/3/17.
 */

public interface RegisterPack {
    @POST("register-pack")
    Call<MassegeResponseJSON>  register(@Body RegisterPack registerPack);
}
