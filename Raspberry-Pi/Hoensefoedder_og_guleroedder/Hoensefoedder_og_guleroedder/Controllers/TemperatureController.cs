using System;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Hoensefoedder_og_guleroedder.Logic;
using Microsoft.AspNetCore.Mvc;

namespace Hoensefoedder_og_guleroedder.Controllers
{
    

[ApiController]
[Route("[controller]")]
public class TemperatureController : ControllerBase
{
    private TemperatureLogic _temperatureLogic = new TemperatureLogic();
    
    private Random _random = new Random();
    
    [HttpGet("Teperature")]
    public IActionResult GetTemperature()
    {
        
        
        return Ok(_temperatureLogic.GetTemperature());
    }
}


}
