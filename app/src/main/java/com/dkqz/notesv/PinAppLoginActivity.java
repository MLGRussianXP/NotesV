package com.dkqz.notesv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PinAppLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_app_login);

        TextView etLogin = findViewById(R.id.etLogin);
        Button btnLogin = findViewById(R.id.btnLogin);
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String pin = preferences.getString("pin", null);
            String input = etLogin.getText().toString();

            if (input.equals(pin)) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else {
                etLogin.setError("Wrong pin!");
            }
        });

        String pin = preferences.getString("pin", null);
        if (pin == null) {
            Intent intent = new Intent(getApplicationContext(), PinCreateActivity.class);
            startActivity(intent);
        }
    }
}