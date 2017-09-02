package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.GetPackJSONModel;
import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hieuit on 8/25/17.
 */

public interface GetPackType {
    @GET("get-pack-add/{type}")
    Call<List<GetPackJSONModel>> getPacks(@Path("type") String id);
}
