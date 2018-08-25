package com.pmaptechnotech.smartworkflow.activites;

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
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adapters.ChatsSAdapter;
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
import setsgets.RegisterUsers;

public class ChatRowsActivity extends AppCompatActivity {

    private ChatsSAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private Context context;
    List<RegisterUsers> cha = new ArrayList<>();
    private SweetAlertDialog pDialog;

    @BindView(R.id.txt_tasks)
    TextView txt_tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rows);
        ButterKnife.bind(this);
        context = ChatRowsActivity.this;

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");

        getUsers();

        txt_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TasksINDIActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_conversation);
        mDeptAdapter = new ChatsSAdapter(context, cha);
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
    }
    private void getUsers() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        pDialog.show();
        //CALL NOW
        webServices.getUsers()
                .enqueue(new Callback<GetUsersResult>() {
                    @Override
                    public void onResponse(Call<GetUsersResult> call, Response<GetUsersResult> response) {
                        if (pDialog.isShowing()) pDialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            P.ShowErrorDialogAndBeHere(context, "ERROR", "server error");
                            return;
                        }
                        GetUsersResult result = response.body();
                        if (result != null) {
                            //P.ShowErrorDialogAndBeHere(context, "Error", "not null");
                            cha = result.users;
                            mDeptAdapter = new ChatsSAdapter(context, cha);
                            recyclerView.setAdapter(mDeptAdapter);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetUsersResult> call, Throwable t) {
                        if (pDialog.isShowing()) pDialog.dismiss();
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        finish();
                    }
                });


    }

}

