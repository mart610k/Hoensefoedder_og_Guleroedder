using System.Collections.Generic;
using Hoensefoedder_og_guleroedder.Logic;
using NUnit.Framework;


namespace Hoensefoedder_og_guleroedder_test;

public class TestCommonClass : CommonLogic
{
    public TestCommonClass()
    {
        
    }
}

public class CommonLogicTests
{
    private TestCommonClass _testCommonClass = new TestCommonClass();

    [TestCase(new float[] {0,0,0,0,0,0,0,0,0,0}, 0, TestName = "Test all Zeroes")]
    [TestCase(new float[] {500,-270,1,1,1,1,1,1,1,1},1,TestName = "Two Extreme Values")]
    [TestCase(new float[] {4,-2,2.4f,2.4f,1.4f,1.2f,2.2f,1.5f,2.0f,1.0f},1.76f,TestName = "General values")]
    
    [TestCase(new float[] {4f, -2f, 2.4f, 2.4f, 1.4f, 1.2f, 2.2f, 1.5f, 2.0f, 1.0f, 3.2f, 3.25f, 23.1f, 1.20f, 2.31f, 3.0f, 13.2f, 2.4f, 3.4f, 5.1f},2.56f,TestName = "20 values test")]
    
    public void GetAverageFromList(float[] temperatures, float expected)
    {
        //SETUP + ACT
        List<float> list = new List<float>(temperatures);
        float actual = _testCommonClass.FindAverageFromList(list);


        //VERIFY
        Assert.AreEqual(expected,actual);
    }
}