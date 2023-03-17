package com.example.todolist_mvp.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.todolist_mvp.R;
import com.example.todolist_mvp.detail.Detail;
import com.example.todolist_mvp.model.AppDataBase;
import com.example.todolist_mvp.model.Task;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdaptor.TaskAdapterEventListener {

    private MainPresenter mainPresenter;

    private final static int REQUEST_CODE=1000;

    public final static String EXTRA_KEY_TASK="task";

    private LinearLayout fullState;
    private LinearLayout emptyState;

    RecyclerView rvTasks;
    TaskAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter=new MainPresenter(this , AppDataBase.getAppDataBase(this ).getTaskDao());

        MaterialButton btnAddTask=findViewById(R.id.btn_main_addTask);
        MaterialButton btnDeleteAllTasks=findViewById(R.id.btn_main_deleteAll);
        EditText etSearch=findViewById(R.id.et_main_search);
        emptyState=findViewById(R.id.layout_main_emptyState);
        fullState=findViewById(R.id.layout_main_fullState);

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

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mainPresenter.onSearch(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mainPresenter.onAttach(this);

    }

    @Override
    public void showTasks(List<Task> tasks) {
        adaptor.setTasks(tasks);
    }

    @Override
    public void clearAllTasks() {
        adaptor.clearItems();
        mainPresenter.setTasks(adaptor.getTasks());
    }

    @Override
    public void addTask(Task task) {
        adaptor.addItem(task);
        mainPresenter.setTasks(adaptor.getTasks());
    }

    @Override
    public void updateTask(Task task) {
        adaptor.updateItem(task);
        mainPresenter.setTasks(adaptor.getTasks());
    }

    @Override
    public void deleteTask(Task task) {
        adaptor.removeItem(task);
        mainPresenter.setTasks(adaptor.getTasks());
    }

    @Override
    public void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        fullState.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyState() {
        emptyState.setVisibility(View.GONE);
        fullState.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDetailActivity() {

        startActivityForResult(new Intent(MainActivity.this , Detail.class) , REQUEST_CODE);

    }

    @Override
    public void loadDetailActivity(Task task) {

        Intent intentTask=new Intent(MainActivity.this , Detail.class);
        intentTask.putExtra(EXTRA_KEY_TASK , task);

        startActivityForResult(intentTask, REQUEST_CODE);

    }

    @Override
    public void onItemClicked(Task task) {
        mainPresenter.onTaskClicked(task);
    }

    @Override
    public void onItemLongClicked(Task task) {
        mainPresenter.onTaskLongClicked(task);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && data!=null){
            Task resultTask=data.getParcelableExtra(Detail.EXTRA_KEY_DETAIL_TASK);
            if (resultTask!=null){
                mainPresenter.onResultReceived(resultCode , resultTask);
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDetach();
    }
}
