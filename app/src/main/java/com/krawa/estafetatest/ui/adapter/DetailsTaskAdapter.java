package com.krawa.estafetatest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Item> mList;
    private final SimpleDateFormat formatDate;
    private final Context ctx;

    public DetailsTaskAdapter(Context ctx) {
        this.ctx = ctx;
        formatDate = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());
        mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item curItem = mList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder)holder;
        viewHolder.name.setText(curItem.name);
        viewHolder.value.setText(curItem.value);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setTask(Task task) {
        mList.clear();

        if(task.getPlannedStartDate() != null)
            mList.add(new Item(ctx.getString(R.string.planned_start_date), formatDate.format(task.getPlannedStartDate())));
        if(task.getPlannedEndDate() != null)
            mList.add(new Item(ctx.getString(R.string.planned_end_date), formatDate.format(task.getPlannedEndDate())));
        if(task.getActualStartDate() != null)
            mList.add(new Item(ctx.getString(R.string.actual_start_date), formatDate.format(task.getActualStartDate())));
        if(task.getActualEndDate() != null)
            mList.add(new Item(ctx.getString(R.string.actual_end_date), formatDate.format(task.getActualEndDate())));

        mList.add(new Item(ctx.getString(R.string.vin), task.getVin()));
        mList.add(new Item(ctx.getString(R.string.brand), task.getBrand()));
        mList.add(new Item(ctx.getString(R.string.model), task.getModel()));
        mList.add(new Item(ctx.getString(R.string.model_code), task.getModelCode()));
        mList.add(new Item(ctx.getString(R.string.survey_point), task.getSurveyPoint()));
        mList.add(new Item(ctx.getString(R.string.driver), task.getDriver()));
        mList.add(new Item(ctx.getString(R.string.carrier), task.getCarrier()));
        mList.add(new Item(ctx.getString(R.string.number), task.getNumber()));
    }

    private class Item {
        private String name;
        private String value;

        public Item(String name, String value){
            this.name = name;
            this.value = value;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView value;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvName);
            value = (TextView)itemView.findViewById(R.id.tvValue);
        }
    }
}
