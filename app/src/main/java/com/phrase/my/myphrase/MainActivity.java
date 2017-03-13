package com.phrase.my.myphrase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;
import com.orm.SugarContext;

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
//        blurLockView.setBlurredView(imageView);

        blurLockView.setTitle(SuccessConstants.ENTER_YOUR_MPIN);
        blurLockView.setLeftButton("");
        blurLockView.setRightButton(AppConstants.BACKSPACE_STRING);
        blurLockView.setType(Password.NUMBER, true);
        if(mpin == null) {
            blurLockView.setCorrectPassword("");
            mode = AppConstants.mode.INITIAL;
        } else {
            blurLockView.setCorrectPassword(mpin);
            mode = AppConstants.mode.ACTIVATED;
        }

        Intent intent = this.getIntent();
        if(intent != null && intent.getExtras() != null){
            mode = AppConstants.mode.OLD_PIN;
            blurLockView.setTitle(SuccessConstants.ENTER_YOUR_OLD_MPIN);
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
        Intent viewActivityIntent = new Intent(MainActivity.this, PasswordViewListActivity.class);
        switch (mode){
            case CONFIRM_INITIAL:
                password.setPassword(inputPassword);
                blurLockView.setCorrectPassword(inputPassword);
                Toast.makeText(this, SuccessConstants.WELCOME_TO_MY_PHRASE, Toast.LENGTH_SHORT).show();
                startActivity(viewActivityIntent);
                finish();
                break;
            case ACTIVATED:
                Toast.makeText(this, SuccessConstants.WELCOME_BACK_TO_MY_PHRASE, Toast.LENGTH_SHORT).show();
                startActivity(viewActivityIntent);
                finish();
                break;
            case OLD_PIN:
                blurLockView.setTitle(SuccessConstants.ENTER_YOUR_NEW_MPIN);
                mode = AppConstants.mode.CHANGE_PIN;
                blurLockView.setCorrectPassword("");
                break;
            case CONFIRM_PIN:
                password.setPassword(inputPassword);
                blurLockView.setCorrectPassword(inputPassword);
                Toast.makeText(this, SuccessConstants.MPIN_CHANGED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, SuccessConstants.RE_ENTER_YOUR_MPIN, Toast.LENGTH_SHORT).show();
                    blurLockView.setCorrectPassword(inputPassword);
                    this.mode = AppConstants.mode.CONFIRM_INITIAL;
                    blurLockView.setTitle(SuccessConstants.CONFIRM_YOUR_MPIN);
                }
                break;
            case CHANGE_PIN:
                if(inputPassword.length() == 4){
                    Toast.makeText(this, SuccessConstants.CONFIRM_YOUR_NEW_MPIN, Toast.LENGTH_SHORT).show();
                    blurLockView.setCorrectPassword(inputPassword);
                    this.mode = AppConstants.mode.CONFIRM_PIN;
                    blurLockView.setTitle(SuccessConstants.CONFIRM_YOUR_NEW_MPIN);
                }
                break;

        }
    }

    @Override
    public void incorrect(String inputPassword) {
        switch (mode){
            case CONFIRM_INITIAL:
                Toast.makeText(this, ErrorConstants.INVALID_MPIN, Toast.LENGTH_SHORT).show();
                this.mode = AppConstants.mode.INITIAL;
                blurLockView.setCorrectPassword("");
                blurLockView.setTitle(SuccessConstants.ENTER_YOUR_MPIN);
                break;
            case ACTIVATED:
                Toast.makeText(this, ErrorConstants.WRONG_MPIN, Toast.LENGTH_SHORT).show();
                break;
            case OLD_PIN:
                Toast.makeText(this, ErrorConstants.WRONG_MPIN, Toast.LENGTH_SHORT).show();
                break;
            case CONFIRM_PIN:
                Toast.makeText(this, ErrorConstants.WRONG_MPIN, Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override
    public void onClick() {

    }

}
