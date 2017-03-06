package com.supinfo.fitnessapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.supinfo.fitnessapplication.CustomAdapter.WeightListAdapter;
import com.supinfo.fitnessapplication.DAO.ScoreDao;
import com.supinfo.fitnessapplication.DAO.UserDao;
import com.supinfo.fitnessapplication.Models.Score;
import com.supinfo.fitnessapplication.Models.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeightActivity extends AppCompatActivity {

    private Context context;
    private UserDao uDataSource;
    private ScoreDao sDataSource;

    private String EXTRA_ID = "userId";
    private int id;
    private User myUser;

    private AlertDialog alertDialog;
    private NumberPicker np;

    private WeightListAdapter adapter;
    private List<Score> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        uDataSource = new UserDao(this);
        uDataSource.open();

        sDataSource = new ScoreDao(this);
        sDataSource.open();

        np = new NumberPicker(WeightActivity.this);
        np.setMinValue(1);
        np.setMaxValue(250);
        np.setValue(50);

        context = this;
        Intent intent = getIntent();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("WEIGHT");
        alertDialog.setView(np);

        alertDialog.setButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    long time = System.currentTimeMillis();
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(time);
                    Date date = cal.getTime();

                    Score addScore = new Score();
                    addScore.setScore_type("WEIGHT");
                    addScore.setPerform_at(date);
                    addScore.setScore_value(np.getValue());
                    addScore.setUser(myUser.getId());
                    Score ThisScore = sDataSource.createScore(addScore);

                    adapter.add(ThisScore);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        if (intent != null) {
            id = intent.getIntExtra(EXTRA_ID, 0);
            myUser = uDataSource.getUser(id);
        }

        try {
            if(sDataSource.getAllSpecificScoreByUserId(id, "WEIGHT") != null) {
                values = sDataSource.getAllSpecificScoreByUserId(id, "WEIGHT");//++Gestion des liste vide
            }else{
                values = new ArrayList<>();
                long time = System.currentTimeMillis();
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(time);
                Date date = cal.getTime();

                Score defaultScore = new Score();
                defaultScore.setScore_type("WEIGHT");
                defaultScore.setPerform_at(date);
                defaultScore.setScore_value(0);
                defaultScore.setUser(id);

                values.add(defaultScore);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setupListViewAdapter(values);

        Button AddWeight = (Button) findViewById(R.id.AddWeightButton);
        AddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        Button ReturnButton = (Button) findViewById(R.id.Return);
        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WeightActivity.this, WelcomeActivity.class);
                myIntent.putExtra(EXTRA_ID, id);
                uDataSource.close();
                sDataSource.close();
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        uDataSource.close();
        sDataSource.close();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!uDataSource.myDb.isOpen()) {
            uDataSource.open();
        }

        if(!sDataSource.myDb.isOpen()) {
            sDataSource.open();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(uDataSource.myDb.isOpen()) {
            uDataSource.close();
        }

        if(sDataSource.myDb.isOpen()) {
            sDataSource.close();
        }
    }

    private void setupListViewAdapter(List<Score> scores){
        adapter = new WeightListAdapter(WeightActivity.this, R.layout.weight_list_item, scores);
        ListView WeightListView = (ListView) findViewById(R.id.listWeight);
        WeightListView.setAdapter(adapter);
    }

    public void removeElementOnClick(View v) {
        Score itemToRemove = (Score)v.getTag();
        sDataSource.deleteScore(itemToRemove);
        adapter.remove(itemToRemove);
    }
}
