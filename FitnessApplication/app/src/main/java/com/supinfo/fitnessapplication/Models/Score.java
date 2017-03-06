package com.supinfo.fitnessapplication.Models;

import java.util.Date;

/**
 * Created by digitalezard on 20/10/2016.
 */

public class Score {

    private int id;
    private int user_id;
    private String score_type;
    private int score_value;
    private Date perform_at;


    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setUser(int userid){this.user_id = userid;}
    public int getUser(){return this.user_id;}

    public void setScore_type(String ST){this.score_type = ST;}
    public String getScore_type(){return this.score_type;}

    public void setScore_value(int SV){this.score_value = SV;}
    public int getScore_value(){return this.score_value;}

    public void setPerform_at(Date PA){this.perform_at = PA;}
    public Date getPerform_at(){return this.perform_at;}

    @Override
    public String toString() {
        String ScoreInfo = "Score Type :" + score_type + " Step :" + score_value + "\n Perform at :" + perform_at ;
        return ScoreInfo;
    }
}
