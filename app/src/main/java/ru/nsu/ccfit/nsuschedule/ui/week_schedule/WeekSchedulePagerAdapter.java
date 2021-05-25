package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ru.nsu.ccfit.nsuschedule.ui.schedule_day.ScheduleDayFragment;

public class WeekSchedulePagerAdapter extends FragmentStateAdapter {
    private final WeekScheduleViewModel model;

    public WeekSchedulePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, WeekScheduleViewModel model) {
        super(fragmentManager, lifecycle);
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ScheduleDayFragment.newInstance(model.getDateForPosition(position), model.getFlowName());
    }

    @Override
    public int getItemCount() {
        return model.getNumberOfDays();
    }
}