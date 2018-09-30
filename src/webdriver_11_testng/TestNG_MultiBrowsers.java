package webdriver_11_testng;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestNG_MultiBrowsers {
  
  WebDriver driver;	
  WebDriverWait wait;
  
  By uEmail = By.xpath("//input[@id='email']");
  By uPassword = By.xpath("//input[@id='pass']");
  By loginBtn = By.xpath("//button[@id='send2']"); 
  By dashBoard = By.xpath("//h1[contains(.,'My Dashboard')]");
  
  @Parameters("browser")
  @BeforeClass
  public void beforeClass(String browser) throws Exception{
	  if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
			driver= new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			System.out.println("Unsupported browser tyype!");
		}
		
		driver.get("http://live.guru99.com/index.php/customer/account/login/");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver,15);
  }

  @Test
	public void TC_Login() {
		sendKeysToElement(uEmail, "automationvalid_01@gmail.com");
		sendKeysToElement(uPassword,"111111");
		clickOnElement(loginBtn);
		Assert.assertTrue(isControlDisplayed(dashBoard));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
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
	
	public boolean isControlDisplayed(By elBy) {
		try {
			WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			return el.isDisplayed();
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

}
