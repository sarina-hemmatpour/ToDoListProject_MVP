package com.example.todolist_mvp;

public interface BasePresenter <T extends BaseView>{
    void onAttach(T view);
    void onDetach();
}
