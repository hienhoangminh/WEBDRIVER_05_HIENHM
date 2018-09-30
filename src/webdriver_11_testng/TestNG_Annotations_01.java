package webdriver_11_testng;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.Random;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterSuite;

public class TestNG_Annotations_01 {

	WebDriver driver;
	Actions action;
	WebDriverWait wait;

    // All the Xpath  
	By myAccountLink = By.xpath("//a[@title='My Account']");
	By loginLink = By.xpath("//a[text()='Login']");
	By sectionNewCustomer = By.xpath("//div[@class='well']/h2[text()='New Customer']");
	By newCustomerRegisterBtn = By.xpath("//a[text()='Continue']");
	By sectionAccount = By.xpath("//h1[text()='Account']");

	By firstName = By.xpath("//input[@id='input-firstname']");
	By lastName = By.xpath("//input[@id='input-lastname']");
	By email = By.xpath("//input[@id='input-email']");
	By telephone = By.xpath("//input[@id='input-telephone']");

	By password = By.xpath("//input[@id='input-password']");
	By confirmPassword = By.xpath("//input[@id='input-confirm']");
	By checkBox = By.xpath("//input[@type='checkbox']");
	By submitBtn = By.xpath("//input[@value='Continue']");

	By confirmMessage = By.xpath("//div[@id='content']/p[1]");

	By privacyLink = By.xpath("//a[text()='Privacy Policy']");
	By sectionPrivacy = By.xpath("//h1");

	// Fixed data
	static final String uMail = "test123@yopmail.com";
	static final String uPass = "123456";
	static final String title = "The Ninja Store";

	// Random data - so it is generated later.
	int randomNum;

	@BeforeSuite
	public void beforeSuite() throws Exception {
		System.out.println("Launch the test suite for site...");
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 15);
		randomNum = generateRandomNumber();

	}

	private int generateRandomNumber() {
		Random rnd = new Random();
		int value = rnd.nextInt(999999) + 1;
		return value;
	}

	@Test(groups = "nwUser")
	public void checkPage() throws Exception{
		goToURL();
		Assert.assertTrue(isCorrectPage(title));
	}

	@Test(groups = "nwUser")
	public void register() {
		goToURL();
		clickOnElement(myAccountLink);
		clickOnElement(loginLink);
		Assert.assertTrue(isControlDisplayed(sectionNewCustomer));
		clickOnElement(newCustomerRegisterBtn);
		Assert.assertTrue(isControlDisplayed(sectionAccount));

		sendKeysToElement(firstName, "Test");
		sendKeysToElement(lastName, "Test");
		sendKeysToElement(email, "test" + randomNum + "@yopmail.com");
		sendKeysToElement(telephone, "841234567890");

		sendKeysToElement(password, "123456");
		sendKeysToElement(confirmPassword, "123456");

		clickOnCheckBox(checkBox);
		clickOnElement(submitBtn);

		Assert.assertTrue(isControlDisplayed(confirmMessage));

	}

	@Test(groups = "unauthenticated")
	public void consultPrivacyPolicy() {
		goToURL();
		clickOnElement(privacyLink);
		Assert.assertTrue(isControlDisplayed(sectionPrivacy));

	}

	public void goToURL() {
		driver.get("http://tutorialsninja.com/demo/index.php");
	}

	public void selectValueFromDropDownList(By locator, String value) {
		Select select = new Select(driver.findElement(locator));
		select.selectByVisibleText(value);
	}

	public boolean isCorrectPage(String title) {
		String pageTitle = driver.getTitle();
		boolean isCorrect = pageTitle.equals(title);
		return isCorrect;
	}

	public void clickOnElement(By locator) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public void sendKeysToElement(By locator, String value) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public void clickOnCheckBox(By locator) {
		try {
			WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
			el.click();
			if (!el.isSelected()) {
				el.click();
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	public boolean isControlDisplayed(By elBy) {
		try {
			WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			return el.isDisplayed();
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

	public void selectCustomDropdownList(By dropdown, By listItems, String valueItem) {
		// Click on element
		WebElement dropdownEl = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));

		WebDriverWait wait = new WebDriverWait(driver, 30);
		dropdownEl.click();

		List<WebElement> allItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listItems));
		wait.until(ExpectedConditions.visibilityOfAllElements(allItems));
		// Loop through the list of retrieved element
		for (WebElement item : allItems) {
			if (item.getText().trim().equalsIgnoreCase(valueItem)) {
				item.isDisplayed();
				item.click();
				break;
			}
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("Finished running test suite...");
	}

}
