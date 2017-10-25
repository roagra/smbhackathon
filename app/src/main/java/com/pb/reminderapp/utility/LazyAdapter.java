package com.pb.reminderapp.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pb.reminderapp.PrintPriviewActivity;
import com.pb.reminderapp.R;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.EventInfo;
import com.pb.reminderapp.model.RateRequest;

import java.util.List;

public class LazyAdapter extends BaseAdapter {

    private Context activity;
    private List<EventInfo> data;
    private static LayoutInflater inflater = null;

    public LazyAdapter(Context a, List<EventInfo> d) {
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

    public List<EventInfo> getData() {
        return data;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) vi.findViewById(R.id.title); // title
        TextView address = (TextView) vi.findViewById(R.id.address); // To Address
        //TextView recommendation = (TextView) vi.findViewById(R.id.recommendation); // artist name
        TextView duration = (TextView) vi.findViewById(R.id.duration); // duration


        final EventInfo eventInfo = data.get(position);

        // Setting all values in listview
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb image
        Drawable myDrawable = activity.getResources().getDrawable(R.drawable.todo);


        CheckBox enabledCheckBox = vi.findViewById(R.id.enabled); // thumb image
        thumb_image.setImageDrawable(myDrawable);
//        if (notification.get() == 1) {
//            myDrawable = activity.getResources().getDrawable(R.drawable.red_alert);
//            thumb_image.setImageDrawable(myDrawable);
//        }
        enabledCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    eventInfo.setEnabled(true);
                } else {
                    eventInfo.setEnabled(false);
                }

            }
        });
        title.setText(eventInfo.getEventTitle());
        address.setText(eventInfo.getToAddress());
        //recommendation.setText(eventInfo.getSuggestion());
        duration.setText(eventInfo.getUserDeliveryDateTime());

        RadioButton standard = vi.findViewById(R.id.standard);
        RadioButton fm = vi.findViewById(R.id.fmRadio);
        RadioButton pm = vi.findViewById(R.id.pmRadio);

        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    eventInfo.getStandardShippingOption().setSelected(true);
                    eventInfo.getFmShippingOption().setSelected(false);
                    eventInfo.getPmShippingOption().setSelected(false);
                }
            }
        });

        fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    eventInfo.getStandardShippingOption().setSelected(false);
                    eventInfo.getFmShippingOption().setSelected(true);
                    eventInfo.getPmShippingOption().setSelected(false);
                }
            }
        });
        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    eventInfo.getStandardShippingOption().setSelected(false);
                    eventInfo.getFmShippingOption().setSelected(false);
                    eventInfo.getPmShippingOption().setSelected(true);
                }
            }
        });

        if (eventInfo.getStandardShippingOption() != null) {
            standard.setVisibility(View.VISIBLE);
            standard.setText(eventInfo.getStandardShippingOption().getNote());
        } else {
            standard.setVisibility(View.INVISIBLE);
        }

        if (eventInfo.getPmShippingOption() != null) {
            pm.setVisibility(View.VISIBLE);
            pm.setText(eventInfo.getPmShippingOption().getNote());
        } else {
            pm.setVisibility(View.INVISIBLE);
        }

        if (eventInfo.getFmShippingOption() != null) {
            fm.setVisibility(View.VISIBLE);
            fm.setText(eventInfo.getFmShippingOption().getNote());
        } else {
            fm.setVisibility(View.INVISIBLE);
        }

        return vi;
    }

/*    private String getMappedAddress(RateRequest.Address address) {
        StringBuilder addressString = new StringBuilder();
        for (String addressLine : address.getAddressLines()) {
            addressString.append(addressLine);
        }
        addressString.append("," + address.getCityTown() + ", " + address.getPostalCode());

        return addressString.toString();
    }*/
}