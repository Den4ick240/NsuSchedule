package ru.nsu.ccfit.nsuschedule.domain.entities;

import java.io.Serializable;
import java.util.Calendar;

public enum Repeating implements Serializable {
    ONCE(Calendar.DATE, 0),
    DAY(Calendar.DATE, 1),
    MONTH(Calendar.MONTH, 1),
    WEEK(Calendar.WEEK_OF_YEAR, 1),
    TWO_WEEK(Calendar.WEEK_OF_MONTH, 2),
    YEAR(Calendar.YEAR, 1);

    public final int dateInterval;
    public final int amount;

    Repeating(int dateInterval, int amount) {
        this.dateInterval = dateInterval;
        this.amount = amount;
    }

    public long getInterval() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.add(dateInterval, amount);
        return calendar.getTimeInMillis();
    }
}
