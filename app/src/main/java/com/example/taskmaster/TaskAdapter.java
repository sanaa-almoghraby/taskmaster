package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    List<Task> allTasksData = new ArrayList<>();


    public TaskAdapter(List<Task> allTasksData) {
        this.allTasksData = allTasksData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Context context = viewHolder.itemView.getContext();

        Task task= allTasksData.get(position);
        viewHolder.textViewTitle.setText(task.getTitle());
        viewHolder.textViewBody.setText(task.getBody());
        viewHolder.textViewState.setText(task.getState());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("my Adapter", "Element " + viewHolder.getAdapterPosition() + " clicked");

                String Task1 = viewHolder.textViewTitle.getText().toString();
                editor.putString("TaskName", Task1);
                editor.apply();
                Intent gotToStd = new Intent(context, Task_Detail.class);
                context.startActivity(gotToStd);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allTasksData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitle;
        public TextView textViewBody;
        public TextView textViewState;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle= (TextView)  itemView.findViewById(R.id.title);
            textViewBody= (TextView)  itemView.findViewById(R.id.body);
            textViewState= (TextView)  itemView.findViewById(R.id.state);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
//    List<Task> allTask=new ArrayList<Task>();
//
//    public TaskAdapter(List<Task> allTask){
//
//        this.allTask=allTask;
//    }
//
//    public static class TaskViewHolder extends RecyclerView.ViewHolder {
//        public Task task;
//
//        View itemView;
//
//        public TaskViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.itemView=itemView;
//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Intent goToDetailsPage=new Intent(v.getContext(),Task_Detail.class);
//                    goToDetailsPage.putExtra("taskName",task.getTitle());
//                    goToDetailsPage.putExtra("taskBody",task.getBody());
//                    goToDetailsPage.putExtra("taskState",task.getState());
//                    v.getContext().startActivity(goToDetailsPage);
//                }
//            });
//
//        }
//    }
//
//
//
//    @NonNull
//    @Override
//    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_task,viewGroup,false);
//        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
//
//        return taskViewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
//        taskViewHolder.task = allTask.get(i);
//        TextView title = taskViewHolder.itemView.findViewById(R.id.text1);
//        TextView body = taskViewHolder.itemView.findViewById(R.id.text2);
//        TextView state = taskViewHolder.itemView.findViewById(R.id.text3);
//
//        title.setText(taskViewHolder.task.getTitle());
//        body.setText(taskViewHolder.task.getBody());
//        state.setText(taskViewHolder.task.getState());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return allTask.size();
//    }


}
