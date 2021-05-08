package ru.nsu.ccfit.nsuschedule.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDayViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduleEvent>> scheduleEventList =
            new MutableLiveData<>();
    private Date day;

    private void loadSchedule() {
        List<ScheduleEvent> list = new ArrayList<>();
        list.add(new ScheduleEvent() {
            @Override
            public CharSequence getSummary() {
                return "first";
            }

            @Override
            public CharSequence getDescription() {
                return "desc first";
            }

            @Override
            public CharSequence getLocation() {
                return "location";
            }

            @Override
            public CharSequence getTime() {
                return "10-34";
            }

            @Override
            public int getId() {
                return 1;
            }
        });
        scheduleEventList.setValue(list);
    }

    public MutableLiveData<List<ScheduleEvent>> getScheduleEventList() {
        return scheduleEventList;
    }

    public void setDay(Date day) {
        this.day = day;
        loadSchedule();
    }

    public interface ScheduleEvent {
        CharSequence getSummary();

        CharSequence getDescription();

        CharSequence getLocation();

        CharSequence getTime();

        int getId();
    }
}
