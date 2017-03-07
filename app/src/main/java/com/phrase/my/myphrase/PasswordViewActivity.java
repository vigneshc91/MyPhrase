package com.phrase.my.myphrase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PasswordViewActivity extends AppCompatActivity {

    private FloatingActionButton addNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_view);

        addNewPassword = (FloatingActionButton) findViewById(R.id.addNewPasswordBtn);

        addNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewPasswordIntent = new Intent(PasswordViewActivity.this, AddPasswordActivity.class);
                startActivity(addNewPasswordIntent);
            }
        });
    }
}
