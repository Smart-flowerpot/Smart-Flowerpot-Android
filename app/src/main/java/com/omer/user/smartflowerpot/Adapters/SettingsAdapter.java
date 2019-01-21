package com.omer.user.smartflowerpot.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omer.user.smartflowerpot.R;

import java.util.List;

public class SettingsAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private FragmentManager fragmentManager;

    public SettingsAdapter(Context context, List<String> list, FragmentManager fragmentManager) {
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
        View layout = LayoutInflater.from(context).inflate(R.layout.settings_item, viewGroup, false);
        TextView item = layout.findViewById(R.id.settings_item);
        item.setText(list.get(i));
        item.setClickable(true);
        setItemClickAction(item);
        return layout;
    }

    private void setItemClickAction(final TextView item) {
        final String item_name = item.getText().toString();
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item_name.equalsIgnoreCase(list.get(0).toString())) {

                } else if (item_name.equalsIgnoreCase(list.get(1).toString())) {
                } else if (item_name.equalsIgnoreCase(list.get(2).toString())) {
                } else if (item_name.equalsIgnoreCase(list.get(3).toString())) {
                }
            }
        });
    }

}
