package webdriver_11_testng;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestNG_DataProviders {

	WebDriver driver;
	WebDriverWait waitTime;

	By depatureAirport = By.xpath("//select[@name='fromPort']");
	By arrivalAirport = By.xpath("//select[@name='toPort']");
	By searchFlight = By.xpath("//input[@value='Find Flights']");

	By searchTitle = By.xpath("//h3");

	By listFlight = By.xpath("//input[@value='Choose This Flight']");

	By purchaseTitle = By.xpath("//h2");

	By name = By.id("inputName");
	By address = By.id("address");
	By city = By.id("city");
	By state = By.id("state");
	By zipCode = By.id("zipCode");
	By cartType = By.xpath("//select[@id='cardType']");
	By cardNumber = By.id("creditCardNumber");
	By cardMonth = By.id("creditCardMonth");
	By cardYear = By.id("creditCardYear");
	By ownerName = By.id("nameOnCard");

	By purchaseBtn = By.xpath("//input[@value='Purchase Flight']");

	By confirmationTitle = By.xpath("//h1");
	By expiryDate = By.xpath("//td[text()='Expiration']/following-sibling::td");

	Faker faker;

	String fullName;
	String cardExpiry;
	String month;
	String year;
	String expectedExpiration;
	String creditCardNumber;

	String postalCode;

	@DataProvider(name = "Itinerary")
	public static Object[][] itineraries() {
		return new Object[][] { { "Boston", "Berlin", 3, "Visa" }, { "San Diego", "Dublin", 2, "American Express" },
				{ "Portland", "London", 1, "Diner's Club" }, { "Paris", "Rome", 4, "Diner's Club" },
				{ "Mexico City", "Cairo", 3, "Visa" } };
	}

	@BeforeClass
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		waitTime = new WebDriverWait(driver, 60);
	}

	@BeforeTest
	public void generateData() {
		faker = new Faker();
		fullName = faker.name().firstName() + " " + faker.name().lastName();
		cardExpiry = faker.business().creditCardExpiry();
		month = cardExpiry.split("-")[1];
		year = cardExpiry.split("-")[0];
		creditCardNumber = faker.business().creditCardNumber();
		expectedExpiration = month + "/" + year;
		postalCode = generateZipCode();
	}

	@Test(dataProvider = "Itinerary")
	public void searchForFlight(	String fromCity,String toCity,int position,String paymentType) throws Exception {
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

		clickOnElementByPosition(listFlight,position);

		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(purchaseTitle)).getText(),
				String.format("Your flight from %s to %s has been reserved.", fromCity, toCity));

		inputValueToElement(name, fullName);

		inputValueToElement(address, faker.address().streetAddress());

		inputValueToElement(city, faker.address().city());

		inputValueToElement(state, faker.address().state());

		inputValueToElement(zipCode, postalCode);

		selectOptionByValue(cartType, paymentType);

		scrollToElement(ownerName);

		inputValueToElement(cardNumber, creditCardNumber);

		inputValueToElement(cardMonth, month);

		inputValueToElement(cardYear, year);

		inputValueToElement(ownerName, fullName);

		clickOnWebElement(purchaseBtn);

		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(confirmationTitle)).getText(),
				"Thank you for your purchase today!");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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

	public void clickOnWebElement(By elBy) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.elementToBeClickable(elBy));
			el.click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void inputValueToElement(By elBy, String value) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			el.sendKeys(value);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String removeSpaceInString(String text) {
		return text.replaceAll(" ", "");

	}

	public void scrollToElement(By elBy) {
		try {
			WebElement el = driver.findElement(elBy);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", el);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void clickOnElementByPosition(By selectBy, int position) {
		try {
			List<WebElement> elList = waitTime.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selectBy));
			if(position < 0) {
				throw new IllegalArgumentException("Invalid index. Index should be alwyas > 0");
			}
			
			if(position > elList.size()) {
				throw new IndexOutOfBoundsException("Value is too big! You should choose an index between 0 and " + elList.size());
			}
			
			elList.get(position).click();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public String generateZipCode() {
		Random rnd = new Random();
		int rndNumber = rnd.nextInt(9999) + 1;
		return String.valueOf(rndNumber);
	}

}
