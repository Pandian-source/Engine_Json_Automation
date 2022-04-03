package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestListeners implements ITestListener
{
    public Response res;
    static final Logger log = LogManager.getLogger(TestListeners.class);
    ExtentReports extent = ExtentManager.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public void onTestStart(ITestResult result)

    {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
        extentTest.set(test);
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Started<b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.INFO, m);
        log.info(logText);
    }
    public void onTestSuccess(ITestResult result)
    {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Success<b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        extentTest.get().log(Status.PASS, m);
        log.info(logText);


        try {
             res = (Response) result.getTestClass().getRealClass().getDeclaredField("response")
                    .get(result.getInstance());
            String fileName=System.getProperty("user.dir") + "/REPORTS/"+result.getName()+"_response.json";

           // Headervalue.Response(fileName, res.asPrettyString());

            extentTest.get().log(Status.PASS, "Test Case Method <b style=\"color:Darkblue;\">"+result.getName()+" </b> Executed successfully and working as expected");

          //  extentTest.get().log(Status.INFO,  System.lineSeparator() +" <a href=\""+fileName+"\">API response in Json</a>");

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();

        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();

        }
    }

    public void onTestFailure(ITestResult result)
    {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Failed<b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.BLUE);
        extentTest.get().log(Status.FAIL, m);
        log.info(logText);
    }


    public void onTestSkipped(ITestResult result)
    {
        String logText = "<b>Test Method " + result.getMethod().getMethodName() + "Skipped<b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        extentTest.get().log(Status.SKIP, m);
        log.info(logText);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

    public void onStart(ITestContext context)
    {

    }

    public void onFinish(ITestContext context)
    {
        if (extent!=null)
        {
            extent.flush();
        }
    }}
