package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

class WeekTabsPagerAdapter extends FragmentStateAdapter {
    private final WeekScheduleViewModel model;
    private final ViewPager2 viewPager;

    public WeekTabsPagerAdapter(@NonNull FragmentManager fragmentManager,
                                @NonNull Lifecycle lifecycle,
                                WeekScheduleViewModel model,
                                ViewPager2 weekTabsViewPager) {
        super(fragmentManager, lifecycle);
        this.model = model;
        viewPager = weekTabsViewPager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int weekPosition) {
        WeekTabsFragment weekTabsFragment = WeekTabsFragment.newInstance(
                model.getDateForWeekPosition(weekPosition), model.getDaysInWeek());

        weekTabsFragment.setOnPositionSelectedListener(dayOfWeekPosition -> {
            if (viewPager.getCurrentItem() == weekPosition)
                model.onPositionSelected(model.getGlobalPosition(dayOfWeekPosition, weekPosition));
        });

        model.getSelectedDayPositionLiveData().observe(weekTabsFragment, globalPosition -> {
            if (weekPosition == model.getWeekPosition(globalPosition)) {
                weekTabsFragment.selectTab(model.getDayOfWeekPosition(globalPosition));
            }
        });
        model.getSelectedWeekPositionLiveData().observe(weekTabsFragment, integer -> weekTabsFragment.setSelectionVisible(integer == weekPosition));

        return weekTabsFragment;
    }

    @Override
    public int getItemCount() {
        return model.getNumberOfWeeks();
    }
}