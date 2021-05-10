package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeekTabNamesProvider {
    private final List<String> dayOfWeekNames;
    public WeekTabNamesProvider(List<String> dayOfWeekNames) {
        this.dayOfWeekNames = dayOfWeekNames;
    }

    public List<CharSequence> getTabNames(Date firstDay, int numberOfDays) {
        List<CharSequence> tabNames = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDay);
        for (int i = 0; i < numberOfDays; i++) {
            tabNames.add(getTabContentDescription(calendar));
            calendar.add(Calendar.DATE, 1);
        }
        return tabNames;
    }

    private String getTabContentDescription(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = dayOfWeekNames.get(dayOfWeekNumber - 1);
        return date + "\n" + dayOfWeek;
    }
}
