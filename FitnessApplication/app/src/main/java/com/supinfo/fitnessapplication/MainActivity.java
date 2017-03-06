package com.supinfo.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.supinfo.fitnessapplication.DAO.UserDao;
import com.supinfo.fitnessapplication.Models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent myIntent;
    private UserDao uDataSource;


    private String EXTRA_LOGIN = "userLogin";
    private String EXTRA_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uDataSource = new UserDao(this);
        uDataSource.open();

        final EditText userName = (EditText) findViewById(R.id.UserNameArea);
        final EditText userPwd = (EditText) findViewById(R.id.UserPwdArea);
        final TextView errorBox = (TextView) findViewById(R.id.affichText);

        Button registerButton = (Button) findViewById(R.id.ButtonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                uDataSource.close();
                startActivity(myIntent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.ButtonSend);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uDataSource.userAuth(userName.getText().toString(), userPwd.getText().toString()) != null) {
                    User myUser = (User) uDataSource.userAuth(userName.getText().toString(), userPwd.getText().toString());
                    myIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                    myIntent.putExtra(EXTRA_LOGIN, myUser.getUser_name());
                    myIntent.putExtra(EXTRA_ID, myUser.getId());
                    uDataSource.close();
                    startActivity(myIntent);
                }else{
                    errorBox.setText("wrong user name or password");
                }

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
