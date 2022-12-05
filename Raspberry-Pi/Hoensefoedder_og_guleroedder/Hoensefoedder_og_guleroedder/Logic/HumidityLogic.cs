using System.Collections.Generic;
using System.Linq;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Tasks;

namespace Hoensefoedder_og_guleroedder.Logic
{
    public class HumidityLogic : CommonLogic
    {
        
        public List<SensorResponse> GetHumidity()
        {
            List<SensorResponse> response = new List<SensorResponse>();
            response.Add(new SensorResponse(FindAverageFromList(DHTTask.Inside.ToList().Select(x => x.Humidity).ToList()), LocationType.INSIDE));
            response.Add(new SensorResponse(FindAverageFromList(DHTTask.Outside.ToList().Select(x => x.Humidity).ToList()), LocationType.OUTSIDE));
            
            return response;
        }
    }
}
