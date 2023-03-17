package com.example.todolist_mvp.main;

import com.example.todolist_mvp.detail.Detail;
import com.example.todolist_mvp.model.Task;
import com.example.todolist_mvp.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private TaskDao taskDao;
    private MainContract.View view;

    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public MainPresenter(MainContract.View view, TaskDao taskDao) {
        this.taskDao = taskDao;
        this.view=view;
        this.tasks=taskDao.getAll();
    }

    @Override
    public void onDeleteAllBtnClicked() {

        taskDao.deleteAll();
        view.clearAllTasks();
        view.showEmptyState();

    }

    @Override
    public void onTaskClicked(Task task) {
        task.setDone(!task.isDone());
        taskDao.update(task);
        view.updateTask(task);
    }

    @Override
    public void onTaskLongClicked(Task task) {
        view.loadDetailActivity(task);
    }

    @Override
    public void onAddTaskBtnClicked() {
        view.loadDetailActivity();

    }

    @Override
    public void onSearch(String query) {
        if (query.length()>0){
            view.showTasks(taskDao.search(query));
        }
        else {
            view.showTasks(this.getTasks());
        }
    }

    @Override
    public void onResultReceived(int code, Task resultTask) {
        if (code== Detail.RESULT_ADD_TASK){ //add
            view.addTask(resultTask);
            view.hideEmptyState();
        } else if (code == Detail.RESULT_UPDATE_TASK) {
            view.updateTask(resultTask);
        } else if (code == Detail.RESULT_DELETE_TASK) {
            view.deleteTask(resultTask);
            if (tasks.size()==0)
                view.showEmptyState();
            else
                view.hideEmptyState();
        }
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
