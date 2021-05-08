package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.R;

public class WeekTabsViewModel extends AndroidViewModel {
    private static final int[] DAYS_OF_WEEK_TITLES = new int[]{
            R.string.sundayTab,
            R.string.mondayTab,
            R.string.tuesdayTab,
            R.string.wednesdayTab,
            R.string.thursdayTab,
            R.string.fridayTab,
            R.string.saturdayTab
    };

    public WeekTabsViewModel(@NonNull Application application) {
        super(application);
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
        String dayOfWeek = getApplication().getResources().getString(DAYS_OF_WEEK_TITLES[dayOfWeekNumber - 1]);
        return date + "\n" + dayOfWeek;
    }
}
