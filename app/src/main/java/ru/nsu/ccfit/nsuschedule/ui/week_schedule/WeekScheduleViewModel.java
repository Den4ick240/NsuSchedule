package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;
import java.util.Date;

public class WeekScheduleViewModel extends ViewModel {
    private static final int DAYS_IN_WEEK = 7;
    private final int numberOfWeeks;
    private final Date firstDay;
    private final MutableLiveData<Integer> selectedDayPosition = new MutableLiveData<>();
    private final LiveData<Integer> selectedWeekPosition = Transformations.map(selectedDayPosition, this::getWeekPosition);
    private final int currentDayPosition;

    public WeekScheduleViewModel() {
        firstDay = new Date();
        numberOfWeeks = 10;
        currentDayPosition = 0;
    }

    public LiveData<Integer> getSelectedDayPositionLiveData() {
        return selectedDayPosition;
    }

    public LiveData<Integer> getSelectedWeekPositionLiveData() {
        return selectedWeekPosition;
    }

    public void onPositionSelected(int position) {
        selectedDayPosition.setValue(position);
    }

    public FragmentStateAdapter getTabsAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        return new WeekTabsPagerAdapter(fragmentManager, lifecycle);
    }

    private int getWeekPosition(int globalPosition) {
        return globalPosition / DAYS_IN_WEEK;
    }

    private int getDayOfWeekPosition(int globalPosition) {
        return globalPosition % DAYS_IN_WEEK;
    }

    private int getGlobalPosition(int dayOfWeekPosition, int weekPosition) {
        return dayOfWeekPosition + DAYS_IN_WEEK * weekPosition;
    }

    private Date getDateForPosition(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDay);
        calendar.add(Calendar.DATE, position * DAYS_IN_WEEK);
        return calendar.getTime();
    }

    public int getStartPosition() {
        return currentDayPosition;
    }

    class WeekTabsPagerAdapter extends FragmentStateAdapter {
        public WeekTabsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int weekPosition) {
            WeekTabsFragment weekTabsFragment = WeekTabsFragment.newInstance(getDateForPosition(weekPosition));

            weekTabsFragment.setOnPositionSelectedListener(dayOfWeekPosition ->
                    selectedDayPosition.setValue(getGlobalPosition(dayOfWeekPosition, weekPosition)));

            selectedDayPosition.observe(weekTabsFragment, globalPosition -> {
                if (weekPosition == getWeekPosition(globalPosition))
                    weekTabsFragment.selectTab(getDayOfWeekPosition(globalPosition));
            });
            return weekTabsFragment;
        }

        @Override
        public int getItemCount() {
            return numberOfWeeks;
        }
    }
}