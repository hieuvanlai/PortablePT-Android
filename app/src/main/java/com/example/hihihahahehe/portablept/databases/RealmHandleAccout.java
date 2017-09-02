package com.example.hihihahahehe.portablept.databases;

import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;

import io.realm.Realm;

/**
 * Created by hieuit on 9/1/17.
 */

public class RealmHandleAccout {
    public static Realm realm = Realm.getDefaultInstance();
    public static void addAccout(LoginAndRegisterResponseJSON accoutModel){
        //thread-safe
        realm.beginTransaction();
        realm.copyToRealm(accoutModel);
        realm.commitTransaction();
    }
    public static LoginAndRegisterResponseJSON getAccout(){
        return realm.where(LoginAndRegisterResponseJSON.class).findFirst();
    }
    public static void deleteAccout(){
        realm.beginTransaction();
        realm.where(LoginAndRegisterResponseJSON.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();

    }
    public static void  UpdateAccout(String role){
        realm.beginTransaction();
        realm.where(LoginAndRegisterResponseJSON.class).findFirst().getData().setRole(role);
        realm.commitTransaction();
    }

}
