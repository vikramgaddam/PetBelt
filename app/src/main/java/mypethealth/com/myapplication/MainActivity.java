package mypethealth.com.myapplication;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static  int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Refresh().execute("gughjkl");



    }

    private class Refresh extends AsyncTask<String, Void, JSONObject> {

        public JSONObject temperature_json;
        @Override
        protected JSONObject doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.

                String temprature_url="https://api.thingspeak.com/channels/120726/feeds/last.json";

                JSONParser jParser = new JSONParser();

                temperature_json = jParser.getJson(temprature_url);


            Log.v("dummy", "dvdsvsdfv");
            return temperature_json;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {


            try {
                String temperature = result.getString("field1");

                TextView t=(TextView)findViewById(R.id.temperature_value);
                int length=temperature.length();
                float f=Float.parseFloat(temperature.substring(1,length));
                temp=(int)f;
                Log.v("dummy",Float.toString( f* (float)1.8));
                f=(f * (float)1.8) + 32;
                temp=(int)f;
                Log.v("dummy",Integer.toString(temp));
                t.setText(Float.toString(f)+"      ");

                TextView status =(TextView)findViewById(R.id.textView);

                if(f>103)
                {

                   status.setText("HIGH BODY TEMPERATURE  ");
                }
                else
                {
                    if(f<80)
                    {
                        status.setText("LOW BODY TEMPERATURE ");
                    }
                }

                t=(TextView)findViewById(R.id.air_quality_value);

                temperature=result.getString("field3");

                int d=Integer.parseInt(temperature);

                if(d>800)
                {
                    t.setText("Good  ");


                }
                else
                {
                    t.setText("BAD AIR  ");

                    String g=(String)status.getText();

                    if(g.length()<=2)
                    {
                        g=g+" BAD AIR  ";
                    }
                    else
                    {
                        g="BAD AIR  ";
                    }

                    status.setText(g);

                    t.setTextColor(Color.rgb(255,0,0));
                }


                temperature=temperature_json.getString("field7");

                t=(TextView)findViewById(R.id.active_points_value);

                d=(int)Float.parseFloat(temperature);

                t.setText(Integer.toString(d)+"  ");





                Log.v("dummy", "");

            }catch (JSONException e)
            {
                e.printStackTrace();
                Log.v("dummy", "jnjnmklmhbjnkl");
            }


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void TemperatureGraph(View v)
    {
        Intent i=new Intent(this,temperatureGraph.class);
        startActivity(i);
    }
    public void airQuality(View v)
    {
        Intent i=new Intent(this,AirQualityGraph.class);
        startActivity(i);
    }

    public void activePoints(View v)
    {
        Intent i=new Intent(this,ActivePoints.class);
        startActivity(i);
    }



}