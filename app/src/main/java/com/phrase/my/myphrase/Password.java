package com.phrase.my.myphrase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vignesh on 6/3/17.
 */

public class Password {

    private final Context context;
    private final SharedPreferences sharedPrefs;
    private final SharedPreferences.Editor editor;

    public Password(Context context){
        this.context = context;
        sharedPrefs = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    public void SetPassword(String password){
        this.editor.putString(AppConstants.MPIN, password);
        this.editor.commit();
    }

    public String GetPassword(){
        return  this.sharedPrefs.getString(AppConstants.MPIN, null);
    }
}
