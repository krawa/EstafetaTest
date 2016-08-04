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
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SortedList<Task> mList;
    private final SimpleDateFormat formatDate;
    private OnTaskClickListener onTaskClickListener;

    public TaskListAdapter() {

        formatDate = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault());

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
                if (oldItem == null || newItem == null) return false;

                if (oldItem.getVin() == null) {
                    if (newItem.getVin() != null) return false;
                } else {
                    if (!oldItem.getVin().equals(newItem.getVin())) return false;
                }

                if (oldItem.getDriver() == null) {
                    if (newItem.getDriver() != null) return false;
                } else {
                    if (!oldItem.getDriver().equals(newItem.getDriver())) return false;
                }

                if (oldItem.getModel() == null) {
                    if (newItem.getModel() != null) return false;
                } else {
                    if (!oldItem.getModel().equals(newItem.getModel())) return false;
                }

                if (oldItem.getBrand() == null) {
                    if (newItem.getBrand() != null) return false;
                } else {
                    if (!oldItem.getBrand().equals(newItem.getBrand())) return false;
                }

                if (oldItem.getActualStartDate() == null) {
                    if (newItem.getActualStartDate() != null) return false;
                } else {
                    if (!oldItem.getActualStartDate().equals(newItem.getActualStartDate())) return false;
                }

                return true;
            }

            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return !(item1 == null || item2 == null) && item1.getId() == item2.getId();
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

        //Удаляем элементы которых больше нет
        for (int i = mList.size() - 1; i >= 0; i--) {
            boolean contain = false;
            for(Task item : items){
                if(item.getId() == mList.get(i).getId()){
                    contain = true;
                    break;
                }
            }
            if(!contain) mList.removeItemAt(i);
        }

        //Дабавляем или обновляем элементы
        for(Task newItem : items){
            int pos = SortedList.INVALID_POSITION;
            for(int i = 0; i < mList.size(); i++){
                if(newItem.getId() == mList.get(i).getId()){
                    pos = i;
                    break;
                }
            }
            if(pos != SortedList.INVALID_POSITION){
                mList.updateItemAt(pos, newItem);
            }else{
                mList.add(newItem);
            }
        }

        mList.endBatchedUpdates();
    }

    public void setOnTaskClickListener(OnTaskClickListener onTaskClickListener){
        this.onTaskClickListener = onTaskClickListener;
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

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTaskClickListener != null)
                        onTaskClickListener.onTaskClick(mList.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface OnTaskClickListener{
        void onTaskClick(Task task);
    }

}
