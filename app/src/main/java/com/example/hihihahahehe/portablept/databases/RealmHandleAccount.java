package com.example.hihihahahehe.portablept.databases;

import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;

import io.realm.Realm;

/**
 * Created by hieuit on 9/1/17.
 */

public class RealmHandleAccount {
    public static Realm realm = Realm.getDefaultInstance();
    public static void addAccount(LoginAndRegisterResponseJSON accountModel){
        //thread-safe
        realm.beginTransaction();
        realm.copyToRealm(accountModel);
        realm.commitTransaction();
    }
    public static LoginAndRegisterResponseJSON getAccount(){
        return realm.where(LoginAndRegisterResponseJSON.class).findFirst();
    }
    public static void deleteAccount(){
        realm.beginTransaction();
        realm.where(LoginAndRegisterResponseJSON.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();

    }
    public static void UpdateAccount(String role){
        realm.beginTransaction();
        realm.where(LoginAndRegisterResponseJSON.class).findFirst().getData().setRole(role);
        realm.commitTransaction();
    }

}
