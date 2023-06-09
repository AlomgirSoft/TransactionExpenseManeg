package com.example.nomn.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {


    public static String showDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyy");
        return dateFormat.format(date);
    }

    public static String showDateMathly(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, yyy");
        return dateFormat.format(date);
    }
}
