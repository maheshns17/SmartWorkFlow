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
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.AssignGroupTaskActivity;
import com.pmaptechnotech.smartworkflow.activites.AssignTaskActivity;
import com.pmaptechnotech.smartworkflow.activites.ConversationActivity;
import com.pmaptechnotech.smartworkflow.activites.GroupConversationActivity;
import com.pmaptechnotech.smartworkflow.activites.ListOfProjectActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.Chat;
import setsgets.GroupNames;

public class ListofGroupAdapter extends RecyclerView.Adapter<ListofGroupAdapter.MyViewHolder> {
    private List<GroupNames> groupNames = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,task,chat;
        public LinearLayout row_details;

        public MyViewHolder(View view) {
            super(view);
            task = (TextView) view.findViewById(R.id.txt_task);
            chat = (TextView) view.findViewById(R.id.txt_chat);
            name = (TextView) view.findViewById(R.id.txt_group_name);
            row_details = (LinearLayout) view.findViewById(R.id.group_row);

        }
    }

    public ListofGroupAdapter(Context context, List<GroupNames> chats) {
        this.groupNames = chats;
        this.context = context;
    }

    @Override
    public ListofGroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_row, parent, false);

        return new ListofGroupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListofGroupAdapter.MyViewHolder holder, int position) {
        final GroupNames GroupNames = groupNames.get(position);
        holder.name.setText(GroupNames.getGroup_name());

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupConversationActivity.class);
                intent.putExtra("gid", GroupNames.getGroup_id());
                intent.putExtra("name", GroupNames.getGroup_name());
                context.startActivity(intent);
            }
        });
        holder.task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AssignGroupTaskActivity.class);
                intent.putExtra("id", GroupNames.getGroup_id());
                intent.putExtra("name", GroupNames.getGroup_name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupNames.size();
    }
}