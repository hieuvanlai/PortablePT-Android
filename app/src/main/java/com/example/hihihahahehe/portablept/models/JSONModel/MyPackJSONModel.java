package com.example.hihihahahehe.portablept.models.JSONModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hieuit on 9/5/17.
 */

public class MyPackJSONModel {
    @SerializedName("_id")
    private String id;
    private GetPackJSONModel pack;
    private String user;

    public String getUser() {
        return user;
    }

    public GetPackJSONModel getPack() {
        return pack;
    }
}
