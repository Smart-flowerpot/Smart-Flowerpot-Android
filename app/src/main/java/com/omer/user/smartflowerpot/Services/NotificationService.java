package com.omer.user.smartflowerpot.Services;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.omer.user.smartflowerpot.Models.ArrayItem;
import com.omer.user.smartflowerpot.Models.Result;
import com.omer.user.smartflowerpot.RestApi.ManagerAll;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    private final MemoryPersistence persistence = new MemoryPersistence();


    public NotificationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connect();
        return START_REDELIVER_INTENT;
    }

    public void connect() {

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client = new MqttAndroidClient(this, "tcp://m24.cloudmqtt.com:16309",
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
                        final String[] data = (new String(message.getPayload())).split(",");
                        m_soil = data[0];
                        m_air = data[1];
                        temp = data[2];
                        frostbite = data[3];

                        final String finalM_soil = m_soil;
                        final String finalM_air = m_air;
                        final String finalTemp = temp;
                        final String finalFrostbite = frostbite;

                        final int[] i = {1};

                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPref = getApplication().getSharedPreferences("data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(i[0]+"", finalM_soil + "," + finalM_air + "," + finalTemp + "," + finalFrostbite);
                                editor.commit();
                                i[0]--;

                                if(i[0] == 12)
                                    i[0] = 1;
                            }
                        },0,1000*60*60*2);


                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                checkPlantStatus("Amaryllis", data);
                            }
                        },0,1000*60*60);



                    } catch (Exception e) {

                    }

                }

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

    private void checkPlantStatus(final String plant_type, final String[] data) {
        ManagerAll.getInstance().getConstantPlantData().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    for (final ArrayItem p : response.body().getArray()) {
                        if (p.getName().equalsIgnoreCase(plant_type)) {

                            int temp_dif = Math.abs(Integer.parseInt(data[2]) - Integer.parseInt(p.getOptimal_temperature()));
                            int moisture_dif = Math.abs(Integer.parseInt(data[1]) - Integer.parseInt(p.getOptimal_moisture()));
                            int moisture_soil_dif = Math.abs(Integer.parseInt(data[0]) - Integer.parseInt(p.getOptimal_moisture_soil()));

                            if (temp_dif < 3) {

                                ManagerAll.getInstance().sendNotification("Temperature is critic.").enqueue(new Callback<com.omer.user.smartflowerpot.Models.Response>() {
                                    @Override
                                    public void onResponse(Call<com.omer.user.smartflowerpot.Models.Response> call, Response<com.omer.user.smartflowerpot.Models.Response> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<com.omer.user.smartflowerpot.Models.Response> call, Throwable t) {

                                    }
                                });

                            }

                            if (moisture_dif < 3) {
                                Toast.makeText(getApplicationContext(), " sfsfs", Toast.LENGTH_SHORT).show();
                                ManagerAll.getInstance().sendNotification("Air moisture is critic.").enqueue(new Callback<com.omer.user.smartflowerpot.Models.Response>() {
                                    @Override
                                    public void onResponse(Call<com.omer.user.smartflowerpot.Models.Response> call, Response<com.omer.user.smartflowerpot.Models.Response> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<com.omer.user.smartflowerpot.Models.Response> call, Throwable t) {

                                    }
                                });

                            }
                            if (moisture_soil_dif < 3) {
                                ManagerAll.getInstance().sendNotification("Soil moisture is critic.").enqueue(new Callback<com.omer.user.smartflowerpot.Models.Response>() {
                                    @Override
                                    public void onResponse(Call<com.omer.user.smartflowerpot.Models.Response> call, Response<com.omer.user.smartflowerpot.Models.Response> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<com.omer.user.smartflowerpot.Models.Response> call, Throwable t) {

                                    }
                                });

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

}
