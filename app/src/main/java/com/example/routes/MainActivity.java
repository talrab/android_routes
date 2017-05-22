package com.example.routes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button zonaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addZonaButtonClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Settings Pressed",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_routes_categories:
                Toast.makeText(getApplicationContext(),"Routes Categories Pressed",Toast.LENGTH_LONG).show();
                Intent intent = new Intent("com.example.routes.RoutesCategoriesActivity");
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
            }
        return true;
    }

    public void addZonaButtonClickListener(){
        zonaButton = (Button)findViewById(R.id.button_zona);
        zonaButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.routes.RoutesCategoriesActivity");
                        startActivity(intent);
                    }
                }
        );
    }

}
