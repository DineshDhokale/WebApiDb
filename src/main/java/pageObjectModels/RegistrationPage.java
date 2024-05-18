package pageObjectModels;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponents.AbstractComponents;

public class RegistrationPage extends AbstractComponents{
    WebDriver driver;

    @FindBy(id = "customer.firstName")
    private WebElement firstNameField;

    @FindBy(id = "customer.lastName")
    private WebElement lastNameField;

    @FindBy(id = "customer.address.street")
    private WebElement streetField;

    @FindBy(id = "customer.address.city")
    private WebElement cityField;

    @FindBy(id = "customer.address.state")
    private WebElement stateField;

    @FindBy(id = "customer.address.zipCode")
    private WebElement zipCodeField;

    @FindBy(id = "customer.phoneNumber")
    private WebElement phoneNumberField;

    @FindBy(id = "customer.ssn")
    private WebElement ssnField;

    @FindBy(id = "customer.username")
    private WebElement usernameField;

    @FindBy(id = "customer.password")
    private WebElement passwordField;

    @FindBy(id = "repeatedPassword")
    private WebElement repeatedPasswordField;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerButton;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void register(String username, String password) {
        firstNameField.sendKeys("Test");
        lastNameField.sendKeys("User");
        streetField.sendKeys("123 Test St");
        cityField.sendKeys("Test City");
        stateField.sendKeys("Test State");
        zipCodeField.sendKeys("12345");
        phoneNumberField.sendKeys("1234567890");
        ssnField.sendKeys("123-45-6789");
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        repeatedPasswordField.sendKeys(password);

        registerButton.click();
    }
}