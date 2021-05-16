package ru.nsu.ccfit.nsuschedule.ui.create_event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private CreateEventViewModel viewModel;
    private TextInputLayout summary;
    private TextInputLayout description;
    private TextInputLayout location;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;
    private Button dateButton;
    private Button endTimeButton;
    private Button startTimeButton;


    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button) view.findViewById(R.id.create_event_button)).setOnClickListener(this);

        dateButton = view.findViewById(R.id.date_picker_button);
        dateButton.setOnClickListener(this);

        summary = view.findViewById(R.id.summary_text_input);
        description = view.findViewById(R.id.description_text_input);
        location = view.findViewById(R.id.location_text_input);
        endTimeButton = view.findViewById(R.id.end_time_button);
        startTimeButton = view.findViewById(R.id.start_time_button);

        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(
                requireActivity(),
                ((ApplicationWithAppContainer) Objects.requireNonNull(getActivity()).getApplication()).getAppContainer().createEventViewModelFactory
        ).get(CreateEventViewModel.class);
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
                summary.getEditText().getText().toString(),
                description.getEditText().getText().toString(),
                location.getEditText().getText().toString());
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

}