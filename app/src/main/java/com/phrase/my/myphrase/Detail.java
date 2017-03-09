package com.phrase.my.myphrase;

import com.orm.SugarRecord;

/**
 * Created by vignesh on 8/3/17.
 */

public class Detail extends SugarRecord {

    String title;
    String userName;
    String password;
    String comment;

    public Detail(){
        super();
    }

    public Detail(String title, String userName, String password, String comment){
        this.title = title;
        this.userName = userName;
        this.password = password;
        this.comment = comment;
    }
}
