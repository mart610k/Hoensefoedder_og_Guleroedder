using System.Text.Json.Serialization;
using Hoensefoedder_og_guleroedder.Enum;

namespace Hoensefoedder_og_guleroedder.DTO;

public class DHTResponse
{
    public float Temperature { get; set; }
    public float Humidity { get; set; }
    
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public LocationType Location { get; set; }

    public DHTResponse()
    {
    }

    public DHTResponse(float temp, float humid, LocationType location)
    {
        temp = Temperature;
        humid = Humidity;
        location = Location;
    }
    
}