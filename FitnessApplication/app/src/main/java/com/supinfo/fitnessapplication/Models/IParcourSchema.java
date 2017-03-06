package com.supinfo.fitnessapplication.Models;

/**
 * Created by digitalezard on 21/10/2016.
 */

public interface IParcourSchema {
    String TABLE_PARCOUR = "parcour";
    String COLUMN_ID = "_id";
    String COLUMN_NAME = "name";
    String COLUMN_SPEED = "speed";
    String COLUMN_USERID = "user";

    String[] allColumns = new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_SPEED, COLUMN_USERID};

    String TABLE_PARCOUR_CREATE = "create table "
            + TABLE_PARCOUR
            + "("
            + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME
            + " text not null, "
            + COLUMN_SPEED
            + " real, "
            + IScoreSchema.COLUMN_USERID
            + " integer, "
            + "FOREIGN KEY ("+IScoreSchema.COLUMN_USERID+") REFERENCES "
            +IUserSchema.TABLE_USERS+"("+COLUMN_ID+"));";
}
