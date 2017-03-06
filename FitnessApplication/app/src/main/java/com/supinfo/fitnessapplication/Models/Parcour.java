package com.supinfo.fitnessapplication.Models;

/**
 * Created by digitalezard on 20/10/2016.
 */

public class Parcour {
    private int id;
    private String name;
    private float Speed;
    private int user_id;

    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setUserId(int userid){this.user_id = userid;}
    public int getUserId(){return this.user_id;}

    public void setName(String Name){this.name = Name;}
    public String getName(){return this.name;}

    public void setSpeed(float speed){this.Speed = speed;}
    public float getSpeed(){return this.Speed;}

    public String toString() {
        //String mySpeed = String.valueOf(Speed);
        String ParcourInfo = "ParcourName :" + name;
        return ParcourInfo;
    }
}
