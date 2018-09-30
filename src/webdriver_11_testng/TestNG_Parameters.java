package webdriver_11_testng;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestNG_Parameters {

	WebDriver driver;
    Actions action;
    WebDriverWait waitTime;
    
	By depatureAirport = By.xpath("//select[@name='fromPort']");
	By arrivalAirport = By.xpath("//select[@name='toPort']");
	By searchFlight = By.xpath("//input[@value='Find Flights']");
	By searchTitle = By.xpath("//h3");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		action = new Actions(driver);
		waitTime = new WebDriverWait(driver, 15);
	}

	@Test
	@Parameters({ "fromCity", "toCity" })
	public void searchFlight(String fromCity, String toCity) throws Exception {
		driver.get("http://blazedemo.com/");

		selectOptionByValue(depatureAirport, fromCity);

		// Wait sometime to select the dropdown list
		Thread.sleep(2000);

		selectOptionByValue(arrivalAirport, toCity);
		// Wait sometime to select the dropdown list
		Thread.sleep(2000);

		clickOnWebElement(searchFlight);

		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(searchTitle)).getText(),
				String.format("Flights from %s to %s:", fromCity, toCity));
	}

	public void clickOnWebElement(By locator) {
		try {
			waitTime.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (Exception e) {
			e.getMessage();
		}		
	}

	@AfterClass
	public void afterClass() {
	}

	public void selectOptionByValue(By selectBy, String value) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.visibilityOfElementLocated(selectBy));
			Select select = new Select(el);
			select.selectByVisibleText(value);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	

}
