package com.example.routes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import static com.example.routes.RoutesCategoriesActivity.routesCategoriesListView;

public class RoutsActivity extends Activity {

    static ListView routesListView;
    TextView categoryNameTextView;
    ArrayList<String> ROUTES_NAMES,ROUTES_DATES,ROUTES_COMMENTS;
    DataBaseHelper myDb;

    Button viewAllButton;
    Button deleteAllButton;
    Button addRouteButton;

    String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routs);

        categoryNameTextView = (TextView)findViewById(R.id.textView_categoryName);
        region = getIntent().getStringExtra("ROUTE_CATEGORY");
        categoryNameTextView.setText(getString(R.string.category_name) + ": " + region);

        myDb = new DataBaseHelper(getApplicationContext());
        //addData(ROUTES_CATEGORIES_DATA_FOR_INITIALIZATION);    //this can be used if you want to initialize the DB with some data.
        fillRoutesList();
        viewAllButton = (Button)findViewById(R.id.button_routes_showData);
        viewAll();
        deleteAll();
        addRoute();

    }

    public void addData(String[]categories){
        for (int i = 0; i < categories.length; i++) {
            myDb.insertCategory(categories[i]);
        }

    }

    public void fillRoutesList(){
        routesListView = (ListView)findViewById(R.id.ListView_Routes);
        ROUTES_NAMES = myDb.getRoutesNames(region);
        ROUTES_DATES = myDb.getRoutesDates(region);
        ROUTES_COMMENTS = myDb.getRoutesComments(region);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.routes_category_list,ROUTES);
        RoutesAdapter adapter = new RoutesAdapter(this,R.layout.routes_list,ROUTES_NAMES.toArray(new String[0]),(String[])ROUTES_DATES.toArray(new String[0]));
        routesListView.setAdapter(adapter);

        routesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //String value = (String)routesListView.getItemAtPosition(position);
                        String name = ROUTES_NAMES.get(position);
                        String date = ROUTES_DATES.get(position);
                        String comment = ROUTES_COMMENTS.get(position);
                        //Toast.makeText(RoutsActivity.this,"Position: " + position + " Name: " + ROUTES_NAMES.get(position) + " Date: " + ROUTES_DATES.get(position) + " Comment: " + ROUTES_COMMENTS.get(position),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("com.example.routes.RouteDetailsActivity");
                        intent.putExtra("ROUTE_NAME", name);
                        intent.putExtra("ROUTE_DATE", date);
                        intent.putExtra("ROUTE_COMMENT", comment);
                        startActivity(intent);
                    }
                }
        );
    }




    public void viewAll(){
        viewAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllRoutesData();      //Cursor: gives random read-write access to the result set returned by a database query.
                        if(res.getCount() == 0){
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        else{
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()){
                                buffer.append("Id :" + res.getString(0) + "\n");
                                buffer.append("Name :" + res.getString(1) + "\n");
                            }
                            showMessage("Data", buffer.toString());
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void deleteAll(){
        deleteAllButton = (Button)findViewById(R.id.button_routes_deleteAll);
        deleteAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.deleteAllDataRoutes();
                        fillRoutesList();
                    }
                }
        );
    }

    public void addRoute(){
        addRouteButton = (Button)findViewById(R.id.button_addRoute);
        addRouteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.routes.AddRouteActivity");
                        //startActivity(intent);
                        startActivityForResult(intent,1);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String routeName = data.getStringExtra("route_name");
                String routeDate = data.getStringExtra("route_date");
                String routeComment = data.getStringExtra("route_comment");
                //String result = routeName + " " + routeDate;
                //if (myDb.insertRoute(result)) {
                if (myDb.insertRoute(routeName,routeDate,routeComment,region)) {
                    fillRoutesList();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Trip already exists",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
