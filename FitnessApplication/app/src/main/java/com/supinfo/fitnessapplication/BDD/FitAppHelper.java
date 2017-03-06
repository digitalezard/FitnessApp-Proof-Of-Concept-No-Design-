package com.supinfo.fitnessapplication.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.supinfo.fitnessapplication.Models.*;

/**
 * Created by digitalezard on 18/10/2016.
 */
public class FitAppHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FitAppDb";


    public FitAppHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IUserSchema.TABLE_USER_CREATE);
        db.execSQL(IScoreSchema.TABLE_SCORE_CREATE);
        db.execSQL(IParcourSchema.TABLE_PARCOUR_CREATE);
        db.execSQL(ILocateSchema.TABLE_LOCATE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FitAppHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + IUserSchema.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + IScoreSchema.TABLE_SCORE);
        db.execSQL("DROP TABLE IF EXISTS " + IParcourSchema.TABLE_PARCOUR);
        db.execSQL("DROP TABLE IF EXISTS " + ILocateSchema.TABLE_LOCATE);
        onCreate(db);
    }
}
