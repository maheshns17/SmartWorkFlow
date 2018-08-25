package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adminside.ListOfRegisteredUsersActivity;

import java.util.ArrayList;
import java.util.List;

import setsgets.RegisterUsers;

public class RegisteredUsersAdapter extends RecyclerView.Adapter<RegisteredUsersAdapter.MyViewHolder> {
    private List<RegisterUsers> personNames = new ArrayList<>();
    private List<RegisterUsers> personNamesCopy;
    private Context context;
    private LayoutInflater inflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // TextView name;
        public CheckBox checkBox1;
        public LinearLayout row_details;

        public MyViewHolder(View view) {
            super(view);
            checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
            row_details = (LinearLayout) view.findViewById(R.id.row_details);
        }

        public void bind(RegisterUsers registerUsers) {
        }
    }

    public RegisteredUsersAdapter(Context context, List<RegisterUsers> personName) {
        this.context = context;
        this.personNames = personName;
        this.personNamesCopy = new ArrayList<RegisterUsers>();
        // this.personNamesCopy.addAll( ListOfRegisteredUsersActivity.personNames);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_users_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RegisterUsers r = personNames.get(position);
        holder.checkBox1.setText(personNames.get(position).getUser_name());
        holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                r.setSelected(holder.checkBox1.isChecked());
                Log.d("REGISTERED", " pos : " + position + ",  " + holder.checkBox1.isChecked() + " " + r.user_name);
            }
        });
        holder.checkBox1.setChecked(r.isSelected);
    }


    @Override
    public int getItemCount() {
        return personNames.size();
    }

}

