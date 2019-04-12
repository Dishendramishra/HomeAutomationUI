package com.example.homeautomationui;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;

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
    AlertDialog alertDialog;

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

    @Override
    protected void onResume() {
        super.onResume();
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Connecting to server...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        new checkServer().execute();


    }

    public class checkServer extends AsyncTask<String, String, String> {

        private int responseCode;

        @Override
        protected String doInBackground(String... strings) {
            // Checking if esp8266 server is working
            ////////////////////////////////////////////////
            try {
                URL url = new URL("http://192.168.1.166");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                responseCode = con.getResponseCode();

                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ////////////////////////////////////////////////
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("Request code= "+responseCode);

            if (responseCode == 200)
                alertDialog.setMessage("Connected !");
            else
                alertDialog.setMessage("Couldn't connect to server!");


            runOnUiThread(new Runnable() {
                public void run() {

                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                }
            });
        }
    }

    public class postRequest extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
                    toast.show();
                }
            });
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                System.out.println("Status code: " + con.getResponseCode());
                con.disconnect();

            } catch (Exception e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.END, 0, 0);
                        toast.show();
                    }
                });
            }

            return null;
        }
    }
}
