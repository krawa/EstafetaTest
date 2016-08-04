package com.krawa.estafetatest.network;

import com.krawa.estafetatest.model.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestAPI {

    @GET("mobilesurveytasks/gettestsurveytasks")
    Call<List<Task>> getTaskList();

}
