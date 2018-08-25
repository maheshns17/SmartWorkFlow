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
import com.pmaptechnotech.smartworkflow.adapters.MyTaskINDIAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPInput;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskGRPResult;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskINDIInput;
import com.pmaptechnotech.smartworkflow.models.GetMyTaskINDIResult;

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
import setsgets.MyTaskINDI;

public class MyTaskINDIActivity extends AppCompatActivity {
    private MyTaskINDIAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    private Context context;
    List<MyTaskINDI> myTaskINDIS = new ArrayList<>();
    private SweetAlertDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_to_others);
        ButterKnife.bind(this);
        context = MyTaskINDIActivity.this;

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");

        getindimytask();


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mDeptAdapter = new MyTaskINDIAdapter(context, myTaskINDIS);
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

    private void getindimytask() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetMyTaskINDIInput messages = new GetMyTaskINDIInput(

                P.getUserDetails(context).user_id

        );


        //CALL NOW
        webServices.getinditask(messages)
                .enqueue(new Callback<GetMyTaskINDIResult>() {
                    @Override
                    public void onResponse(Call<GetMyTaskINDIResult> call, Response<GetMyTaskINDIResult> response) {
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        GetMyTaskINDIResult result = response.body();
                        if (result.success) {

                            myTaskINDIS = result.individual_task;
                            mDeptAdapter = new MyTaskINDIAdapter(context, myTaskINDIS);
                            recyclerView.setAdapter(mDeptAdapter);
                            recyclerView.scrollToPosition(myTaskINDIS.size() - 1);
                            // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        } else {

                            // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetMyTaskINDIResult> call, Throwable t) {
                        // P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });
    }

}