package com.pmaptechnotech.smartworkflow.adminside;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adapters.RegisteredUsersAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetUsersResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.RecyclerTouchListener;
import setsgets.RegisterUsers;

public class TasksForIndividualUserActivity extends AppCompatActivity {
    @BindView(R.id.btn_Create)
    Button btn_ok;
    @BindView(R.id.btn_group)
    Button btn_group;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    private Context context;
    private RegisteredUsersAdapter mDeptAdapter;
    private SweetAlertDialog dialog;
    private RecyclerView recyclerView;
    List<RegisterUsers> personNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_for_individual_user);
        context = TasksForIndividualUserActivity.this;
        ButterKnife.bind(this);
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("Loading...");

        getUsers();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDeptAdapter = new RegisteredUsersAdapter(context, personNames);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of RegisteredUsersAdapter to send the reference and data to Adapter
        //  RegisteredUsersAdapter registeredUsersAdapter = new RegisteredUsersAdapter(ListOfRegisteredUsersActivity.this, personNames);
        //recyclerView.setAdapter(registeredUsersAdapter); // set the Adapter to RecyclerView


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDeptAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {


            @Override
            public void onClick(View view, int position) {
                RegisterUsers user = personNames.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, TasksForIndividualUserActivity.class);
                startActivity(intent);
            }
        });

        btn_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, GroupRowsActivity.class);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, TasksForIndividualUserActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getUsers() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);
        dialog.show();
        //CALL NOW
        webServices.getUsers()
                .enqueue(new Callback<GetUsersResult>() {
                    @Override
                    public void onResponse(Call<GetUsersResult> call, Response<GetUsersResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            P.ShowErrorDialogAndBeHere(context, "ERROR", "server error");
                            return;
                        }
                        GetUsersResult result = response.body();
                        if (result!=null) {
                            //P.ShowErrorDialogAndBeHere(context, "Error", "not null");
                         //   personNames = result.users;
                            mDeptAdapter = new RegisteredUsersAdapter(context, personNames);
                            recyclerView.setAdapter(mDeptAdapter);
                        }

                    }
                    @Override
                    public void onFailure(Call<GetUsersResult> call, Throwable t) {
                        if (dialog.isShowing()) dialog.dismiss();
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        finish();
                    }
                });
    }

}


