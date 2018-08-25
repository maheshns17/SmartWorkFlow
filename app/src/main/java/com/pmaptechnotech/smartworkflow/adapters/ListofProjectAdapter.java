package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.ChatRowsActivity;

import java.util.ArrayList;


public class ListofProjectAdapter extends RecyclerView.Adapter<ListofProjectAdapter.MyViewHolder> {

    ArrayList<String> personNames;
    Context context;
    Button btn_chat;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public View btn_chat;


        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.txt_project_name);
            btn_chat = (LinearLayout) view.findViewById(R.id.row_project);

            
        }
    }

    public ListofProjectAdapter(Context context, ArrayList<String> personNames) {
        this.context = context;
        this.personNames = personNames;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(personNames.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, personNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // RECYCLERVIEW NEXT ACTIVITY
       holder.btn_chat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatRowsActivity.class);

                /*intent.putExtra("id", RegisterUsers.getUser_id());
                intent.putExtra("name", RegisterUsers.getUser_name());*/
                context.startActivity(intent);
            }
        });
    }

   /*  txt_forgot_password.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(context, PasswordCreationActivity.class);
            startActivity(intent);
        }
    });*/

    @Override
    public int getItemCount() {
        return personNames.size();
    }


}
