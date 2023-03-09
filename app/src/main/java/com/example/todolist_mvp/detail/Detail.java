package com.example.todolist_mvp.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.todolist_mvp.R;
import com.example.todolist_mvp.model.Task;

public class Detail extends AppCompatActivity {

    private Task.ImportanceLevel selectedImportance = Task.ImportanceLevel.NORMAL;
    private ImageView lastSelectedImportanceIv;

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
    }
}