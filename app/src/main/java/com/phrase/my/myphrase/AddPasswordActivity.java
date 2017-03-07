package com.phrase.my.myphrase;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText title, userName, password, comments;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        title = (EditText) findViewById(R.id.addPasswordEditTextTitle);
        userName = (EditText) findViewById(R.id.addPasswordEditTextUserName);
        password = (EditText) findViewById(R.id.addPasswordEditTextPassword);
        comments = (EditText) findViewById(R.id.addPasswordEditTextComments);

        addBtn = (Button) findViewById(R.id.addPasswordBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.length() == 0 || userName.length() == 0 || password.length() == 0){
                    AlertDialog.Builder errorAlertBuilder = new AlertDialog.Builder(AddPasswordActivity.this);
                    errorAlertBuilder.setTitle(ErrorConstants.ALERT);
                    errorAlertBuilder.setMessage(ErrorConstants.REQUIRED_FIELDS_EMPTY);
                    errorAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog errorAlert = errorAlertBuilder.create();
                    errorAlert.show();
                }
            }
        });
    }
}
