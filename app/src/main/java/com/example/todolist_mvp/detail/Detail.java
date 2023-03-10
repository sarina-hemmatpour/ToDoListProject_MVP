package com.example.todolist_mvp.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todolist_mvp.R;
import com.example.todolist_mvp.model.AppDataBase;
import com.example.todolist_mvp.model.Task;
import com.google.android.material.button.MaterialButton;

public class Detail extends AppCompatActivity implements DetailContract.View {

    private Task.ImportanceLevel selectedImportance = Task.ImportanceLevel.NORMAL;
    private ImageView lastSelectedImportanceIv;

    private EditText etTitle;

    private ImageView btnDelete;

    private DetailPresenter detailPresenter;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);



        View normalImportanceBtn = findViewById(R.id.btn_detail_normalImportance);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.iv_detail_normalImportance);

        View highImportanceBtn = findViewById(R.id.btn_detail_highImportance);
        highImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.ImportanceLevel.HIGH) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.iv_detail_highImportance);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.ImportanceLevel.HIGH;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });
        View lowImportanceBtn = findViewById(R.id.btn_detail_lowImportance);
        lowImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.ImportanceLevel.LOW) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.iv_detail_lowImportance);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.ImportanceLevel.LOW;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        normalImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.ImportanceLevel.NORMAL) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.iv_detail_normalImportance);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.ImportanceLevel.NORMAL;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });


        detailPresenter=new DetailPresenter(AppDataBase.getAppDataBase(this).getTaskDao(),this);


        ImageView btnBack=findViewById(R.id.btn_detail_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.onBackBtnClicked();
            }
        });

        etTitle =findViewById(R.id.et_detail_title);

        MaterialButton btnSave=findViewById(R.id.btn_detail_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.onSaveBtnClicked(etTitle.getText().toString().trim() , selectedImportance);
            }
        });

        btnDelete=findViewById(R.id.btn_detail_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.onDeleteBtnClicked(task);
            }
        });
    }

    @Override
    public void showTask(Task task) {
        //*****************************************importance

        etTitle.setText(task.getTitle());

    }

    @Override
    public void showDialog(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeImportance(Task.ImportanceLevel level) {

    }

    @Override
    public void setDeleteBtnVisibility(Boolean visibility) {

        if (visibility)
            btnDelete.setVisibility(View.VISIBLE);
        else
            btnDelete.setVisibility(View.GONE);


    }

    @Override
    public void finishActivity(int resultCode ,@Nullable Task task) {
        if(resultCode==RESULT_CANCELED){
            setResult(RESULT_CANCELED);
            finish();
        }
        else if (task!=null && resultCode==RESULT_OK){
            Intent resultIntent=new Intent("newTask",task.);//********************************************
            setResult(RESULT_OK , resultIntent);
            finish();
        }
    }
}