package com.example.hihihahahehe.portablept.models.JSONModel;

import io.realm.RealmObject;

/**
 * Created by hieuit on 8/26/17.
 */

public class LoginAndRegisterResponseJSON extends RealmObject {
    private int success;
    private String message;
    private DataLoginJSON data;
    private String token;

    public void setData(DataLoginJSON data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataLoginJSON getData() {
        return data;
    }
}
