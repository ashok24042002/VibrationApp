package com.example.vibrationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {

    private List<Machine> mMachineList;

    public MachineAdapter(List<Machine> machineList) {
        mMachineList = machineList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_machine, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Machine machine = mMachineList.get(position);
        holder.nameTextView.setText(machine.getName());
        holder.thresholdTextView.setText("Threshold: " + machine.getThreshold());
        holder.conditionTextView.setText("Condition: " + machine.getCondition());
    }

    @Override
    public int getItemCount() {
        return mMachineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView thresholdTextView;
        public TextView conditionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.machine_name);
            thresholdTextView = itemView.findViewById(R.id.machine_threshold);
            conditionTextView = itemView.findViewById(R.id.machine_condition);
        }
    }

//    public static class MachineViewHolder extends RecyclerView.ViewHolder {
//        public TextView machineName;
//        public TextView machineThreshold;
//        public TextView machineCondition;
//
//        public MachineViewHolder(View itemView) {
//            super(itemView);
//            machineName = itemView.findViewById(R.id.machine_name);
//            machineThreshold = itemView.findViewById(R.id.machine_threshold);
//            machineCondition = itemView.findViewById(R.id.machine_condition);
//        }
//    }

}
