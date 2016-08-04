package com.krawa.estafetatest.ui.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krawa.estafetatest.R;
import com.krawa.estafetatest.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SortedList<Task> mList;
    private final SimpleDateFormat formatDate;

    public TaskListAdapter() {

        formatDate = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

        mList = new SortedList<>(Task.class, new SortedListAdapterCallback<Task>(this) {
            @Override
            public int compare(Task item1, Task item2) {
                //Тут можно реализовать необходимую сортировку.
                //Сейчас как выдает сервер.
                return -1;
            }

            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                // return whether the items' visual representations are the same or not.
                //TODO check NULL
                return oldItem.getVin().equals(newItem.getVin())
                        && oldItem.getDriver().equals(newItem.getDriver())
                        && oldItem.getModel().equals(newItem.getModel())
                        && oldItem.getBrand().equals(newItem.getBrand())
                        && oldItem.getActualStartDate().equals(newItem.getActualStartDate());
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.getId() == item2.getId();
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Task curItem = mList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder)holder;
        viewHolder.vin.setText(curItem.getVin());
        viewHolder.car.setText(curItem.getBrand() + " " +curItem.getModel());
        viewHolder.driver.setText(curItem.getDriver());
        viewHolder.number.setText(curItem.getNumber());
        viewHolder.date.setText(formatDate.format(curItem.getActualStartDate()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<Task> items) {
        mList.beginBatchedUpdates();
        for(Task item : items){
            mList.add(item);
        }
        mList.endBatchedUpdates();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final TextView vin;
        private final TextView car;
        private final TextView driver;
        private final TextView number;

        public ItemViewHolder(View itemView) {
            super(itemView);

            vin = (TextView)itemView.findViewById(R.id.tvVin);
            car = (TextView)itemView.findViewById(R.id.tvCar);
            driver = (TextView)itemView.findViewById(R.id.tvDriver);
            number = (TextView)itemView.findViewById(R.id.tvNumder);
            date = (TextView)itemView.findViewById(R.id.tvDate);

        }
    }

}
