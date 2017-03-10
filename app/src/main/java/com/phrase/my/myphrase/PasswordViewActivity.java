package com.phrase.my.myphrase;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PasswordViewActivity extends AppCompatActivity {

    private FloatingActionButton addNewPassword;
    private ListView detailListView;

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

        setListAdapter();

        detailListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = detailListView.getItemAtPosition(position);
                Detail detail = (Detail) obj;

                Intent viewPasswordIntent = new Intent(PasswordViewActivity.this, ViewPasswordActivity.class);
                viewPasswordIntent.putExtra("detail_id", detail.getId().toString());
                startActivity(viewPasswordIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListAdapter();
    }

    private void setListAdapter(){
        detailListView = (ListView) findViewById(R.id.passwordListView);
        List<Detail> detailList = DbOperations.getAllDetails();

        DetailListAdapter detailListAdapter = new DetailListAdapter(this, detailList);
        detailListView.setAdapter(detailListAdapter);
    }
}
