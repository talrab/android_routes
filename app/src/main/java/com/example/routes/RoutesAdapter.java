package com.example.routes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tal on 19/02/2017.
 */

public class RoutesAdapter extends ArrayAdapter<String> {

    private Context context;
    private String [] routeNames = {};
    private String [] routeDates = {};



    public RoutesAdapter(Context context, int resource, String [] routeNames, String [] routeDates) {
        super(context, resource, routeNames);

        this.context = context;
        this.routeNames = routeNames;
        this.routeDates = routeDates;

    }

    //Inner Class to hold our views
    public class ViewHolder {
        TextView name;
        TextView date;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // First let's verify the convertView is not null
        if (convertView == null) {
        // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.routes_list, parent, false);
        }

        final ViewHolder vh = new ViewHolder();
        vh.name = (TextView) convertView.findViewById(R.id.name);
        vh.date = (TextView) convertView.findViewById(R.id.date);

        vh.name.setText(routeNames[position]);
        vh.date.setText(routeDates[position]);

        return convertView;
    }
}
