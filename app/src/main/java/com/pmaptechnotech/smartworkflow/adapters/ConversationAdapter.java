package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.ConversationActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.Conversation;


public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private List<Conversation> conv = new ArrayList<>();
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,msg,date;
        public LinearLayout row_details;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.conv_name);
            msg = (TextView) view.findViewById(R.id.conv_msg);
            date = (TextView) view.findViewById(R.id.conv_datetime);
            row_details = (LinearLayout) view.findViewById(R.id.conv_row);
        }
    }

    public ConversationAdapter(Context context, List<Conversation> personNames) {
        this.conv = personNames;
        this.context = context;
    }
    @Override
    public ConversationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_row, parent, false);

        return new ConversationAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Conversation Conversation = conv.get(position);
        holder.msg.setText(Conversation.getChat_msg());
        holder.date.setText(Conversation.getChat_date());
        holder.name.setText(Conversation.getFrom_user_name());
    }
    @Override
    public int getItemCount() {
        return conv.size();
    }
}

