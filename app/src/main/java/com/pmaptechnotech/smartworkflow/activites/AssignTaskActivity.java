package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.AssignIndiInput;
import com.pmaptechnotech.smartworkflow.models.AssignIndiResult;
import com.pmaptechnotech.smartworkflow.models.UserLoginInput;
import com.pmaptechnotech.smartworkflow.models.UserLoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AssignTaskActivity extends AppCompatActivity {
    @BindView(R.id.task)
    EditText task;
    @BindView(R.id.deadline)
    EditText deadline;
    @BindView(R.id.submit)
    Button submit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        ButterKnife.bind(this);
        context = AssignTaskActivity.this;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignIndTask();
            }
        });


    }

    private void assignIndTask() {


        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        AssignIndiInput userLoginInput = new AssignIndiInput(
                P.getUserDetails(context).user_id,
                getIntent().getExtras().getString("id"),
                task.getText().toString().trim(),
                deadline.getText().toString().trim()
        );
        P.hideSoftKeyboard(AssignTaskActivity.this);


        //CALL NOW
        webServices.assignindi(userLoginInput)
                .enqueue(new Callback<AssignIndiResult>() {
                    @Override
                    public void onResponse(Call<AssignIndiResult> call, Response<AssignIndiResult> response) {
                        // if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            //btn_login.setProgress(0);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        AssignIndiResult result = response.body();
                        if (result.is_success) {
                            P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);

                        } else {
                            // btn_login.setProgress(0);

                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                            //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AssignIndiResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        //if (dialog.isShowing()) dialog.dismiss();
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));
                    }
                });

    }
}
