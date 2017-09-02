package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.UserJSONModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hieuit on 8/26/17.
 */

public interface RegisterAccout {
    @POST("register")
    Call<LoginAndRegisterResponseJSON> creatAccout(@Body UserJSONModel userJSONModel);
}
