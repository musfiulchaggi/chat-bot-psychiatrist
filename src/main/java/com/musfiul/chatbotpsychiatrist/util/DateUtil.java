package com.musfiul.chatbotpsychiatrist.util;

import java.text.ParseException;
import java.util.Date;

public class DateUtil {

    public static Date timeSecondToDate(Long date){
        Date dateConverted = null;
        try{
            Long dateLongMiliseconds = date * 1000;
            dateConverted = new Date(dateLongMiliseconds);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return dateConverted;
    }
}
