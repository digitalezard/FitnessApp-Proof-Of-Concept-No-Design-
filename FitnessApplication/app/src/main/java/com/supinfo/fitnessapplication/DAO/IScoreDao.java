package com.supinfo.fitnessapplication.DAO;

import com.supinfo.fitnessapplication.Models.Score;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by digitalezard on 22/10/2016.
 */

public interface IScoreDao {
    public Score createScore(Score score) throws ParseException;
    public int deleteScore(Score score);
    public List<Score> getAllScores() throws ParseException;
    //public List<Score> getAllSpecificItem(String scoreType) throws ParseException;
    public List<Score> getAllSpecificScoreByUserId(int id, String ScoreType) throws ParseException;
    public Score getLastScoreByUserId(int id, String scoreType) throws ParseException;
}
