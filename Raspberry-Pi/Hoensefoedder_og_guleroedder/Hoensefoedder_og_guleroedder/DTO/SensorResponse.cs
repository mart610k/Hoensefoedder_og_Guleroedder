using System.Text.Json.Serialization;
using Hoensefoedder_og_guleroedder.Enum;

namespace Hoensefoedder_og_guleroedder.DTO;

public class SensorResponse
{
    public float Value {get; set; }
    
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public LocationType Location { get; set; }


    public SensorResponse()
    {
        
    }

    public SensorResponse(float value, LocationType location)
    {
        Value = value;
        Location = location;
    }
}