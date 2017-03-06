package com.supinfo.fitnessapplication.Models;

/**
 * Created by digitalezard on 21/10/2016.
 */

public interface IScoreSchema {

    String TABLE_SCORE = "score";
    String COLUMN_ID = "_id";
    String COLUMN_USERID = "user";
    String COLUMN_SCORE_TYPE = "score_type";
    String COLUMN_SCORE_VALUE = "score_value";
    String COLUMN_PERFORM_AT = "perform_at";

    String[] allColumns = new String[] { COLUMN_ID, COLUMN_USERID, COLUMN_SCORE_TYPE, COLUMN_SCORE_VALUE, COLUMN_PERFORM_AT};

    String TABLE_SCORE_CREATE ="create table " + TABLE_SCORE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_SCORE_TYPE + " text not null, "
            + COLUMN_SCORE_VALUE + " integer, " + COLUMN_PERFORM_AT + " date, " + COLUMN_USERID + " integer, "+
            "FOREIGN KEY ("+COLUMN_USERID+") REFERENCES "+IUserSchema.TABLE_USERS+"("+COLUMN_ID+"));";
}
