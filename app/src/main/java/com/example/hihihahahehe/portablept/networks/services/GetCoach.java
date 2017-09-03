package com.example.hihihahahehe.portablept.networks.services;

import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hieuit on 9/3/17.
 */

public interface GetCoach {
    @GET("get-hlv")
    Call <List<DataLoginJSON>> getCoach();
}
