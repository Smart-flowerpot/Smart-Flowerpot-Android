package com.omer.user.smartflowerpot.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omer.user.smartflowerpot.Models.AchievementlistItem;
import com.omer.user.smartflowerpot.R;

import java.util.List;

public class AchievementsAdapter extends BaseAdapter {

    private Context context;
    private List<AchievementlistItem> list;
    private FragmentManager fragmentManager;

    public AchievementsAdapter(Context context, List<AchievementlistItem> list, FragmentManager fragmentManager) {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = LayoutInflater.from(context).inflate(R.layout.achievements_item, viewGroup, false);
        TextView name = layout.findViewById(R.id.name);
        TextView des = layout.findViewById(R.id.description);
        LinearLayout background = layout.findViewById(R.id.background);

        SharedPreferences sharedPref = context.getSharedPreferences("achievements", Context.MODE_PRIVATE);

        if (sharedPref.getInt("water", 0) >= 5 && i == 0)
            background.setBackgroundColor(Color.parseColor("#B2FCCE"));

        if (sharedPref.getInt("takecare", 0) >= 10 && i == 5)
            background.setBackgroundColor(Color.parseColor("#B2FCCE"));

        name.setText(list.get(i).getAchievementname());
        des.setText(list.get(i).getPoints());
        return layout;
    }
}
