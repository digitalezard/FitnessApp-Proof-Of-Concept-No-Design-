package com.supinfo.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.supinfo.fitnessapplication.DAO.ScoreDao;
import com.supinfo.fitnessapplication.DAO.UserDao;
import com.supinfo.fitnessapplication.Models.Score;
import com.supinfo.fitnessapplication.Models.User;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private UserDao uDataSource;
    private ScoreDao sDataSource;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uDataSource = new UserDao(this);
        uDataSource.open();

        sDataSource = new ScoreDao(this);
        sDataSource.open();



        final EditText userName = (EditText) findViewById(R.id.NewUserNameArea);
        final EditText userPwd = (EditText) findViewById(R.id.NewUserPwdArea);
        final EditText userHeight = (EditText) findViewById(R.id.NewUserHeightArea);
        final EditText userWeight = (EditText) findViewById(R.id.FirstWeightArea);
        final EditText userMail = (EditText) findViewById(R.id.NewUserMailArea);
        final EditText userAge = (EditText) findViewById(R.id.NewUserAgeArea);
        final RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.RegisterradioGender);

        Button sendRegisterButton = (Button) findViewById(R.id.ButtonSendRegister);
        sendRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectedButton = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());

                User user = new User();
                user.setUser_name(userName.getText().toString());
                user.setUser_password(userPwd.getText().toString());
                user.setUser_height(Float.valueOf(userHeight.getText().toString()));
                user.setUser_weight(Float.valueOf(userWeight.getText().toString()));
                user.setUser_mail(userMail.getText().toString());
                user.setUser_age(Integer.valueOf(userAge.getText().toString()));
                user.setUser_gender(selectedButton.getText().toString());
                User thatUser = uDataSource.createUser(user);

                try {
                    Score newWeight = new Score();

                    long time = System.currentTimeMillis();
                    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                    cal.setTimeInMillis(time);
                    Date date = cal.getTime();

                    newWeight.setUser(thatUser.getId());
                    newWeight.setScore_value(Integer.valueOf(userWeight.getText().toString()));
                    newWeight.setScore_type("WEIGHT");
                    newWeight.setPerform_at(date);
                    sDataSource.createScore(newWeight);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                myIntent = new Intent(RegisterActivity.this, MainActivity.class);
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
}
