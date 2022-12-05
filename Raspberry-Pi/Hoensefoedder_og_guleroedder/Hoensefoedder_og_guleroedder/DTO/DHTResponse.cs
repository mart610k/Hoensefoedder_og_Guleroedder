using System.Text.Json.Serialization;
using Hoensefoedder_og_guleroedder.Enum;

namespace Hoensefoedder_og_guleroedder.DTO;

public class DHTResponse
{
    public float Temperature { get; set; }
    public float Humidity { get; set; }
    
    public DHTResponse()
    {
    }

    public DHTResponse(float temp, float humid)
    {
        temp = Temperature;
        humid = Humidity;
    }
    
}