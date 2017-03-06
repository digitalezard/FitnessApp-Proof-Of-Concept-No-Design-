package com.supinfo.fitnessapplication.DAO;

import com.supinfo.fitnessapplication.Models.Parcour;

import java.util.List;

/**
 * Created by digitalezard on 22/10/2016.
 */

public interface IParcourDao {
    public Parcour getParcour(int id);
    public List<Parcour> getAllParcours();
    public Parcour createParcour(Parcour parcour);
    public List<Parcour> getParcoursByUserId(int id);

    public int deleteParcour(Parcour parcour);
}
