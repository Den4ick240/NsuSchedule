package ru.nsu.ccfit.nsuschedule.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.R;


public class ScheduleDayFragment extends Fragment {

    private static final String DAY_PARAM = "day";
    private Date day;

    public static ScheduleDayFragment newInstance(Date day) {
        ScheduleDayFragment fragment = new ScheduleDayFragment();
        Bundle args = new Bundle();
        args.putSerializable(DAY_PARAM, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = (Date) getArguments().getSerializable(DAY_PARAM);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.list_view);

        ScheduleDayViewModel model = new ViewModelProvider(this).get(ScheduleDayViewModel.class);
        DaysAdapter adapter = new DaysAdapter();
        model.setDay(day);
        model.getScheduleEventList().observe(getViewLifecycleOwner(), adapter::setEventList);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule_day, container, false);
    }

    class DaysAdapter extends BaseAdapter {
        private List<ScheduleEvent> eventList = new ArrayList<>();

        public void setEventList(List<ScheduleEvent> eventList) {
            this.eventList = eventList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return eventList.size();
        }

        @Override
        public ScheduleEvent getItem(int position) {
            return eventList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View item, ViewGroup parent) {
            if (item == null) {
                item = getLayoutInflater().inflate(R.layout.fragment_schedule_item, parent, false);
            }
            ScheduleEvent event = eventList.get(position);
            ((TextView) item.findViewById(R.id.description)).setText(event.getDescription());
            ((TextView) item.findViewById(R.id.summary)).setText(event.getSummary());
            ((TextView) item.findViewById(R.id.time)).setText(event.getTime());
            ((TextView) item.findViewById(R.id.location)).setText(event.getLocation());
            return item;
        }

    }
}