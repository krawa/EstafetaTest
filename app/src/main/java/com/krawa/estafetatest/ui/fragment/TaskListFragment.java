package com.krawa.estafetatest.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.model.Task;
import com.krawa.estafetatest.ui.adapter.TaskListAdapter;
import com.krawa.estafetatest.ui.custom.DividerItemDecoration;

import java.util.List;

public class TaskListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        TaskListAdapter.OnTaskClickListener {

    public static final String TAG = "TaskListFragment";
    private RecyclerView list;
    private SwipeRefreshLayout refreshLayout;
    private TextView emptyText;
    private TaskListAdapter listAdapter;
    private TaskListFragmentInterface callback;

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

        if(callback != null) callback.getTaskList();

        return v;
    }

    protected void setupList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        list.addItemDecoration(new DividerItemDecoration(getActivity()));

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);

        listAdapter = new TaskListAdapter();
        listAdapter.setOnTaskClickListener(this);
        list.setAdapter(listAdapter);
    }

    public void updateList(List<Task> items) {
        listAdapter.addAll(items);
        setEmptyText(null);
    }

    public void showListProgress(final boolean show){
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
        if(callback != null) callback.getTaskList();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.task_list_title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        toolbar.setNavigationOnClickListener(null);
    }

    @Override
    public void onTaskClick(Task task) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, DetailsTaskFragment.newInstance(task), DetailsTaskFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (TaskListFragmentInterface) activity;
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public void setEmptyText(String msg) {
        emptyText.setVisibility(listAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        emptyText.setText(msg != null ? msg : getString(R.string.empty_list));
    }

    public interface TaskListFragmentInterface{
        void getTaskList();
    }
}
