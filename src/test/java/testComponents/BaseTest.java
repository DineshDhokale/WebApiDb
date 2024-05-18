package testComponents;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import abstractComponents.AbstractComponents;
import abstractComponents.JsonUtils;
import pageObjectModels.LoginPage;
import pageObjectModels.RegistrationPage;
import org.apache.commons.io.FileUtils;

import tests.APITest.APIListener;
import tests.APITest.ApiTest;
import tests.APITest.DBTest;

public class BaseTest {
    public WebDriver driver;
    private String username;
    private String password;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private AbstractComponents abstractComponents;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        abstractComponents=new AbstractComponents(driver);
    }

    @Test
    public void testRegistration() throws Exception {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");

        driver.manage().window().maximize();
        username = abstractComponents.generateRandomUsername(6);
        password = JsonUtils.getPassword();
        registrationPage.register(username, password);
    }
    
    @Test(dependsOnMethods = { "testRegistration" }, priority = 1)
    public void testAPIResponse() throws Exception {
    	APIListener.verifyEncryptedPassword(driver);
    }
    
    @Test(dependsOnMethods = { "testAPIResponse" }, priority = 2)
    public void testDB() throws Exception {
    	DBTest.verifyEncryptedPasswordInDB(driver);
    }
    
    @Test(dependsOnMethods = { "testRegistration" }, priority = 3)
    public void testLogin() {
    	loginPage.clickLogout();
        loginPage.login(username, password);
    }

    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}