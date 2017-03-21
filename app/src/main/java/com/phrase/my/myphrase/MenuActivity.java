package com.phrase.my.myphrase;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by vignesh on 13/3/17.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCreateDetail:
                Intent addNewPasswordIntent = new Intent(MenuActivity.this, AddPasswordActivity.class);
                startActivity(addNewPasswordIntent);
                return true;
            case R.id.menuChangeMpin:
                Intent mpinIntent = new Intent(MenuActivity.this, MainActivity.class);
                mpinIntent.putExtra("changePin", true);
                startActivity(mpinIntent);
                return true;
            case R.id.menuExitApp:
                this.finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
