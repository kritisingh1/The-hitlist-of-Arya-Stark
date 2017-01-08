package com.example.dell.aryashitlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by kriti on 04-01-2017.
 */

public class AddActivity extends Activity{

    EditText nameEditText,descriptionEditText;
    Spinner statusSpinner;
    Button submitButton;

    String name;
    String status;
    String description;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        statusSpinner = (Spinner)findViewById(R.id.statusSpinner);
        submitButton = (Button)findViewById(R.id.submitButton);
        descriptionEditText = (EditText)findViewById(R.id.descriptionEditText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });
    }

    public void addToDatabase(){
        name = nameEditText.getText().toString();
        status = statusSpinner.getSelectedItem().toString().toLowerCase();
        description = descriptionEditText.getText().toString();
        DatabaseHandler dh = new DatabaseHandler(ctx);
        dh.addInfo(dh,name,status,description,R.drawable.dummy);
        Toast.makeText(getApplicationContext(),"Person Added",Toast.LENGTH_SHORT).show();
        finish();
    }

}
