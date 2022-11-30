using System;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Logic;
using Microsoft.AspNetCore.Mvc;

namespace Hoensefoedder_og_guleroedder.Controllers
{
    [ApiController]
    [Route("api/[controller]")]

    public class WaterTankController: ControllerBase
    {
        private WaterTankLogic _waterTankLogic = new WaterTankLogic();
        
        [HttpGet]
        public IActionResult GetTemperature()
        {
            return Ok(_waterTankLogic.GetTankCapicity());
        }
    }
}