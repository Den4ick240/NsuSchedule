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

import com.google.android.material.textfield.TextInputLayout;

import java.util.Optional;

import ru.nsu.ccfit.nsuschedule.R;

public abstract class BaseEventFragment extends Fragment implements View.OnClickListener {

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

    public static CreateEventFragment newInstance() {
        return new CreateEventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        Button createButton = view.findViewById(R.id.create_event_button);
        createButton.setText(getTextId());
        createButton.setOnClickListener(this);

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = getViewModel();
        viewModel.getEventCreated().observe(getViewLifecycleOwner(), this::onEventCreated);
        summary.getEditText().setText(viewModel.getSummary());
        description.getEditText().setText(viewModel.getDescription());
        location.getEditText().setText(viewModel.getLocation());
        repeating.setText(viewModel.getRepeating());
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

    protected abstract int getTextId();

    protected abstract void onEventCreated(Void unused);

    protected abstract CreateEventViewModel getViewModel();

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
}
