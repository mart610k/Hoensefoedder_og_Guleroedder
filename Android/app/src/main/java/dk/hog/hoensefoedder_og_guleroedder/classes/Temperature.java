package dk.hog.hoensefoedder_og_guleroedder.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dk.hog.hoensefoedder_og_guleroedder.Datamodel.APIResponse;
import dk.hog.hoensefoedder_og_guleroedder.R;
import dk.hog.hoensefoedder_og_guleroedder.enums.SensorType;
import dk.hog.hoensefoedder_og_guleroedder.interfaces.IGUI;

public class Temperature implements IGUI {
private Context context;

    public Temperature(Context context){
        this.context = context;
    }

    public JSONArray GetTemp(){
        ApiCaller caller = new ApiCaller();
        JSONObject object = new JSONObject();
        JSONArray objectArray = null;
        try {
            objectArray = caller.GetAPIData("GET","/temperature");
        }
        catch (Exception e) {
            Log.i("Exception", e.toString());
        }
        return objectArray;
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void ShowOnGUI(SensorType type, Object value, TextView data, TextView indicator) {
        switch (type) {
            case TEMPERATURE:
                data.setText(String.format("%.02f", Float.parseFloat(value.toString())) + context.getResources().getString(R.string.TEMPERATURE));
                if(Float.parseFloat(value.toString()) > 25) {
                    indicator.setText(R.string.high);
                    indicator.setBackgroundColor(context.getResources().getColor(R.color.red, null));
                }
                else if (Float.parseFloat(value.toString()) <= 0) {
                    indicator.setText(R.string.low);
                    indicator.setBackgroundColor(context.getResources().getColor(R.color.blue,null));
                }
                else {
                    indicator.setText(R.string.ok);
                    indicator.setBackgroundColor(context.getResources().getColor(R.color.green, null));
                }
                break;
        }


    }
}
