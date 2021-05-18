package ru.nsu.ccfit.nsuschedule.ui.create_event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Optional;

import ru.nsu.ccfit.nsuschedule.AppContainer;
import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private CreateEventViewModel viewModel;
    private TextInputLayout summary;
    private TextInputLayout description;
    private TextInputLayout location;
    private AutoCompleteTextView repeating;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;
    private Button dateButton;
    private Button endTimeButton;
    private Button startTimeButton;
    private Snackbar snackbar;

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event_fragment, container, false);
        ((Button) view.findViewById(R.id.create_event_button)).setOnClickListener(this);

        dateButton = view.findViewById(R.id.date_picker_button);
        dateButton.setOnClickListener(this);

        summary = view.findViewById(R.id.summary_text_input);
        description = view.findViewById(R.id.description_text_input);
        location = view.findViewById(R.id.location_text_input);
        endTimeButton = view.findViewById(R.id.end_time_button);
        startTimeButton = view.findViewById(R.id.start_time_button);
        repeating = view.findViewById(R.id.repeating_text_view);

        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);

        String[] dropDownItems = getResources().getStringArray(R.array.repeating_enum_translations);
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<>(requireContext(), R.layout.repeating_dropdown_item, dropDownItems);
        repeating.setAdapter(dropDownAdapter);

        snackbar = Snackbar.make(
                view.findViewById(R.id.coordinator_layout),
                getResources().getString(R.string.snackbar_event_created), BaseTransientBottomBar.LENGTH_LONG);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppContainer appContainer = ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer();
        viewModel = new ViewModelProvider(
                requireActivity(),
                appContainer.createEventViewModelFactory
        ).get(CreateEventViewModel.class);
        viewModel.getEventCreated().observe(getViewLifecycleOwner(), unused -> showEventCreatedSnackBar());
        initDatePicker();
        initEndTimePicker();
        initStartTimePicker();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_event_button) {
            addEvent();
        }
        if (v.getId() == R.id.date_picker_button) {
            datePickerDialog.show();
        }
        if (v.getId() == R.id.start_time_button) {
            startTimePickerDialog.show();
        }
        if (v.getId() == R.id.end_time_button) {
            endTimePickerDialog.show();
        }
    }

    private void addEvent() {
        viewModel.addEvent(
                getText(summary),
                getText(description),
                getText(location),
                repeating.getText().toString());
    }

    private String getText(TextInputLayout input) {
        return Optional.ofNullable(input.getEditText())
                .map(t -> t.getText().toString())
                .orElse("");
    }

    private void initDatePicker() {
        viewModel.getSelectedDateString().observe(getViewLifecycleOwner(), dateButton::setText);
        datePickerDialog = new DatePickerDialog(
                getActivity(),
                (datePicker, year, month, day) -> viewModel.onDateSet(year, month, day),
                viewModel.getCurrentYear(),
                viewModel.getCurrentMonth(),
                viewModel.getCurrentDay());
    }

    private void initStartTimePicker() {
        viewModel.getStartTime().observe(getViewLifecycleOwner(), startTimeButton::setText);
        startTimePickerDialog = new TimePickerDialog(
                getActivity(),
                (view, hourOfDay, minute) -> viewModel.onStartTimeSet(hourOfDay, minute),
                viewModel.getStartHour(),
                viewModel.getStartMinute(),
                true);
    }

    private void initEndTimePicker() {
        viewModel.getEndTime().observe(getViewLifecycleOwner(), endTimeButton::setText);
        endTimePickerDialog = new TimePickerDialog(
                getActivity(),
                (view, hourOfDay, minute) -> viewModel.onEndTimeSet(hourOfDay, minute),
                viewModel.getEndHour(),
                viewModel.getEndMinute(),
                true);
    }

    private void showEventCreatedSnackBar() {
        snackbar.show();
    }
}