package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;
import java.util.Date;

import ru.nsu.ccfit.nsuschedule.ui.ScheduleDayFragment;

public class WeekSchedulePagerAdapter extends FragmentStateAdapter {

    private final Date firstDayDate;

    public WeekSchedulePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Date firstDayDate) {
        super(fragmentManager, lifecycle);
        this.firstDayDate = firstDayDate;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayDate);
        calendar.add(Calendar.DATE, position);
        return ScheduleDayFragment.newInstance(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return 20000;
    }
}
