using System;
using System.Collections.Generic;
using System.Linq;

namespace Hoensefoedder_og_guleroedder.Logic;

public abstract class CommonLogic
{
    
    /// <summary>
    /// Removes potential extremes and finds the average value from the given values by standard it takes the 80% of the middle of the data
    /// </summary>
    /// <param name="values">The values</param>
    /// <param name="percentageToSort">The values to ignore from the top and bottom. default 20% or 0.2</param>
    /// <returns>The average of the values that was received</returns>
    public float FindAverageFromList(List<float> values,float percentageToSort = 0.2F)
    {
        values.Sort();

        //Get the elements to remove (80%)
        int removeamount = (int)(values.Count * percentageToSort);
        
        //If the result is uneven
        if (removeamount % 2 == 1)
        {
            values.RemoveAt(0);
            removeamount--;
        }

        for (int i = 0; i < removeamount; i += 2)
        {
            values.RemoveAt(0);
            values.RemoveAt(values.Count - 1);
        }

        return Single.Round(values.Average(),2);
    }
}