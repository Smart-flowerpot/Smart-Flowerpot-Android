package com.omer.user.smartflowerpot.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omer.user.smartflowerpot.Models.ArrayItem;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.Models.Result;
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

    @BindView(R.id.value_moisture_soil)
    TextView moisture_soil;

    @BindView(R.id.value_temp)
    TextView temperature;

    @BindView(R.id.water)
    CardView water;

    @BindView(R.id.moisture)
    CardView moisture_card;

    @BindView(R.id.moisture_soil)
    CardView moisture_soil_card;

    @BindView(R.id.temperature)
    CardView temperature_card;

    @BindView(R.id.light)
    CardView light_card;

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
        ManagerAll.getInstance().getPlant(1).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, final Response<Plant> response) {
                if (response.isSuccessful()) {
                    setActionBar(response.body().getName());
                    temperature.setText(response.body().getTemperature() + "");
                    light.setText(("%" + (response.body().getLight() * 100 / 1024)) + "");
                    moisture.setText("%" + response.body().getMoisture_air());
                    moisture_soil.setText("%" + response.body().getMoisture_soil());

                    checkPlantStatus(response.body().getType());

                    water.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ManagerAll.getInstance().updateWaterStatus(response.body().getPlant_id(), 1).enqueue(new Callback<com.omer.user.smartflowerpot.Models.Response>() {
                                @Override
                                public void onResponse(Call<com.omer.user.smartflowerpot.Models.Response> call, Response<com.omer.user.smartflowerpot.Models.Response> response) {
                                    if (response.isSuccessful() && response.body().getResponse().equalsIgnoreCase("S")) {
                                        Toast.makeText(getContext(), "You watered your plant :)", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<com.omer.user.smartflowerpot.Models.Response> call, Throwable t) {

                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Plant> call, Throwable t) {

            }
        });
    }

    private void checkPlantStatus(final String plant_type) {
        final ArrayItem[] arrayItem = new ArrayItem[1];
        ManagerAll.getInstance().getConstantPlantData().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.i("error", response.body().getArray().toString());
                } else
                    Log.i("Error", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });

    }


}
