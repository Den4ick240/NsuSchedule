package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class WeekTabsPagerAdapter extends FragmentStateAdapter {
    private final WeekScheduleViewModel model;

    public WeekTabsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, WeekScheduleViewModel model) {
        super(fragmentManager, lifecycle);
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int weekPosition) {
        WeekTabsFragment weekTabsFragment = WeekTabsFragment.newInstance(
                model.getDateForWeekPosition(weekPosition), model.getDaysInWeek());

        weekTabsFragment.setOnPositionSelectedListener(dayOfWeekPosition ->
                model.onPositionSelected(model.getGlobalPosition(dayOfWeekPosition, weekPosition)));

        model.getSelectedDayPositionLiveData().observe(weekTabsFragment, globalPosition -> {
            if (weekPosition == model.getWeekPosition(globalPosition))
                weekTabsFragment.selectTab(model.getDayOfWeekPosition(globalPosition));
        });
        return weekTabsFragment;
    }

    @Override
    public int getItemCount() {
        return model.getNumberOfWeeks();
    }
}