 package dk.hog.hoensefoedder_og_guleroedder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import dk.hog.hoensefoedder_og_guleroedder.classes.Temperature;
import dk.hog.hoensefoedder_og_guleroedder.enums.SensorType;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialising the Temperature class
        Temperature temperatureClass = new Temperature(this);

        // Initialization of the Textviews
        //region temperature textviews
        TextView tempIn = findViewById(R.id.insideTemp);
        TextView tempIndicatorIn = findViewById(R.id.insideTempIndicator);
        TextView tempOut = findViewById(R.id.outsideTemp);
        TextView tempIndicatorOut = findViewById(R.id.outsideTempIndicator);
        //endregion

        //region Thread setup for Temperature Data!

        // Creation of the Temperature Thread
        Thread temperatureThread = new Thread(() -> {
            // Ensuring the thread always runs
            while (true) {
                // Get the Temperature data in a JsonArray
                JSONArray testing = temperatureClass.GetTemp();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Try to get the data of each specific object, and call the ShowOnGUI method based on location of sensor
                        try {
                            for(int i = 0; i < testing.length(); i++) {
                                if(testing.getJSONObject(i).get("location") == "INSIDE"){
                                    temperatureClass.ShowOnGUI(SensorType.TEMPERATURE,testing.getJSONObject(i).get("value"),tempIn,tempIndicatorIn);
                                }
                                else {
                                    temperatureClass.ShowOnGUI(SensorType.TEMPERATURE,testing.getJSONObject(i).get("value"),tempOut,tempIndicatorOut);
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
        temperatureThread.start();
        //endregion
    }
}