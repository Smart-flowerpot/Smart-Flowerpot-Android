package com.omer.user.smartflowerpot.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omer.user.smartflowerpot.Fragments.PlantFragment;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.ViewHolder> {

    List<Plant> list;
    LayoutInflater layoutInflater;

    public PlantsAdapter(List<Plant> list, Context context) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.plant_list_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.status)
        TextView status;

        @BindView(R.id.type)
        TextView type;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
            itemView.setOnClickListener(this);
        }

        public void setData(Plant plant, int position) {
            name.setText(plant.getName());
            status.setText(plant.getStatus() + "%");
            type.setText(plant.getType());
        }

        @Override
        public void onClick(View view) {
            final int position = getLayoutPosition();
            Plant plant = list.get(position);

            Bundle bundle = new Bundle();
            bundle.putInt("id", plant.getId());

            PlantFragment plantFragment = new PlantFragment();
            plantFragment.setArguments(bundle);

            ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, plantFragment, "plant_fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("plant")
                    .commit();
        }
    }
}
