package com.example.todolist_mvp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todolist_mvp.R;
import com.example.todolist_mvp.model.Task;
import com.example.todolist_mvp.model.TaskDao;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdaptor.TaskAdapterEventListener {

    static MainPresenter mainPresenter;

    RecyclerView rvTasks;
    TaskAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mainPresenter==null){
           // mainPresenter=new MainPresenter(this,);
        }

        MaterialButton btnAddTask=findViewById(R.id.btn_main_addTask);
        MaterialButton btnDeleteAllTasks=findViewById(R.id.btn_main_deleteAll);
        EditText etSearch=findViewById(R.id.et_main_search);

        rvTasks=findViewById(R.id.rv_main_tasks);
        adaptor=new TaskAdaptor(this , this);
        rvTasks.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL , false));
        rvTasks.setAdapter(adaptor);


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.onAddTaskBtnClicked();
            }
        });

        btnDeleteAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.onDeleteAllBtnClicked();
            }
        });



    }

    @Override
    public void showTasks(List<Task> tasks) {

    }

    @Override
    public void clearAllTasks() {

    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void showEmptyState() {

    }

    @Override
    public void hideEmptyState() {

    }

    @Override
    public void onItemClicked(Task task) {
        mainPresenter.onTaskClicked(task);
    }

    @Override
    public void onItemLongClicked(Task task) {
        mainPresenter.onTaskLongClicked(task);
    }



}
