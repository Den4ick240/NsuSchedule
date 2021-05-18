package ru.nsu.ccfit.nsuschedule.ui.import_schedule;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class ImportScheduleFragment extends Fragment implements View.OnClickListener {

    private ImportScheduleViewModel viewModel;
    private TextInputEditText urlTextEdit;

    public static ImportScheduleFragment newInstance() {
        return new ImportScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import_schedule, container, false);
        view.findViewById(R.id.download_url_button).setOnClickListener(this);
        urlTextEdit = view.findViewById(R.id.download_url_edit_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                new ViewModelProvider(this,
                        ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer().importScheduleViewModelFactory
                ).get(ImportScheduleViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download_url_button) {
            viewModel.downloadFromUrl(Optional.ofNullable(urlTextEdit.getText())
                    .map(Object::toString).orElse(""));
        }
    }
}