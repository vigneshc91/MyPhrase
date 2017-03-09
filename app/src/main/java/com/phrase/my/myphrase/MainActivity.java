package com.phrase.my.myphrase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;
import com.orm.SugarContext;
import com.orm.SugarRecord;

public class MainActivity extends AppCompatActivity implements BlurLockView.OnLeftButtonClickListener, BlurLockView.OnPasswordInputListener {
    private BlurLockView blurLockView;
    private ImageView imageView;
    private String mpin;
    private com.phrase.my.myphrase.Password password;
    private AppConstants.mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = new com.phrase.my.myphrase.Password(this);
        mpin = password.getPassword();

        imageView = (ImageView)findViewById(R.id.image_1);
        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);
        blurLockView.setBlurredView(imageView);

        blurLockView.setTitle("Enter your mpin");
        blurLockView.setLeftButton("Confirm");
        blurLockView.setRightButton("Erase");
        blurLockView.setType(Password.NUMBER, true);
        if(mpin == null) {
            blurLockView.setCorrectPassword("");
            mode = AppConstants.mode.INITIAL;
        } else {
            blurLockView.setCorrectPassword(mpin);
            mode = AppConstants.mode.ACTIVATED;
        }
        blurLockView.setOnPasswordInputListener(this);
        blurLockView.setOnLeftButtonClickListener(this);
        SugarContext.init(this);
    }

    public void onTerminate(){
        SugarContext.terminate();
    }

    @Override
    public void correct(String inputPassword) {
        Intent viewActivityIntent = new Intent(MainActivity.this, PasswordViewActivity.class);
        switch (mode){
            case CONFIRM_INITIAL:
                password.setPassword(inputPassword);
                blurLockView.setCorrectPassword(inputPassword);
                Toast.makeText(this, "Welcome To My Phrase", Toast.LENGTH_SHORT).show();
                startActivity(viewActivityIntent);
                finish();
                break;
            case ACTIVATED:
                Toast.makeText(this, "Welcome back To My Phrase", Toast.LENGTH_SHORT).show();
                startActivity(viewActivityIntent);
                finish();
                break;
        }

    }

    @Override
    public void input(String inputPassword) {
        switch (mode){
            case INITIAL:
                if(inputPassword.length() == 4){
                    Toast.makeText(this, "Re-Enter the mpin", Toast.LENGTH_SHORT).show();
                    blurLockView.setCorrectPassword(inputPassword);
                    this.mode = AppConstants.mode.CONFIRM_INITIAL;
                    blurLockView.setTitle("Confirm your mpin");
                }
                break;
        }
    }

    @Override
    public void incorrect(String inputPassword) {
        switch (mode){
            case CONFIRM_INITIAL:
                Toast.makeText(this, "Invalid mpin, Please try again "+inputPassword, Toast.LENGTH_SHORT).show();
                this.mode = AppConstants.mode.INITIAL;
                blurLockView.setCorrectPassword("");
                blurLockView.setTitle("Enter your mpin");
                break;
            case ACTIVATED:
                Toast.makeText(this, "Wrong mpin, Please try again", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override
    public void onClick() {

    }
}
