package com.pmaptechnotech.smartworkflow.adminside;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pmaptechnotech.smartworkflow.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateProjectAndTaskActivity extends AppCompatActivity {
    @BindView(R.id.btn_create_project)
    Button btn_create_project;
    @BindView(R.id.btn_create_task)
    Button btn_create_task;
    private Context context;
    private SweetAlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project_and_task);
        context = CreateProjectAndTaskActivity.this;
        ButterKnife.bind(this);



        btn_create_project.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListOfRegisteredUsersActivity.class);
                startActivity(intent);
            }
        });

        btn_create_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, TasksForIndividualUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
