package com.phrase.my.myphrase;

/**
 * Created by shiva on 21/2/17.
 */

public class AppConstants {

    public static final String SHARED_PREFERENCES_NAME = "MyPhraseSharedPrefs";
    public static final String MPIN = "mpin";
    public static final String ENCRYPTION_SECRET_KEY = "MyPhraseKey!";

    public static final String BACKSPACE_STRING = "Erase";

    public static enum mode{
        INITIAL,
        CONFIRM_INITIAL,
        ACTIVATED,
        OLD_PIN,
        CHANGE_PIN,
        CONFIRM_PIN
    }
}
