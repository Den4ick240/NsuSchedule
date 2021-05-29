package ru.nsu.ccfit.nsuschedule.ui.schedule_day;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.nsu.ccfit.nsuschedule.ApplicationWithAppContainer;
import ru.nsu.ccfit.nsuschedule.R;
import ru.nsu.ccfit.nsuschedule.domain.entities.ScheduleEvent;


public class ScheduleDayFragment extends Fragment {

    private static final String DAY_PARAM = "day";
    private static final String FLOW_NAME_PARAM = "flow_name";
    private Date day;
    private ScheduleDayViewModel model;
    private String flowName;
    private boolean visible;

    public static ScheduleDayFragment newInstance(Date day, String flowName) {
        ScheduleDayFragment fragment = new ScheduleDayFragment();
        Bundle args = new Bundle();
        args.putSerializable(DAY_PARAM, day);
        args.putString(FLOW_NAME_PARAM, flowName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateContextMenu(@NonNull @NotNull ContextMenu menu, @NonNull @NotNull View v,
                                    @Nullable @org.jetbrains.annotations.Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(model.menuId, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (!visible) return false;

        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
            model.handleContextItemSelected(item.getItemId(), ((AdapterView.AdapterContextMenuInfo) menuInfo).position);
        }
        return true;
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        visible = menuVisible;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = (Date) getArguments().getSerializable(DAY_PARAM);
            flowName = getArguments().getString(FLOW_NAME_PARAM);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.list_view);
        listView.setOnCreateContextMenuListener(this);
        ViewModelProvider.Factory factory = ((ApplicationWithAppContainer) requireActivity().getApplication()).getAppContainer()
                .getScheduleDayViewModelFactory(flowName);
        model = new ViewModelProvider(this, factory).get(ScheduleDayViewModel.class);
        DaysAdapter adapter = new DaysAdapter();
        model.setDay(day);
        model.getScheduleEventList().observe(getViewLifecycleOwner(), adapter::setEventList);
        model.getNavigateUpdateLiveData().observe(getViewLifecycleOwner(), unused ->
        {
            model.clearNavigateUpdateLiveData();
            Navigation.findNavController(view).navigate(R.id.updateEventFragment);
        });
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