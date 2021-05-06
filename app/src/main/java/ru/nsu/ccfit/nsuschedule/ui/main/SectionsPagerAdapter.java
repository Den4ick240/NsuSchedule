package ru.nsu.ccfit.nsuschedule.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.R;

import static java.util.Calendar.DATE;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private static final int[] DAYS_OF_WEEK_TITLES = new int[]{
            R.string.sundayTab,
            R.string.mondayTab,
            R.string.tuesdayTab,
            R.string.wednesdayTab,
            R.string.thursdayTab,
            R.string.fridayTab,
            R.string.saturdayTab
    };
    private final Context mContext;
    private final int numberOfDays;
    private final Calendar firstDate;
    private final List<Calendar> calendarList;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int behaviour,
                                Calendar firstDate, int numberOfDays) {
        super(fm, behaviour);
        mContext = context;
        this.numberOfDays = numberOfDays;
        this.firstDate = firstDate;
        calendarList = getCalendarList();
    }

    private List<Calendar> getCalendarList() {
        ArrayList<Calendar> calendars = new ArrayList<>();
        for (int i = 0; i < numberOfDays; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(firstDate.getTime());
            c.add(Calendar.DATE, i);
            calendars.add(c);
        }
        return calendars;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(calendarList.get(position).get(Calendar.DAY_OF_MONTH));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Calendar calendar = calendarList.get(position);
        return calendar.get(DATE) + "\n" + mContext.getResources().getString(DAYS_OF_WEEK_TITLES[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return calendarList.size();
    }
}