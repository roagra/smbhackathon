package com.pb.reminderapp.utility;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pb.reminderapp.R;
import com.pb.reminderapp.model.EventInfo;

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

        final EventInfo eventInfo = data.get(position);
        TextView title = (TextView) vi.findViewById(R.id.title); // title
        if (eventInfo.isSevere()) {
            title.setTextColor(Color.RED);
        }
        TextView address = (TextView) vi.findViewById(R.id.address); // To Address
        //TextView recommendation = (TextView) vi.findViewById(R.id.recommendation); // artist name
        TextView duration = (TextView) vi.findViewById(R.id.duration); // duration


//        // Setting all values in listview
//        ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb image
//        Drawable myDrawable = activity.getResources().getDrawable(R.drawable.todo);


        CheckBox enabledCheckBox = vi.findViewById(R.id.enabled); // thumb image
        //thumb_image.setImageDrawable(myDrawable);
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

        RadioGroup radioGroup = vi.findViewById(R.id.radioGroup);
        radioGroup.removeAllViews();
        RadioButton standard = null;
        if (eventInfo.getStandardShippingOption() != null) {
            standard = new RadioButton(activity.getApplicationContext());
            standard.setTextSize(11);
//            standard.setScaleX(0.5f);
//            standard.setScaleY(0.5f);
            //standard.setEnabled(eventInfo.getStandardShippingOption().isSelected());
            standard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.isEnabled()) {
                        if (eventInfo.getStandardShippingOption() != null)
                            eventInfo.getStandardShippingOption().setSelected(true);
                        if (eventInfo.getFmShippingOption() != null)
                            eventInfo.getFmShippingOption().setSelected(false);
                        if (eventInfo.getPmShippingOption() != null)
                            eventInfo.getPmShippingOption().setSelected(false);
                    }
                }
            });
            standard.setText(eventInfo.getStandardShippingOption().getNote());
            radioGroup.addView(standard);
        }

        RadioButton fm = null;

        if (eventInfo.getFmShippingOption() != null) {
            fm = new RadioButton(activity.getApplicationContext());
            fm.setTextSize(11);
//            fm.setScaleX(0.5f);
//            fm.setScaleY(0.5f);
            //fm.setEnabled(eventInfo.getFmShippingOption().isSelected());
            fm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.isEnabled()) {
                        if (eventInfo.getStandardShippingOption() != null)
                            eventInfo.getStandardShippingOption().setSelected(false);
                        if (eventInfo.getFmShippingOption() != null)
                            eventInfo.getFmShippingOption().setSelected(true);
                        if (eventInfo.getPmShippingOption() != null)
                            eventInfo.getPmShippingOption().setSelected(false);
                    }
                }
            });
            fm.setText(eventInfo.getFmShippingOption().getNote());
            radioGroup.addView(fm);
        }
        RadioButton pm = null;

        if (eventInfo.getPmShippingOption() != null) {
            pm = new RadioButton(activity.getApplicationContext());
            pm.setTextSize(11);
//            pm.setScaleX(0.5f);
//            pm.setScaleY(0.5f);
            //fm.setEnabled(eventInfo.getPmShippingOption().isSelected());
            pm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.isEnabled()) {
                        if (eventInfo.getStandardShippingOption() != null)
                            eventInfo.getStandardShippingOption().setSelected(false);
                        if (eventInfo.getFmShippingOption() != null)
                            eventInfo.getFmShippingOption().setSelected(false);
                        if (eventInfo.getPmShippingOption() != null)
                            eventInfo.getPmShippingOption().setSelected(true);
                    }
                }
            });
            pm.setText(eventInfo.getPmShippingOption().getNote());
            radioGroup.addView(pm);
        }


//        if (eventInfo.getStandardShippingOption() != null) {
//            standard.setVisibility(View.VISIBLE);
//
//        } else {
//            standard.setVisibility(View.INVISIBLE);
//        }
//
//        if (eventInfo.getPmShippingOption() != null) {
//            pm.setVisibility(View.VISIBLE);
//            pm.setText(eventInfo.getPmShippingOption().getNote());
//        } else {
//            pm.setVisibility(View.INVISIBLE);
//        }
//
//        if (eventInfo.getFmShippingOption() != null) {
//            fm.setVisibility(View.VISIBLE);
//            fm.setText(eventInfo.getFmShippingOption().getNote());
//        } else {
//            fm.setVisibility(View.INVISIBLE);
//        }

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