package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

public class bottom_activity extends AppCompatActivity {
    Button dora;
    TextView h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_activity);
        h=findViewById(R.id.textView3);
        dora=findViewById(R.id. cprogram);
        dora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n="";
                try {
                    InputStream inputStream=getAssets().open("c program.c");
                    int m= inputStream.available();
                    byte[] b = new byte[m];
                    inputStream.read(b);
                    inputStream.close();
                    n=new String(b);


                }
                catch (Exception  e){
                    e.printStackTrace();
                }
                h.setText((CharSequence) n);

            }
        });
    }
}
