package com.supinfo.fitnessapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.supinfo.fitnessapplication.BDD.DbDataProvider;
import com.supinfo.fitnessapplication.Models.IParcourSchema;
import com.supinfo.fitnessapplication.Models.Parcour;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalezard on 19/11/2016.
 */

public class ParcourDao extends DbDataProvider implements IParcourSchema, IParcourDao {

    private ContentValues values;
    private Cursor cursor;

    public ParcourDao(Context context) { super(context); }

    @Override
    public Parcour createParcour(Parcour parcour) {
        setContentValue(parcour);

        try {
            long Id = super.create(TABLE_PARCOUR, getContentValue());
            if(Id > 0){
                String[] insertId = new String[]{String.valueOf(Id)};
                cursor = super.retrieve(TABLE_PARCOUR, allColumns, COLUMN_ID + " = ?", insertId, COLUMN_ID);
                cursor.moveToFirst();
                Parcour newParcour = cursorToEntity(cursor);
                cursor.close();
                return newParcour;
            }
        }catch (SQLiteConstraintException ex){
            return null;
        }
        return null;
    }

    @Override
    public Parcour getParcour(int id) {
        Parcour parcour = new Parcour();
        String[] thatId = new String[]{String.valueOf(id)};
        cursor = super.retrieve(TABLE_PARCOUR, allColumns, COLUMN_ID + " = ?", thatId, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            parcour = cursorToEntity(cursor);
        }
        cursor.close();
        return parcour;
    }

    @Override
    public List<Parcour> getAllParcours() {
        List<Parcour> parcours = new ArrayList<Parcour>();
        cursor = super.retrieve(TABLE_PARCOUR, allColumns, null, null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Parcour parcour = cursorToEntity(cursor);
                parcours.add(parcour);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return parcours;
    }

    @Override
    public List<Parcour> getParcoursByUserId(int id){
        List<Parcour> parcours;

        Cursor c = super.rawQuery("select * from "+TABLE_PARCOUR+
                " where "+COLUMN_USERID+" = ? ", new String[]{String.valueOf(id) } );

        super.retrieve(TABLE_PARCOUR, allColumns, COLUMN_USERID + " = ?", new String[]{String.valueOf(id) }, COLUMN_ID);

        if(c.getCount() > 0){
            parcours = new ArrayList<Parcour>();
            c.moveToFirst();
            try {
                while (!c.isAfterLast()) {
                    parcours.add(cursorToEntity(c));
                    c.moveToNext();
                }
            }finally {
                c.close();
            }
        }else{
            parcours = null;
        }
        return parcours;
    }

    @Override
    public int deleteParcour(Parcour parcour) {
        long id = parcour.getId();
        int res = super.delete(TABLE_PARCOUR, COLUMN_ID + " = " + id, null);
        return res;
    }

    @Override
    protected Parcour cursorToEntity(Cursor cursor) {
        Parcour parcour = new Parcour();

        int idIndex, userIdIndex, nameIndex, speedIndex;
        if(cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                parcour.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_USERID) != -1){
                userIdIndex = cursor.getColumnIndexOrThrow(COLUMN_USERID);
                parcour.setUserId(cursor.getInt(userIdIndex));
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1){
                nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
                parcour.setName(cursor.getString(nameIndex));
            }
            if (cursor.getColumnIndex(COLUMN_SPEED) != -1){
                speedIndex = cursor.getColumnIndexOrThrow(COLUMN_SPEED);
                parcour.setSpeed(cursor.getInt(speedIndex));
            }
            return parcour;
        }
        return null;
    }

    private void setContentValue(Parcour parcour){
        values = new ContentValues();

        if(parcour.getUserId() != 0) {
            values.put(COLUMN_USERID, parcour.getUserId());
        }
        if(parcour.getName() != null) {
            values.put(COLUMN_NAME, parcour.getName());
        }
        if (parcour.getSpeed() != 0) {
            values.put(COLUMN_SPEED, parcour.getSpeed());
        }
    }

    private ContentValues getContentValue() {
        return values;
    }
}
