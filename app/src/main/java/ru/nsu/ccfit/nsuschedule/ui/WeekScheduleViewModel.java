package ru.nsu.ccfit.nsuschedule.ui;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewpager.widget.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.nsu.ccfit.nsuschedule.R;

import static java.util.Calendar.DATE;

public class WeekScheduleViewModel extends AndroidViewModel {

    private static final int[] DAYS_OF_WEEK_TITLES = new int[]{
            R.string.sundayTab,
            R.string.mondayTab,
            R.string.tuesdayTab,
            R.string.wednesdayTab,
            R.string.thursdayTab,
            R.string.fridayTab,
            R.string.saturdayTab
    };

    private final Date firstDate, lastDate, currentDate;
    private final int firstDatePos, lastDatePos, currentDatePos;

    public WeekScheduleViewModel(Application app) {
        super(app);

        int pastDaysNumber = app.getResources().getInteger(R.integer.number_of_past_days_shown);
        int futureDaysNumber = app.getResources().getInteger(R.integer.number_of_future_days_shown);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);
        currentDate = calendar.getTime();
        calendar.add(Calendar.DATE, -pastDaysNumber);
        firstDate = calendar.getTime();
        currentDatePos = pastDaysNumber;
        lastDatePos = pastDaysNumber + futureDaysNumber;
        firstDatePos = 0;
        calendar.add(Calendar.DATE, lastDatePos);
        lastDate = calendar.getTime();

    }

    public PagerAdapter getPagerAdapter(Context context, FragmentManager parentFragmentManager) {
//        return new SectionsPagerAdapter(context,
//                parentFragmentManager, 0, Calendar.getInstance(), 1000);
        return new SchedulePagerAdapter(parentFragmentManager, 0);
    }

    public Date getDate(int position) {
        Calendar c = Calendar.getInstance();
        c.setTime(firstDate);
        c.add(Calendar.DATE, position);
        return c.getTime();
    }

    public int getStartingPosition() {
        return currentDatePos;
    }

    public String getDateFormatted(int position) {
        return SimpleDateFormat.getDateInstance().format(getDate(position));
    }

    private class SchedulePagerAdapter extends FragmentPagerAdapter {

        public SchedulePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ScheduleDayFragment.newInstance(getDate(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate(position));
            return calendar.get(DATE) + "\n" +
                    getApplication().getResources()
                            .getString(DAYS_OF_WEEK_TITLES[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }

        @Override
        public int getCount() {
            return lastDatePos;
        }
    }
}