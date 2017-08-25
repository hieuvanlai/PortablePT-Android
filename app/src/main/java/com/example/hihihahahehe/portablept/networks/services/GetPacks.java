package com.example.hihihahahehe.portablept.networks.services;
import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by hihihahahehe on 8/18/17.
 */

public interface GetPacks {
    @GET("get-pack-add/Zumba")
    Call<List<PackJSONModel>> getPacks ();
}
