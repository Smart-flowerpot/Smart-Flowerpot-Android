package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.omer.user.smartflowerpot.Adapters.AchievementsAdapter;
import com.omer.user.smartflowerpot.Models.AchievementlistItem;
import com.omer.user.smartflowerpot.Models.ArrayItem;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.Models.Result;
import com.omer.user.smartflowerpot.R;
import com.omer.user.smartflowerpot.RestApi.ManagerAll;
import com.omer.user.smartflowerpot.Utility;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantFragment extends Fragment {

    @OnClick(R.id.game)
    public void scrollGame() {
        focusOnView(ac_text);
    }

    @OnClick(R.id.stats)
    public void scrollStats() {
        focusOnView(stats_text);
    }

    @BindView(R.id.achievements_text)
    TextView ac_text;

    @BindView(R.id.a)
    NestedScrollView nestedScrollView;

    @BindView(R.id.value_moisture)
    TextView moisture;

    @BindView(R.id.value_moisture_soil)
    TextView moisture_soil;

    @BindView(R.id.value_temp)
    TextView temperature;

    @BindView(R.id.value_freeze)
    TextView freeze;

    @BindView(R.id.water)
    CardView water;

    @BindView(R.id.moisture)
    CardView moisture_card;

    @BindView(R.id.moisture_soil)
    CardView moisture_soil_card;

    @BindView(R.id.temperature)
    CardView temperature_card;

    @BindView(R.id.freeze)
    CardView freeze_card;

    @BindView(R.id.info_freeze)
    ImageView info_freeze;

    @BindView(R.id.info_moisture)
    ImageView info_moisture;

    @BindView(R.id.info_moisture_soil)
    ImageView info_moisture_soil;

    @BindView(R.id.info_temp)
    ImageView info_temp;

    @BindView(R.id.water_me_text)
    TextView water_me_text;


    @BindView(R.id.graph_m_a)
    GraphView lineChart_m_a;

    @BindView(R.id.graph_m_s)
    GraphView lineChart_m_s;

    @BindView(R.id.graph_t)
    GraphView lineChart_t;


    @BindView(R.id.achievements)
    ListView achievements;

    @BindView(R.id.points)
    TextView points;

    @BindView(R.id.stats_text)
    TextView stats_text;

    private AchievementsAdapter achievementsAdapter;
    private List<AchievementlistItem> list;


    View view;
    private final MemoryPersistence persistence = new MemoryPersistence();
    MqttAndroidClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plant, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        connect();
        // openGame();
        //openStats();
        setActionBar("Plant 1");
        setStats();
        setAchievements();

        SharedPreferences sharedPref = getContext().getSharedPreferences("achievements", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("takecare", sharedPref.getInt("takecare", 0) + 1);
        editor.commit();

        return view;
    }

    private void setAchievements() {
        SharedPreferences sharedPref = getContext().getSharedPreferences("achievements", Context.MODE_PRIVATE);

        if (sharedPref.getInt("water", 0) >= 5)
            points.setText("Points: 10");

        if (sharedPref.getInt("takecare", 0) >= 10)
            points.setText("Points: 10");

        if (sharedPref.getInt("water", 0) >= 5 && sharedPref.getInt("takecare", 0) >= 10)
            points.setText("Points: 20");

        list = new ArrayList<>();
        list.add(new AchievementlistItem("Water 5 Times In Total", "10 points", "0"));
        list.add(new AchievementlistItem("Take Care Of 2 Plants At The Same Time", "20 points", "0"));
        list.add(new AchievementlistItem("Keep A Plant In A Healthy Condition For 2 Days", "10 points", "0"));
        list.add(new AchievementlistItem("Take Care Of A Cactus", "20 points", "0"));
        list.add(new AchievementlistItem("Collect 5 Experience Points", "10 points", "0"));
        list.add(new AchievementlistItem("Check The Condition Of Your Plants 10 Times", "10 points", "0"));
        achievementsAdapter = new AchievementsAdapter(getActivity(), list, getFragmentManager());
        achievements.setAdapter(achievementsAdapter);
        Utility.setListViewHeightBasedOnChildren(achievements);
    }

    private void setStats() {
        SharedPreferences sharedPref;

        sharedPref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        String[] temp = sharedPref.getString("temp", "").split(",");
        String[] m_air = sharedPref.getString("m_air", "").split(",");
        String[] m_soil = sharedPref.getString("m_soil", "").split(",");

        try {
            setTemperatureChart(temp);
            setMoistureAirChart(m_air);
            setMoistureSoilChart(m_soil);
        } catch (Exception e) {

        }
    }

    private final void focusOnView(final View view) {
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.scrollTo(0, view.getBottom()+800);
            }
        });
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
                new DataPoint(1, Integer.parseInt(data[data.length - 12]) * 100 / 1024),
                new DataPoint(2, Integer.parseInt(data[data.length - 11]) * 100 / 1024),
                new DataPoint(3, Integer.parseInt(data[data.length - 10]) * 100 / 1024),
                new DataPoint(4, Integer.parseInt(data[data.length - 9]) * 100 / 1024),
                new DataPoint(5, Integer.parseInt(data[data.length - 8]) * 100 / 1024),
                new DataPoint(6, Integer.parseInt(data[data.length - 7]) * 100 / 1024),
                new DataPoint(7, Integer.parseInt(data[data.length - 6]) * 100 / 1024),
                new DataPoint(8, Integer.parseInt(data[data.length - 5]) * 100 / 1024),
                new DataPoint(9, Integer.parseInt(data[data.length - 4]) * 100 / 1024),
                new DataPoint(10, Integer.parseInt(data[data.length - 3]) * 100 / 1024),
                new DataPoint(11, Integer.parseInt(data[data.length - 2]) * 100 / 1024),
                new DataPoint(12, Integer.parseInt(data[data.length - 1]) * 100 / 1024)
        });
        lineChart_m_s.addSeries(series);
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
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void openGame() {
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame, new GameFragment(), "fragment_settings")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("game")
                        .commit();
            }
        });
    }

    private void openStats() {
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame, new StatsFragment(), "fragment_settings")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("stats")
                        .commit();
            }
        });
    }*/

    private void setActionBar(String name) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'>" + name + "</font>"));
    }

   /* @Override
    public void onResume() {
        super.onResume();
            connect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }*/

    private void checkPlantStatus(final String plant_type) {
        /*ManagerAll.getInstance().getConstantPlantData().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    for (final ArrayItem p : response.body().getArray()) {

                        if (p.getName().equalsIgnoreCase(plant_type)) {

                            int temp_dif = Integer.parseInt(p.getOptimal_temperature()) -
                                    Integer.parseInt(temperature.getText().toString().substring(0, temperature.getText().toString().indexOf(" ")));
                            int moisture_dif = Integer.parseInt(p.getOptimal_moisture()) -
                                    Integer.parseInt(moisture.getText().toString().substring(0, moisture.getText().toString().indexOf("%")));
                            int moisture_soil_dif = Integer.parseInt(p.getOptimal_moisture_soil()) -
                                    Integer.parseInt(moisture_soil.getText().toString().substring(0, moisture_soil.getText().toString().indexOf("%")));

                            info_moisture.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Air moisture information");
                                    builder.setMessage("Current air moisture: " + moisture.getText().toString() + "\n" +
                                            "Ideal air moisture: " + p.getOptimal_moisture() + "%");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                                }
                            });

                            info_moisture_soil.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Soil moisture information");
                                    builder.setMessage("Current soil moisture: " + moisture_soil.getText().toString() + "\n" +
                                            "Ideal soil moisture: " + p.getOptimal_moisture_soil() + "%");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                                }
                            });

                            info_temp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Temperature information");
                                    builder.setMessage("Current temperature: " + temperature.getText().toString() + "\n" +
                                            "Ideal temperature: " + p.getOptimal_temperature() + " °C");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                                }
                            });

                            if (temp_dif < -3) {
                                alert(temperature_card);
                                //  temperature_card.setCardBackgroundColor(Color.parseColor("#F32222"));

                            } else if (temp_dif > 3) {
                                alert(temperature_card);

                                //  temperature_card.setCardBackgroundColor(Color.parseColor("#F32222"));
                            }

                            if (moisture_dif < -3) {
                                alert(moisture_card);
                                //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));

                            } else if (moisture_dif > 3) {
                                alert(moisture_card);
                                //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));
                            }

                            if (moisture_soil_dif < -3) {
                                alert(moisture_soil_card);
                                //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));

                            } else if (moisture_soil_dif > 3) {
                                alert(moisture_soil_card);
                                //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));
                            }
                        }
                    }

                } else
                    Log.i("Error", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.i("Error", t.getMessage());
            }
        });*/

        int temp_dif = 27 -
                Integer.parseInt(temperature.getText().toString().substring(0, temperature.getText().toString().indexOf(" ")));
        int moisture_dif = 50 -
                Integer.parseInt(moisture.getText().toString().substring(0, moisture.getText().toString().indexOf("%")));
        int moisture_soil_dif = 52 -
                Integer.parseInt(moisture_soil.getText().toString().substring(0, moisture_soil.getText().toString().indexOf("%")));

        info_moisture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Air moisture information");
                builder.setMessage("Current air moisture: " + moisture.getText().toString() + "\n" +
                        "Ideal air moisture: " + 50 + "%");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        info_moisture_soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Soil moisture information");
                builder.setMessage("Current soil moisture: " + moisture_soil.getText().toString() + "\n" +
                        "Ideal soil moisture: " + 52 + "%");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        info_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Temperature information");
                builder.setMessage("Current temperature: " + temperature.getText().toString() + "\n" +
                        "Ideal temperature: " + 27 + " °C");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        if (temp_dif < -3) {
            alert(temperature_card);
            //  temperature_card.setCardBackgroundColor(Color.parseColor("#F32222"));

        } else if (temp_dif > 3) {
            alert(temperature_card);

            //  temperature_card.setCardBackgroundColor(Color.parseColor("#F32222"));
        }

        if (moisture_dif < -3) {
            alert(moisture_card);
            //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));

        } else if (moisture_dif > 3) {
            alert(moisture_card);
            //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));
        }

        if (moisture_soil_dif < -3) {
            alert(moisture_soil_card);
            //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));

        } else if (moisture_soil_dif > 3) {
            alert(moisture_soil_card);
            //  moisture_card.setCardBackgroundColor(Color.parseColor("#F32222"));
        }

    }

    private void disconnect() {
        try {
            client.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        client.unregisterResources();
    }

    private void alert(final CardView temp_card) {
        final int[] i = {0};
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (i[0] % 2 == 0)
                    temp_card.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (i[0] % 2 == 1)
                    temp_card.setBackgroundColor(Color.parseColor("#FE2E2E"));
                i[0]++;
                temp_card.invalidate();
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }


    public void connect() {

        String clientId = MqttAsyncClient.generateClientId();
        client = new MqttAndroidClient(getActivity(), "tcp://m24.cloudmqtt.com:16309",
                clientId, persistence);


        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        mqttConnectOptions.setUserName("cissktzx");
        mqttConnectOptions.setPassword("ETUpXfdiWfMO".toCharArray());
        mqttConnectOptions.setCleanSession(true);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.equals("sensorler")) {
                    String m_soil = "";
                    String m_air = "";
                    String temp = "";
                    String frostbite = "";

                    try {
                        String[] data = (new String(message.getPayload())).split(",");
                        m_soil = data[0];
                        m_air = data[1];
                        temp = data[2];
                        frostbite = data[3];
                    } catch (Exception e) {

                    }

                    int m_soil_v = Integer.parseInt(m_soil);
                    m_soil_v = m_soil_v * 100 / 1024;
                    moisture_soil.setText(m_soil_v + "%");
                    moisture.setText(m_air + "%");
                    temperature.setText(temp + " °C");
                    freeze.setText(frostbite);
                }

                checkPlantStatus("Amaryllis");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i("ö", "Delivery Complete!");
            }
        });

        try {
            client.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("ö", "Connection Success!");
                    try {

                        client.subscribe("sensorler", 0);

                        water.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    if (water_me_text.getText().toString().equalsIgnoreCase("Water me")) {
                                        client.publish("motor", new MqttMessage("1".getBytes()));
                                        water_me_text.setText("Stop watering");
                                    } else if (water_me_text.getText().toString().equalsIgnoreCase("Stop watering")) {
                                        Toast.makeText(getContext(), "You watered your plant :)", Toast.LENGTH_SHORT).show();
                                        client.publish("motor", new MqttMessage("0".getBytes()));
                                        water_me_text.setText("Water me");

                                        SharedPreferences sharedPref = getContext().getSharedPreferences("achievements", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putInt("water", sharedPref.getInt("water", 0) + 1);
                                        editor.commit();

                                    }
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } catch (MqttException ex) {

                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("ö", "Connection Failure!");
                    Log.i("ö", "throwable: " + exception.toString());
                }
            });
        } catch (MqttException ex) {
            Log.i("ö", ex.toString());
        }


    }

}
