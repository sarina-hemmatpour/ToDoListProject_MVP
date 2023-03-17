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
import com.example.todolist_mvp.main.MainActivity;
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

    public static final String EXTRA_KEY_DETAIL_TASK ="new task";
    public static final int RESULT_ADD_TASK =255;
    public static final int RESULT_UPDATE_TASK =256;
    public static final int RESULT_DELETE_TASK =257;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);


        View normalImportanceBtn = findViewById(R.id.btn_detail_normalImportance);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.iv_detail_normalImportance);

        lastSelectedImportanceIv.setImageResource(R.drawable.ic_check_white_24dp);

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


        btnDelete=findViewById(R.id.btn_detail_delete);
        etTitle =findViewById(R.id.et_detail_title);

        //load task
        task=getIntent().getParcelableExtra(MainActivity.EXTRA_KEY_TASK);
        if (task!=null){
            detailPresenter=new DetailPresenter(AppDataBase.getAppDataBase(this).getTaskDao() , this , this.task);
            detailPresenter.onActivityHasTask(task);
        }
        else{
            detailPresenter=new DetailPresenter(AppDataBase.getAppDataBase(this).getTaskDao(),this);
            detailPresenter.onActivityHasNoTask();
        }



        ImageView btnBack=findViewById(R.id.btn_detail_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailPresenter.onBackBtnClicked();
            }
        });



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




        detailPresenter.onAttach(this);
    }

    @Override
    public void showTask(Task task) {
        etTitle.setText(task.getTitle());

        lastSelectedImportanceIv.setImageResource(0);
        switch (task.getImportance()){
            case HIGH:
//                View highImportanceBtn = findViewById(R.id.btn_detail_highImportance);
//                lastSelectedImportanceIv = highImportanceBtn.findViewById(R.id.iv_detail_highImportance);
//                selectedImportance= Task.ImportanceLevel.HIGH;
                findViewById(R.id.btn_detail_highImportance).performClick();
                break;
            case NORMAL:
//                View normalImportanceBtn = findViewById(R.id.btn_detail_normalImportance);
//                lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.iv_detail_normalImportance);
//                selectedImportance= Task.ImportanceLevel.NORMAL;
                findViewById(R.id.btn_detail_normalImportance).performClick();
                break;
            case LOW:
//                View lowImportanceBtn = findViewById(R.id.btn_detail_lowImportance);
//                lastSelectedImportanceIv = lowImportanceBtn.findViewById(R.id.iv_detail_lowImportance);
//                selectedImportance= Task.ImportanceLevel.LOW;
                findViewById(R.id.btn_detail_lowImportance).performClick();
                break;
        }
        lastSelectedImportanceIv.setImageResource(R.drawable.ic_check_white_24dp);

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
    public void finishActivity(int resultCode ,@Nullable Task task , int operationCode) {
        if(resultCode==RESULT_CANCELED){
            setResult(RESULT_CANCELED);
            finish();
        }
        else if (task!=null && resultCode==RESULT_OK){
            Intent resultIntent=new Intent();
            if(operationCode==Detail.RESULT_ADD_TASK){

                resultIntent.putExtra(EXTRA_KEY_DETAIL_TASK, task);

                setResult(RESULT_ADD_TASK , resultIntent);

            }
            else if (operationCode==RESULT_UPDATE_TASK){
                resultIntent.putExtra(EXTRA_KEY_DETAIL_TASK, task);

                setResult(RESULT_UPDATE_TASK, resultIntent);
            }
            else if (operationCode==RESULT_DELETE_TASK){

                resultIntent.putExtra(EXTRA_KEY_DETAIL_TASK, task);

                setResult(RESULT_DELETE_TASK, resultIntent);
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.onDetach();
    }
}