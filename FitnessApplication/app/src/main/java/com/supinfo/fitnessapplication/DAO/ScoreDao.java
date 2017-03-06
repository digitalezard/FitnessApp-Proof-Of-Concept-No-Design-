package com.supinfo.fitnessapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;


import com.supinfo.fitnessapplication.BDD.DbDataProvider;
import com.supinfo.fitnessapplication.Models.IScoreSchema;
import com.supinfo.fitnessapplication.Models.Score;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by digitalezard on 16/11/2016.
 */

public class ScoreDao extends DbDataProvider implements IScoreSchema, IScoreDao {

    UserDao userDao;
    private ContentValues values;
    private Cursor cursor;

    public ScoreDao(Context context) {
        super(context);
    }

    @Override
    public Score createScore(Score score) throws ParseException {
        setContentValue(score);
        try {
            long Id = super.create(TABLE_SCORE, getContentValue());

            if(Id > 0){
                String[] insertId = new String[]{String.valueOf(Id)};
                cursor = super.retrieve(TABLE_SCORE, allColumns, COLUMN_ID + " = ?", insertId, COLUMN_ID);
                cursor.moveToFirst();
                Score newScore = cursorToEntity(cursor);
                cursor.close();
                return newScore;
            }
        }catch (SQLiteConstraintException ex){
            return null;
        }
        return null;
    }

    @Override
    public List<Score> getAllSpecificScoreByUserId(int id, String ScoreType) throws ParseException{ //getAllSpecifiqueScoreByUserId
        List<Score> scores;

        Cursor c = super.rawQuery("select * from "+TABLE_SCORE+" where "+COLUMN_USERID +" = ? AND "+COLUMN_SCORE_TYPE+" = ?", new String[]{String.valueOf(id), ScoreType });
        c.moveToFirst();

        if(c.getCount() > 0){
            scores = new ArrayList<Score>();
            try {
                while (!c.isAfterLast()) {
                    scores.add(cursorToEntity(c));
                    c.moveToNext();
                }
            }finally {
                c.close();
            }
        }else{
            scores = null;
        }
        return scores;
    }

    @Override
    public List<Score> getAllScores() throws ParseException {
        List<Score> scores = new ArrayList<Score>();

        cursor = super.retrieve(TABLE_SCORE,
                allColumns, null, null, COLUMN_ID);

        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                scores.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return scores;

    }

    @Override
    public Score getLastScoreByUserId(int id, String scoreType) throws ParseException {
        Score score = new Score();

        cursor = super.retrieve(TABLE_SCORE,
                allColumns,COLUMN_SCORE_TYPE + " = ? AND "+COLUMN_USERID+" = ?", new String[]{scoreType, String.valueOf(id)}, null);

        cursor.moveToLast();
        score = cursorToEntity(cursor);
        cursor.close();
        return score;
    }

    @Override
    public int deleteScore(Score score) {
        long id = score.getId();
        int res = super.delete(TABLE_SCORE, COLUMN_ID
                + " = " + id, null);
        return res;
    }

    @Override
    protected Score cursorToEntity(Cursor cursor) throws ParseException{
        Score score = new Score();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        int idIndex, userIdIndex, scTypIndex, scValIndex, perfDateIndex;
        if(cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int id = cursor.getInt(idIndex);
                score.setId(cursor.getInt(idIndex));
            }
            if (cursor.getColumnIndex(COLUMN_USERID) != -1){
                userIdIndex = cursor.getColumnIndexOrThrow(COLUMN_USERID);
                score.setUser(cursor.getInt(userIdIndex));
            }
            if (cursor.getColumnIndex(COLUMN_SCORE_TYPE) != -1){
                scTypIndex = cursor.getColumnIndexOrThrow(COLUMN_SCORE_TYPE);
                score.setScore_type(cursor.getString(scTypIndex));
            }
            if (cursor.getColumnIndex(COLUMN_SCORE_VALUE) != -1){
                scValIndex = cursor.getColumnIndexOrThrow(COLUMN_SCORE_VALUE);
                score.setScore_value(cursor.getInt(scValIndex));
            }
            if (cursor.getColumnIndex(COLUMN_PERFORM_AT) != -1){
                perfDateIndex = cursor.getColumnIndexOrThrow(COLUMN_PERFORM_AT);
                score.setPerform_at(dateFormat.parse(cursor.getString(perfDateIndex)));
            }
        }
        return score;
    }

    private void setContentValue(Score score){
        values = new ContentValues();

        if(score.getUser() != 0) {
            values.put(COLUMN_USERID, score.getUser());
        }
        if(score.getScore_type() != null) {
            values.put(COLUMN_SCORE_TYPE, score.getScore_type());
        }
        if (score.getScore_value() != 0) {
            values.put(COLUMN_SCORE_VALUE, score.getScore_value());
        }
        if(score.getPerform_at() != null){
            values.put(COLUMN_PERFORM_AT, String.valueOf(score.getPerform_at()));
        }
    }
    private ContentValues getContentValue() {
        return values;
    }
}
