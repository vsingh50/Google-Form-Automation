package demo.wrappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;

public class Wrappers {
    
    public static void click(WebElement elementToClick, WebDriver driver){
        if(elementToClick.isDisplayed()){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", elementToClick);
            elementToClick.click();
        }
    }

    public static void sendKeys(WebDriver driver, WebElement inputBox, String keysToSend){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", inputBox);
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
    }

    public static void navigate(WebDriver driver , String url){
        if(!driver.getCurrentUrl().equals(url)){
            driver.get(url);
        }
    }

    public static String getPracticeString(String textInput){
        long epoch = System.currentTimeMillis()/1000;
        return textInput + String.valueOf(epoch);
    }

    public static WebElement findElementWithRetry(WebDriver driver , By by , int retryCount ){
        return driver.findElement(by);
    }

    public static String getDaysAgo(int days){
        LocalDate todaysDate = LocalDate.now();
        LocalDate sevenDaysAgo = todaysDate.minusDays(days);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateInFormat = sevenDaysAgo.format(dateFormatter);
        return dateInFormat;
    }

    public static void selectExperience(WebDriver driver, List<WebElement> experienceRadioButtons, String experience){
        try{
            for(WebElement element : experienceRadioButtons ){
                if(element.getText().equals(experience)){
                    element.click();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void selectSkills(WebDriver driver, List<WebElement> skills, String skill){
        try{
            for(WebElement element : skills){
                if(element.getText().equals(skill)){
                    element.click();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        } 
    }

    public static void selectAddresse(WebDriver driver, List<WebElement> addresseeList, String address){
        try{
            for(WebElement element : addresseeList){
                if(element.getText().equals(address)){
                    element.click();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String capture(WebDriver driver) throws IOException{
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir")+File.separator+"reports"+System.currentTimeMillis()+".png");
        String errflPath = dest.getAbsolutePath();
        FileUtils.copyFile(srcFile, dest);
        return errflPath;
    }
}
