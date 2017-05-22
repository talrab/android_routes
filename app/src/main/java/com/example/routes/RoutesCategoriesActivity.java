package com.example.routes;

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
import android.widget.Toast;

import java.util.ArrayList;

public class RoutesCategoriesActivity extends AppCompatActivity {

    static ListView routesCategoriesListView;
    //static String[]ROUTES_CATEGORIES = new String[]{"Mountain bike", "Cycling", "Hiking"};
    ArrayList<String> ROUTES_CATEGORIES;
    String[]ROUTES_CATEGORIES_DATA_FOR_INITIALIZATION = new String[]{"Mountain bike", "Cycling", "Hiking"}; ;
    DataBaseHelper myDb;

    Button viewAllButton;
    Button deleteAllButton;
    ImageButton refreshImageButton;
    Button addCategoryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_categories);

        myDb = new DataBaseHelper(getApplicationContext());
        //addData(ROUTES_CATEGORIES_DATA_FOR_INITIALIZATION);    //this can be used if you want to initialize the DB with some data.
        fillRoutesCategoriesList();
        viewAllButton = (Button)findViewById(R.id.button_showData);
        viewAll();
        deleteAll();
        refresh();
        addCategory();

    }

    public void addData(String[]categories){
        for (int i = 0; i < categories.length; i++) {
            myDb.insertCategory(categories[i]);
        }

    }

    public void fillRoutesCategoriesList(){
        routesCategoriesListView = (ListView)findViewById(R.id.ListView_RoutesCategories);
        ROUTES_CATEGORIES = myDb.getCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.routes_category_list,ROUTES_CATEGORIES);
        routesCategoriesListView.setAdapter(adapter);
        routesCategoriesListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String)routesCategoriesListView.getItemAtPosition(position);
                        //Toast.makeText(RoutesCategoriesActivity.this,"Position: " + position + " value: " + value,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent("com.example.routes.RoutsActivity");
                        intent.putExtra("ROUTE_CATEGORY", value);
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
                        Cursor res = myDb.getAllData();      //Cursor: gives random read-write access to the result set returned by a database query.
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
        deleteAllButton = (Button)findViewById(R.id.button_deleteAll);
        deleteAllButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.deleteAllData();
                        fillRoutesCategoriesList();
                    }
                }
        );
    }

    public void refresh(){
        refreshImageButton = (ImageButton)findViewById(R.id.imageButton_refresh);
        refreshImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fillRoutesCategoriesList();
                    }
                }
        );
    }

    public void addCategory(){
        addCategoryButton = (Button)findViewById(R.id.button_addCategoryName);
        addCategoryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.routes.AddCategoryActivity");
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
                String result=data.getStringExtra("category_name");
                if (myDb.insertCategory(result)) {
                    fillRoutesCategoriesList();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Category already exists",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
