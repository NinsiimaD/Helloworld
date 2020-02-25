package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button play=(Button)findViewById(R.id.button3);
       play.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,top_activity.class);
               startActivity(intent);
           }
       });
       Button lis= (Button)findViewById(R.id.button2);
       lis.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(MainActivity.this,listview.class);
               startActivity(intent);
           }
       });



        }
        public void sendMessage(View view){
           EditText message =(EditText)findViewById(R.id.message);
            Toast.makeText(this, "sending message" +message.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra("MESSAGE",message.getText().toString());
            startActivity(intent);

            message.setText("");


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.men,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.media:
                startActivity(new Intent(this,Begin_activity.class));
                return  true;
            case R.id.search:
                startActivity(new Intent(this,End_activity.class));
                return  true;
default:
            return true;
        }

    }
}
