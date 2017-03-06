package com.supinfo.fitnessapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.supinfo.fitnessapplication.BDD.DbDataProvider;
import com.supinfo.fitnessapplication.Models.ILocateSchema;
import com.supinfo.fitnessapplication.Models.Locate;
import com.supinfo.fitnessapplication.Models.Parcour;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by digitalezard on 19/11/2016.
 */

public class LocateDao extends DbDataProvider implements ILocateSchema, ILocateDao {

    private ContentValues values;
    private Cursor cursor;

    public LocateDao(Context context) { super(context); }

    @Override
    public Locate createLocate(Locate locate) throws ParseException {
        setContentValue(locate);
        try {
            long Id = super.create(TABLE_LOCATE, getContentValue());

            if(Id > 0){
                String[] insertId = new String[]{String.valueOf(Id)};
                cursor = super.retrieve(TABLE_LOCATE, allColumns, COLUMN_ID + " = ?", insertId, COLUMN_ID);
                cursor.moveToFirst();
                Locate newLocate = cursorToEntity(cursor);
                cursor.close();
                return newLocate;
            }
        } catch (SQLiteConstraintException ex){
            return null;
        }
        return null;
    }

    @Override
    public List<Locate> getAllLocatesById(int ParcourId) throws ParseException {
        List<Locate> Locates = new ArrayList<Locate>();
        String thatId[] =new String[]{String.valueOf(ParcourId)};
        cursor = super.retrieve(TABLE_LOCATE, allColumns, COLUMN_PARCOURID + " = ?", thatId, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Locate locate = cursorToEntity(cursor);
                Locates.add(locate);
                cursor.moveToNext();
            }
            cursor.close();
            return Locates;
        }
        return null;
    }

    @Override
    public List<Locate> getAllLocates() throws ParseException {
        List<Locate> Locates = new ArrayList<Locate>();
        cursor = super.retrieve(TABLE_LOCATE, allColumns, null, null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Locate locate = cursorToEntity(cursor);
                Locates.add(locate);
                cursor.moveToNext();
            }
            cursor.close();
            return Locates;
        }
        return null;
    }

    @Override
    protected Locate cursorToEntity(Cursor cursor) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        int idIndex, latIndex, longIndex, parcIdIndex, dateIndex;

        if(cursor != null) {
            Locate locate = new Locate();

            if(cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                locate.setId(cursor.getInt(idIndex));
            }
            if(cursor.getColumnIndex(COLUMN_LATITUDE) != -1){
                latIndex = cursor.getColumnIndexOrThrow(COLUMN_LATITUDE);
                locate.setLattitude(cursor.getDouble(latIndex));
            }
            if(cursor.getColumnIndex(COLUMN_LONGITUDE) != -1){
                longIndex = cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE);
                locate.setLongitude(cursor.getDouble(longIndex));
            }
            if(cursor.getColumnIndex(COLUMN_PARCOURID) != -1){
                parcIdIndex = cursor.getColumnIndexOrThrow(COLUMN_PARCOURID);
                locate.setParcourId(cursor.getInt(parcIdIndex));
            }
            if(cursor.getColumnIndex(COLUMN_DATE) != -1){
                dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE);
                locate.setDate(dateFormat.parse(cursor.getString(dateIndex)));
            }
            return locate;
        }
        return null;
    }

    private void setContentValue(Locate locate){
        values = new ContentValues();

        if(locate.getLattitude() != 0) {
            values.put(COLUMN_LATITUDE, locate.getLattitude());
        }
        if(locate.getLongitude() != 0) {
            values.put(COLUMN_LONGITUDE,locate.getLongitude());
        }
        if (locate.getParcourId() != 0) {
            values.put(COLUMN_PARCOURID, locate.getParcourId());
        }
        if(locate.getDate() != null) {
            values.put(COLUMN_DATE, String.valueOf(locate.getDate()));
        }
    }

    private ContentValues getContentValue() {
        return values;
    }
}
