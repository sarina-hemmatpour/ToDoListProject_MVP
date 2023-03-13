package com.example.todolist_mvp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist_mvp.R;
import com.example.todolist_mvp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdaptor extends RecyclerView.Adapter<TaskAdaptor.TaskViewHolder> {

    private List<Task> tasks=new ArrayList<>();
    public TaskAdapterEventListener eventListener;

    public TaskAdaptor(TaskAdapterEventListener eventListener , Context context) {
        this.eventListener = eventListener;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindItem(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

     class TaskViewHolder extends RecyclerView.ViewHolder{

        ImageView checkbox;
        TextView title;
        View importance;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox=itemView.findViewById(R.id.task_iv_checkbox);
            title=itemView.findViewById(R.id.task_tv_title);
            importance=itemView.findViewById(R.id.task_view_importance);
        }

        public void bindItem(final Task task){
            title.setText(task.getTitle());

            if (task.isDone()){
                checkbox.setImageResource(R.drawable.ic_check_white_24dp);
                checkbox.setBackgroundResource(R.drawable.shape_checkbox_checked);
                title.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                checkbox.setBackgroundResource(R.drawable.shape_checkbox_default);
                checkbox.setImageResource(0);
                title.setPaintFlags(0);
            }

            switch (task.getImportance()){
                case LOW:
                    importance.setBackgroundResource(R.drawable.shape_importance_low_rec);
                    break;
                case NORMAL:
                    importance.setBackgroundResource(0);
                    break;
                case HIGH:
                    importance.setBackgroundResource(R.drawable.shape_importance_high_rec);
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventListener.onItemClicked(task);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eventListener.onItemClicked(task);
                    return false;
                }
            });

        }
    }


    public void addItem(Task task){
        tasks.add(task);
        notifyItemChanged(tasks.size()-1);
    }


    public void updateItem(Task task){
        int pos=-1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId())
            {
                pos=i;
                break;
            }
        }

        if (pos>-1)
        {
            tasks.set(pos , task);
        }

        notifyItemChanged(pos);

    }
    public void removeItem(Task task){
        int pos=-1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId()==task.getId())
            {
                pos=i;
                break;
            }
        }

        if (pos>-1)
        {
            tasks.remove(pos);
        }

        notifyItemRemoved(pos);

    }
    @SuppressLint("NotifyDataSetChanged")
    public void clearItems(){
        tasks.clear();
        notifyDataSetChanged();
    }

    interface TaskAdapterEventListener{
        void onItemClicked(Task task);
        void onItemLongClicked(Task task);
    }
}
