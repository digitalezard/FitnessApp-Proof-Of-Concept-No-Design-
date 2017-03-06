package com.supinfo.fitnessapplication.Models;

import java.util.Date;

/**
 * Created by digitalezard on 20/10/2016.
 */

public class Locate {
    private int id;
    private double Lattitude;
    private double Longitude;
    private Date date;
    private int parcour_id;

    public void setId(int id){ this.id = id; }
    public int getId(){ return this.id; }

    public void setLattitude(double lattitude){this.Lattitude = lattitude;}
    public double getLattitude(){ return this.Lattitude;}

    public void setLongitude(double longitude){this.Longitude = longitude;}
    public double getLongitude(){return this.Longitude;}

    public void setDate(Date d){this.date = d;}
    public Date getDate(){return this.date;}

    public void setParcourId(int parcourId){this.parcour_id = parcourId;}
    public int getParcourId(){return this.parcour_id;}
}
