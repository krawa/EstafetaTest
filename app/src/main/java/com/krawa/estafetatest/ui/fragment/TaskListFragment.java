package com.krawa.estafetatest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.model.Task;
import com.krawa.estafetatest.network.RestClient;
import com.krawa.estafetatest.ui.adapter.TaskListAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TaskListAdapter.OnTaskClickListener {

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
        listAdapter.setOnTaskClickListener(this);
        list.setAdapter(listAdapter);
    }

    private void getTaskList() {
        RestClient.get().getTaskList().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (getActivity() == null) return;
                showListProgress(false);
                if (response.isSuccessful()) {
                    updateList(response.body());
                    if(response.headers().get("Warning") != null){
                        Toast.makeText(getActivity().getApplicationContext(), R.string.stale_warning, Toast.LENGTH_LONG).show();
                    }
                } else {
                    handleServerError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                if (getActivity() == null) return;
                showListProgress(false);
                String error;
                if (t instanceof IOException) {
                    error = getString(R.string.error_connect);
                } else {
                    error = t.getMessage();
                }
                showErrorMessage(error);
            }
        });
    }

    private void updateList(List<Task> items) {
        listAdapter.addAll(items);
        emptyText.setVisibility(listAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void showListProgress(final boolean show){
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(show);
            }
        });
    }

    private void handleServerError(Response<List<Task>> response) {
        String error;
        if (response.code() == 401) {
            error = getString(R.string.auth_error);
        }else{
            error = getString(R.string.server_error);
        }
        showErrorMessage(error);
    }

    private void showErrorMessage(String msg) {
        emptyText.setVisibility(listAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        emptyText.setText(getString(R.string.error)+". "+msg);

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void onRefresh() {
        emptyText.setVisibility(View.GONE);
        getTaskList();
    }

    @Override
    public void onTaskClick(Task task) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, DetailsTaskFragment.newInstance(task), DetailsTaskFragment.TAG)
                .addToBackStack(null)
                .commit();
    }
}
