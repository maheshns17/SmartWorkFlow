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

import setsgets.Indi;
import setsgets.MyTaskINDI;

public class TaskToMeINDIAdapter extends RecyclerView.Adapter<TaskToMeINDIAdapter.MyViewHolder> {
    private List<Indi> chat = new ArrayList<>();
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

    public TaskToMeINDIAdapter(Context context, List<Indi> chats) {
        this.chat = chats;
        this.context = context;
    }

    @Override
    public TaskToMeINDIAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.getmygrptasks, parent, false);

        return new TaskToMeINDIAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskToMeINDIAdapter.MyViewHolder holder, int position) {
        final Indi Indi = chat.get(position);
        holder.name.setText(Indi.getIndi_task_name());
        holder.dead.setText(Indi.getIndi_task_deadline());

    }

    @Override
    public int getItemCount() {
        return chat.size();
    }
}
