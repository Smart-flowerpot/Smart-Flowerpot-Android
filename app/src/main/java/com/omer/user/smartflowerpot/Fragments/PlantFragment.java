package com.omer.user.smartflowerpot.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import java.io.UnsupportedEncodingException;

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

    @BindView(R.id.info_light)
    ImageView info_light;

    @BindView(R.id.info_moisture)
    ImageView info_moisture;

    @BindView(R.id.info_moisture_soil)
    ImageView info_moisture_soil;

    @BindView(R.id.info_temp)
    ImageView info_temp;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plant, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        connect();
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
                    for (final ArrayItem p : response.body().getArray())
                        if (p.getName().equalsIgnoreCase(plant_type)) {

                            int temp_dif = Integer.parseInt(p.getOptimal_temperature()) -
                                    Integer.parseInt(temperature.getText().toString().substring(0, temperature.getText().toString().indexOf(" ")));
                            int moisture_dif = Integer.parseInt(p.getOptimal_moisture()) -
                                    Integer.parseInt(moisture.getText().toString().substring(0, moisture.getText().toString().indexOf("%")));

                            info_moisture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Moisture information");
                                    builder.setMessage("Current moisture: " + moisture.getText().toString() + "\n" +
                                            "Ideal moisture: " + p.getOptimal_moisture() + "%");
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
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void connect() {

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(getActivity(), "tcp://m24.cloudmqtt.com:16309",
                clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        options.setCleanSession(false);
        options.setUserName("cissktzx");
        options.setPassword("ETUpXfdiWfMO".toCharArray());

        try {
            IMqttToken token = client.connect(options);
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
        } catch (MqttException e) {
            Log.i("ö", e.getMessage().toString());
        }
    }

    public void publish(MqttAndroidClient client, String topic, String payload) {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            Log.i("ö", e.getMessage().toString());
        }
    }


    public void subscribe(MqttAndroidClient client, String topic) {
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                }
            });
        } catch (MqttException e) {
            Log.i("ö", e.getMessage().toString());
        }
    }


}
