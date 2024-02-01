package Hooks;

import loggerUtility.LoggerUtility;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class Hooks {
    public String testName;
    @BeforeMethod
    public void setUp(){
        testName=this.getClass().getSimpleName();
        LoggerUtility.startTestCase(testName);
    }
    @AfterMethod
    public void tearDown(){
        LoggerUtility.endTestCase(testName);
    }
    @AfterSuite
    public void mergeLogs(){
        LoggerUtility.mergeLogsIntoOne();
    }
}
