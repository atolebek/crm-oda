package kz.tele2.crmoda.util;

import java.time.LocalDate;

public class DateFormatUtil {

    public static String getRussianMonthName(LocalDate date){
        String response = "";
        switch (date.getMonthValue()) {
            case 1:
                response = "Январь";
                break;
            case 2:
                response = "Февраль";
                break;
            case 3:
                response = "Март";
                break;
            case 4:
                response = "Апрель";
                break;
            case 5:
                response = "Май";
                break;
            case 6:
                response = "Июнь";
                break;
            case 7:
                response = "Июль";
                break;
            case 8:
                response = "Август";
                break;
            case 9:
                response = "Сентябрь";
                break;
            case 10:
                response = "Октябрь";
                break;
            case 11:
                response = "Ноябрь";
                break;
            case 12:
                response = "Декабрь";
                break;
        }
        return response;
    }

}
