package com.pb.reminderapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PrintPriviewActivity extends Activity {
    private PrintPriviewActivity printPriviewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_priview);
        this.printPriviewActivity = this;

        ImageView backImageView = findViewById(R.id.image_view_header_icon);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPriviewActivity.onBackPressed();
            }
        });


    }
}
