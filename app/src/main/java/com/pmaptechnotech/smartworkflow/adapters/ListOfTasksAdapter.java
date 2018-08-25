package com.pmaptechnotech.smartworkflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.activites.GroupConversationActivity;

import java.util.ArrayList;


public class ListOfTasksAdapter extends RecyclerView.Adapter<ListOfTasksAdapter.MyViewHolder> {

    ArrayList<String> personName;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        public LinearLayout row_details;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.txt_task_name);
            row_details = (LinearLayout) view.findViewById(R.id.task_row);


        }
    }


    public ListOfTasksAdapter(Context context, ArrayList<String> personName) {
        this.context = context;
        this.personName = personName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText( personName.get( position ) );
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText( context, personName.get( position ), Toast.LENGTH_SHORT ).show();
            }
        } );

        // RECYCLERVIEW NEXT ACTIVITY
        holder.row_details.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, GroupConversationActivity.class );

                /*intent.putExtra("id", RegisterUsers.getUser_id());
                intent.putExtra("name", RegisterUsers.getUser_name());*/
                context.startActivity( intent );
            }
        } );


    }

    @Override
    public int getItemCount() {
        return personName.size();
    }


}
