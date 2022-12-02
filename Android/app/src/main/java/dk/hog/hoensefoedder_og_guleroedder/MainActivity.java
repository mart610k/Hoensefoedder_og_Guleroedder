 package dk.hog.hoensefoedder_og_guleroedder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dk.hog.hoensefoedder_og_guleroedder.classes.HumidityService;
import dk.hog.hoensefoedder_og_guleroedder.classes.TemperatureService;
import dk.hog.hoensefoedder_og_guleroedder.classes.WaterCapacityService;
import dk.hog.hoensefoedder_og_guleroedder.enums.LocationType;

 public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialising the Temperature class
        TemperatureService temperatureServiceClass = new TemperatureService(this);
        HumidityService humidityServiceClass = new HumidityService(this);
        WaterCapacityService waterCapacityService = new WaterCapacityService(this);

        // Initialization of LocationType
        String location = LocationType.INSIDE.toString();

        // Initialization of the TextViews
        //region textViews
        TextView tempIn = findViewById(R.id.insideTemp);
        TextView tempIndicatorIn = findViewById(R.id.insideTempIndicator);
        TextView tempOut = findViewById(R.id.outsideTemp);
        TextView tempIndicatorOut = findViewById(R.id.outsideTempIndicator);
        TextView humidityIn = findViewById(R.id.insideHumidity);
        TextView humidityIndicatorIn = findViewById(R.id.insideHumidityIndicator);
        TextView humidityOut = findViewById(R.id.outsideHumidity);
        TextView humidityIndicatorOut = findViewById(R.id.outsideHumidityIndicator);
        TextView waterLevel = findViewById(R.id.capacity);
        TextView waterLevelIndicator = findViewById(R.id.capacityIndicator);
        //endregion

        //region Buttons
        Button tempButton = findViewById(R.id.temperatureButton);
        Button humidityButton = findViewById(R.id.humidityButton);
        Button waterButton = findViewById(R.id.waterButton);
        //endregion

        //region Threads
        Thread waterLevelThread;
        Thread humidityThread;
        Thread temperatureThread;
        //endregion

        //region Thread setup

        //Temperature Thread
        temperatureThread = new Thread(() -> {
            // Ensuring the thread always runs
            while (true) {
                // Get the Temperature data in a JsonArray
                JSONArray temperatureArray = temperatureServiceClass.GetTemp();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Try to get the data of each specific object, and call the ShowOnGUI method based on location of sensor
                        try {
                            if(temperatureArray != null) {
                                for(int i = 0; i < temperatureArray.length(); i++) {
                                    if(temperatureArray.getJSONObject(i).get("location").equals(location)){
                                        temperatureServiceClass.ShowOnGUI(temperatureArray.getJSONObject(i).get("value"),tempIn,tempIndicatorIn);
                                    }
                                    else {
                                        temperatureServiceClass.ShowOnGUI(temperatureArray.getJSONObject(i).get("value"),tempOut,tempIndicatorOut);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Humidity Thread Setup
        humidityThread = new Thread(() -> {
            while (true) {
                JSONArray humidityArray = humidityServiceClass.GetHumidity();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(humidityArray != null) {
                                for(int i = 0; i < humidityArray.length(); i++){
                                    if(humidityArray.getJSONObject(i).get("location").equals(location)){
                                        humidityServiceClass.ShowOnGUI(humidityArray.getJSONObject(i).get("value"),humidityIn,humidityIndicatorIn);
                                    } else {
                                        humidityServiceClass.ShowOnGUI(humidityArray.getJSONObject(i).get("value"),humidityOut,humidityIndicatorOut);
                                    }
                                }
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        waterLevelThread = new Thread(() ->{
            while (true){
                JSONObject waterLevelObject = waterCapacityService.GetWaterLevel();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(waterLevelObject != null) {
                                waterCapacityService.ShowOnGUI(waterLevelObject.get("min"),waterLevelObject.get("max"),waterLevel,waterLevelIndicator);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        //endregion

        //region Starting of threads
        temperatureThread.start();
        humidityThread.start();
        waterLevelThread.start();
        //endregion

        //region Button Clicks

        tempButton.setOnClickListener(v -> {
            AlertDialog.Builder builder =  new AlertDialog.Builder(v.getRootView().getContext());
            View mView =  LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_temperature_info, null);

            builder.setView(mView)
                    .setPositiveButton("Tilbage", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        humidityButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View mView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_humidity_info,null);

            builder.setView(mView)
                    .setPositiveButton(this.getString(R.string.back), (dialog, which) -> dialog.cancel());
            builder.show();
        });

        waterButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View mView = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.activity_water_capacity,null);

            builder.setView(mView)
                    .setPositiveButton(this.getString(R.string.back), (dialog, which) -> dialog.cancel());
            builder.show();
        });
        //endregion
    }
 }