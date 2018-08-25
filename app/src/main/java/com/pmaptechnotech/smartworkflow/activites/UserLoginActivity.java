package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adminside.CreateProjectAndTaskActivity;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.logics.U;
import com.pmaptechnotech.smartworkflow.models.UserLoginInput;
import com.pmaptechnotech.smartworkflow.models.UserLoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {
    @BindView(R.id.edt_user_name)
    EditText edt_user_name;
    @BindView(R.id.edt_Password)
    EditText edt_Password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.txt_forgot_password)
    TextView txt_forgot_password;
    private SweetAlertDialog dialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        context = UserLoginActivity.this;
        ButterKnife.bind(this);
        // tool_bar_logo.setImageResource(R.drawable.ic_more_vert_black_24dp);// tool bar icon


        if (P.isUserLoggedIn(context)) {
            moveToNextActivity();
        }


        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UserRegisterActivity.class);
                startActivity(intent);
            }
        });

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, PasswordCreationActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }


    private void validation() {
        if (!P.isValidEditText(edt_user_name, "User Name")) return;
        if (!P.isValidEditText(edt_Password, "Password")) return;
        userLogin();
    }

    private void userLogin() {

//        if (edt_user_name.getText().toString().equals("admin") && (edt_Password.getText().toString().equals("admin"))) {
//            Intent intent = new Intent(context, CreateProjectAndTaskActivity.class);
//            startActivity(intent);
//            finish();
//        } else {

            Retrofit retrofit = Api.getRetrofitBuilder(this);
            WebServices webServices = retrofit.create(WebServices.class);

            //PREPARE INPUT/REQUEST PARAMETERS
            UserLoginInput userLoginInput = new UserLoginInput(
                    edt_user_name.getText().toString().trim(),
                    edt_Password.getText().toString().trim()
            );
            dialog = P.showBufferDialog(context, "Processing...");
            //btn_login.setProgress(1);
            btn_login.setEnabled(false);
            P.hideSoftKeyboard(UserLoginActivity.this);


            //CALL NOW
            webServices.userLogin(userLoginInput)
                    .enqueue(new Callback<UserLoginResult>() {
                        @Override
                        public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            if (!P.analyseResponse(response)) {
                                //btn_login.setProgress(0);
                                btn_login.setEnabled(true);

                                P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                                return;
                            }
                            UserLoginResult result = response.body();
                            if (result.is_success) {
                                P.saveUserLoginData(context, result.user.get(0));
                                P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);
                                // Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                                moveToNextActivity();
                            } else {
                                // btn_login.setProgress(0);
                                btn_login.setEnabled(true);
                                P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                                //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserLoginResult> call, Throwable t) {
                            P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                            t.printStackTrace();
                            if (dialog.isShowing()) dialog.dismiss();
                            // btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));
                        }
                    });

    }

    private void moveToNextActivity() {
        Intent intent = new Intent(context, TasksPageActivity.class);
        startActivity(intent);
        finish();
    }
}

