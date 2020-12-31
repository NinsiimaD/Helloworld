package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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


    URL ImageUrl = null;
    InputStream is = null;
    Bitmap bmImg = null;
    ImageView imageView= null;
    ProgressDialog p;
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";
    ExampleDBHelper dbHelper;
    android.widget.ListView listView;

    public void sql(View view) {
        Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
        intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
        startActivity(intent);
    }

    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Downloading...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmImg;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(bitmap);
            }else {
                p.show();
            }
        }
    }
    Button start, count;
    public void startAlert() {
        EditText text = findViewById(R.id.time);
        int i = Integer.parseInt(text.getText().toString());
        if(text.getText().toString().equals("")){

        }
        else {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
            Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(r,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        start = findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlert();
            }
        });
        count = findViewById(R.id.count);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountDown(v);
            }
        });
        Button button=findViewById(R.id.asyncTask);
        imageView=findViewById(R.id.image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("https://firebasestorage.googleapis.com/v0/b/biodataapp-61fc1.appspot.com/o/Account%2FImages%2Fcropped8245198034823675483.jpg?alt=media&token=29558a89-eedb-4d28-a650-24398d3d0794");
            }
        });


        dbHelper = new ExampleDBHelper(this);

        final Cursor cursor = dbHelper.getAllPersons();
        String [] columns = new String[] {
                ExampleDBHelper.PERSON_COLUMN_ID,
                ExampleDBHelper.PERSON_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.personID,
                R.id.personName
        };

        listView = findViewById(R.id.listView1);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.person_info,
                cursor, columns, widgets, 0);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) MainActivity.this.listView.getItemAtPosition(position);
                int personID = itemCursor.getInt(itemCursor.getColumnIndex(ExampleDBHelper.PERSON_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, personID);
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
                startActivity(new Intent(this,End_activity.class));
                return  true;

            case R.id.readc:
                startActivity(new Intent(this,bottom_activity.class));
                return  true;
            case R.id.search:
                startActivity(new Intent(this,top_activity.class));
                return  true;

            case R.id.listviewandrecycerl:
                startActivity(new Intent(this,ScrollViews.class));
                return  true;

            case R.id.cp:
                startActivity(new Intent(this,AccountProvider.class));
                return true;

            case R.id.books:
                startActivity(new Intent(this, MapsActivity.class));
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
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog22;
    private void showCountDown(View view){
        builder = new AlertDialog.Builder(MainActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
        final TextView ok =dialogView.findViewById(R.id.ok_txt);
        final EditText value =dialogView.findViewById(R.id.value);
        builder.setView(dialogView);
        builder.setView(dialogView);
        alertDialog22 = builder.create();

        alertDialog22.setCancelable(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (value.getText().toString().equals("")){

                }else {
                    value.setEnabled(false);
                    int minutes = Integer.parseInt(value.getText().toString());
                    long millis = minutes * 60 * 1000;
                    new CountDownTimer(millis, 1000) {

                        public void onTick(long millisUntilFinished) {
                            ok.setText("seconds remaining: " + millisUntilFinished / 1000);
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            ok.setText("done!");
                            value.setEnabled(true);
                            alertDialog22.setCancelable(true);
                            alertDialog22.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    alertDialog22.dismiss();
                                }
                            });
                        }

                    }.start();
                }

            }
        });
        alertDialog22.show();
    }


    public void alarmbar(View view) {
//        EditText txt = (EditText) findViewById(R.id.editText);
//        int d=Integer.parseInt(txt.getText().toString());
//        Intent i =new Intent(this, MyReceiver.class);
//        //creating apending in and call the reciever
//        PendingIntent pendingIntent= PendingIntent.getBroadcast(this.getApplicationContext(),0,i,0);
//
//        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(d*1000),pendingIntent);
//        Toast.makeText(this,"alarm set in"+d+"Seconds",Toast.LENGTH_LONG).show();

    }
}
