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

public class DHTTask : BackgroundService
{
    public static Queue<DHTResponse> Inside = new Queue<DHTResponse>();

    public static Queue<DHTResponse> Outside = new Queue<DHTResponse>();
    
    protected override async Task<Task> ExecuteAsync(CancellationToken stoppingToken)
    {
        while (true)
        {
            await Task.Delay(3000, stoppingToken);
            // Take outside temperature
            Task task = TakeReading("http://192.168.1.10:80",LocationType.OUTSIDE);
            if(await Task.WhenAny(task,Task.Delay(3000,stoppingToken)) != task)
            {
                Console.WriteLine("Couldn't finish task within the given time");
                
            }
            // Take inside temperature
            task = TakeReading("http://192.168.1.11:80",LocationType.INSIDE);
            if(await Task.WhenAny(task,Task.Delay(3000,stoppingToken)) != task)
            {
                Console.WriteLine("Couldn't finish task within the given time");
                
            }
        }
    }



    /// <summary>
    /// Takes a reading from the arduino and saves in the memory
    /// </summary>
    /// <returns>task being done</returns>
    private static async Task<Task> TakeReading(string hostname,LocationType location)
    {
        try
        {
            HttpClient client = new HttpClient();

            HttpResponseMessage responseMessage = await client.GetAsync( hostname +"/dht");

            DHTResponse response = await responseMessage.Content.ReadFromJsonAsync<DHTResponse>();

            switch (location)
            {
                case LocationType.OUTSIDE:
                    if (Outside.Count >= 10)
                    {
                        Outside.Dequeue();
                    }
                    Outside.Enqueue(response);

                    break;
                case LocationType.INSIDE:
                    if (Inside.Count >= 10)
                    {
                        Inside.Dequeue();
                    }

                    Inside.Enqueue(response);
                    break;
            }
        }
        catch (Exception exception)
        {
            Console.WriteLine("Failed to obtain information from Sensor");
            Console.WriteLine(exception.Message);
        }

        return Task.FromResult("Done");
    }
}