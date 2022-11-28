using System;
using System.Collections.Generic;
using System.IO;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json.Nodes;
using System.Threading;
using Microsoft.Extensions.Hosting;
using System.Threading.Tasks;
using Hoensefoedder_og_guleroedder.DTO;
using Hoensefoedder_og_guleroedder.Enum;

namespace Hoensefoedder_og_guleroedder.Tasks;

public class TemperatureTask : BackgroundService
{
    public static Queue<float> Inside = new Queue<float>();

    public static Queue<float> Outside = new Queue<float>();

    
    
    

    protected override async Task<Task> ExecuteAsync(CancellationToken stoppingToken)
    {
        while (true)
        {
            await Task.Delay(3000, stoppingToken);
            await TakeReading();
        }
    }



    /// <summary>
    /// Takes a reading from the arduino and saves in the memory
    /// </summary>
    /// <returns>task being done</returns>
    private static async Task<Task> TakeReading()
    {
        HttpClient client = new HttpClient();


        HttpResponseMessage responseMessage = await client.GetAsync("http://192.168.1.10:80/temperature");

        Console.WriteLine(await responseMessage.Content.ReadAsStringAsync());

        List<SensorResponse> responses = await responseMessage.Content.ReadFromJsonAsync<List<SensorResponse>>();

        foreach (SensorResponse response in responses)
        {
            
        
            switch (response.Location)
            {
                case LocationType.OUTSIDE:
                    if (Outside.Count >= 10)
                    {
                        Outside.Dequeue();
                    }
                    Outside.Enqueue(response.Value);

                    break;
                case LocationType.INSIDE:
                    if (Inside.Count >= 10)
                    {
                        Inside.Dequeue();
                    }
                    Inside.Enqueue(response.Value);

                    break;
            }
        }
        
        
        
        return Task.FromResult("Done");
    }
}