using System.Collections.Generic;
using Hoensefoedder_og_guleroedder.Logic;
using Microsoft.AspNetCore.Mvc;

namespace Hoensefoedder_og_guleroedder.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class HumidityController : ControllerBase
    {
        private HumidityLogic _humidityLogic = new HumidityLogic();
        
        [HttpGet]
        public IActionResult GetHumidity()
        {
            return Ok(_humidityLogic.GetHumidity());
        }
    }
}