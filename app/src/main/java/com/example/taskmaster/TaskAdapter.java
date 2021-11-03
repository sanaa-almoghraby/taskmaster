package com.example.taskmaster;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> allTask=new ArrayList<Task>();

    public TaskAdapter(List<Task> allTask){

        this.allTask=allTask;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public Task task;

        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent goToDetailsPage=new Intent(v.getContext(),Task_Detail.class);
                    goToDetailsPage.putExtra("taskName",task.title);
                    goToDetailsPage.putExtra("taskBody",task.body);
                    goToDetailsPage.putExtra("taskState",task.state);
                    v.getContext().startActivity(goToDetailsPage);
                }
            });

        }
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_task,viewGroup,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);

        return taskViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.task = allTask.get(i);
        TextView title = taskViewHolder.itemView.findViewById(R.id.text1);
        TextView body = taskViewHolder.itemView.findViewById(R.id.text2);
        TextView state = taskViewHolder.itemView.findViewById(R.id.text3);

        title.setText(taskViewHolder.task.title);
        body.setText(taskViewHolder.task.body);
        state.setText(taskViewHolder.task.state);

    }

    @Override
    public int getItemCount() {
        return allTask.size();
    }


}
