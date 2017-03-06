package com.supinfo.fitnessapplication.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;

/**
 * Created by digitalezard on 21/10/2016.
 */

public abstract class DbDataProvider {

    public SQLiteDatabase myDb;
    public FitAppHelper myHelper;
    protected abstract <T> T cursorToEntity(Cursor cursor) throws ParseException;

    public DbDataProvider(Context context) {
        this.myHelper = new FitAppHelper(context);
    }

    public void open() throws SQLException{
        myDb = myHelper.getWritableDatabase();
    }

    public void close(){
        myHelper.close();
    }

    public long create(String tblName, ContentValues values){
        return myDb.insert(tblName, null, values);
    }

    public Cursor retrieve(String tblName, String[] columns, String select, String[] sArgs, String order) {
        return myDb.query(tblName, columns, select, sArgs, null, null, order);
    }

    public Cursor retrieve(String tblName, String[] columns, String select, String[] sArgs, String order, String limit) {
        return myDb.query(tblName, columns, select, sArgs, null, null, order, limit);
    }

    public Cursor retrieve(String tblName, String[] columns, String select, String[] sArgs, String grpBy, String having, String order, String limit) {
        return myDb.query(tblName, columns, select, sArgs, grpBy, having, order, limit);
    }

    public int update(String tblName, ContentValues values, String select, String[] sArgs) {
        return myDb.update(tblName, values, select, sArgs);
    }

    public int delete(String tblName, String selection, String[] sArgs){
        return myDb.delete(tblName, selection, sArgs);
    }

    public Cursor rawQuery(String sql, String[] sArgs) {
        return myDb.rawQuery(sql, sArgs);
    }
}
