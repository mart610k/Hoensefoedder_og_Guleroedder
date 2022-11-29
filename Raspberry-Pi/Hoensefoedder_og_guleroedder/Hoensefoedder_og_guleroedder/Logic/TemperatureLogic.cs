using System;
using System.Collections.Generic;
using System.Linq;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Tasks;

namespace Hoensefoedder_og_guleroedder.Logic;

public class TemperatureLogic : CommonLogic
{
    
    /// <summary>
    /// Gets the temperature from the TemperatureTask, calculates the average and returns the value
    /// </summary>
    /// <returns>A list of temperatures from the air sensors</returns>
    public List<SensorResponse> GetTemperature()
    {
        List<SensorResponse> response = new List<SensorResponse>();
        response.Add(new SensorResponse(FindAverageFromList(DHTTask.Inside.ToList().Select(x => x.Temperature).ToList()), LocationType.INSIDE));
        response.Add(new SensorResponse(FindAverageFromList(DHTTask.Outside.ToList().Select(x => x.Temperature).ToList()), LocationType.OUTSIDE));
        
        return response;
    }


    
}