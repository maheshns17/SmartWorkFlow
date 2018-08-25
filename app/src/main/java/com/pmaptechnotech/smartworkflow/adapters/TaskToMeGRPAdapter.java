package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;

import java.util.ArrayList;
import java.util.List;

import setsgets.GetmyGRP;
import setsgets.GrpToMe;

public class TaskToMeGRPAdapter extends RecyclerView.Adapter<TaskToMeGRPAdapter.MyViewHolder> {
    private List<GrpToMe> chat = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,dead;

        public LinearLayout row_details;


        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.txt_task_name);
            dead = (TextView) view.findViewById(R.id.txt_deadline);
            row_details = (LinearLayout) view.findViewById(R.id.chats_row);

        }
    }

    public TaskToMeGRPAdapter(Context context, List<GrpToMe> chats) {
        this.chat = chats;
        this.context = context;
    }

    @Override
    public TaskToMeGRPAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.getmygrptasks, parent, false);

        return new TaskToMeGRPAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskToMeGRPAdapter.MyViewHolder holder, int position) {
        final GrpToMe GrpToMe = chat.get(position);
        holder.name.setText(GrpToMe.getGrp_task_name());
        holder.dead.setText(GrpToMe.getGrp_task_deadline());

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }
}