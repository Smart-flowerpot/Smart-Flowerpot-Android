package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.omer.user.smartflowerpot.R;

import butterknife.ButterKnife;

public class MenuFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setActionBar();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings_back, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings_back) {
            Toast.makeText(getContext(), "back", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'> Settings </font>"));
    }

}
