package com.pmaptechnotech.smartworkflow.adminside;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.TasksPageActivity;
import com.pmaptechnotech.smartworkflow.adapters.RegisteredUsersAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetUsersResult;
import com.pmaptechnotech.smartworkflow.models.NewGroupChattingInput;
import com.pmaptechnotech.smartworkflow.models.NewGroupChattingResult;
import com.pmaptechnotech.smartworkflow.models.UserID;

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

public class ListOfRegisteredUsersActivity extends AppCompatActivity {
    @BindView(R.id.btn_Create)
    Button btn_Create;
    @BindView(R.id.btn_group)
    Button btn_group;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    EditText searchView;
    private Context context;
    RegisteredUsersAdapter mDeptAdapter;
    private SweetAlertDialog dialog;
    private RecyclerView recyclerView;
    public static List<RegisterUsers> personNames;
    public static List<RegisterUsers> array_sort;
    int textlength = 0;

    public List<UserID> group_members_id = new ArrayList<>();
    private String group_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_registered_users);
        context = ListOfRegisteredUsersActivity.this;
        ButterKnife.bind(this);
        searchView = (EditText) findViewById(R.id.searchView);
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("Loading...");

        getUsers();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        personNames = populateList();

        mDeptAdapter = new RegisteredUsersAdapter(context, personNames);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        array_sort = new ArrayList<>();
        array_sort = populateList();
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

        btn_Create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               /* Intent intent = new Intent(context, ListOfRegisteredUsersActivity.class);
                startActivity(intent);*/
                prepareGroupUsers();
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
                Intent intent = new Intent(context, ListOfRegisteredUsersActivity.class);
                startActivity(intent);
            }
        });


        //Enabling Search Filter
        searchView.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = searchView.getText().length();
                array_sort.clear();
                for (int i = 0; i < personNames.size(); i++) {
                    if (textlength <= personNames.get(i).getUser_name().length()) {
                        Log.d("ertyyy", personNames.get(i).getUser_name().toLowerCase().trim());
                        if (personNames.get(i).getUser_name().toLowerCase().trim().contains(
                                searchView.getText().toString().toLowerCase().trim())) {
                            array_sort.add(personNames.get(i));
                        }
                    }
                }
                mDeptAdapter = new RegisteredUsersAdapter(ListOfRegisteredUsersActivity.this, array_sort);
                recyclerView.setAdapter(mDeptAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            }
        });

    }

    private List<RegisterUsers> populateList() {

        List<RegisterUsers> List = new ArrayList<>();

        return List;
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
                        if (result != null) {
                            //P.ShowErrorDialogAndBeHere(context, "Error", "not null");
                            personNames = result.users;
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

    private void prepareGroupUsers() {
        group_members_id.clear();
        for (int i = 0; i < personNames.size(); i++) {
            RegisterUsers r = personNames.get(i);
            if (r.isSelected()) {
                group_members_id.add(new UserID(r.user_id));
            }
        }

        if (group_members_id.size() == 0) {
            Toast.makeText(context, "No User Selected", Toast.LENGTH_LONG).show();
            return;
        }
        showGroupNameDialog();

    }

    public void showGroupNameDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.group_name_dilaog_layout, null);
        dialogBuilder.setView(dialogView);

        final EditText edt_group_name = (EditText) dialogView.findViewById(R.id.edt_group_name);
        final Button btn_create = (Button) dialogView.findViewById(R.id.btn_create);

        dialogBuilder.setTitle("Group Name");
        dialogBuilder.setMessage("Enter Group Name");
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!P.isValidEditText(edt_group_name, "Group Name")) return;
                group_name = edt_group_name.getText().toString().trim();
                createGroup();
            }
        });
        /*dialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                if (!P.isValidEditText(edt_group_name, "Group Name")) return;
                group_name = edt_group_name.getText().toString().trim();
                createGroup();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });*/
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void createGroup() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        NewGroupChattingInput userLoginInput = new NewGroupChattingInput(
                group_name,
                P.getUserDetails(context).user_id,
                group_members_id
        );
        dialog = P.showBufferDialog(context, "Processing...");


        //CALL NOW
        webServices.newGroupChatting(userLoginInput)
                .enqueue(new Callback<NewGroupChattingResult>() {
                    @Override
                    public void onResponse(Call<NewGroupChattingResult> call, Response<NewGroupChattingResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {

                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        NewGroupChattingResult result = response.body();
                        if (result.success) {

                            P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);

                        } else {

                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                            //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewGroupChattingResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();

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

