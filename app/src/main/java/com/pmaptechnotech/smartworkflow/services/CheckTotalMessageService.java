package com.pmaptechnotech.smartworkflow.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
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

public class CheckTotalMessageService extends IntentService {

    public CheckTotalMessageService() {
        super("ReceiveMessageService");
    }

    private Context context;
    private String chat_to_id;
    private String group_id;

    @Override
    protected void onHandleIntent(Intent intent) {
        context = CheckTotalMessageService.this;
        if (intent != null) {
            chat_to_id = P.getUserDetails(context).user_id;
            checkingMyMsg();
        }
    }
    
    private void checkingMyMsg() {
        Log.d("LOGIN","checkingMyMsg Called");
        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        GetMsgInput messages = new GetMsgInput(
                "0",
                chat_to_id,
                ""

        );

        //CALL NOW
        webServices.checkingMyMsg(messages)
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

                            if (result.chating.size() > P.getUserDetails(context).user_total_msg) {


                                sendNotification(result.chating.get(result.chating.size() - 1).from_user_name,
                                        result.chating.get(result.chating.size() - 1).chat_msg);
                            }
                            P.saveUserTotalMsg(context, result.chating.size());

                        }

                    }

                    @Override
                    public void onFailure(Call<GetMsgResult> call, Throwable t) {
                       // P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();

                    }
                });
    }

    public void sendNotification(String name, String msg) {

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(this);

        //Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(context, GroupConversationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon( R.mipmap.ic_launcher);
        mBuilder.setContentTitle(name);
        mBuilder.setContentText(msg);

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }
}
