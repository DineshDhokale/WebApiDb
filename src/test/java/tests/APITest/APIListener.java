package tests.APITest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.Response;
import org.testng.Assert;

import abstractComponents.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Optional;

public class APIListener {

	public static void verifyEncryptedPassword(WebDriver driver) throws Exception {
		String registrationEndPoint = "/parabank/register";
		String expectedpassword = JsonUtils.getPassword();

		DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();

		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

		devTools.addListener(Network.responseReceived(), responseReceived -> {
			Response response = responseReceived.getResponse();
			if (response.getUrl().contains(registrationEndPoint)) {
				String actualPassword = "";
				try {
					String responseBody = devTools.send(Network.getResponseBody(responseReceived.getRequestId()))
							.getBody();
					JSONObject responseObject = new JSONObject(responseBody);

					actualPassword = responseObject.getString("password");
					Assert.assertNotEquals(actualPassword, expectedpassword,
							"Password should be encrypted in the response");
				} catch (Exception e) {
					System.out.println("Password encryption verification failed = " + actualPassword);
					e.printStackTrace();
				}
			}
		});
	}
}
