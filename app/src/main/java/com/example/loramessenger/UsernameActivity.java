package com.example.loramessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UsernameActivity extends AppCompatActivity {
    private DatabaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        dataBaseHelper = new DatabaseHelper(getApplicationContext(), "Message_History.db", null, 1);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        Button buttonSetUsername = findViewById(R.id.buttonSetUsername);
        buttonSetUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                dataBaseHelper.addUsername(username);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }
}