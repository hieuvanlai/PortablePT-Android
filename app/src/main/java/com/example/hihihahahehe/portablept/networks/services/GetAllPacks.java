package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by valky on 8/25/2017.
 */

public interface GetAllPacks {
    @GET("get-pack-all")
    Call<List<PackJSONModel>> getAllPacks();
}
