package ru.nsu.ccfit.nsuschedule.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;

public class ScheduleDayViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduleEvent>> scheduleEventList =
            new MutableLiveData<>();
    private Date day;
    private final GetEventsForDay getEventsForDay;
    private final RemoveEvent removeEvent;

    public ScheduleDayViewModel(GetEventsForDay getEventsForDay, RemoveEvent removeEvent) {
        this.getEventsForDay = getEventsForDay;
        this.removeEvent = removeEvent;
    }


    private void loadSchedule() {
        Callable<List<Event>> task = () -> getEventsForDay.getEvents(day);
        Observable<List<ScheduleEvent>> observable = Observable
                .fromCallable(task)
                .map(l ->
                        l.stream().map(ScheduleEvent::new).collect(Collectors.toList())
                )
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
                //unused
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
                removeEvent.remove(scheduleEventList.getValue().get(position).getEvent());
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            loadSchedule();
        }).start();
    }
}
