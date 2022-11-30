package dk.hog.hoensefoedder_og_guleroedder.classes;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import dk.hog.hoensefoedder_og_guleroedder.R;
import dk.hog.hoensefoedder_og_guleroedder.interfaces.IGUI;

public class HumidityService implements IGUI {
    private Context context;

    public HumidityService(Context context){
        this.context = context;
    }

    public JSONArray GetHumidity(){
        ApiCaller caller = new ApiCaller();
        JSONArray objectArray = null;
        try {
            objectArray = caller.GetArrayData("GET", "/Humidity");
        }
        catch (Exception e) {
            Log.i("Exception", e.toString());
        }
        return objectArray;
    }

    @Override
    public void ShowOnGUI(Object value, TextView data, TextView indicator) {
        data.setText(String.format("%.1f", Float.parseFloat(value.toString())) + context.getResources().getString(R.string.PERCENTILE));
        if(Float.parseFloat(value.toString()) > 70) {
            indicator.setText(R.string.high);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.red, null));
        }
        else {
            indicator.setText(R.string.ok);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.green, null));
        }
    }
}
