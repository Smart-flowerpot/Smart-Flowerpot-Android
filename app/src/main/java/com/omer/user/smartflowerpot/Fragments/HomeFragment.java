package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omer.user.smartflowerpot.Adapters.PlantsAdapter;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    private View view;
    private PlantsAdapter adapter;
    private List<Plant> list_plant;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'> Home </font>"));
        return view;
    }

    private void fillList(List<Plant> list) {
        if (getContext() != null) {
            adapter = new PlantsAdapter(list, getContext());
            this.list.setAdapter(adapter);
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.list.setLayoutManager(linearLayoutManager);
        }
    }
}
