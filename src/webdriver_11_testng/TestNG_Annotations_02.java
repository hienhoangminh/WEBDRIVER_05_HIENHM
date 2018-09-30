package webdriver_11_testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestNG_Annotations_02 {
	
	WebDriver driver;
	WebDriverWait wait;
	Actions action;

	By myAccountLink = By.xpath("//a[@title='My Account']");
	
	By registerLink = By.xpath("//a[text()='Register']");
    By loginLink = By.xpath("//a[text()='Login']");
    
    By sectionReturningCust = By.xpath("//div[@class='well']/h2[text()='Returning Customer']");
    By userEmailField = By.xpath("//input[@id='input-email']");
    By userPasswordField = By.xpath("//input[@id='input-password']");
    By btnLogin = By.xpath("//input[@value='Login']");

    By sectionMyAccount = By.xpath("//h2");
    
    String uMail = "test123@yopmail.com";
    String uPass = "123456";

    By logOut = By.xpath("//a[text()='Logout']");
    
    //
    By dropdownMenu = By.xpath("//button[@data-toggle='dropdown']");
    By lstOption = By.xpath("//ul[@class='dropdown-menu']/li/button");
    String option = "£ Pound Sterling";
  
    By homeUrl = By.xpath("//a[text()='The Ninja Store']");
    By priceMacBook = By.xpath("//a[text()='MacBook']/../../p[@class='price']");
    
	@BeforeClass
	public void setUp() throws Exception{
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		action = new Actions(driver);
		wait = new WebDriverWait(driver, 15);
		
	}
	
	@Test(groups="rgtUser")
	public void loginByLoginLink() {
		goToURL();
		clickOnElement(myAccountLink);
		clickOnElement(loginLink);
		Assert.assertTrue(isControlDisplayed(sectionReturningCust));
		sendKeysToElement(userEmailField, uMail);
		sendKeysToElement(userPasswordField, uPass);
		clickOnElement(btnLogin);
		
		Assert.assertTrue(isControlDisplayed(sectionMyAccount));
	
	
	}
	

	@Test(groups="rgtUser")
	public void changeCurrency() {
		goToURL();

		selectCustomDropdownList(dropdownMenu, lstOption, option);
		clickOnElement(homeUrl);
		Assert.assertTrue(driver.findElement(priceMacBook).getText().contains("£"));

		
	}
	
	public void goToURL() {
		driver.get("http://tutorialsninja.com/demo/index.php");
	}
	
	public void selectValueFromDropDownList(By locator,String value) {
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
		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public void sendKeysToElement(By locator,String value) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public void clickOnCheckBox(By locator){
		try {
			WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
			el.click();
			if(!el.isSelected()) {
				el.click();
			}
		}catch (Exception e) {
			e.getMessage();
		}
		
	}
	
	public boolean isControlDisplayed(By elBy) {
		try {
			WebElement el = 	wait.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			return el.isDisplayed();
		}catch(Exception e) {
			e.getMessage();
			return false;
		}
	}
	
	public void selectCustomDropdownList(By dropdown, By listItems, String valueItem) {
		// Click on element
		WebElement dropdownEl = 	wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));

		WebDriverWait wait = new WebDriverWait(driver, 30);
		//((JavascriptExecutor) driver).executeScript("arugments[0].scrollIntoView(true);", dropdownEl);
		dropdownEl.click();

		List<WebElement> allItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listItems));
		wait.until(ExpectedConditions.visibilityOfAllElements(allItems));
		// Loop through the list of retrieved element
		for (WebElement item : allItems) {
			if (item.getText().trim().equalsIgnoreCase(valueItem)) {
				//((JavascriptExecutor) driver).executeScript("arugments[0].scrollIntoView(true);", item);
				item.isDisplayed();
				item.click();
				break;
			}
		}
	}
	

}
