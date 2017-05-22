package com.example.routes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRouteActivity extends Activity {

    Button addRouteNameButton;
    EditText addRouteEditText;
    boolean editBoxFirstClick = true;

    static final int D_DIALOG_ID = 1;
    Button ShowDatePickerDialog;
    int year_x;
    int month_x;
    int day_x;
    TextView dateTextView;

    EditText commentEditText;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        addRouteEditText = (EditText)findViewById(R.id.editText_addRoute);
        addRouteNameButtonOnClickListener();
        setAddRouteEditTextClickListener();

        final Calendar cal = Calendar.getInstance(); //the below variables are initialized with the current date so the dialog will be opened with the current date presented
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDatePickerDialog();

        dateTextView = (TextView)findViewById(R.id.textView_date);

        commentEditText = (EditText) findViewById(R.id.editText_comment);

    }

    public void addRouteNameButtonOnClickListener(){
        addRouteNameButton = (Button)findViewById(R.id.button_addRouteName);
        addRouteNameButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("route_name",addRouteEditText.getText().toString());
                        intent.putExtra("route_date",dateTextView.getText().toString());
                        intent.putExtra("route_comment",commentEditText.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
        );
    }

    public void setAddRouteEditTextClickListener(){
        addRouteEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editBoxFirstClick){
                            addRouteEditText.setText("");
                            editBoxFirstClick = false;
                        }
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog (int id){
        return new DatePickerDialog(AddRouteActivity.this,dPickerListener,year_x,month_x,day_x);

    }
    public void showDatePickerDialog(){
        ShowDatePickerDialog = (Button)findViewById(R.id.button_setDate);
        ShowDatePickerDialog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(D_DIALOG_ID);
                    }
                }
        );
    }


    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month+1;
            day_x = dayOfMonth;
            String dateString = day_x + "/" + month_x + "/" + year_x;
            //Toast.makeText(AddRouteActivity.this,day_x + "/" + month_x + "/" + year_x,Toast.LENGTH_LONG).show();
            dateTextView.setText(dateString);

        }
    };


}
