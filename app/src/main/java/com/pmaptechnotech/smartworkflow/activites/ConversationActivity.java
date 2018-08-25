package com.pmaptechnotech.smartworkflow.activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adapters.ConvAdapter;
import com.pmaptechnotech.smartworkflow.adapters.ConversationAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetMsgInput;
import com.pmaptechnotech.smartworkflow.models.GetMsgResult;
import com.pmaptechnotech.smartworkflow.models.SendMsgInput;
import com.pmaptechnotech.smartworkflow.models.SendMsgResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.Conv;
import setsgets.Conversation;

public class ConversationActivity extends AppCompatActivity {
    @BindView(R.id.edt_msg)
    EditText edt_msg;
    @BindView(R.id.btn_send)
    Button btn_send;
    @BindView(R.id.conv_name)
    TextView conv_name;
    private Context context;

    private ConversationAdapter mDeptAdapter;
    // private ConvAdapter mDeptAdapter;
    private RecyclerView recyclerView;
    public static List<Conversation> convs = new ArrayList<>();
    // public static List<Conv> co = new ArrayList<>();
    private SweetAlertDialog pDialog;
    private static String to_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        context = ConversationActivity.this;

        if (conv_name != null) {
            conv_name.setText(getIntent().getExtras().getString("name"));
        }
        registerReceiver(receiveMsg, new IntentFilter("com.conversation.chat"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //getMsg();
            }
        }, 200);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading...");

        to_id = getIntent().getExtras().getString("id");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_conv);
        mDeptAdapter = new ConversationAdapter(context, convs);
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
        startCountDownTimer();
    }

    public void validation() {
        if (!P.isValidEditText(edt_msg, "Message")) return;
        sendMsg();
    }


    private void getMsg() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetMsgInput messages = new GetMsgInput(

                P.getUserDetails(context).user_id,
                to_id,
                ""

        );


        //CALL NOW
        webServices.Msg(messages)
                .enqueue(new Callback<GetMsgResult>() {
                    @Override
                    public void onResponse(Call<GetMsgResult> call, Response<GetMsgResult> response) {
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        GetMsgResult result = response.body();
                        if (result.success) {

                            convs = result.chating;
                            mDeptAdapter = new ConversationAdapter(context, convs);
                            recyclerView.setAdapter(mDeptAdapter);
                            recyclerView.scrollToPosition(convs.size() - 1);
                           // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        } else {

                            //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetMsgResult> call, Throwable t) {
                        // P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // getMsg();
    }


    private void sendMsg() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        SendMsgInput messages = new SendMsgInput(

                P.getUserDetails(context).user_id,
                to_id,
                edt_msg.getText().toString().trim(),
                ""

        );


        //CALL NOW
        webServices.Send(messages)
                .enqueue(new Callback<SendMsgResult>() {
                    @Override
                    public void onResponse(Call<SendMsgResult> call, Response<SendMsgResult> response) {
                        if (!P.analyseResponse(response)) {

                            Toast.makeText(context, "Null", Toast.LENGTH_LONG).show();
                            return;
                        }
                        SendMsgResult result = response.body();
                        if (result.is_success) {
                            getMsg();
                            edt_msg.setText("");
                        } else {

                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendMsgResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });


        //
    }

    BroadcastReceiver receiveMsg = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("CONV", "BroadCast onReceive");
            mDeptAdapter = new ConversationAdapter(context, ConversationActivity.convs);
            recyclerView.setAdapter(mDeptAdapter);
        }
    };

    private void startCountDownTimer() {
        new CountDownTimer(24 * 60 * 60 * 1000, 2000) {

            public void onTick(long millisUntilFinished) {
               /* Intent intent = new Intent(context, ReceiveMessageService.class);
                intent.putExtra("chat_from_id", P.getUserDetails(context).user_id);
                intent.putExtra("chat_to_id", to_id);
                startService(intent);*/
                getMsg();
            }

            public void onFinish() {

            }
        }.start();
    }

}
