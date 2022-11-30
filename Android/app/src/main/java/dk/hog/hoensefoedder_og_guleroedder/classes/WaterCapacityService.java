package dk.hog.hoensefoedder_og_guleroedder.classes;

import android.content.Context;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import dk.hog.hoensefoedder_og_guleroedder.R;
import dk.hog.hoensefoedder_og_guleroedder.interfaces.IExtendedGUI;

public class WaterCapacityService implements IExtendedGUI {
    Context context;
    public WaterCapacityService(Context context) {
        this.context = context;
    }

    public JSONObject GetWaterLevel() {
        ApiCaller caller = new ApiCaller();
        JSONObject waterLevelArray = null;
        try{
            waterLevelArray = caller.GetObjectData("GET", "/watertank");

        } catch (Exception e){
            e.printStackTrace();
        }
        return waterLevelArray;
    }

    @Override
    public void ShowOnGUI(Object min, Object max, TextView data, TextView indicator) {
        data.setText(String.format("%.0f", Float.parseFloat(min.toString())) + context.getResources().getString(R.string.PERCENTILE) + " " +context.getResources().getString(R.string.FROMTO) + " " + String.format("%.0f", Float.parseFloat(max.toString())) + context.getResources().getString(R.string.PERCENTILE));
        if(Float.parseFloat(min.toString()) >= 95) {
            indicator.setText(R.string.full);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.green, null));
        }
        else if(Float.parseFloat(min.toString()) >= 60) {
            indicator.setText(R.string.ok);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.green, null));
        }
        else if(Float.parseFloat(min.toString()) >= 10) {
            indicator.setText(R.string.low);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.yellow, null));
        }
        else {
            indicator.setText(R.string.empty);
            indicator.setBackgroundColor(context.getResources().getColor(R.color.red,null));
        }
    }
}
