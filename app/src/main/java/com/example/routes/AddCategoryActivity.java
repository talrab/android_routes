package com.example.routes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddCategoryActivity extends Activity {

    Button addCategoryNameButton;
    EditText addCategoryEditText;
    boolean editBoxFirstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        addCategoryEditText = (EditText)findViewById(R.id.editText_addCategory);
        addCategoryNameButtonOnClickListener();
        setAddCategoryEditTextClickListenr();
    }

    public void addCategoryNameButtonOnClickListener(){
        addCategoryNameButton = (Button)findViewById(R.id.button_addCategoryName);
        addCategoryNameButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("category_name",addCategoryEditText.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
        );
    }

    public void setAddCategoryEditTextClickListenr(){
        addCategoryEditText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editBoxFirstClick){
                            addCategoryEditText.setText("");
                            editBoxFirstClick = false;
                        }
                    }
                }
        );

    }

}
