package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.omer.user.smartflowerpot.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsFragment extends Fragment {

    @BindView(R.id.graph_m_a)
    GraphView lineChart_m_a;

    @BindView(R.id.graph_m_s)
    GraphView lineChart_m_s;

    @BindView(R.id.graph_t)
    GraphView lineChart_t;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        SharedPreferences sharedPref;
        DateFormat df = new SimpleDateFormat("dd");

        sharedPref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int[] data_temp = new int[12];
        int[] data_m_a = new int[12];
        int[] data_m_s = new int[12];

        try {
            for (int i = 1; i < 13; i++)
                data_temp[i - 1] = Integer.parseInt(sharedPref.getString(i + "", 0 + "").split(",")[2]);

            for (int i = 1; i < 13; i++)
                data_m_a[i - 1] = Integer.parseInt(sharedPref.getString(i + "", 0 + "").split(",")[1]);

            for (int i = 1; i < 13; i++)
                data_m_s[i - 1] = Integer.parseInt(sharedPref.getString(i + "", 0 + "").split(",")[0]);
        } catch (Exception e) {

        }

        try {

            setTemperatureChart(data_temp);
            setMoistureAirChart(data_m_a);
            setMoistureSoilChart(data_m_s);
        } catch (Exception e) {

        }
        return view;
    }

    private void setTemperatureChart(int[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, data[12]),
                new DataPoint(2, data[12]),
                new DataPoint(3, data[12]),
                new DataPoint(4, data[12]),
                new DataPoint(5, data[12]),
                new DataPoint(6, data[12]),
                new DataPoint(7, data[12]),
                new DataPoint(8, data[12]),
                new DataPoint(9, data[12]),
                new DataPoint(10, data[12]),
                new DataPoint(11, data[12]),
                new DataPoint(12, data[12])
        });

        lineChart_t.addSeries(series);
    }

    private void setMoistureAirChart(int[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, data[12]),
                new DataPoint(2, data[12]),
                new DataPoint(3, data[12]),
                new DataPoint(4, data[12]),
                new DataPoint(5, data[12]),
                new DataPoint(6, data[12]),
                new DataPoint(7, data[12]),
                new DataPoint(8, data[12]),
                new DataPoint(9, data[12]),
                new DataPoint(10, data[12]),
                new DataPoint(11, data[12]),
                new DataPoint(12, data[12])
        });
        lineChart_m_a.addSeries(series);

    }

    private void setMoistureSoilChart(int[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, data[12]),
                new DataPoint(2, data[12]),
                new DataPoint(3, data[12]),
                new DataPoint(4, data[12]),
                new DataPoint(5, data[12]),
                new DataPoint(6, data[12]),
                new DataPoint(7, data[12]),
                new DataPoint(8, data[12]),
                new DataPoint(9, data[12]),
                new DataPoint(10, data[12]),
                new DataPoint(11, data[12]),
                new DataPoint(12, data[12])
        });
        lineChart_m_s.addSeries(series);
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

}
