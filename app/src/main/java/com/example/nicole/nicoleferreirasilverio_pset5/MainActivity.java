package com.example.nicole.nicoleferreirasilverio_pset5;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvItems;
    String mainlist_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.main_list);
        mainlist_name = "MainList";
        showMainList();
    }

    public class MainCursorAdapter extends CursorAdapter {

        public MainCursorAdapter(Context context, Cursor cursor) {

            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.layout_lists, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView tvElement = (TextView) view.findViewById(R.id.list_element);
            // Extract properties from cursor
            String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
            // Populate fields with extracted properties
            tvElement.setText(task);
        }
    }


    public void AddToMainList(View view){
        DBhelper dbHelper = new DBhelper(this, mainlist_name);

        EditText list_input = (EditText) findViewById(R.id.editText);
        String list_main = list_input.getText().toString();


        if (list_main.length() != 0) {
            dbHelper.create(list_main);
            dbHelper.close();
            list_input.setText("");
        }
        else{
            Toast.makeText(this, "Please enter a listname!", Toast.LENGTH_LONG).show();
        }
        showMainList();
    }

    public void showMainList() {
        final DBhelper dbHelper = new DBhelper(this, mainlist_name);
        final Cursor cursor = dbHelper.read();

        // Setup cursor adapter using cursor from last step
        MainCursorAdapter todoAdapter = new MainCursorAdapter(this, cursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);


        dbHelper.close();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Cursor listcursor = (Cursor) lvItems.getItemAtPosition(position);
                String name_element = listcursor.getString(listcursor.getColumnIndexOrThrow("task"));
                Intent goToListActivity = new Intent(MainActivity.this, ListActivity.class);
                goToListActivity.putExtra("NameList", name_element);
                startActivity(goToListActivity);

            }
        });
    }

}
