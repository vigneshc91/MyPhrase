package com.phrase.my.myphrase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.GeneralSecurityException;

public class EditPasswordActivity extends MenuActivity {

    private EditText title, userName, password, comments;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String detailId = intent.getExtras().getString("detail_id");

        title = (EditText) findViewById(R.id.editPasswordEditTextTitle);
        userName = (EditText) findViewById(R.id.editPasswordEditTextUserName);
        password = (EditText) findViewById(R.id.editPasswordEditTextPassword);
        comments = (EditText) findViewById(R.id.editPasswordEditTextComments);

        editBtn = (Button) findViewById(R.id.editPasswordBtn);

        Detail detail = DbOperations.getDetailById(Integer.parseInt(detailId));

        Password secretKey = new Password(this);
        final String mpin = secretKey.getPassword();
        String decryptedPassword = "";

        try {
            decryptedPassword = Encryption.decryptPassword(detail.password, mpin);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        title.setText(detail.title);
        userName.setText(detail.userName);
        password.setText(decryptedPassword);
        comments.setText(detail.comment);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = title.getText().toString();
                String userNameString = userName.getText().toString();
                String passwordString = password.getText().toString();
                String commentsString = comments.getText().toString();

                if(titleString.length() == 0 || userNameString.length() == 0 || passwordString.length() == 0){
                    AlertDialog.Builder errorAlertBuilder = new AlertDialog.Builder(EditPasswordActivity.this);
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
                } else {
                    String encryptedPassword = "";
                    try {
                        encryptedPassword = Encryption.encryptPassword(passwordString, mpin);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }

                    if(DbOperations.updateDetail(Integer.parseInt(detailId), titleString, userNameString, encryptedPassword, commentsString)){
                        Toast.makeText(EditPasswordActivity.this, SuccessConstants.PASSWORD_EDIT_SUCCESS, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPasswordActivity.this, ErrorConstants.PASSWORD_EDIT_FAILED, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem editMenuItem = menu.findItem(R.id.menuEditDetail);
        editMenuItem.setVisible(false);
        MenuItem deleteMenuItem = menu.findItem(R.id.menuDeleteDetail);
        deleteMenuItem.setVisible(false);
        return true;
    }

}
