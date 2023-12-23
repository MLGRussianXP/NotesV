package com.dkqz.notesv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etNote = findViewById(R.id.etAdd);
        Button btnAdd = findViewById(R.id.btnAdd);
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);

        if (preferences.getString("notes", "").equals(""))
            data = new ArrayList<>();
        else
            data = new ArrayList<>(
                    Arrays.asList(
                            preferences.getString("notes", "").split("___")
                    )
            );

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, data
        );
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete this note");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int n) {
                        data.remove(i);
                        adapter.notifyDataSetChanged();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("notes", String.join("___", data));
                        editor.apply();
                    }
                });

                builder.setNegativeButton("No", null);

                builder.show();

                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAdd = etNote.getText().toString();

                if (toAdd.equals(""))
                    return;

                data.add(toAdd);
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(data.size());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("notes", String.join("___", data));
                editor.apply();

                etNote.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);

        // change pin
        if (item.getItemId() == R.id.menu_item_1) {
            Intent intent = new Intent(getApplicationContext(), PinCreateActivity.class);
            startActivity(intent);
        }

        // clear notes
        else if (item.getItemId() == R.id.menu_item_2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear notes");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("notes", "");
                    editor.apply();

                    data.clear();
                    adapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("No", null);

            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Are you sure?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }
}