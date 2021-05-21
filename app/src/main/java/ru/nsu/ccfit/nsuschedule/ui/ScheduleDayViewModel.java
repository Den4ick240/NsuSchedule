package ru.nsu.ccfit.nsuschedule.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventOccurrence;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;

public class ScheduleDayViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduleEvent>> scheduleEventList =
            new MutableLiveData<>();
    private Date day;
    private final GetEventsForDay getEventsForDay;
    private final RemoveEvent removeEvent;
    private final DateFormat timeFormat;

    public ScheduleDayViewModel(GetEventsForDay getEventsForDay, RemoveEvent removeEvent, DateFormat timeFormat) {
        this.getEventsForDay = getEventsForDay;
        this.removeEvent = removeEvent;
        this.timeFormat = timeFormat;
    }

    private List<ScheduleEvent> mapEventListToScheduleEventList(List<Event> list) {
        List<ScheduleEvent> outList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);
        for (Event event : list) {
            for (EventOccurrence eventOccurrence : event.getDate().getOccurrencesBetweenDates(day, calendar.getTime())) {
                outList.add(new ScheduleEvent(event, eventOccurrence, timeFormat));
            }
        }
        Comparator<ScheduleEvent> scheduleEventComparator = (o1, o2) ->
                o1.getStartTime().compareTo(o2.getStartTime());
        Collections.sort(outList, scheduleEventComparator);
        return outList;
    }

    private void loadSchedule() {
        Callable<List<Event>> task = () -> getEventsForDay.getEvents(day);
        Observable<List<ScheduleEvent>> observable = Observable
                .fromCallable(task)
                .map(this::mapEventListToScheduleEventList)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<List<ScheduleEvent>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                //unused
            }

            @Override
            public void onNext(@NonNull List<ScheduleEvent> scheduleEvents) {
                scheduleEventList.setValue(scheduleEvents);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                //unused
            }
        });
    }


    public MutableLiveData<List<ScheduleEvent>> getScheduleEventList() {
        return scheduleEventList;
    }

    public void setDay(Date day) {
        this.day = day;
        loadSchedule();
    }


    public void deleteEvent(int position) {
        new Thread(() -> {
            try {
                if (scheduleEventList.getValue() != null && position < scheduleEventList.getValue().size())
                    removeEvent.remove(scheduleEventList.getValue().get(position).getEvent());
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            loadSchedule();
        }).start();
    }
}
