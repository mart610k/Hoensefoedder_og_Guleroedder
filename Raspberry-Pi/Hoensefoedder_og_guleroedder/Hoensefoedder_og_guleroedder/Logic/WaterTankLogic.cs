using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Tasks;

namespace Hoensefoedder_og_guleroedder.Logic
{
    

public class WaterTankLogic
{
    public WaterTankResponse GetTankCapicity()
    {
        return WaterTankTask.WaterTankResponseData;
    }
}

}
