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
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adapters.MyTaskGRPAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPInput;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.GetmyGRP;
import setsgets.GrpToMe;

public class MYTaskGRPActivity extends AppCompatActivity {
    private MyTaskGRPAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private Context context;
    List<GrpToMe> getmyGRPS = new ArrayList<>();
    private SweetAlertDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_grp);
        ButterKnife.bind(this);
        context = MYTaskGRPActivity.this;

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");

        getgrpmytask();


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mDeptAdapter = new MyTaskGRPAdapter(context, getmyGRPS);
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

    private void getgrpmytask() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetMyTaskGRPInput messages = new GetMyTaskGRPInput(

                P.getUserDetails(context).user_id

        );


        //CALL NOW
        webServices.getgrptsk(messages)
                .enqueue(new Callback<GetMyTaskGRPResult>() {
                    @Override
                    public void onResponse(Call<GetMyTaskGRPResult> call, Response<GetMyTaskGRPResult> response) {
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        GetMyTaskGRPResult result = response.body();
                        if (result.success) {

                            getmyGRPS = result.group_task;
                            mDeptAdapter = new MyTaskGRPAdapter(context, getmyGRPS);
                            recyclerView.setAdapter(mDeptAdapter);
                            recyclerView.scrollToPosition(getmyGRPS.size() - 1);
                            // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        } else {

                            // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetMyTaskGRPResult> call, Throwable t) {
                        // P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });
    }

}

