package com.supinfo.fitnessapplication.Models;

/**
 * Created by digitalezard on 20/10/2016.
 */

public class User {
    private int id;
    private String user_name;
    private String user_password;
    private String user_gender;
    private int user_age;
    private float user_height;
    private float user_weight;
    private String user_mail;

    private int target_weight;
    private int target_step;
    private int target_mileage;
    private int target_steponrun;

    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setUser_name(String uN) { this.user_name = uN; }
    public String getUser_name() { return this.user_name; }

    public void setUser_password(String uP) { this.user_password = uP; }
    public String getUser_password() { return this.user_password; }

    public void setUser_gender(String uG) { this.user_gender = uG; }
    public String getUser_gender() { return this.user_gender; }

    public void setUser_age(int age) {this.user_age = age;}
    public int getUser_age() {return this.user_age;}

    public void setUser_height(float height){this.user_height = height;}
    public float getUser_height(){return this.user_height;}

    public void setUser_weight(float weight){this.user_weight = weight;}
    public float getUser_weight(){return this.user_weight;}

    public void setUser_mail(String uM) {this.user_mail = uM;}
    public String getUser_mail() { return this.user_mail; }

    public void setTarget_weight(int TW){this.target_weight = TW;}
    public int getTarget_weight(){return this.target_weight;}

    public void setTarget_step(int TS){this.target_step = TS;}
    public int getTarget_step(){return this.target_step;}

    public void setTarget_mileage(int TM){this.target_mileage = TM;}
    public int getTarget_mileage(){return this.target_mileage;}

    public void setTarget_steponrun(int TM){this.target_steponrun = TM;}
    public int getTarget_steponrun(){return this.target_steponrun;}

    @Override
    public String toString() {
        String UserInfo = "Name :" + user_name + " PWD :"+ user_password + "\n Gender :"+ user_gender
                + " Mail :"+ user_mail;
        return UserInfo;
    }
}
