package dk.hog.hoensefoedder_og_guleroedder.interfaces;

import android.widget.TextView;

import dk.hog.hoensefoedder_og_guleroedder.enums.LocationType;
import dk.hog.hoensefoedder_og_guleroedder.enums.SensorType;

public interface IGUI {
    public void ShowOnGUI(SensorType type, Object value, TextView temperature, TextView indicator);
}
