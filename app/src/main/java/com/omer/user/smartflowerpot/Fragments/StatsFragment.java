package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

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

        String[] temp = sharedPref.getString("temp", "").split(",");
        String[] m_air = sharedPref.getString("m_air", "").split(",");
        String[] m_soil = sharedPref.getString("m_soil", "").split(",");

        Log.i("ü", sharedPref.getString("temp", ""));

        Log.i("ü", sharedPref.getString("m_air", ""));

        Log.i("ü", sharedPref.getString("m_soil", ""));


        setTemperatureChart(temp);
        setMoistureAirChart(m_air);
        setMoistureSoilChart(m_soil);

        return view;
    }

    private void setTemperatureChart(String[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, Integer.parseInt(data[data.length - 12])),
                new DataPoint(2, Integer.parseInt(data[data.length - 11])),
                new DataPoint(3, Integer.parseInt(data[data.length - 10])),
                new DataPoint(4, Integer.parseInt(data[data.length - 9])),
                new DataPoint(5, Integer.parseInt(data[data.length - 8])),
                new DataPoint(6, Integer.parseInt(data[data.length - 7])),
                new DataPoint(7, Integer.parseInt(data[data.length - 6])),
                new DataPoint(8, Integer.parseInt(data[data.length - 5])),
                new DataPoint(9, Integer.parseInt(data[data.length - 4])),
                new DataPoint(10, Integer.parseInt(data[data.length - 3])),
                new DataPoint(11, Integer.parseInt(data[data.length - 2])),
                new DataPoint(12, Integer.parseInt(data[data.length - 1]))
        });

        lineChart_t.addSeries(series);
    }

    private void setMoistureAirChart(String[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, Integer.parseInt(data[data.length - 12])),
                new DataPoint(2, Integer.parseInt(data[data.length - 11])),
                new DataPoint(3, Integer.parseInt(data[data.length - 10])),
                new DataPoint(4, Integer.parseInt(data[data.length - 9])),
                new DataPoint(5, Integer.parseInt(data[data.length - 8])),
                new DataPoint(6, Integer.parseInt(data[data.length - 7])),
                new DataPoint(7, Integer.parseInt(data[data.length - 6])),
                new DataPoint(8, Integer.parseInt(data[data.length - 5])),
                new DataPoint(9, Integer.parseInt(data[data.length - 4])),
                new DataPoint(10, Integer.parseInt(data[data.length - 3])),
                new DataPoint(11, Integer.parseInt(data[data.length - 2])),
                new DataPoint(12, Integer.parseInt(data[data.length - 1]))
        });
        lineChart_m_a.addSeries(series);

    }

    private void setMoistureSoilChart(String[] data) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(1, Integer.parseInt(data[data.length - 12])),
                new DataPoint(2, Integer.parseInt(data[data.length - 11])),
                new DataPoint(3, Integer.parseInt(data[data.length - 10])),
                new DataPoint(4, Integer.parseInt(data[data.length - 9])),
                new DataPoint(5, Integer.parseInt(data[data.length - 8])),
                new DataPoint(6, Integer.parseInt(data[data.length - 7])),
                new DataPoint(7, Integer.parseInt(data[data.length - 6])),
                new DataPoint(8, Integer.parseInt(data[data.length - 5])),
                new DataPoint(9, Integer.parseInt(data[data.length - 4])),
                new DataPoint(10, Integer.parseInt(data[data.length - 3])),
                new DataPoint(11, Integer.parseInt(data[data.length - 2])),
                new DataPoint(12, Integer.parseInt(data[data.length - 1]))
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
