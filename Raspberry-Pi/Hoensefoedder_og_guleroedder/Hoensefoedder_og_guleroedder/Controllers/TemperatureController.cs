using System;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Logic;
using Microsoft.AspNetCore.Mvc;

namespace Hoensefoedder_og_guleroedder.Controllers
{
    /// <summary>
    /// Returns the data relating to the air temperature.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class TemperatureController : ControllerBase
    {
        private TemperatureLogic _temperatureLogic = new TemperatureLogic();
        
        /// <summary>
        /// Gets the temperatures from the sensors.
        /// </summary>
        /// <returns>A list of average temperatures</returns>
        [HttpGet]
        public IActionResult GetTemperature()
        {
            return Ok(_temperatureLogic.GetTemperature());
        }
    }
}
