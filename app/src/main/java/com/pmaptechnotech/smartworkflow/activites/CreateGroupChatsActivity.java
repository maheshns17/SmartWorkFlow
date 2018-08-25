package com.pmaptechnotech.smartworkflow.activites;

import android.content.Context;
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
import com.pmaptechnotech.smartworkflow.adapters.ChatAdapter;
import com.pmaptechnotech.smartworkflow.api.Api;
import com.pmaptechnotech.smartworkflow.api.WebServices;
import com.pmaptechnotech.smartworkflow.logics.P;
import com.pmaptechnotech.smartworkflow.models.GetUsersResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import setsgets.Chat;
import setsgets.GroupNames;
import setsgets.RecyclerTouchListener;
import setsgets.SelectedUsers;

public class CreateGroupChatsActivity extends AppCompatActivity {
    public static List<SelectedUsers> selectedUsers = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;
    ChatAdapter mDeptAdapter;
    private SweetAlertDialog dialog;
    List<Chat> chats = new ArrayList<>();
    @BindView(R.id.txt_grp)
    TextView txt_grp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_row);
        ButterKnife.bind(this);
        context= CreateGroupChatsActivity.this;

        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("Loading...");

        txt_grp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //for(int i=0;i<selectedUsers.size();i++){
                  //  System.out.println(selectedUsers.get(i));
                Toast.makeText(context, (CharSequence) selectedUsers.get(0), Toast.LENGTH_SHORT).show();
            }
        });

        getUsers();

        recyclerView = (RecyclerView) findViewById( R.id.recycle_view_chats );
        mDeptAdapter = new ChatAdapter( context, chats );
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter( mDeptAdapter );
        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Chat user = chats.get( position );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

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
                          //  chats = result.users;
                            mDeptAdapter = new ChatAdapter(context, chats);
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
