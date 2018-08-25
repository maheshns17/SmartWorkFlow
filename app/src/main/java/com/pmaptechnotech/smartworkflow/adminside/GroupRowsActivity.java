package com.pmaptechnotech.smartworkflow.adminside;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.TasksGRPActivity;
import com.pmaptechnotech.smartworkflow.adapters.ListofGroupAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetGroupsInput;
import com.pmaptechnotech.smartworkflow.models.GetGroupsResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.GroupNames;

public class GroupRowsActivity extends AppCompatActivity {
@BindView(R.id.txt_tasks)
    TextView txt_tasks;
    private Context context;
    private RecyclerView recyclerView;
    ListofGroupAdapter mDeptAdapter;

    List<GroupNames> groupNames = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_and_tasks);
        context = GroupRowsActivity.this;
        ButterKnife.bind(this);
/*
        txt_group_name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListOfProjectActivity.class);
                startActivity(intent);
            }
        });*/
        txt_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TasksGRPActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_group);
        mDeptAdapter = new ListofGroupAdapter(context, groupNames);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDeptAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
//
//            @Override
//            public void onClick(View view, int position) {
//                Conversation citemsSet = new.get(position);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        getGroups();
    }

    public void getGroups() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetGroupsInput gr = new GetGroupsInput(

                P.getUserDetails(context).user_id

        );


        //CALL NOW
        webServices.getgroups(gr)
                .enqueue(new Callback<GetGroupsResult>() {
                    @Override
                    public void onResponse(Call<GetGroupsResult> call, Response<GetGroupsResult> response) {
                        if (!P.analyseResponse(response)) {

                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        GetGroupsResult result = response.body();
                        if (result.success) {
                            groupNames = result.groups;
                            mDeptAdapter = new ListofGroupAdapter(context, groupNames);
                            recyclerView.setAdapter(mDeptAdapter);
                          //  P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);

                        } else {

                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                            //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetGroupsResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));
                    }
                });
    }

}

