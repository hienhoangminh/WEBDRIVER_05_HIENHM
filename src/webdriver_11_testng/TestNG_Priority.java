package webdriver_11_testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestNG_Priority {
	
	WebDriver driver;
	Actions action;
	WebDriverWait wait;
	
    By btnSearchFlight = By.xpath("//input[@value='Find Flights']");
    By fromCity = By.xpath("//select[@name='fromPort']");
    By toCity = By.xpath("//select[@name='toPort']");
    
    String departureCity = "Paris";
    String arrivalCity = "Berlin";
    
    By selectFlight = By.xpath("(//input[@value='Choose This Flight'])[1]");

	@BeforeClass
	public void setUp() throws Exception{
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 15);
		
	}
	
	@BeforeTest
	public void logging() throws Exception{
	   System.out.println("Start running the test case...");
		
	}
	
	@Test(priority = 0)
	public void goToMainPage() {
		goToURL();
        Assert.assertTrue(isCorrectPage());
	}

	@Test(priority = 1)
	public void searchFlight() {
		goToURL();
		selectValueFromDropDownList(fromCity, "Paris");
		selectValueFromDropDownList(toCity, "Berlin");
		clickOnElement(btnSearchFlight);
	}
	
	@Test(dependsOnMethods="searchFlight",priority=1)
	public void chooseFlight() {
		clickOnElement(selectFlight);
		Assert.assertEquals(driver.findElement(By.xpath("//h2")).getText(),String.format("Your flight from %s to %s has been reserved.", departureCity,arrivalCity));
	}
	
	public void goToURL() {
		driver.get("http://blazedemo.com/index.php");
	}
	
	public void selectValueFromDropDownList(By locator,String value) {
	    Select select = new Select(driver.findElement(locator));
	    select.selectByVisibleText(value);
	}
	
	public boolean isCorrectPage() {
	    String pageTitle = driver.getTitle();
	    String pageUrl = driver.getCurrentUrl();
        boolean isCorrect = pageTitle.equals("BlazeDemo") && pageUrl.equals("http://blazedemo.com/index.php");
	    return isCorrect;	    
	}
	
	public void clickOnElement(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}catch (Exception e) {
			e.getMessage();
		}
		
	}

}
