package com.example.todolist_mvp.detail;

import com.example.todolist_mvp.model.Task;
import com.example.todolist_mvp.model.TaskDao;

public class DetailPresenter implements DetailContract.Presenter {

    private TaskDao taskDao;
    private DetailContract.View view;
    private Task task;


    public final static int RESULT_CANCELED=0;
    public final static int RESULT_OK=-1;

    public DetailPresenter(TaskDao taskDao, DetailContract.View view) {
        this.taskDao = taskDao;
        this.view = view;
    }

    public DetailPresenter(TaskDao taskDao, DetailContract.View view, Task task) {
        this.taskDao = taskDao;
        this.view = view;
        this.task = task;
    }

    @Override
    public void onAttach(DetailContract.View view) {
        if (task!=null){
            view.showTask(task);
            view.setDeleteBtnVisibility(true);
        }else {
            view.setDeleteBtnVisibility(false);
        }
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveBtnClicked(String title, Task.ImportanceLevel importanceLevel) {
        if (title.length()==0){
            view.showDialog("Please Write a Title.");
            return;
        }

        if (this.task==null){
            Task newTask=new Task(title , importanceLevel);
            long id=taskDao.add(newTask);
            newTask.setId(id);
            taskDao.update(newTask);
            view.finishActivity(RESULT_OK , newTask, Detail.RESULT_ADD_TASK);
        }else {
            task.setTitle(title);
            task.setImportance(importanceLevel);
            taskDao.update(task);
            view.finishActivity(RESULT_OK , task,Detail.RESULT_UPDATE_TASK );
        }


    }

    @Override
    public void onDeleteBtnClicked(Task task) {
        taskDao.delete(task);
        view.finishActivity(RESULT_OK , task, Detail.RESULT_DELETE_TASK);
    }

    @Override
    public void onBackBtnClicked() {
        view.finishActivity(RESULT_CANCELED , null , -1000);
    }

    @Override
    public void onActivityHasTask(Task task) {
        view.showTask(task);
        view.setDeleteBtnVisibility(true);
    }

    @Override
    public void onActivityHasNoTask() {
        view.setDeleteBtnVisibility(false);
    }
}
