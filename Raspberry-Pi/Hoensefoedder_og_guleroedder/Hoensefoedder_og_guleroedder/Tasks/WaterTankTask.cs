using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading;
using System.Threading.Tasks;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;
using Microsoft.Extensions.Hosting;

namespace Hoensefoedder_og_guleroedder.Tasks;

public class WaterTankTask : BackgroundService
{
    public static WaterTankResponse WaterTankResponseData { private set; get; }


    protected override async Task<Task> ExecuteAsync(CancellationToken stoppingToken)
    {
        while (true)
        {
            await Task.Delay(5000, stoppingToken);
            Task task = TakeReading();
            if(await Task.WhenAny(task,Task.Delay(3000,stoppingToken)) != task)
            {
                Console.WriteLine("Couldn't finish task within the given time");
                
            }
            await TakeReading();
        }
    }



    /// <summary>
    /// Takes a reading from the arduino and saves in the memory
    /// </summary>
    /// <returns>task being done</returns>
    private static async Task<Task> TakeReading()
    {
        try{
            HttpClient client = new HttpClient();

            HttpResponseMessage responseMessage = await client.GetAsync("http://192.168.1.10:80/tank");

            WaterTankResponseData = await responseMessage.Content.ReadFromJsonAsync<WaterTankResponse>();
        }
        catch (Exception exception)
        {
            Console.WriteLine("Failed to obtain information from Sensor");
            Console.WriteLine(exception.Message);
        }
        return Task.FromResult("Done");
    }
}