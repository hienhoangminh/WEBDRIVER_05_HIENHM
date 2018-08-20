package selenium_api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_11_ImplicitWait_ExplicitWait_FluentWait {

	WebDriver driver;
	WebDriverWait waitTime;
	Wait<WebDriver> fluentWaitTime;

	ChromeOptions options;
	JavascriptExecutor js;
	Calendar now;

	Date date;
	SimpleDateFormat df2;
	String dateText;
	String dateNo;
	// 1 - LiveGuru
	By startBtn = By.xpath("//div[@id='start']/button");
	By txtMessage = By.xpath("//div[@id='finish']/h4");

	// 2 - Demos Telerik
	By calendarPicker = By.xpath("//div[contains(@class,'RadCalendar')]");
	By selectedDate = By.xpath("//span[@class='label']");
	By loader = By.xpath("//div[@class='raDiv']");

	// 3 - StuntCoder
	By countDown = By.xpath("//div[@id='javascript_countdown_time']");
    
	// 4 - ToolsQA
	By changeColorBtn = By.xpath("//button[@id='colorVar']");
    By textMsg = By.xpath("//span[@id='clock']");
    By sectionName = By.xpath("//h2[contains(text(),'Java Script Timing')]");
    
	@BeforeTest
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		waitTime = new WebDriverWait(driver, 60);

		now = Calendar.getInstance();
		date = now.getTime();
		df2 = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		dateText = df2.format(date);
		dateNo = String.valueOf(now.get(Calendar.DATE));
	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void TC_01_HerokuApp_ImplicitWait() throws Exception {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		clickOnElementBy(startBtn);

		// Date picker is visible
		Assert.assertEquals(driver.findElement(txtMessage).getText(), "Hello World!");

	}

	@Test
	public void TC_02_DemosTelerik_ExplicitWait() throws Exception {
		driver.get(
				"http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		System.out.println(waitTime.until(ExpectedConditions.visibilityOfElementLocated(selectedDate)).getText());
        
		// Just for waiting sometime since it happens to quick
		Thread.sleep(3000);

		selectedDate(dateText);

		// Just for waiting sometime since it happens to quick
		Thread.sleep(3000);

		boolean isLoadingCompleted = waitTime.until(ExpectedConditions.invisibilityOfElementLocated(loader));
		if (isLoadingCompleted) {
			System.out.println(waitTime.until(ExpectedConditions.visibilityOfElementLocated(selectedDate)).getText());
			Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(selectedDate)).getText(),
					dateText);

		}
		// Just for waiting sometime since it happens to quick
		Thread.sleep(3000);
	}

	@Test
	public void TC_03_StunCoders_FluentWait() throws Exception {

		int maxTime = 15;
		int pollTime = 1;
		
		driver.get("https://stuntcoders.com/snippets/javascript-countdown/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		waitTime.until(ExpectedConditions.visibilityOfElementLocated(countDown));

		try {
			waitUntilConditionIsTrue(maxTime, pollTime,countDown,"00:00:00");
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found!");
		} catch (TimeoutException te) {
			System.out.println("We can not find the element within " + maxTime + " seconds");
		}

	}
	

	@Test
	public void TC_04_ToolsQA_FluentWait() throws Exception {

		int maxTime = 45;
		int pollTime1 = 5;
		int pollTime2 = 10;

		driver.get("http://toolsqa.wpengine.com/automation-practice-switch-windows/");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		scrollToElement(sectionName);
		
		try {
			waitUntilConditionIsTrue(maxTime, pollTime1,changeColorBtn,"style","red");
			waitUntilConditionIsTrue(maxTime, pollTime2,textMsg,"Buzz Buzz");
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found!");
		} catch (TimeoutException te) {
			System.out.println("We can not find the element within " + maxTime + " seconds");
		}
	}

	public void clickOnElementBy(By elBy) {
		waitTime.until(ExpectedConditions.elementToBeClickable(elBy)).click();
	}

	public void selectedDate(String date) {
		By dateBy = By.xpath("//table[@class='rcMainTable']/tbody/tr/td[@title='" + date + "']/a");
		waitTime.until(ExpectedConditions.elementToBeClickable(dateBy)).click();
	}

	public String getValueOfParentNodeAttribute(By elBy, String att) {
		WebElement parentNode = (WebElement) js.executeScript("return arguments[0].parentNode",
				driver.findElement(elBy));
		String attribute = parentNode.getAttribute(att);
		return attribute;
	}

	public By generateSelectedDate(String val) {
		String txt = "//*[contains(@class,'rcSelected')]//a[text()='" + val + "']";
		return By.xpath(txt);
	}

	public void waitUntilConditionIsTrue(int maxTime, int pollTime, By elBy,String value) {
		new FluentWait<WebDriver>(driver).withTimeout(maxTime, TimeUnit.SECONDS)
				.pollingEvery(pollTime, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						WebElement el = driver
								.findElement(elBy);
						return el.getText().equals(value);
					}
				});
	}

	public void waitUntilConditionIsTrue(int maxTime, int pollTime, By elBy,String attribute, String value) {
		new FluentWait<WebDriver>(driver).withTimeout(maxTime, TimeUnit.SECONDS)
				.pollingEvery(pollTime, TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						WebElement el = driver
								.findElement(elBy);
						String attValue = el.getAttribute(attribute);
						return attValue.contains(value);
					}
				});
	}
	
	public void scrollToElement(By elBy) {
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(elBy));
	}
}
