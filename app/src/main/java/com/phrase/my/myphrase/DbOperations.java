package com.phrase.my.myphrase;

import java.util.List;

/**
 * Created by vignesh on 8/3/17.
 */

public class DbOperations {

    public static boolean createDetail(String title, String userName, String password, String comment){
        Detail detail = new Detail(title, userName, password, comment);
        detail.save();
        return true;
    }

    public static Detail getDetailById(int id){
        Detail detail = Detail.findById(Detail.class, id);
        return detail;
    }

    public static List<Detail> getAllDetails(){
        List<Detail> details = Detail.listAll(Detail.class);
        return details;
    }

    public static boolean updateDetail(int id, String title, String userName, String password, String comment){
        Detail detail = Detail.findById(Detail.class, id);
        detail.title = title;
        detail.userName = userName;
        detail.password = password;
        detail.comment = comment;
        detail.save();

        return true;
    }

    public static boolean deleteDetail(int id){
        Detail detail = Detail.findById(Detail.class, id);
        return detail.delete();
    }
}
