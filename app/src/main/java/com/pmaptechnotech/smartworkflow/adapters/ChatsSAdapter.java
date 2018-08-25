package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.AssignTaskActivity;
import com.pmaptechnotech.smartworkflow.activites.ConversationActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.RegisterUsers;

public class ChatsSAdapter extends RecyclerView.Adapter<ChatsSAdapter.MyViewHolder> {
    private List<RegisterUsers> chat = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,task,chat;

        public LinearLayout row_details;


        public MyViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.txt_task);
            chat = (TextView) view.findViewById(R.id.txt_chat);
            name = (TextView) view.findViewById(R.id.txt_user_name);
            row_details = (LinearLayout) view.findViewById(R.id.chats_row);

        }
    }

    public ChatsSAdapter(Context context, List<RegisterUsers> chats) {
        this.chat = chats;
        this.context = context;
    }

    @Override
    public ChatsSAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats, parent, false);

        return new ChatsSAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RegisterUsers RegisterUsers = chat.get(position);
        holder.name.setText(RegisterUsers.getUser_name());

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConversationActivity.class);
                intent.putExtra("id", RegisterUsers.getUser_id());
                intent.putExtra("name", RegisterUsers.getUser_name());
                context.startActivity(intent);
            }
        });
        holder.task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssignTaskActivity.class);
                intent.putExtra("id", RegisterUsers.getUser_id());
                intent.putExtra("name", RegisterUsers.getUser_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }
}