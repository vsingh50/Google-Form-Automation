package demo;

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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException{
        System.out.println("Start Google Form Automation");
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//input[@type='text'])[1]")));
        WebElement nameInput = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
        nameInput.sendKeys("Crio Learner");
        System.out.println("Successfully Added Name in the form");
        long epoch = System.currentTimeMillis()/1000;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Thread.sleep(2000);
        WebElement practicingInput = driver.findElement(By.tagName("textarea"));
        js.executeScript("arguments[0].scrollIntoView();", practicingInput);
        practicingInput.sendKeys("I want to be the best QA Engineer! "+ String.valueOf(epoch));
        System.out.println("Successfully Added Participating answer");
        WebElement experience = driver.findElement(By.xpath("//div[@class='SG0AAe']/div[2]/label/div/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", experience);
        experience.click();
        List<WebElement> checkBox = driver.findElements(By.xpath("(//div[@role='list'])[2]/div/label/div/div[1]"));
        List<WebElement> checkBoxText = driver.findElements(By.xpath("(//div[@role='list'])[2]/div/label/div/div[2]"));
        // js.executeScript("arguments[0].scrollIntoView();", checkBox);
        // js.executeScript("arguments[0].scrollIntoView();", checkBoxText);
        for(int i = 0 ; i < checkBox.size() ; i++){
            js.executeScript("arguments[0].scrollIntoView();", checkBox.get(i));
            js.executeScript("arguments[0].scrollIntoView();", checkBoxText.get(i));
            if(checkBoxText.get(i).getText().equals("Java") || 
                checkBoxText.get(i).getText().equals("Selenium") ||
                checkBoxText.get(i).getText().equals("TestNG")){
                checkBox.get(i).click();
            }
        }
        System.out.println("Successfully Selected Checkboxes");
        WebElement addressList = driver.findElement(By.xpath("(//div[@role='listbox'])[1]/div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", addressList);
        addressList.click();
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='option']/span[text()='Mr']")));
        WebElement mr = driver.findElement(By.xpath("//div[@role='option']/span[text()='Mr']"));
        js.executeScript("arguments[0].scrollIntoView();", mr);
        mr.click();
        System.out.println("Successfully selected Mr");
        LocalDate todaysDate = LocalDate.now();
        LocalDate sevenDaysAgo = todaysDate.minusDays(7);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateInFormat = sevenDaysAgo.format(dateFormatter);
        WebElement datePicker = driver.findElement(By.xpath("//input[@type='date']"));
        js.executeScript("arguments[0].scrollIntoView();", datePicker);
        datePicker.sendKeys(dateInFormat);
        System.out.println("Successfully Selected Date");
        WebElement hour = driver.findElement(By.xpath("(//input[@type='text'])[2]"));
        //js.executeScript("arguments[0].scrollIntoView();", hour);
        hour.sendKeys("7");
        WebElement minutes = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        js.executeScript("arguments[0].scrollIntoView();", minutes);
        minutes.sendKeys("30");
        System.out.println("Successfully Added Time");
        WebElement submitButton = driver.findElement(By.xpath("(//div[@role='button'])[1]"));
        js.executeScript("arguments[0].scrollIntoView();", submitButton);
        submitButton.click();
        System.out.println("Successfully Clicked on Submit Button");
        Thread.sleep(3000);
        WebElement thankYou = driver.findElement(By.xpath("//div[text()='Thanks for your response, Automation Wizard!']"));
        String thankYouMsg = thankYou.getText();
        Assert.assertEquals(thankYouMsg, "Thanks for your response, Automation Wizard!");
        System.out.println("Successfully Submitted Google Form");

    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
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
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");


        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        // driver.close();
        // driver.quit();

    }
}