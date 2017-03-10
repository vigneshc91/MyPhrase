package com.phrase.my.myphrase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        editBtn = (FloatingActionButton) findViewById(R.id.editDetailBtn);

        Password passwordPin = new Password(this);
        mpin = passwordPin.getPassword();

        Intent intent = getIntent();
        detailId = intent.getExtras().getString("detail_id");

        updateValues();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
