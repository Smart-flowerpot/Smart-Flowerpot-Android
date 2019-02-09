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
import android.widget.TextView;

import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.R;
import com.omer.user.smartflowerpot.RestApi.ManagerAll;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantFragment extends Fragment {

    @BindView(R.id.value_light)
    TextView light;

    @BindView(R.id.value_moisture)
    TextView moisture;

    @BindView(R.id.value_temp)
    TextView temperature;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plant, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setPlant();
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


    private void setActionBar(String name) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'>" + name + "</font>"));
    }

    private void setPlant() {
        ManagerAll.getInstance().getPlant(5).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, Response<Plant> response) {
                if (response.isSuccessful()) {
                    setActionBar(response.body().getName());
                    temperature.setText(response.body().getTemperature()+"");
                    light.setText(response.body().getLight()+"");
                    moisture.setText(response.body().getMoisture()+"");
                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {

            }
        });
    }

}
