package com.pmaptechnotech.smartworkflow.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.pmaptechnotech.smartworkflow.activites.GroupConversationActivity;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetMsgInput;
import com.pmaptechnotech.smartworkflow.models.GetMsgResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReceiveMessageService extends IntentService {

    public ReceiveMessageService() {
        super("ReceiveMessageService");
    }
    private Context context;
    private String chat_from_id, chat_to_id, chat_group_id;

    @Override
    protected void onHandleIntent(Intent intent) {
        context = ReceiveMessageService.this;
        if (intent != null) {
            chat_from_id = intent.getStringExtra("chat_from_id");
            chat_to_id = intent.getStringExtra("chat_to_id");
            chat_group_id = intent.getStringExtra("chat_group_id");
            getMsg();
        }
    }

    private void getMsg() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetMsgInput messages = new GetMsgInput(
                chat_from_id,
                chat_to_id,
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

                            GroupConversationActivity.convs = result.chating;

                            Intent intent = new Intent("com.conversation.chat");
                            sendBroadcast(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<GetMsgResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });
    }


}
