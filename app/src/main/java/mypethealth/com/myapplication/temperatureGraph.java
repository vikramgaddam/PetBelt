package mypethealth.com.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.lang.Object;
import java.util.Date;
import java.util.List;

public class temperatureGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String temprature_url="https://api.thingspeak.com/channels/120726/fields/1.json?results=100";



        JSONParser jParser = new JSONParser();

        JSONObject temperature_json = jParser.getJson(temprature_url);

        String temperature_sensor;

        try
        {
            if(temperature_json!=null) {
                JSONArray values = temperature_json.getJSONArray("feeds");

                String temperature_values[]=new String[values.length()];

                for(int i=0;i<values.length();i++) {
                    JSONObject obj = values.getJSONObject(i);

                    temperature_sensor = obj.getString("field1");
                    int length=temperature_sensor.length();

                    float f=Float.parseFloat(temperature_sensor.substring(1,length));

                    f=(f*(float)1.8)+32;

                    temperature_values[i]=Float.toString(f);


                }

        //float data[]={1,2,3,4,9,8,7,6};



        ListView l1=(ListView)findViewById(R.id.temperatute_list);



        float data[]=new float[temperature_values.length];

        for(int i=0;i<data.length;i++)
        {
            data[i]=Float.parseFloat(temperature_values[i]);
        }

        SparkView sparkView = (SparkView) findViewById(R.id.sparkview);
        sparkView.setAdapter(new MyAdapter(data));


        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.temperature_list_item, Arrays.asList(temperature_values));

        l1.setAdapter(arrayAdapter);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public class MyAdapter extends SparkAdapter {
        private float[] yData;

        public MyAdapter(float[] yData) {
            this.yData = yData;


        }

        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }

    }

}
