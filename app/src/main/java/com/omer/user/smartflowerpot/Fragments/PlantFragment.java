package com.omer.user.smartflowerpot.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.omer.user.smartflowerpot.Models.ArrayItem;
import com.omer.user.smartflowerpot.Models.Plant;
import com.omer.user.smartflowerpot.Models.Result;
import com.omer.user.smartflowerpot.R;
import com.omer.user.smartflowerpot.RestApi.ManagerAll;

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
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantFragment extends Fragment {

    @BindView(R.id.game)
    ImageView game;

    @BindView(R.id.stats)
    ImageView stats;

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

    View view;
    private final MemoryPersistence persistence = new MemoryPersistence();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plant, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        connect();
        setPlant();
        openGame();
        openStats();
        setActionBar("Plant 1");
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

    private void openGame() {
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
    }

    private void setActionBar(String name) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html
                .fromHtml("<font style='align:center' color='#808080'>" + name + "</font>"));
    }

    private void setPlant() {



        /*
        ManagerAll.getInstance().getPlant(1).enqueue(new Callback<Plant>() {
            @Override
            public void onResponse(Call<Plant> call, final Response<Plant> response) {
                if (response.isSuccessful()) {
                    setActionBar(response.body().getName());
                    temperature.setText(response.body().getTemperature() + " °C");
                    light.setText(((response.body().getLight() * 100 / 1024)) + "%");
                    moisture.setText(response.body().getMoisture_air() + "%");
                    moisture_soil.setText(response.body().getMoisture_soil() + "%");

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
        });*/
    }

    private void checkPlantStatus(final String plant_type) {
        ManagerAll.getInstance().getConstantPlantData().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    for (final ArrayItem p : response.body().getArray()) {
                        Log.i("ö", p.getName());
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
        });

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

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(getActivity(), "tcp://m24.cloudmqtt.com:16309",
                clientId, persistence);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i("ö", "Connection was lost!");
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

                    moisture_soil.setText(m_soil + "%");
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

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        mqttConnectOptions.setUserName("cissktzx");
        mqttConnectOptions.setPassword("ETUpXfdiWfMO".toCharArray());
        mqttConnectOptions.setCleanSession(true);

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


       /* String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(getActivity(), "http://m24.cloudmqtt.com:16309",
                clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setCleanSession(true);
        options.setUserName("cissktzx");
        options.setPassword("ETUpXfdiWfMO".toCharArray());
        options.setAutomaticReconnect(true);


        try {
            IMqttToken token = client.connect(options);
            if (client.isConnected()) {
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        subscribe(client, "toprakNem");
                        subscribe(client, "havaNem");
                        subscribe(client, "havaIsi");

                        publish(client, "led", "1");

                        water.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("ö", "watered");
                                publish(client, "motor", "1");
                            }
                        });

                        client.setCallback(new MqttCallback() {
                            @Override
                            public void connectionLost(Throwable cause) {
                                Log.i("ö", cause.getMessage().toString());
                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {

                                Log.i("ö", topic.toString());
                                if (topic.equals("toprakNem")) {
                                    moisture_soil.setText(message.getPayload().toString());
                                }

                                if (topic.equals("havaNem")) {
                                    moisture.setText(message.getPayload().toString());
                                }

                                if (topic.equals("havaIsi")) {
                                    temperature.setText(message.getPayload().toString());
                                }

                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {
                                try {
                                    Log.i("ö", token.getMessage().toString());
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.i("ö", "onFailure");

                    }


                });
            } else
                Log.i("ö", "not connected");
        } catch (MqttException e) {
            Log.i("ö", e.getMessage().toString());
        }*/
    }

}
