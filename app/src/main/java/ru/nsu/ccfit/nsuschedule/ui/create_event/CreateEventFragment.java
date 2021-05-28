package ru.nsu.ccfit.nsuschedule.ui.create_event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class CreateEventFragment extends BaseEventFragment {
    private Snackbar snackbar;

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        snackbar = Snackbar.make(
                view.findViewById(R.id.coordinator_layout),
                getResources().getString(R.string.snackbar_event_created),
                BaseTransientBottomBar.LENGTH_LONG);
        return view;
    }

    @Override
    protected int getTextId() {
        return R.string.create_event_button_title;
    }

    @Override
    protected void onEventCreated(Void unused) {
        showEventCreatedSnackBar();
    }

    @Override
    protected CreateEventViewModel getViewModel() {
        AppContainer appContainer = ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer();
        return new ViewModelProvider(
                requireActivity(),
                appContainer.createEventViewModelFactory
        ).get(CreateEventViewModel.class);
    }

    private void showEventCreatedSnackBar() {
        snackbar.show();
    }
}