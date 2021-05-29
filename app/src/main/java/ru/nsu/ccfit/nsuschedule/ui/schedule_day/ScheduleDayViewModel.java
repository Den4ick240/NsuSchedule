package ru.nsu.ccfit.nsuschedule.ui.schedule_day;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.R;
import ru.nsu.ccfit.nsuschedule.domain.entities.Event;
import ru.nsu.ccfit.nsuschedule.domain.entities.EventOccurrence;
import ru.nsu.ccfit.nsuschedule.domain.repository.RepositoryException;
import ru.nsu.ccfit.nsuschedule.domain.usecases.AddEventFromDownloadedSchedule;
import ru.nsu.ccfit.nsuschedule.domain.usecases.GetEventsForDay;
import ru.nsu.ccfit.nsuschedule.domain.usecases.RemoveEvent;
import ru.nsu.ccfit.nsuschedule.domain.entities.ScheduleEvent;

public class ScheduleDayViewModel extends ViewModel {
    private final MutableLiveData<List<ScheduleEvent>> scheduleEventList =
            new MutableLiveData<>();
    private MutableLiveData<Void> navigateUpdate = new MutableLiveData<>();
    private Date day;
    private final GetEventsForDay getEventsForDay;
    private final AddEventFromDownloadedSchedule addEventFromDownloadedSchedule;
    private final RemoveEvent removeEvent;
    private final DateFormat timeFormat;
    public final int menuId;
    private final AppContainer appContainer;

    public ScheduleDayViewModel(GetEventsForDay getEventsForDay, AddEventFromDownloadedSchedule addEventFromDownloadedSchedule, RemoveEvent removeEvent, DateFormat timeFormat, int menuId, AppContainer appContainer) {
        this.getEventsForDay = getEventsForDay;
        this.addEventFromDownloadedSchedule = addEventFromDownloadedSchedule;
        this.removeEvent = removeEvent;
        this.timeFormat = timeFormat;
        this.menuId = menuId;
        this.appContainer = appContainer;
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

    public void handleContextItemSelected(int itemId, int position) {
        if (itemId == R.id.delete_event_item) {
            deleteEvent(position);
        }
        if (itemId == R.id.add_event_to_schedule_item) {
            new Thread(() -> {
                try {
                    addEventFromDownloadedSchedule.add(Objects.requireNonNull(scheduleEventList.getValue()).get(position).getEvent());
                    loadSchedule();
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            }
            ).start();
        }
        if (itemId == R.id.update_event_item) {
            new Thread(() -> {
                appContainer.initCreateEventViewModelFactory(
                        Objects.requireNonNull(scheduleEventList.getValue()).get(position).getEvent());
                navigateUpdate.postValue(null);
            }).start();
        }
    }

    public LiveData<Void> getNavigateUpdateLiveData() {
        return navigateUpdate;
    }

    public void clearNavigateUpdateLiveData() {
        navigateUpdate = new MutableLiveData<>();
    }
}
