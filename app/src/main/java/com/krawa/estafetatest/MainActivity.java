package com.krawa.estafetatest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.krawa.estafetatest.model.Task;
import com.krawa.estafetatest.network.RestAPI;
import com.krawa.estafetatest.ui.fragment.TaskListFragment;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TaskListFragment.TaskListFragmentInterface{

    @Inject
    RestAPI restAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new TaskListFragment(), TaskListFragment.TAG)
                    .commit();
        }

        App.getAppComponent().inject(this);
    }

    @Override
    public void getTaskList() {
        restAPI.getTaskList().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                TaskListFragment taskList = getTaskListFragment();
                if(taskList != null){
                    taskList.showListProgress(false);
                    if (response.isSuccessful()) {
                        taskList.updateList(response.body());
                        if(response.headers().get("Warning") != null){
                            Toast.makeText(getApplicationContext(), R.string.stale_warning, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String error = handleServerError(response);
                        taskList.setEmptyText(getString(R.string.error_and_msg, error));
                        showErrorMessage(error);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                TaskListFragment taskList = getTaskListFragment();
                if(taskList != null) {
                    taskList.showListProgress(false);
                    String error = handleFailure(t);
                    taskList.setEmptyText(getString(R.string.error_and_msg, error));
                    showErrorMessage(error);
                }
            }
        });
    }

    private TaskListFragment getTaskListFragment() {
        Fragment taskList = getSupportFragmentManager().findFragmentByTag(TaskListFragment.TAG);
        if(taskList != null){
            return (TaskListFragment) taskList;
        }
        return null;
    }

    private String handleServerError(Response<List<Task>> response) {
        String error;
        if (response.code() == 401) {
            error = getString(R.string.auth_error);
        }else{
            error = getString(R.string.server_error);
        }
        return error;
    }

    private String handleFailure(Throwable t) {
        String error;
        if (t instanceof IOException) {
            error = getString(R.string.error_connect);
        } else {
            error = t.getMessage();
        }
        return error;
    }

    private void showErrorMessage(String msg) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
