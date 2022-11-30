namespace Hoensefoedder_og_guleroedder.DTO;

public class WaterTankResponse
{
    public int Min { get; set; }
    public int Max { get; set; }


    public WaterTankResponse()
    {
        
    }

    public WaterTankResponse(int min, int max)
    {
        Min = min;
        Max = max;
    }

}