package com.example.hihihahahehe.portablept.events;

import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;

/**
 * Created by SMC on 09/26/2017.
 */

public class OnclickProfile
{
    private DataLoginJSON dataUser;

    public DataLoginJSON getDataUser() {
        return dataUser;
    }

    public void setDataUser(DataLoginJSON dataUser) {
        this.dataUser = dataUser;
    }
}
