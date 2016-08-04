package com.krawa.estafetatest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.model.Task;
import com.krawa.estafetatest.ui.adapter.DetailsTaskAdapter;

public class DetailsTaskFragment extends Fragment{

    public static final String TAG = "DetailsTaskFragment";

    private static final String EXTRA_TASK = "task";
    private RecyclerView list;
    private DetailsTaskAdapter listAdapter;

    public static Fragment newInstance(Task task) {
        Fragment fragment = new DetailsTaskFragment();
        Bundle args = new Bundle(1);
        args.putSerializable(EXTRA_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsTaskFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        list = (RecyclerView) v.findViewById(R.id.list);

        setupList();

        bindTask((Task) getArguments().getSerializable(EXTRA_TASK));

        return v;
    }

    protected void setupList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        listAdapter = new DetailsTaskAdapter(getActivity().getApplicationContext());
        list.setAdapter(listAdapter);
    }

    private void bindTask(Task task) {
        listAdapter.setTask(task);
    }

}
