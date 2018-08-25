package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.ConversationActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.Chat;
import setsgets.RegisterUsers;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Chat> chat = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox name;

        public LinearLayout row_details;


        public MyViewHolder(View view) {
            super(view);

            name = (CheckBox) view.findViewById(R.id.chk_name);
            row_details = (LinearLayout) view.findViewById(R.id.chats_row);

        }
    }

    public ChatAdapter(Context context, List<Chat> chats) {
        this.chat = chats;
        this.context = context;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_row, parent, false);

        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Chat Chat = chat.get(position);
                holder.name.setText(Chat.getUser_name());

        holder.row_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ConversationActivity.class);

                intent.putExtra("id", Chat.getUser_id());
                intent.putExtra("name", Chat.getUser_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }
}