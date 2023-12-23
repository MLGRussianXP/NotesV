package com.dkqz.notesv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PinCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_create);

        TextView etCreate1 = findViewById(R.id.etCreate1), etCreate2 = findViewById(R.id.etCreate2);
        Button btnCreate = findViewById(R.id.btnCreate);
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);

        // change window button
        if (!preferences.getString("pin", "").equals(""))
            btnCreate.setText("Change");

        btnCreate.setOnClickListener(v -> {
            String pin1 = etCreate1.getText().toString(), pin2 = etCreate2.getText().toString();

            boolean valid = true;
            if (pin1.equals("")) {
                etCreate1.setError("Enter PIN!");
                valid = false;
            }
            if (pin2.equals("")) {
                etCreate2.setError("Enter PIN!");
                valid = false;
            }

            if (valid) {
                if (pin1.equals(pin2)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pin", pin1);
                    editor.apply();
                    finish();
                }
                else
                    etCreate2.setError("This pin doesn't match the first pin");
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}