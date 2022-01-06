package grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Level_00_Register_Login_Step_By_Step {
    String projectFolder = System.getProperty("user.dir");
    WebDriver driver;
    Select select;
    String firstName, lastName, email, companyName, password;

    @Parameters({"browser", "ipAddress", "port"})
    @BeforeClass
    public void beforeClass(String browserName, String ipAddress, String port) {
        DesiredCapabilities capability = null;
        switch (browserName){
            case "firefox":
                System.setProperty("webdriver.gecko.driver","./browserDrivers/geckodriver.exe");
                capability = DesiredCapabilities.firefox();
                capability.setBrowserName(browserName);
                capability.setPlatform(Platform.WINDOWS);

//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                firefoxOptions.merge(capability);
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver","./browserDrivers/chromedriver.exe");
                capability = DesiredCapabilities.chrome();
                capability.setBrowserName(browserName);
                capability.setPlatform(Platform.WINDOWS);

//                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.merge(capability);
                break;
            default:
                throw new RuntimeException("Invalid browser name!");
        }
        try {
            driver = new RemoteWebDriver(new URL(String.format("http://%s:%s/wd/hub", ipAddress, port)), capability);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");

        firstName = "Au";
        lastName = "To";
        email = "auto" + getRandomNumber() + "@hotmail.net";
        companyName = "AutoCom";
        password = "abc123456";
    }

    @Test
    public void TC_01_Register() {
        driver.findElement(By.className("ico-register")).click();
        driver.findElement(By.id("gender-male")).click();
        sleepInSecond(1);

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);

        select = new Select(driver.findElement(By.name("DateOfBirthDay")));
        select.selectByVisibleText("10");

        select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        select.selectByVisibleText("August");

        select = new Select(driver.findElement(By.name("DateOfBirthYear")));
        select.selectByVisibleText("1960");

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Company")).sendKeys(companyName);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        driver.findElement(By.id("register-button")).click();
        sleepInSecond(1);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='page registration-result-page']//div[@class='result']")).getText(), "Your registration completed");

        driver.findElement(By.className("ico-logout")).click();
        sleepInSecond(1);
    }

    @Test
    public void TC_02_Login() {
        driver.findElement(By.className("ico-login")).click();
        sleepInSecond(1);

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector(".login-button")).click();
        sleepInSecond(1);

        Assert.assertTrue(driver.findElement(By.className("ico-account")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className("ico-logout")).isDisplayed());
    }

    public int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(999999);
    }

    public void sleepInSecond(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
