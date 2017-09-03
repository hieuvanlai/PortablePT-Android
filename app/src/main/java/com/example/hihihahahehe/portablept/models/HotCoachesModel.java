package com.example.hihihahahehe.portablept.models;

/**
 * Created by valky on 8/17/2017.
 */

public class HotCoachesModel {
    private String id;
    private String name;

    public HotCoachesModel() {
    }
    private String avata;

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public HotCoachesModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
