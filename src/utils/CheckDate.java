package utils;

import java.time.LocalDate;

public class CheckDate {
    public static  boolean checkDate(LocalDate localDate , LocalDate datNow){
        return localDate.isAfter(datNow);
    }
}
