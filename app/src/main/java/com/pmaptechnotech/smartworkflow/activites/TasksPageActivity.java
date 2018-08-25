package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adminside.GroupRowsActivity;
import com.pmaptechnotech.smartworkflow.adminside.ListOfRegisteredUsersActivity;
import com.pmaptechnotech.smartworkflow.logics.P;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class TasksPageActivity extends AppCompatActivity {

    @BindView(R.id.btn_group_task)
    Button btn_group_task;
    @BindView(R.id.btn_individual_task)
    Button btn_individual_task;
    @BindView(R.id.btn_task_assign_page)
    Button btn_task_assign_page;
    @BindView(R.id.btn_log_out)
    Button btn_log_out;
    private SweetAlertDialog dialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_page);
        context = TasksPageActivity.this;
        ButterKnife.bind(this);

        btn_group_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, GroupRowsActivity.class);
                startActivity(intent);
            }
        });

        btn_individual_task.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatRowsActivity.class);
                startActivity(intent);
            }
        });

        btn_task_assign_page.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListOfRegisteredUsersActivity.class);
                startActivity(intent);
            }
        });

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onLogout(view);
            }
        });

    }
    public void onLogout(View v) {
        P.userLogout(context);
        Intent intent = new Intent(context, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
