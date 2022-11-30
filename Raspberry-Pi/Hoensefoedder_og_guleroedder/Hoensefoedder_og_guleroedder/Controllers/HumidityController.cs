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
        
        /// <summary>
        /// Gets and returns the humidity after confirming averaging the data
        /// </summary>
        /// <returns>A list of humidity values based on location</returns>
        [HttpGet]
        public IActionResult GetHumidity()
        {
            return Ok(_humidityLogic.GetHumidity());
        }
    }
}