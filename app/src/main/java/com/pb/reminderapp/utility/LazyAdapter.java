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

import com.pb.reminderapp.PrintPriviewActivity;
import com.pb.reminderapp.R;
import com.pb.reminderapp.model.EventDetails;
import com.pb.reminderapp.model.RateRequest;

import java.util.List;

public class LazyAdapter extends BaseAdapter {

    private Context activity;
    private List<EventDetails> data;
    private static LayoutInflater inflater = null;

    public LazyAdapter(Context a, List<EventDetails> d) {
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

    public List<EventDetails> getData() {
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
        TextView artist = (TextView) vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView) vi.findViewById(R.id.duration); // duration


        final EventDetails eventDetails = data.get(position);

        // Setting all values in listview
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb image
        Drawable myDrawable = activity.getResources().getDrawable(R.drawable.todo);
        thumb_image.setImageDrawable(myDrawable);
//        if (notification.get() == 1) {
//            myDrawable = activity.getResources().getDrawable(R.drawable.red_alert);
//            thumb_image.setImageDrawable(myDrawable);
//        }
        title.setText(eventDetails.getEventTitle());
        artist.setText(getMappedAddress(eventDetails.getRateRequest().getToAddress()));
        duration.setText(eventDetails.getEventStartDate().toString());
        ImageView mapViewLink = (ImageView) vi.findViewById(R.id.detailViewLink); // thumb image
        mapViewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), PrintPriviewActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {

//                    Intent intent = new Intent(view.getContext(), TransactionDetailsActivity.class);
//                    view.getContext().startActivity(intent);
                }
            }
        });


        return vi;
    }

    private String getMappedAddress(RateRequest.Address address) {
        StringBuilder addressString = new StringBuilder();
        for (String addressLine : address.getAddressLines()) {
            addressString.append(addressLine);
        }
        addressString.append("," + address.getCityTown() + ", " + address.getPostalCode());

        return addressString.toString();
    }
}