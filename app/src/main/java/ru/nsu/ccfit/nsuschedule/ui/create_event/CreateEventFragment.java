package ru.nsu.ccfit.nsuschedule.ui.create_event;

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

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private CreateEventViewModel viewModel;
    private TextInputLayout summary;
    private TextInputLayout description;
    private TextInputLayout location;


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
        Button button = view.findViewById(R.id.create_event_button);
        button.setOnClickListener(this);
        summary = view.findViewById(R.id.summary_text_input);
        description = view.findViewById(R.id.description_text_input);
        location = view.findViewById(R.id.location_text_input);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(
                this,
                ((ApplicationWithAppContainer) getActivity().getApplication()).getAppContainer().createEventViewModelFactory
        ).get(CreateEventViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_event_button) {
            viewModel.addEvent(summary.getEditText().getText().toString(), description.getEditText().getText().toString(), location.getEditText().getText().toString());
        }
    }
}