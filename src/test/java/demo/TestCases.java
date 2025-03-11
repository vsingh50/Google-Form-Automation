package demo;

import demo.wrappers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Start Google Form Automation");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        //Loading Google Form URL
        Wrappers.navigate(driver, "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        
        //Input Name 
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text']")));
        WebElement nameInput = driver.findElement(By.xpath("//input[@type='text']"));
        Wrappers.sendKeys(driver, nameInput, "Crio Learner");  
        System.out.println("Successfully Added Name in the form");
        
        //Input into TextArea
        Thread.sleep(2000);
        WebElement practicingInput = driver.findElement(By.tagName("textarea"));
        String textToInput = Wrappers.getPracticeString("I want to be the best QA Engineer! ");
        Wrappers.sendKeys(driver, practicingInput, textToInput);
        System.out.println("Successfully Added Participating answer");

        //Selecting Experience using Radio Buttons
        List<WebElement> experience = driver.findElements(By.xpath("//span[contains(@class,'OvPDhc ')]"));
        Wrappers.selectExperience(driver, experience, "3 - 5");
       
        //Selecting skills using checkBoxes
        List<WebElement> checkBoxText = driver.findElements(By.xpath("//span[contains(@class,'n5vBHf ')]"));
        Wrappers.selectSkills(driver, checkBoxText, "Java");
        Wrappers.selectSkills(driver, checkBoxText, "TestNG");
        Wrappers.selectSkills(driver, checkBoxText, "Selenium");
        System.out.println("Successfully Selected Checkboxes");

        //Selecting honorific word
        WebElement addressList = driver.findElement(By.xpath("//div[@role='listbox']//div[contains(@class,'KKjvXb ')]"));
        Wrappers.click(addressList, driver);
        Thread.sleep(3000);
        List<WebElement> personToBeAddressed = driver.findElements(By.xpath("//div[@jsname='V68bde']//div[contains(@class,'OIC90c ')]/span"));
        Wrappers.selectAddresse(driver, personToBeAddressed, "Mr");

       
        //Selecting date
        WebElement datePicker = driver.findElement(By.xpath("//input[@type='date']"));
        String dateToSend = Wrappers.getDaysAgo(7);
        Wrappers.sendKeys(driver, datePicker, dateToSend);
        System.out.println("Successfully Selected Date");

        //Providing Time
        WebElement hour = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
        Wrappers.sendKeys(driver, hour, "7");
        WebElement minutes = driver.findElement(By.xpath("//input[@aria-label='Minute']"));
        Wrappers.sendKeys(driver, minutes, "30"); 
        System.out.println("Successfully Added Time");

        //Submit Form
        WebElement submitButton = driver.findElement(By.xpath("//div[@role='button']//span[text()='Submit']"));
        Wrappers.click(submitButton, driver);
        System.out.println("Successfully Clicked on Submit Button");
        
        //Validating Thank You message
        Thread.sleep(3000);
        WebElement thankYou = driver.findElement(By.className("vHW8K"));
        String thankYouMsg = thankYou.getText();
        if(thankYouMsg.contains("Thanks for your response")){
            System.out.println("Successfully Submitted Google Form: PASS");
        }
        else{
            System.out.println("Successfully Submitted Google Form: FAIL");
        }
        
    }

     
    
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        // Connect to the chrome-window running on debugging port
        //options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();
    }
}