package com.phrase.my.myphrase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.GeneralSecurityException;

public class ViewPasswordActivity extends AppCompatActivity {

    private TextView userName, password, comments;
    private FloatingActionButton editBtn;
    String mpin, detailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);

        userName = (TextView) findViewById(R.id.userNameTextView);
        password = (TextView) findViewById(R.id.passwordTextView);
        comments = (TextView) findViewById(R.id.commentsTextView);
        editBtn = (FloatingActionButton) findViewById(R.id.editDetailFabBtn);

        Password passwordPin = new Password(this);
        mpin = passwordPin.getPassword();

        Intent intent = getIntent();
        detailId = intent.getExtras().getString("detail_id");

        updateValues();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editPasswordIntent = new Intent(ViewPasswordActivity.this, EditPasswordActivity.class);
                editPasswordIntent.putExtra("detail_id", detailId);
                startActivity(editPasswordIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }

    private void updateValues(){
        Detail detail = DbOperations.getDetailById(Integer.parseInt(detailId));
        setTitle(detail.title);

        String decryptedPassword = "";

        try {
            decryptedPassword = Encryption.decryptPassword(detail.password, mpin);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        userName.setText(detail.userName);
        password.setText(decryptedPassword);
        comments.setText(detail.comment);
    }
}
