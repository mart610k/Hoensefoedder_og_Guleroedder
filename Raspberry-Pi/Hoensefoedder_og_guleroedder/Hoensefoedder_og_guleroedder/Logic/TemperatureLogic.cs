using System;
using System.Collections.Generic;
using System.Linq;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;

namespace Hoensefoedder_og_guleroedder.Logic;

public class TemperatureLogic
{
    
    
    
    public SensorResponse GetTemperatureForLocation(LocationType locationType)
    {
        return null;
    }


    /// <summary>
    /// Removes potential extremes and finds the average temperature from the given temperatures
    /// </summary>
    /// <param name="temperatures">The temperatures</param>
    /// <returns>The average of the temperatures that was sensed</returns>
    public float FindAverageTemperature(List<float> temperatures)
    {
        temperatures.Sort();

        //Get the elements to remove (80%)
        int removeamount = (int)(temperatures.Count * 0.2);
        
        //If the result is uneven
        if (removeamount % 2 == 1)
        {
            temperatures.RemoveAt(0);
            removeamount--;
        }

        for (int i = 0; i < removeamount; i += 2)
        {
            temperatures.RemoveAt(0);
            temperatures.RemoveAt(temperatures.Count - 1);
        }

        return Single.Round(temperatures.Average(),2);
    }
}