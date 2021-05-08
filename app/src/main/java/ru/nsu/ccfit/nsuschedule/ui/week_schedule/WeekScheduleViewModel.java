package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekScheduleViewModel extends ViewModel {
    static final int DAYS_IN_WEEK = 7;
    private final int numberOfWeeks;
    private final int numberOfDays;
    private final int currentDayPosition;
    private final Date firstDay;
    private final MutableLiveData<Integer> selectedDayPosition = new MutableLiveData<>();
    private final LiveData<Integer> selectedWeekPosition =
            Transformations.map(selectedDayPosition, this::getWeekPosition);
    private final LiveData<CharSequence> currentDateString =
            Transformations.map(selectedDayPosition, this::formatDateForPosition);

    private String formatDateForPosition(int dayPosition) {
        return DateFormat.getDateInstance().format(getDateForPosition(dayPosition));
    }

    public WeekScheduleViewModel() {
        firstDay = new Date();
        numberOfWeeks = 10;
        numberOfDays = numberOfWeeks * DAYS_IN_WEEK;
        currentDayPosition = 2;
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

    public int getWeekPosition(int globalPosition) {
        return globalPosition / DAYS_IN_WEEK;
    }

    public int getDayOfWeekPosition(int globalPosition) {
        return globalPosition % DAYS_IN_WEEK;
    }

    public int getGlobalPosition(int dayOfWeekPosition, int weekPosition) {
        return dayOfWeekPosition + DAYS_IN_WEEK * weekPosition;
    }

    public Date getDateForWeekPosition(int weekPosition) {
        return getDateForPosition(weekPosition * DAYS_IN_WEEK);
    }

    public Date getDateForPosition(int dayPosition) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDay);
        calendar.add(Calendar.DATE, dayPosition);
        return calendar.getTime();
    }


    public int getStartPosition() {
        return currentDayPosition;
    }

    public LiveData<CharSequence> getCurrentDateString() {
        return currentDateString;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public int getDaysInWeek() {
        return DAYS_IN_WEEK;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }
}