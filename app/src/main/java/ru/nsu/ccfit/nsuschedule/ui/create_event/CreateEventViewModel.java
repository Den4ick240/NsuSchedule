package ru.nsu.ccfit.nsuschedule.ui.create_event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

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

    public CreateEventViewModel(AddEvent addEvent) {
        this.addEvent = addEvent;
    }

    public void addEvent(String summary, String description, String location) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Runnable task = () ->
        {
            try {
                addEvent.add(
                        new Event(
                                new EventInfo(summary, description, location),
                                new EventDate(calendar.getTime(), calendar.getTime(), Repeating.ONCE)));
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
}