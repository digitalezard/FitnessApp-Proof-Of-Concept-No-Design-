package com.supinfo.fitnessapplication.Models;

import com.supinfo.fitnessapplication.BDD.FitAppHelper;

/**
 * Created by digitalezard on 21/10/2016.
 */

public interface IUserSchema {
    String TABLE_USERS = "users";
    String COLUMN_ID = "_id";
    String COLUMN_USER_NAME = "user_name";
    String COLUMN_USER_PWD = "user_pwd";
    String COLUMN_USER_AGE = "user_age";
    String COLUMN_USER_GENDER = "user_gender";
    String COLUMN_USER_HEIGHT = "user_height";
    String COLUMN_USER_WEIGHT = "user_weight";
    String COLUMN_USER_MAIL = "user_mail";
    String COLUMN_TARGET_WEIGHT = "target_weight";
    String COLUMN_TARGET_STEP = "target_step";
    String COLUMN_TARGET_MILEAGE ="target_mileage";
    String COLUMN_TARGET_STEPONRUN = "target_steponrun";

    String[] allColumns = new String[] { COLUMN_ID,
           COLUMN_USER_NAME, COLUMN_USER_PWD,
            COLUMN_USER_GENDER,COLUMN_USER_AGE,
            COLUMN_USER_HEIGHT, COLUMN_USER_WEIGHT, COLUMN_USER_MAIL,
            COLUMN_TARGET_WEIGHT, COLUMN_TARGET_STEP, COLUMN_TARGET_MILEAGE, COLUMN_TARGET_STEPONRUN};

    String TABLE_USER_CREATE = "create table "
            + TABLE_USERS
            + "("
            + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_USER_NAME
            + " text not null, "
            + COLUMN_USER_PWD
            + " text not null, "
            + COLUMN_USER_GENDER
            + " text not null, "
            + COLUMN_USER_AGE
            + " integer, "
            + COLUMN_USER_HEIGHT
            + " real, "
            +COLUMN_USER_WEIGHT
            + " integer, "
            +COLUMN_USER_MAIL
            + " text not null, "
            + COLUMN_TARGET_WEIGHT
            + " integer, "
            + COLUMN_TARGET_STEP
            + " integer, "
            + COLUMN_TARGET_MILEAGE
            + " integer, "
            +COLUMN_TARGET_STEPONRUN
            + "  integer);";
}
