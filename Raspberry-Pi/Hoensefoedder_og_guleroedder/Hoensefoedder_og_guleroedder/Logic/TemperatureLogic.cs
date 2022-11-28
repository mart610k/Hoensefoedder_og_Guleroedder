using System;
using System.Collections.Generic;
using System.Linq;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Tasks;

namespace Hoensefoedder_og_guleroedder.Logic;

public class TemperatureLogic{
    
    /// <summary>
    /// Gets the temperature from the TemperatureTask, calculates the average and returns the value
    /// </summary>
    /// <returns>A list of temperatures from the air sensors</returns>
    public List<SensorResponse> GetTemperature()
    {
        List<SensorResponse> response = new List<SensorResponse>();
        
        response.Add(new SensorResponse(FindAverageTemperature(TemperatureTask.Inside.ToList()), LocationType.INSIDE));
        response.Add(new SensorResponse(FindAverageTemperature(TemperatureTask.Outside.ToList()), LocationType.OUTSIDE));
        return response;
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