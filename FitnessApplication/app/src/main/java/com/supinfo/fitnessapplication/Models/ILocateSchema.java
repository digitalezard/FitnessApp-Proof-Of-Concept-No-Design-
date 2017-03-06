package com.supinfo.fitnessapplication.Models;

/**
 * Created by digitalezard on 21/10/2016.
 */

public interface ILocateSchema {
    String TABLE_LOCATE = "locate";
    String COLUMN_ID = "_id";
    String COLUMN_LATITUDE = "latitude";
    String COLUMN_LONGITUDE = "longitude";
    String COLUMN_PARCOURID = "parcour";
    String COLUMN_DATE = "date";

    String[] allColumns = new String[] {COLUMN_ID, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_PARCOURID, COLUMN_DATE};

    String TABLE_LOCATE_CREATE = "create table "
            + TABLE_LOCATE
            + "("
            + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LATITUDE
            + " double, "
            + COLUMN_LONGITUDE
            + " double, "
            + COLUMN_DATE
            + " date, "
            + COLUMN_PARCOURID
            + " integer, "
            + "FOREIGN KEY ("+COLUMN_PARCOURID+") REFERENCES "
            +IParcourSchema.TABLE_PARCOUR+"("+COLUMN_ID+"));";
}
