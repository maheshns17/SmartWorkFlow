package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksGRPActivity extends AppCompatActivity {
    @BindView(R.id.txt_from_task)
    TextView txt_from_task;
    @BindView(R.id.txt_to_task)
    TextView txt_to_task;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_grp);
        ButterKnife.bind(this);
        context = TasksGRPActivity.this;

        txt_from_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TaskToMeGRPActivity.class);
                startActivity(intent);
            }
        });
        txt_to_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MYTaskGRPActivity.class);
                startActivity(intent);
            }
        });
    }
}
