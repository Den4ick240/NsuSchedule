package ru.nsu.ccfit.nsuschedule.ui.week_schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;

import java.util.Date;

import ru.nsu.ccfit.nsuschedule.R;


public class WeekTabsFragment extends Fragment {
    interface OnPositionSelectedListener {
        void selected(int position);
    }
    private static final String FIRST_DAY_ARG = "first_day";
    private static final String NUMBER_OF_DAYS_ARG = "number_of_days";
    public static WeekTabsFragment newInstance(Date firstDate, int numberOfDays) {
        Bundle bundle = new Bundle();
        WeekTabsFragment weekTabsFragment = new WeekTabsFragment();
        bundle.putSerializable(FIRST_DAY_ARG, firstDate);
        bundle.putInt(NUMBER_OF_DAYS_ARG, numberOfDays);
        weekTabsFragment.setArguments(bundle);
        return weekTabsFragment;

    }

    private Date firstDayDate;
    private int numberOfDays;
    private TabLayout tabs;
    private OnPositionSelectedListener onPositionSelectedListener = unused -> {};

    public void setOnPositionSelectedListener(OnPositionSelectedListener onPositionSelectedListener) {
        this.onPositionSelectedListener = onPositionSelectedListener;
    }

    public void selectTab(int i) {
        tabs.selectTab(tabs.getTabAt(i));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            firstDayDate = (Date) getArguments().getSerializable(FIRST_DAY_ARG);
            numberOfDays = getArguments().getInt(NUMBER_OF_DAYS_ARG);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WeekTabsViewModel model = new ViewModelProvider(this).get(WeekTabsViewModel.class);

        tabs = view.findViewById(R.id.week_day_tabs);

        for (CharSequence name : model.getTabNames(firstDayDate, numberOfDays)) {
            tabs.addTab(tabs.newTab().setText(name));
        }
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onPositionSelectedListener.selected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //unused
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //unused
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_tab, container, false);
    }
}