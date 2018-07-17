package com.example.aartithapar.charm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText zipcodeText;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button) findViewById(R.id.submitButton);
        zipcodeText = (EditText) findViewById(R.id.zipcodeText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("zipcodes").setValue(zipcodeText.getText().toString());
                Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                startActivity(intent);
            }
        });

    }
}
