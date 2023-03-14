package com.example.todolist_mvp.main;

import com.example.todolist_mvp.model.Task;
import com.example.todolist_mvp.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private TaskDao taskDao;
    private MainContract.View view;

    private List<Task> tasks;

    public MainPresenter(MainContract.View view, TaskDao taskDao) {
        this.taskDao = taskDao;
        this.view=view;
        this.tasks=taskDao.getAll();
    }

    @Override
    public void onDeleteAllBtnClicked() {

    }

    @Override
    public void onTaskClicked(Task task) {

    }

    @Override
    public void onTaskLongClicked(Task task) {

    }

    @Override
    public void onAddTaskBtnClicked() {
        view.loadDetailActivity();

    }

    @Override
    public List<Task> onSearch(String query) {
        return null;
    }

    @Override
    public void onAttach(MainContract.View view) {
        if (tasks.size()!=0){
            view.hideEmptyState();
            view.showTasks(tasks);
        }
        else{
            view.showEmptyState();
        }
    }

    @Override
    public void onDetach() {

    }
}
