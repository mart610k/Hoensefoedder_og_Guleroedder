 package dk.hog.hoensefoedder_og_guleroedder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import dk.hog.hoensefoedder_og_guleroedder.enums.SensorType;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random();
        TextView tempIn = findViewById(R.id.insideTemp);
        TextView tempOut = findViewById(R.id.outsideTemp);
        final int[] i = {0};
        //region Thread setup for Getting Data!
        Thread test = new Thread(new Runnable() {
            @Override
            public void run() {
                while (i[0] < 5) {
                    int ShowRand = rand.nextInt(100);
                    tempIn.post(new Runnable() {
                        @Override
                        public void run() {
                            tempIn.setText(ShowRand + "° Celcius");
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i[0]++;
                }
            }
        });
        test.start();
        //endregion
    }
}