namespace Hoensefoedder_og_guleroedder.DTO;

public class SensorResponse
{
    public float Value {get; set; }

    public SensorResponse()
    {
        
    }

    public SensorResponse(float value)
    {
        Value = value;
    }
}