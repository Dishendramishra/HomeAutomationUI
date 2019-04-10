package com.example.homeautomationui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button fan1;
    Button fan2;
    Button ledBulb;
    Button tubelight;
    Button door;
    Button socket;
    Button watertank;
    Button chandelier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fan1 = findViewById(R.id.fan1);
        fan2 = findViewById(R.id.fan2);
        ledBulb = findViewById(R.id.ledBulb);
        tubelight = findViewById(R.id.tubelight);
        door = findViewById(R.id.door);
        socket = findViewById(R.id.socket);
        watertank = findViewById(R.id.watertank);
        chandelier = findViewById(R.id.chandelier);

        fan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/fan1");
            }
        });

        fan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/fan2");
            }
        });

        ledBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/ledBulb");
            }
        });

        tubelight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/tubelight");
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/doorLight");
            }
        });

        socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/socket");
            }
        });

        watertank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/watertank");
            }
        });

        chandelier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postRequest().execute("http://192.168.1.166/chandelier");
            }
        });

    }

    public class postRequest extends AsyncTask<String,String,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.END, 0, 0);
                    toast.show();
                }
            });
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                System.out.println("Status code: "+con.getResponseCode());
                con.disconnect();

            }catch (Exception e){
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP|Gravity.END, 0, 0);
                        toast.show();
                    }
                });
            }

            return null;
        }
    }
}
