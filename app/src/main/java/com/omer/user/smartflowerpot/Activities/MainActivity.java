package com.omer.user.smartflowerpot.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.omer.user.smartflowerpot.Fragments.HomeFragment;
import com.omer.user.smartflowerpot.R;
import com.omer.user.smartflowerpot.Services.NotificationService;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment();
        configureActionBar();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        checkService();
    }

    private void checkService(){
        if (!isServiceOn()) {
            Intent intent = new Intent(getApplicationContext(), NotificationService.class);
            //intent.putExtra("id", getIntent().getStringExtra("id").toString());
            startService(intent);
        }
    }

    public boolean isServiceOn() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void changeFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(tag)
                .commit();
    }

    private void homeFragment() {
        changeFragment(new HomeFragment(), "home_fragment");
    }

    private void configureActionBar() {
        getSupportActionBar().show();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F2F2F2")));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
