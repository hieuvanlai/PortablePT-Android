package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hieuit on 9/2/17.
 */

public interface UpdateUser {
    @POST("update-user")
    Call<LoginAndRegisterResponseJSON> update(@Body DataLoginJSON dataLoginJSON);
}
