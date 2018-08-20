package selenium_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_09_JavascriptExecutor {

	WebDriver driver;
	WebDriverWait waitTime;
	JavascriptExecutor js;
	
	// 1 - LiveGuru
	String openUrl = "http://live.guru99.com/";
	String navigateUrl = "http://demo.guru99.com/v4/";
	// 2 - TryIt Editor
	String openUrl2 = "https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled";
	String lastName = "Automation Testing";

        // JS Scripts
	final String clickOnElement = "arguments[0].click();";
	final String getDomainScript = "return document.domain;";
	final String getUrlScript = "return document.URL;";
	final String getPageInnerText = "return document.documentElement.innerText;";
	final String getPageTitle = "return document.title;";
	final String scrollToBottom = "window.scrollBy(0,document.body.scrollHeight || document.documentElement.scrollHeight);";
	final String verifyIfElementExists = "return document.body.contains(arguments[0]);";
	String navigateUrlScript = "window.location ='%s'";

	final String getPageState = "return document.readyState;";
        final String removeDisableScript = "arguments[0].disabled=false;";
	String inputValueToField = "arguments[0].value='"+lastName+"'";
	String phoneName = "Samsung Galaxy";
	String message = phoneName + " was added to your shopping cart.";

	By mobileLink = By.xpath("//a[text()='Mobile']");
	By addToCart = By.xpath("//a[text()='" + phoneName + "']/../following-sibling::div[@class='actions']/button");

	By privacyLink = By.xpath("//div[@class='footer']//a[text()='Privacy Policy']");
	By privacyText = By.xpath("//table[@class='data-table']/tbody//th[text()='WISHLIST']/..//following-sibling::tr");

	String domain, url;

	// 2 - TryIt Editor
	By loginFrame = By.xpath("//iframe[@id='iframeResult']");
	By lname = By.xpath("//input[@name='lname']");
	By btnSubmit = By.xpath("//input[@value='Submit']");

	By breakWord = By.xpath("//p[contains(text(),'processed your input')]/preceding-sibling::div");

	@BeforeTest
	public void setUp() throws Exception {
//		System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
//		driver = new InternetExplorerDriver();
		
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
     	driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		waitTime = new WebDriverWait(driver, 60);
	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void TC_01_LiveGuruJS() throws Exception {
		
		navigateToUrl(navigateUrlScript, openUrl);
		driver.get("http://live.guru99.com/");
		// Step 2: Get page domain
		domain = (String) executeScriptJS(getDomainScript);
		Assert.assertTrue(domain.equals("live.guru99.com"));

		// Step 3: Get page Url
		url = (String) executeScriptJS(getUrlScript);
		Assert.assertTrue(url.equals("http://live.guru99.com/"));

		// Step 4 : Open Mobile page
		executeScriptJS(clickOnElement, mobileLink);
		Thread.sleep(7000);

		// Step 5: Add Samsung Galaxy to the cart
		executeScriptJS(clickOnElement, addToCart);
		Thread.sleep(7000);

		// Step 6: Verify message
		String pageInnerText = (String) executeScriptJS(getPageInnerText);
		Assert.assertTrue(pageInnerText.contains(message));

		// Step 7 : Open Privacy Policy page
		executeScriptJS(clickOnElement, privacyLink);
		Thread.sleep(7000);

		String title = (String) executeScriptJS(getPageTitle);
		Assert.assertTrue(title.equals("Privacy Policy"));

		scrollToBottom();
		Thread.sleep(7000);

		boolean isElementExisted = (boolean) executeScriptJS(verifyIfElementExists, privacyText);
		Assert.assertTrue(isElementExisted);
		System.out.println("First checkpoint is : " + isElementExisted);

		navigateToUrl(navigateUrlScript, navigateUrl);
		Thread.sleep(7000);

		String pageState = (String) executeScriptJS(getPageState);

		if (pageState.equals("complete")) {
			url = (String) executeScriptJS(getUrlScript);
			System.out.println("Second checkpoint is : " + url.equals("http://demo.guru99.com/v4/"));
			Assert.assertTrue(url.equals("http://demo.guru99.com/v4/"));

		}
	}

	@Test(alwaysRun=true)
	public void TC_02_TryitEditor() throws Exception {
		
		navigateToUrl(navigateUrlScript, openUrl2);

		switchToIframe(loginFrame);
		Thread.sleep(7000);

		// Remove value of disabled attribute
		executeScriptJS(removeDisableScript, lname);
		Thread.sleep(7000);
		
		// Set the value for input field
                executeScriptJS(inputValueToField, lname);
		Thread.sleep(7000);

		// Click on button Submit
     	        executeScriptJS(clickOnElement, btnSubmit);
 		Thread.sleep(7000);
 		
 		System.out.println("First checkpoint is : " + driver.findElement(breakWord).getText().contains(lastName));

    	    Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfElementLocated(breakWord)).getText().contains(lastName));

	}

	public Object executeScriptJS(String jsScript) {
		return js.executeScript(jsScript);
	}
	
	public void navigateToUrl(String jsScript,String value) {
		String jsScriptWithValue = String.format(jsScript, value);
		js.executeScript(jsScriptWithValue);
	}

	public Object executeScriptJS(String jsScript, By elBy) {
		return js.executeScript(jsScript, waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy)));
	}


	public void scrollToBottom() {
		js.executeScript(scrollToBottom);

	}
	
	public Object setDisableProperty(String jsScript,By elBy) {
		try {
			return executeScriptJS(jsScript, elBy);
		} catch (Exception e) {
			return null;
		}
	}

	public void switchToIframe(String name) {
		driver.switchTo().frame(name);
	}

	public void switchToIframe(int num) {
		driver.switchTo().frame(num);
	}

	public void switchToIframe(By elBy) {
		driver.switchTo().frame(driver.findElement(elBy));
	}

}
