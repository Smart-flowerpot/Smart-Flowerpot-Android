package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.omer.user.smartflowerpot.Adapters.AchievementsAdapter;
import com.omer.user.smartflowerpot.Adapters.SettingsAdapter;
import com.omer.user.smartflowerpot.Models.AchievementlistItem;
import com.omer.user.smartflowerpot.Models.AllachievementsItem;
import com.omer.user.smartflowerpot.R;
import com.omer.user.smartflowerpot.RestApi.ManagerAll;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.omer.user.smartflowerpot.Activities.MainActivity.current_f;


public class GameFragment extends Fragment {

    @BindView(R.id.achievements)
    ListView achievements;

    @BindView(R.id.points)
    TextView points;

    View view;
    private AchievementsAdapter achievementsAdapter;
    private List<AchievementlistItem> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        setSettingsOptions();

        current_f = "game";

        SharedPreferences sharedPref = getContext().getSharedPreferences("achievements", Context.MODE_PRIVATE);

        if (sharedPref.getInt("water", 0) >= 5)
            points.setText("Points: 10");

        if (sharedPref.getInt("takecare", 0) >= 10)
            points.setText("Points: 10");

        if(sharedPref.getInt("water", 0) >= 5 && sharedPref.getInt("takecare", 0) >= 10)
            points.setText("Points: 20");


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
            getFragmentManager().popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSettingsOptions() {
        list = new ArrayList<>();
        list.add(new AchievementlistItem("Water 5 Times In Total", "10 points", "0"));
        list.add(new AchievementlistItem("Take Care Of 2 Plants At The Same Time", "20 points", "0"));
        list.add(new AchievementlistItem("Keep A Plant In A Healthy Condition For 2 Days", "10 points", "0"));
        list.add(new AchievementlistItem("Take Care Of A Cactus", "20 points", "0"));
        list.add(new AchievementlistItem("Collect 5 Experience Points", "10 points", "0"));
        list.add(new AchievementlistItem("Check The Condition Of Your Plants 10 Times", "10 points", "0"));
        achievementsAdapter = new AchievementsAdapter(getActivity(), list, getFragmentManager());
        achievements.setAdapter(achievementsAdapter);

    }

}
