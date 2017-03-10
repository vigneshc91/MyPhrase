package com.phrase.my.myphrase;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.SugarContext;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AddPasswordActivity extends AppCompatActivity {

    private EditText title, userName, password, comments;
    private Button addBtn;
    private String mpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        Password passwordInstance = new Password(this);
        mpin = passwordInstance.getPassword();

        title = (EditText) findViewById(R.id.addPasswordEditTextTitle);
        userName = (EditText) findViewById(R.id.addPasswordEditTextUserName);
        password = (EditText) findViewById(R.id.addPasswordEditTextPassword);
        comments = (EditText) findViewById(R.id.addPasswordEditTextComments);

        addBtn = (Button) findViewById(R.id.addPasswordBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String userNameText = userName.getText().toString();
                String passwordText = password.getText().toString();
                String commentsText = comments.getText().toString();
                if(titleText.length() == 0 || userNameText.length() == 0 || passwordText.length() == 0){
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
                } else {
                    String encryptedPassword = "";
                    try {
                        encryptedPassword = Encryption.encryptPassword(passwordText, mpin);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (InvalidParameterSpecException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }

                    if(DbOperations.createDetail(titleText, userNameText, encryptedPassword, commentsText)){
                        title.setText("");
                        userName.setText("");
                        password.setText("");
                        comments.setText("");
                        Toast.makeText(AddPasswordActivity.this, SuccessConstants.PASSWORD_ADD_SUCCESS, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddPasswordActivity.this, ErrorConstants.PASSWORD_ADD_FAILED, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
