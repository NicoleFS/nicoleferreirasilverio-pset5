package com.example.nicole.nicoleferreirasilverio_pset5;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    String name_list;
    ListView lvListItems;
    DBhelper dataBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // get the data needed
        name_list = getIntent().getExtras().getString("NameList");
        lvListItems = (ListView) findViewById(R.id.next_list);
        // dataBHelper = new DBhelper(ListActivity.this, name_list);
    }

    public class TodoCursorAdapter extends CursorAdapter {

        public TodoCursorAdapter(Context context, Cursor cursor) {

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

    public void AddToList(View view){

        dataBHelper = new DBhelper(ListActivity.this, name_list);

        EditText list_input = (EditText) findViewById(R.id.editTextList);
        String list_element = list_input.getText().toString();


        if (list_element.length() != 0) {
            dataBHelper.create(list_element);
            dataBHelper.close();
            list_input.setText("");
        }
        else{
            Toast.makeText(this, "Please enter a task!", Toast.LENGTH_LONG).show();
        }
        showList();
    }

    public void showList() {
        dataBHelper = new DBhelper(ListActivity.this, name_list);
        final Cursor cursor = dataBHelper.read();

        // Setup cursor adapter using cursor from last step
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, cursor);
        // Attach cursor adapter to the ListView
        lvListItems.setAdapter(todoAdapter);


        dataBHelper.close();
    }

}
