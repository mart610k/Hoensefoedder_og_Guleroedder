package dk.hog.hoensefoedder_og_guleroedder.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dk.hog.hoensefoedder_og_guleroedder.R;
import dk.hog.hoensefoedder_og_guleroedder.enums.SensorType;
import dk.hog.hoensefoedder_og_guleroedder.interfaces.IGUI;

public class Temperature implements IGUI {
private Context context;

    public Temperature(Context context){
        this.context = context;
    }

    public JSONArray GetTemp(){



        JSONObject object = new JSONObject();
        JSONArray objectArray = new JSONArray();
        try {
                object.put("location", "INSIDE");
                object.put("value", 15.55f);
                objectArray.put(object);
                object = new JSONObject();
                object.put("location", "OUTSIDE");
                object.put("value", 4.26f);
                objectArray.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objectArray;
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void ShowOnGUI(SensorType type, Object value, TextView data, TextView indicator) {
        switch (type) {
            case TEMPERATURE:
                data.setText(String.format("%.02f", Float.parseFloat(value.toString())) + context.getResources().getString(R.string.TEMPERATURE));
                if(Float.parseFloat(value.toString()) > 30) {
                    indicator.setText(R.string.high);
                    indicator.setBackgroundColor(context.getResources().getColor(R.color.red, null));
                }
                else if (Float.parseFloat(value.toString()) < 15) {
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
