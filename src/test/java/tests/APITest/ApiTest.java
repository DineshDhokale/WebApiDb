package tests.APITest;
import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import abstractComponents.JsonUtils;

public class ApiTest {

     public static void verifyEncryptedPassword(WebDriver driver) throws Exception {
        String expectedpassword = JsonUtils.getPassword();
        String registrationEndPoint = "https://parabank.parasoft.com/parabank/register.htm"; 
        String actualpassword ="";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(registrationEndPoint);
            CloseableHttpResponse response = httpClient.execute(request);

            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONObject responseObject = new JSONObject(jsonResponse);

            actualpassword = responseObject.getString("password");
            Assert.assertNotEquals(actualpassword, expectedpassword, "Password should be encrypted in the response");
        } catch (IOException e) {
            System.out.println("Password encryption verification failed = " + actualpassword);
            e.printStackTrace();
        }
    }
  
}