package com.supinfo.fitnessapplication.DAO;

import android.database.Cursor;

import com.supinfo.fitnessapplication.Models.User;

import java.util.List;

/**
 * Created by digitalezard on 22/10/2016.
 */

public interface IUserDao {
    public User getUser(int id);
    public List<User> getAllUsers();
    public Object createUser(User user);
    public Object userAuth(String userName, String userPwd);

    public int updateUser(User user);
    public int deleteUser(User user);


}
