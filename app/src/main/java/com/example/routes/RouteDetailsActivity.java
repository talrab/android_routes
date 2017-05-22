package com.example.routes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RouteDetailsActivity extends Activity {

    TextView routeDetailsNameTextView;
    TextView routeDetailsDateTextView;
    TextView routeDetailsCommentTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        routeDetailsNameTextView = (TextView)findViewById(R.id.textView_routeDetailsName);
        routeDetailsDateTextView = (TextView)findViewById(R.id.textView_routeDetailsDate);
        routeDetailsCommentTextView = (TextView)findViewById(R.id.textView_routeDetailsComments);

        String receivedValue_name = getIntent().getStringExtra("ROUTE_NAME");
        String receivedValue_date = getIntent().getStringExtra("ROUTE_DATE");
        String receivedValue_comment = getIntent().getStringExtra("ROUTE_COMMENT");

        routeDetailsNameTextView.setText(receivedValue_name);
        routeDetailsDateTextView.setText(receivedValue_date);
        routeDetailsCommentTextView.setText(receivedValue_comment);

    }
}
