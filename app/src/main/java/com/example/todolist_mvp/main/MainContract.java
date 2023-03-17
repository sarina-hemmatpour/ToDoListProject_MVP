package com.example.todolist_mvp.main;

import com.example.todolist_mvp.BasePresenter;
import com.example.todolist_mvp.BaseView;
import com.example.todolist_mvp.model.Task;

import java.util.List;

public interface MainContract {
    interface View extends BaseView {

        void showTasks(List<Task> tasks); //presenter calls this method
        void clearAllTasks();
        void addTask(Task task);
        void updateTask(Task task);
        void deleteTask(Task task);
        void showEmptyState();
        void hideEmptyState();

        void loadDetailActivity();

        void loadDetailActivity(Task task);

        //void sedTaskState(boolean isDone); //its the same as updateTask()



    }
    interface Presenter extends BasePresenter<View> {
        void onDeleteAllBtnClicked();
        void onTaskClicked(Task task);

        //this method can be removed
        void onTaskLongClicked(Task task);

        void onAddTaskBtnClicked();
        void onSearch(String query);

        void onResultReceived(int code , Task newTask);

    }
}
