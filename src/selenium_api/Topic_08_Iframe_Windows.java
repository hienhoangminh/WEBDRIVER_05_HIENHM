package selenium_api;

import org.testng.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_08_Iframe_Windows {

	WebDriver driver;
	WebDriverWait waitTime;
	ChromeOptions options;
	Actions actions;
	JavascriptExecutor js;

	// 1 - HDFC Banks - Iframes only
	By popupFrame = By.xpath("//iframe[@id='vizury-notification-template']");

	By closeButton = By.xpath("//div[@id='div-close']");

	By frameBy1 = By.xpath("//div[@class='flipBannerWrap']//iframe");
	By frameBy2 = By.xpath("//div[@class='slidingbanners']//iframe");

	By text = By.xpath("//span[@id='messageText' and text()='What are you looking for?']");
	By banner = By.xpath("//div[@id='productcontainer']");
	By bannerImg = By.xpath("//div[@id='productcontainer']/div[@class='item-list']");

	By fliperBanner = By.xpath("//div[@class='flipBanner']/div[contains(@class,'product')]");

	// 2 - DMD page
	By title = By.xpath("//legend[text()='Window']");
	By link = By.xpath("//a[text()='Click Here']");
	final String newWindowTitle = "Google";

	// 3 - HDFC Banks - Mixed between window handles and iframe
	By agriLink = By.xpath("//a[text()='Agri']");
	By accountLink = By.xpath("//p[text()='Account Details']/../..");
	
	By footerFrame = By.xpath("//frameset/frame[@name='footer']");
	By policyLink = By.xpath("//a[text()='Privacy Policy']");
	
	By csrLink = By.xpath("//a[text()='CSR']");

	final String firstWindowTitle = "HDFC Bank Kisan Dhan Vikas e-Kendra";
	final String secondWindowTitle = "Welcome to HDFC Bank NetBanking";
	final String thirdWindowTitle = "HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan";
	
	@BeforeTest
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		actions = new Actions(driver);
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
	public void TC_01_Iframe_HDFCBack() throws Exception {
		// Step 1 - Access to the page
		driver.get("https://www.hdfcbank.com/");

		driver.switchTo().defaultContent();

		// Step 2 - Close the popup if exists
		//reloadUntilElementShown(popupFrame);

		checkAndClosePopUpIfExist(popupFrame,closeButton);

		Thread.sleep(2000);

		// Switch back to default iframe
		driver.switchTo().defaultContent();

		Thread.sleep(2000);
		
		// Step 3 - Verify text
		switchToIframe(frameBy1);
		
		Assert.assertEquals(driver.findElement(text).getText(), "What are you looking for?");
		// Step 4 - Verify banner
		driver.switchTo().defaultContent();

		Thread.sleep(2000);
		switchToIframe(frameBy2);
		// Verify if banner is shown + banner has 6 images
		Assert.assertTrue(driver.findElement(banner).isDisplayed());
		Assert.assertEquals(driver.findElements(bannerImg).size(), 6);

		// Step 5 - Verify flipper banner

		driver.switchTo().defaultContent();
     	Thread.sleep(2000);
		Assert.assertEquals(driver.findElements(fliperBanner).size(), 8);
	}

	@Test
	public void TC_02_WindowHandle_DMD() throws Exception {
		driver.get("http://daominhdam.890m.com/");
		
		String parentWindow = driver.getWindowHandle();

		scrollToElement(title);
		
		Thread.sleep(3000);

		clickOnElement(link);
		
		Thread.sleep(3000);

		switchToWindowByTitle(newWindowTitle);

		Assert.assertEquals(driver.getTitle(), newWindowTitle);
		
		Thread.sleep(3000);
		
		closeAllWithoutParentWindows(parentWindow);

	}
	
	@Test
	public void TC_03_WindowHandle_HDFCBack() throws Exception {
		// Step 1 - Access to the page
		driver.get("https://www.hdfcbank.com/");

		String parentWindow = driver.getWindowHandle();
		
		checkAndClosePopUpIfExist(popupFrame,closeButton);
		
		clickOnElement(agriLink);
		
		switchToWindowByTitle(firstWindowTitle);
		
		clickOnElement(accountLink);
		
		switchToWindowByTitle(secondWindowTitle);

	    Thread.sleep(2000);

	    switchToIframe(footerFrame);
		
	    Thread.sleep(2000);
		
		clickOnElement(policyLink);
		
		switchToWindowByTitle(thirdWindowTitle);

		clickOnElement(csrLink);
		
	    Thread.sleep(2000);
		
		driver.switchTo().window(parentWindow);
		
	    Thread.sleep(2000);
		
		closeAllWithoutParentWindows(parentWindow);

	}
	
	public void checkAndClosePopUpIfExist(By elBy,By closeBy) {
		  List<WebElement> notiIframe = driver.findElements(elBy);
		  if(!notiIframe.isEmpty()) {
			  switchToIframe(notiIframe.get(0));
			  executeJsScript("arguments[0].click();", closeBy);
			  System.out.println("Close popup");
			  //Switch to Topwindows
			  driver.switchTo().defaultContent();
		  }
		
	}
	
	public void executeJsScript(String jsScript,By elBy) {
		js.executeScript(jsScript, driver.findElement(elBy));
	}
	
	public void clickOnElement(By elBy) {
		waitTime.until(ExpectedConditions.elementToBeClickable(elBy)).click();
	}

	public void reloadUntilElementShown(By elBy) {
		do {
			driver.navigate().refresh();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (driver.findElements(elBy).size() < 1);
	}

	public void switchToIframe(String name) {
		driver.
		      switchTo().
		         frame(name);
	}

	public void switchToIframe(int num) {
		driver.
		      switchTo().
		         frame(num);
	}

	public void switchToIframe(By elBy) {
		driver.
		      switchTo().
		        frame(driver.findElement(elBy));
	}
	
	public void switchToIframe(WebElement el) {
		driver.
		      switchTo().
		        frame(el);
	}

	public void scrollToElement(By elBy) {
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(elBy));
	}

	public void switchToWindowByTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWithoutParentWindows(String parentWindow) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}	

}
