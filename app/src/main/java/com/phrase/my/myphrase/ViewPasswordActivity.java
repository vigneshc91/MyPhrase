package com.phrase.my.myphrase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.security.GeneralSecurityException;

public class ViewPasswordActivity extends MenuActivity {

    private TextView userName, password, comments;
    private ToggleButton passwordShow;
    private FloatingActionButton editBtn;
    String mpin, detailId, maskedPassword = "", decryptedPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = (TextView) findViewById(R.id.userNameTextView);
        password = (TextView) findViewById(R.id.passwordTextView);
        comments = (TextView) findViewById(R.id.commentsTextView);
        editBtn = (FloatingActionButton) findViewById(R.id.editDetailFabBtn);
        passwordShow = (ToggleButton) findViewById(R.id.passwordShowToggle);


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

        passwordShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ToggleButton) v).isChecked()){
                    password.setText(decryptedPassword);
                } else {
                    password.setText(maskedPassword);
                }
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

        try {
            decryptedPassword = Encryption.decryptPassword(detail.password, mpin);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        String mask = "";
        for (int i=0; i<decryptedPassword.length(); i++){
            mask += "*";
        }
        maskedPassword = mask;

        userName.setText(detail.userName);
        password.setText(maskedPassword);
        comments.setText(detail.comment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuEditDetail:
                Intent editPasswordIntent = new Intent(ViewPasswordActivity.this, EditPasswordActivity.class);
                editPasswordIntent.putExtra("detail_id", detailId);
                startActivity(editPasswordIntent);
                return true;
            case R.id.menuDeleteDetail:
                AlertDialog.Builder deleteAlertBuilder = new AlertDialog.Builder(ViewPasswordActivity.this);
                deleteAlertBuilder.setTitle(ErrorConstants.ALERT);
                deleteAlertBuilder.setMessage(ErrorConstants.WANT_TO_DELETE_PASSWORD);
                deleteAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(DbOperations.deleteDetail(Integer.parseInt(detailId.toString()))){
                            Toast.makeText(ViewPasswordActivity.this, SuccessConstants.PASSWORD_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                            Intent passwordListIntent = new Intent(ViewPasswordActivity.this, PasswordViewListActivity.class);
                            startActivity(passwordListIntent);
                            finish();
                        } else {
                            Toast.makeText(ViewPasswordActivity.this, ErrorConstants.PASSWORD_DELETE_FAILED, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                deleteAlertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog deleteAlert = deleteAlertBuilder.create();
                deleteAlert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
