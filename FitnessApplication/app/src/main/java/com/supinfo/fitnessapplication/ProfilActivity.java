package com.supinfo.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.supinfo.fitnessapplication.DAO.UserDao;
import com.supinfo.fitnessapplication.Models.User;

public class ProfilActivity extends AppCompatActivity {


    private String EXTRA_ID = "userId";
    private String EXTRA_LOGIN = "userLogin";
    private int id;
    private String Name;

    private EditText UserName;
    private EditText Password;
    private RadioGroup radioSexGroup;
    private EditText userAge;
    private EditText userHeight;
    private EditText userMail;

    private UserDao uDataSource;
    private Intent myIntent;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        myUser = null;

        uDataSource = new UserDao(this);
        uDataSource.open();

        Intent intent = getIntent();
        if (intent != null) {

            id = intent.getIntExtra(EXTRA_ID, 0);
            Name = intent.getStringExtra(EXTRA_LOGIN);

            myUser = uDataSource.getUser(id);

            UserName = (EditText) findViewById(R.id.ProfilUserNameArea);
            UserName.setText(myUser.getUser_name());

            Password = (EditText) findViewById(R.id.ProfilUserPwdArea);
            Password.setText(myUser.getUser_password());

            radioSexGroup = (RadioGroup) findViewById(R.id.ProfilradioGender);
            RadioButton radioButton1 = (RadioButton) findViewById(R.id.ProfilradioMale);
            RadioButton radioButton2 = (RadioButton) findViewById(R.id.ProfilradioFemale);

            if (myUser.getUser_gender().equals("Male")) {
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
            } else {
                radioButton1.setChecked(false);
                radioButton2.setChecked(true);
            }

            userAge = (EditText) findViewById(R.id.ProfilUserAgeArea);
            userAge.setText(String.valueOf(myUser.getUser_age()));

            userHeight = (EditText) findViewById(R.id.ProfilUserHeightArea);
            userHeight.setText(String.valueOf(myUser.getUser_height()));

            userMail = (EditText) findViewById(R.id.ProfilUserMailArea);
            userMail.setText(myUser.getUser_mail());

            Button SendProfil = (Button) findViewById(R.id.ButtonSendProfil);
            SendProfil.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    myIntent = new Intent(ProfilActivity.this, WelcomeActivity.class);
                    myIntent.putExtra(EXTRA_LOGIN, myUser.getUser_name());
                    myIntent.putExtra(EXTRA_ID, myUser.getId());

                    RadioButton selectedButton = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());

                    myUser.setUser_name(UserName.getText().toString());
                    myUser.setUser_password(Password.getText().toString());
                    myUser.setUser_height(Float.valueOf(userHeight.getText().toString()));
                    myUser.setUser_mail(userMail.getText().toString());
                    myUser.setUser_age(Integer.valueOf(userAge.getText().toString()));
                    myUser.setUser_gender(selectedButton.getText().toString());
                    uDataSource.updateUser(myUser);

                    uDataSource.close();
                    startActivity(myIntent);
                }
            });
        }
            Button returnButton = (Button) findViewById(R.id.ReturnProfil);
            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myIntent = new Intent(ProfilActivity.this, WelcomeActivity.class);
                    myIntent.putExtra(EXTRA_LOGIN, myUser.getUser_name());
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }
            });
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
