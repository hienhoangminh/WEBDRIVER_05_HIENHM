package selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_01_CheckEnvironment {

	WebDriver driver;

	@Test
	public void Topic_01_CheckUrlAndTitle() {
		// Verify title of the page
		String expectedTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, "Guru99 Bank Home Page");

		// Verify if current URL is correct
		String expectedUrl = driver.getCurrentUrl();
		Assert.assertEquals(expectedUrl, "http://demo.guru99.com/v4/");
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
