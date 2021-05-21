package ru.nsu.ccfit.nsuschedule.ui.create_event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventDate;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventInfo;
import ru.nsu.ccfit.nsuschedule.domain.entities.Repeating;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEvent;

public class CreateEventViewModel extends ViewModel {
    private final AddEvent addEvent;
    private final MutableLiveData<Void> eventCreated = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> selectedDateString = new MutableLiveData<>();
    private final Calendar selectedDay;
    private final Calendar startTime;
    private final Calendar endTime;
    private final MutableLiveData<String> startTimeStringLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> endTimeStringLiveData = new MutableLiveData<>();
    private final DateFormat timeFormat;
    private final Map<String, Repeating> repeatingStringToEnumMap;

    public CreateEventViewModel(Map<String, Repeating> repeatingEnumTranslationMap, AddEvent addEvent, DateFormat timeFormat) {
        repeatingStringToEnumMap = repeatingEnumTranslationMap;
        this.addEvent = addEvent;
        this.timeFormat = timeFormat;
        selectedDay = Calendar.getInstance();
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        updateSelectedDateString();
        updateStartTimeString();
        updateEndTimeString();
    }

    public void addEvent(String summary, String description, String location, String repeatingString) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getCurrentYear(), getCurrentMonth(), getCurrentDay(), getStartHour(), getStartMinute(), 0);
        Date startDate = calendar.getTime();
        calendar.set(getCurrentYear(), getCurrentMonth(), getCurrentDay(), getEndHour(), getEndMinute(), 0);
        Date endDate = calendar.getTime();
        Runnable task = () ->
        {
            try {
                addEvent.add(
                        new Event(
                                new EventInfo(summary, description, location),
                                new EventDate(startDate, endDate, repeatingStringToEnumMap.get(repeatingString))));
                eventCreated.postValue(null);
            } catch (RepositoryException e) {
                e.printStackTrace();
                errorMessage.postValue(e.getLocalizedMessage());
            }
        };
        new Thread(task).start();
    }


    public LiveData<Void> getEventCreated() {
        return eventCreated;
    }

    public String makeDateString(Date date) {
        return DateFormat.getDateInstance().format(date);
    }

    public String makeDateString() {
        return makeDateString(selectedDay.getTime());
    }

    public void onDateSet(int year, int month, int day) {
        selectedDay.set(year, month, day);
        updateSelectedDateString();
    }

    public LiveData<String> getSelectedDateString() {
        return selectedDateString;
    }

    public int getCurrentDay() {
        return selectedDay.get(Calendar.DATE);
    }

    public int getCurrentMonth() {
        return selectedDay.get(Calendar.MONTH);
    }

    public int getCurrentYear() {
        return selectedDay.get(Calendar.YEAR);
    }

    private void updateSelectedDateString() {
        selectedDateString.setValue(makeDateString());
    }

    public LiveData<String> getStartTime() {
        return startTimeStringLiveData;
    }

    public int getStartHour() {
        return startTime.get(Calendar.HOUR_OF_DAY);
    }

    public int getStartMinute() {
        return startTime.get(Calendar.MINUTE);
    }

    public LiveData<String> getEndTime() {
        return endTimeStringLiveData;
    }

    public int getEndHour() {
        return endTime.get(Calendar.HOUR_OF_DAY);
    }

    public int getEndMinute() {
        return endTime.get(Calendar.MINUTE);
    }

    public void onStartTimeSet(int hourOfDay, int minute) {
        startTime.set(0, 0, 0, hourOfDay, minute);
        updateStartTimeString();
    }

    private void updateStartTimeString() {
        startTimeStringLiveData.setValue(timeFormat.format(startTime.getTime()));
    }

    public void onEndTimeSet(int hourOfDay, int minute) {
        endTime.set(0, 0, 0, hourOfDay, minute);
        updateEndTimeString();
    }

    private void updateEndTimeString() {
        endTimeStringLiveData.setValue(timeFormat.format(endTime.getTime()));
    }
}