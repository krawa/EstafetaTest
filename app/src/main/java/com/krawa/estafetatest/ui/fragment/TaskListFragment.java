package com.krawa.estafetatest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.ui.adapter.TaskListAdapter;

public class TaskListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "TaskListFragment";
    private RecyclerView list;
    private SwipeRefreshLayout refreshLayout;
    private TextView emptyText;
    private TaskListAdapter listAdapter;

    public TaskListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);

        list = (RecyclerView) v.findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        emptyText = (TextView) v.findViewById(R.id.tvEmptyText);

        setupList();

        showListProgress(true);

        getTaskList();

        return v;
    }

    protected void setupList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);

        listAdapter = new TaskListAdapter();
        list.setAdapter(listAdapter);
    }

    private void getTaskList() {

    }

    private void showListProgress(final boolean show){
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void onRefresh() {
        emptyText.setVisibility(View.GONE);
        getTaskList();
    }

}
