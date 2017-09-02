package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.RoleJSON;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hieuit on 9/1/17.
 */

public interface UpdateRole {
    @POST("update-role")
    Call<MassegeResponseJSON> updateRole(@Body RoleJSON roleJSON);
}
