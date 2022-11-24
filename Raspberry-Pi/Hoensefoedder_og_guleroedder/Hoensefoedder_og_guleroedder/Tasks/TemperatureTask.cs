using System;
using System.Collections.Generic;
using System.Threading;
using Microsoft.Extensions.Hosting;
using System.Threading.Tasks;

namespace Hoensefoedder_og_guleroedder.Tasks;

public class TemperatureTask : BackgroundService
{
    public Queue<float> Readings = new Queue<float>();



    protected override async Task<Task> ExecuteAsync(CancellationToken stoppingToken)
    {
        while (true)
        {
            await Task.Delay(3000, stoppingToken);
            await TakeReading();
        }
    }



    private static async Task<Task> TakeReading()
    {
        //Logic to Take readings
        Console.WriteLine("Take Reading");



        return Task.FromResult("Done");
    }
}