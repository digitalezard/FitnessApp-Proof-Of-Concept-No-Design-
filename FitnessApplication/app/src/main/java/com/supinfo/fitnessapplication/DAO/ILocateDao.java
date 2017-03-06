package com.supinfo.fitnessapplication.DAO;

import com.supinfo.fitnessapplication.Models.Locate;

import java.text.ParseException;
import java.util.List;

/**
 * Created by digitalezard on 22/10/2016.
 */

public interface ILocateDao {
    public List<Locate> getAllLocatesById(int ParcourId) throws ParseException;
    public List<Locate> getAllLocates() throws ParseException;
    public Locate createLocate(Locate locate) throws ParseException;

}
