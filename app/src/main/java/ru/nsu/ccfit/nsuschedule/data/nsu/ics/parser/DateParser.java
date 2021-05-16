package ru.nsu.ccfit.nsuschedule.data.nsu.ics.parser;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateParser {

    public Date parse(String value) {
        Calendar calendar = new GregorianCalendar();

        char[] year = new char[4];
        value.getChars(0, 4, year, 0);
        calendar.set(Calendar.YEAR, Integer.parseInt(String.valueOf(year)));

        char[] month = new char[2];
        value.getChars(4, 6, month, 0);
        calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf(month)));

        char[] day = new char[2];
        value.getChars(6, 8, day, 0);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(day)));

        char[] hour = new char[2];
        value.getChars(9, 11, hour, 0);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(String.valueOf(hour)));

        char[] minute = new char[2];
        value.getChars(11, 13, minute, 0);
        calendar.set(Calendar.MINUTE, Integer.parseInt(String.valueOf(minute)));

        return calendar.getTime();
    }
}
