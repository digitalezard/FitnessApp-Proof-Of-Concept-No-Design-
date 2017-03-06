package com.supinfo.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.supinfo.fitnessapplication.DAO.UserDao;
import com.supinfo.fitnessapplication.Models.User;

public class WelcomeActivity extends AppCompatActivity {

    private int id;
    private String EXTRA_ID = "userId";
    private String EXTRA_LOGIN = "userLogin";
    private String Name;
    private UserDao uDataSource;
    private Intent myIntent;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        uDataSource = new UserDao(this);
        uDataSource.open();

        Intent intent = getIntent();
        if (intent != null) {

            id = intent.getIntExtra(EXTRA_ID, 0);
            myUser = uDataSource.getUser(id);

            Name = myUser.getUser_name();

            TextView DisplayName = (TextView) findViewById(R.id.UNameDisplay);
            DisplayName.setText(Name);

            TextView DisplayAge = (TextView) findViewById(R.id.UAgeDisplay);
            DisplayAge.setText(String.valueOf(myUser.getUser_age()));

            TextView DisplayGender = (TextView) findViewById(R.id.UGenderDisplay);
            DisplayGender.setText(myUser.getUser_gender());

            TextView DisplayHeight = (TextView) findViewById(R.id.UHeightDisplay);
            DisplayHeight.setText(String.valueOf(myUser.getUser_height()));

            TextView DisplayWeight = (TextView) findViewById(R.id.UWeightDisplay);
            DisplayWeight.setText(String.valueOf(myUser.getUser_weight()));

            Button WeightCurveButton = (Button) findViewById(R.id.ButtonWeightCurve);
            WeightCurveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myIntent = new Intent(WelcomeActivity.this, WeightCurveActivity.class);
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }
            });

            Button WeightButton = (Button) findViewById(R.id.ButtonWeight);
            WeightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myIntent = new Intent(WelcomeActivity.this, WeightActivity.class);
                    myIntent.putExtra(EXTRA_LOGIN, myUser.getUser_name());
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }
            });

            Button FootRaceButton = (Button) findViewById(R.id.FootRace);
            FootRaceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myIntent = new Intent(WelcomeActivity.this, UMapActivity.class);
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }
            });

            Button ProfilButton = (Button) findViewById(R.id.ButtonProfil);
            ProfilButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myIntent = new Intent(WelcomeActivity.this, ProfilActivity.class);
                    myIntent.putExtra(EXTRA_LOGIN, myUser.getUser_name());
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }
            });

        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        uDataSource.close();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!uDataSource.myDb.isOpen()) {
            uDataSource.open();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(uDataSource.myDb.isOpen()) {
            uDataSource.close();
        }
    }
}
