package ru.nsu.ccfit.nsuschedule.ui.create_event;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class UpdateEventFragment extends BaseEventFragment {
    @Override
    protected int getTextId() {
        return R.string.update_event_button_title;
    }

    @Override
    protected void onEventCreated(Void unused) {
        Navigation.findNavController(getView()).navigate(R.id.weekScheduleFragment);
    }

    @Override
    protected CreateEventViewModel getViewModel() {
        AppContainer appContainer = ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer();
        return new ViewModelProvider(
                requireActivity(),
                appContainer.getUpdateEventViewModelFactory()
        ).get(CreateEventViewModel.class);
    }
}
