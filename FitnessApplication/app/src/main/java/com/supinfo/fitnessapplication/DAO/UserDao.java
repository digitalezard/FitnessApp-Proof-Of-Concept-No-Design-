package com.supinfo.fitnessapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;

import com.supinfo.fitnessapplication.BDD.DbDataProvider;
import com.supinfo.fitnessapplication.Models.IUserSchema;
import com.supinfo.fitnessapplication.Models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalezard on 22/10/2016.
 */

public class UserDao extends DbDataProvider implements IUserSchema, IUserDao {

    private Cursor cursor;
    private ContentValues values;


    public UserDao(Context context) {
        super(context);
    }

    @Override
    public User createUser(User user) {
        setContentValue(user);
        try {
            long Id = super.create(TABLE_USERS, getContentValue());

            if(Id > 0){
                String[] insertId = new String[]{String.valueOf(Id)};
                cursor = super.retrieve(TABLE_USERS, allColumns, COLUMN_ID + " = ?", insertId, COLUMN_ID);
                cursor.moveToFirst();
                User newUser = cursorToEntity(cursor);
                cursor.close();
                return newUser;
            }
        } catch (SQLiteConstraintException ex){
            return null;
        }
        return null;
    }

    @Override
    public Object userAuth(String userName, String userPwd){
        Cursor c = super.rawQuery("select * from "+TABLE_USERS+" where "+COLUMN_USER_NAME+" = ? AND "+ COLUMN_USER_PWD +" = ?", new String[]{userName, userPwd} );

        User user = new User();
        try {

            if (c.getCount() > 0) {

                c.moveToFirst();
                user = cursorToEntity(c);
                c.close();
            } else {
                user = null;
            }
        }finally {
            c.close();
        }
        return user;
    }

    @Override
    public User getUser(int id) {
        User user = new User();
        String thatId[] =new String[]{String.valueOf(id)};
        cursor = super.retrieve(TABLE_USERS, allColumns, COLUMN_ID + " = ?", thatId, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            user = cursorToEntity(cursor);
        }
        cursor.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        cursor = super.retrieve(TABLE_USERS, allColumns, null, null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToEntity(cursor);
                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return users;
    }

    @Override
    public int updateUser(User user) {
        String strFilter;
        int res = 0;
        if(user.getId() > 0){
            strFilter = "_id=" + user.getId();
            setContentValue(user);
            res = super.update(TABLE_USERS, getContentValue(), strFilter, null);
        }
        return res;
    }

    @Override
    public int deleteUser(User user) {
        long id = user.getId();
        int res = super.delete(TABLE_USERS, COLUMN_ID + " = " + id, null);
        return res;
    }

    private void setContentValue(User user){
        values = new ContentValues();

        if(user.getUser_name() != null) {
            values.put(COLUMN_USER_NAME, user.getUser_name());
        }
        if(user.getUser_password() != null) {
            values.put(COLUMN_USER_PWD, user.getUser_password());
        }
        if (user.getUser_gender() != null) {
            values.put(COLUMN_USER_GENDER, user.getUser_gender());
        }

        if(user.getUser_mail() != null){
            values.put(COLUMN_USER_MAIL, user.getUser_mail());
        }

            values.put(COLUMN_USER_AGE, user.getUser_age());
            values.put(COLUMN_USER_HEIGHT, user.getUser_height());
            values.put(COLUMN_USER_WEIGHT, user.getUser_weight());

            values.put(COLUMN_TARGET_WEIGHT, user.getTarget_weight());
            values.put(COLUMN_TARGET_STEP, user.getTarget_step());
            values.put(COLUMN_TARGET_MILEAGE, user.getTarget_mileage());
            values.put(COLUMN_TARGET_STEPONRUN, user.getTarget_steponrun());
    }

    private ContentValues getContentValue() {
        return values;
    }

    @Override
    protected User cursorToEntity(Cursor cursor) {
        User user = new User();
        int idIndex, uNIndex, pwdIndex, gndrIndex, ageIndex, heightIndex, weightIndex, mailIndex, tWeightIndex, tStepIndex, tMileageIndex, tStepOnRunIndex;

        if(cursor != null && !cursor.isAfterLast()) {
            if(cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.setId(cursor.getInt(idIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_NAME) != -1) {
                uNIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_NAME);
                user.setUser_name(cursor.getString(uNIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_PWD) != -1) {
                pwdIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_PWD);
                user.setUser_password(cursor.getString(pwdIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_GENDER) != -1) {
                gndrIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_GENDER);
                user.setUser_gender(cursor.getString(gndrIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_AGE) != -1) {
                ageIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_AGE);
                user.setUser_age(cursor.getInt(ageIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_HEIGHT) != -1) {
                heightIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_HEIGHT);
                user.setUser_height(cursor.getFloat(heightIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_WEIGHT) != -1) {
                weightIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_WEIGHT);
                user.setUser_weight(cursor.getFloat(weightIndex));
            }
            if(cursor.getColumnIndex(COLUMN_USER_MAIL) != -1) {
                mailIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_MAIL);
                user.setUser_mail(cursor.getString(mailIndex));
            }
            if(cursor.getColumnIndex(COLUMN_TARGET_WEIGHT) != -1) {
                tWeightIndex = cursor.getColumnIndexOrThrow(COLUMN_TARGET_WEIGHT);
                user.setTarget_weight(cursor.getInt(tWeightIndex));
            }
            if(cursor.getColumnIndex(COLUMN_TARGET_STEP) != -1) {
                tStepIndex = cursor.getColumnIndexOrThrow(COLUMN_TARGET_STEP);
                user.setTarget_step(cursor.getInt(tStepIndex));
            }
            if(cursor.getColumnIndex(COLUMN_TARGET_MILEAGE) != -1) {
                tMileageIndex = cursor.getColumnIndexOrThrow(COLUMN_TARGET_MILEAGE);
                user.setTarget_mileage(cursor.getInt(tMileageIndex));
            }
            if(cursor.getColumnIndex(COLUMN_TARGET_MILEAGE) != -1) {
                tStepOnRunIndex = cursor.getColumnIndexOrThrow(COLUMN_TARGET_MILEAGE);
                user.setTarget_steponrun(cursor.getInt(tStepOnRunIndex));
            }
        }
        return user;
    }
}
