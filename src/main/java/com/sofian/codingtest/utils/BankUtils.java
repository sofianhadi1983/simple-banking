package com.sofian.codingtest.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BankUtils {

    public static boolean isFutureDate(Date date) {
        LocalDate dateUnderTest = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();

        if (dateUnderTest.getYear() > now.getYear()) {
           return true;
        }

        if (dateUnderTest.getMonthValue() > now.getMonthValue()) {
            return true;
        }

        if (dateUnderTest.getDayOfMonth() > now.getDayOfMonth()) {
            return true;
        }

        return false;
    }
}
