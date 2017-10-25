package com.pb.reminderapp.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pb.reminderapp.LabelCountActivity;
import com.pb.reminderapp.PrintPriviewActivity;
import com.pb.reminderapp.R;
import com.pb.reminderapp.model.EventInfo;

import java.util.List;

public class LabelCountListAdapter extends BaseAdapter {

    private Context activity;
    private List<LabelCountActivity.LabelCount> data;
    private static LayoutInflater inflater = null;

    public LabelCountListAdapter(Context a, List<LabelCountActivity.LabelCount> d) {
        this.activity = a;
        this.data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public List<LabelCountActivity.LabelCount> getData() {
        return data;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.lablel_count_row, null);

        TextView dateRange = (TextView) vi.findViewById(R.id.dateRange);
        TextView address = (TextView) vi.findViewById(R.id.countValue);
        final LabelCountActivity.LabelCount labelCount = data.get(position);
        dateRange.setText(labelCount.getDateRange());
        address.setText(labelCount.getCount());
        return vi;
    }

}