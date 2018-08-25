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
import com.pmaptechnotech.smartworkflow.activites.ConversationActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.Chat;
import setsgets.Conv;

public class ConvAdapter extends RecyclerView.Adapter<ConvAdapter.MyViewHolder> {
    private List<Conv> con = new ArrayList<>();
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

    public ConvAdapter(Context context, List<Conv> con) {
        this.con = con;
        this.context = context;
    }

    @Override
    public ConvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_row, parent, false);

        return new ConvAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConvAdapter.MyViewHolder holder, int position) {
        final Conv Conv = con.get(position);
        holder.name.setText(Conv.getUser_name());

        holder.row_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ConversationActivity.class);

                intent.putExtra("id", Conv.getUser_id());
                intent.putExtra("name", Conv.getUser_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return con.size();
    }
}