package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver r=new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int y = intent.getIntExtra("level", 0);
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
            bar.setProgress(y);
            TextView textView = (TextView) findViewById(R.id.textView4);
            textView.setText("battery level is " + Integer.toString(y) + "%");

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(r,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));



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
                startActivity(new Intent(this,End_activity.class));
                return  true;

            case R.id.readc:
                startActivity(new Intent(this,bottom_activity.class));
                return  true;
            case R.id.search:
                startActivity(new Intent(this,top_activity.class));
                return  true;

            case R.id.books:
                startActivity(new Intent(this,listview.class));
                return true;

            case R.id.call:

                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData( Uri.parse("tel:0752045505"));

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent2);
                }
                return true;

            case R.id.email:

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("emailto:"));
                String to[] = {"ninsiimadoreen85@gmail.com", "steven.muzeyi@gmail.com", "kunywananigyep@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, "good day");
                intent.putExtra(Intent.EXTRA_TEXT, "inbox me soon");
                intent.setType("message/rfc822");
                startActivity(intent);
                return true;


            default:
            return true;
        }

    }

    public void alarmbar(View view) {
        EditText txt = (EditText) findViewById(R.id.editText);
        int d=Integer.parseInt(txt.getText().toString());
        Intent i =new Intent(this, MyReceiver.class);
        //creating apending in and call the reciever
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this.getApplicationContext(),0,i,0);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(d*1000),pendingIntent);
        Toast.makeText(this,"alarm set in"+d+"Seconds",Toast.LENGTH_LONG).show();

    }
}
