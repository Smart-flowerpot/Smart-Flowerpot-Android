package com.omer.user.smartflowerpot.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.omer.user.smartflowerpot.Adapters.PlantsAdapter;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.add_plant)
    CardView add_plant;

    View view;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setActionBar();
        List<Plant> p_list = new ArrayList<>();
        Plant a = new Plant();
        a.setName("a");
        p_list.add(a);
        fillList(p_list);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home_menu) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame, new SettingsFragment(), "fragment_settings")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("settings")
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'> Home </font>"));
    }

    private void fillList(List<Plant> list) {
        if (getContext() != null) {
            this.list.setAdapter(new PlantsAdapter(list, getContext()));
            linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.list.setLayoutManager(linearLayoutManager);
        }
    }
}
