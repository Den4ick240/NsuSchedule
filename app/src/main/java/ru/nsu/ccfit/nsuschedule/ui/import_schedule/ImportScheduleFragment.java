package ru.nsu.ccfit.nsuschedule.ui.import_schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;

public class ImportScheduleFragment extends Fragment implements View.OnClickListener {

    private ImportScheduleViewModel viewModel;
    private TextInputEditText urlTextEdit;
    private TextInputEditText groupNumberTextEdit;

    public static ImportScheduleFragment newInstance() {
        return new ImportScheduleFragment();
    }

    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_import_schedule, container, false);
        view.findViewById(R.id.download_url_button).setOnClickListener(this);
        view.findViewById(R.id.download_nsu_button).setOnClickListener(this);
        urlTextEdit = view.findViewById(R.id.download_url_edit_text);
        groupNumberTextEdit = view.findViewById(R.id.group_number_text_edit);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                new ViewModelProvider(this,
                        ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer().importScheduleViewModelFactory
                ).get(ImportScheduleViewModel.class);
        viewModel.getScheduleReady().observe(getViewLifecycleOwner(), this::onScheduleReady);
    }

    private void onScheduleReady(Void unused) {
//        ((NavHostFragment)getParentFragmentManager().findFragmentById(R.id.nav_host_fragment))//cannot be cast to navhostfragment
//                .getNavController()
        Navigation.findNavController(view)
                .navigate(R.id.action_importScheduleFragment_to_downloadedScheduleFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download_url_button) {
            Optional.ofNullable(urlTextEdit.getText())
                    .map(Object::toString)
                    .ifPresent(viewModel::downloadFromUrl);
        }
        if (v.getId() == R.id.download_nsu_button) {
            Optional.ofNullable(groupNumberTextEdit.getText())
                    .map(Object::toString)
                    .ifPresent(viewModel::downloadFromNsu);
        }
    }
}