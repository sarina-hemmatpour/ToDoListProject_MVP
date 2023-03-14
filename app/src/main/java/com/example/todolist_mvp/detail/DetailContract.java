package com.example.todolist_mvp.detail;

import androidx.annotation.Nullable;

import com.example.todolist_mvp.BasePresenter;
import com.example.todolist_mvp.BaseView;
import com.example.todolist_mvp.model.Task;

public interface DetailContract {

    interface View extends BaseView {
        void showTask(Task task);
        void showDialog(String message);
        void changeImportance(Task.ImportanceLevel level);
        void setDeleteBtnVisibility(Boolean visibility);
        void finishActivity(int resultCode ,@Nullable Task task);
    }

    interface Presenter extends BasePresenter<View> {
        void onSaveBtnClicked(String title , Task.ImportanceLevel importanceLevel);
        void onDeleteBtnClicked(Task task);
        void onBackBtnClicked();
    }
}
