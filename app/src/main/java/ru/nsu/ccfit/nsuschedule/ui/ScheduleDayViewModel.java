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
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;

public class ScheduleDayViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduleEvent>> scheduleEventList =
            new MutableLiveData<>();
    private Date day;
    private final GetEventsForDay getEventsForDay;

    public ScheduleDayViewModel(GetEventsForDay getEventsForDay) {
        this.getEventsForDay = getEventsForDay;
    }


    private void loadSchedule() {

        Callable<List<Event>> task;
        task = () -> getEventsForDay.getEvents(day);
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


}
