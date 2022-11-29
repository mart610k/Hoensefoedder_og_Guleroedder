 package dk.hog.hoensefoedder_og_guleroedder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import dk.hog.hoensefoedder_og_guleroedder.classes.Humidity;
import dk.hog.hoensefoedder_og_guleroedder.classes.Temperature;
import dk.hog.hoensefoedder_og_guleroedder.enums.LocationType;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialising the Temperature class
        Temperature temperatureClass = new Temperature(this);
        Humidity humidityClass = new Humidity(this);

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
        //endregion

        //region Thread setup

        //Temperature Thread
        Thread temperatureThread = new Thread(() -> {
            // Ensuring the thread always runs
            while (true) {
                // Get the Temperature data in a JsonArray
                JSONArray temperatureArray = temperatureClass.GetTemp();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Try to get the data of each specific object, and call the ShowOnGUI method based on location of sensor
                        try {
                            if(temperatureArray != null) {
                                for(int i = 0; i < temperatureArray.length(); i++) {
                                    if(temperatureArray.getJSONObject(i).get("location").equals(location)){
                                        temperatureClass.ShowOnGUI(temperatureArray.getJSONObject(i).get("value"),tempIn,tempIndicatorIn);
                                    }
                                    else {
                                        temperatureClass.ShowOnGUI(temperatureArray.getJSONObject(i).get("value"),tempOut,tempIndicatorOut);
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
        Thread humidityThread = new Thread(() -> {
            while (true) {
                JSONArray humidityArray = humidityClass.GetHumidity();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(humidityArray != null) {
                                for(int i = 0; i < humidityArray.length(); i++){
                                    if(humidityArray.getJSONObject(i).get("location").equals(location)){
                                        humidityClass.ShowOnGUI(humidityArray.getJSONObject(i).get("value"),humidityIn,humidityIndicatorIn);
                                    } else {
                                        humidityClass.ShowOnGUI(humidityArray.getJSONObject(i).get("value"),humidityOut,humidityIndicatorOut);
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
        //endregion

        //region Starting of threads
        temperatureThread.start();
        humidityThread.start();
        //endregion
    }
}