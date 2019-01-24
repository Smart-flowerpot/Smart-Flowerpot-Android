package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.omer.user.smartflowerpot.R;

import butterknife.ButterKnife;

public class PlantFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plant, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setActionBar();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.plant_screen, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_plant_back) {
            getFragmentManager().popBackStackImmediate();
        } else if (item.getItemId() == R.id.action_more) {

        }
        return super.onOptionsItemSelected(item);
    }


    private void setActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'> Plant A </font>"));
    }

}
