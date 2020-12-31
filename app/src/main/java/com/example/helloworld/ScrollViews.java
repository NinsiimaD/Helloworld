package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScrollViews extends AppCompatActivity implements Recycler_Adapter.ItemClickListener {
    Recycler_Adapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_views);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Hi ");
        arrayList.add("What are your plans today?");
        arrayList.add("Would you like some Ice Cream?");
        arrayList.add("Do you have a boyfriend,");

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new Recycler_Adapter(this, arrayList);
        messageAdapter.setClickListener(this);
        recyclerView.setAdapter(messageAdapter);

        final ListView listView = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),"You clicked  :"+itemValue+"  on row number " +itemPosition , Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + messageAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}