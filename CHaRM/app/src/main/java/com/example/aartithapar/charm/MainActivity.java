package com.example.aartithapar.charm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText zipcodeText;
    private DatabaseReference mDatabase;
    private Spinner spinner;
    private String saltStr;

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.categories);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        submitButton = (Button) findViewById(R.id.submitButton);
        zipcodeText = (EditText) findViewById(R.id.zipcodeText);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipCode = zipcodeText.getText().toString();
                try {
                    int num = Integer.parseInt(zipCode);
                } catch (NumberFormatException exception) {
                    System.out.println("Input is not a valid integer");
                    Context context = getApplicationContext();
                    CharSequence text = "Incorrect zip code format";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    return;
                }
                if (zipCode.length() != 5) {
                    Context context = getApplicationContext();
                    CharSequence text = "Incorrect zip code length";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
                    saltStr = getSaltString();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child(saltStr).child("Zip Code: ").setValue(zipCode);
                    mDatabase.child(saltStr).child("Category of Item Recycled: ").setValue(spinner.getSelectedItem().toString());
                    mDatabase.child(saltStr).child("Date: ").setValue(date);
                    Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
