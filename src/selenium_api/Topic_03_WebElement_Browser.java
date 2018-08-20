package selenium_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_03_WebElement_Browser {

	WebDriver driver;

	@BeforeTest
	public void setUp() throws Exception {
		// Run with firefox
		// driver = new FirefoxDriver();
		
		// Run with Chrome
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_IsDisplayed() throws Exception {
		driver.get("http://daominhdam.890m.com/");

		WebElement email = driver.findElement(By.id("mail"));
		WebElement ageUnder18 = driver.findElement(By.id("under_18"));
		WebElement education = driver.findElement(By.id("edu"));

		List<WebElement> elementsToCheck = new ArrayList<WebElement>();
		elementsToCheck.add(email);
		elementsToCheck.add(ageUnder18);
		elementsToCheck.add(education);

		for (WebElement wel : elementsToCheck) {
			Assert.assertTrue(isElementDisplayed(wel));
		}

		if (isElementDisplayed(email)) {
           email.sendKeys("Automation Testing");
		}
		
		if (isElementDisplayed(ageUnder18)) {
			ageUnder18.click();

		}
		
		if (isElementDisplayed(education)) {
			education.sendKeys("Automation Testing");

		}

	}

	@Test
	public void TC_01_IsEnabled() throws Exception {
		driver.get("http://daominhdam.890m.com/");
		// Search all elements : enabled
		WebElement email = driver.findElement(By.id("mail"));
		WebElement ageUnder18 = driver.findElement(By.id("under_18"));
		WebElement education = driver.findElement(By.id("edu"));
		WebElement job1 = driver.findElement(By.id("job1"));
		WebElement interestDev = driver.findElement(By.id("development"));
		WebElement slider1 = driver.findElement(By.id("slider-1"));
		WebElement btnEnabled = driver.findElement(By.id("button-enabled"));

		// Search all elements : disabled
		WebElement password = driver.findElement(By.id("password"));
		WebElement ageRadioBbtnDisable = driver.findElement(By.id("radio-disabled"));
		WebElement biography = driver.findElement(By.id("bio"));
		WebElement job2 = driver.findElement(By.id("bio"));
		WebElement interestCheckBoxDisable = driver.findElement(By.id("check-disbaled"));
		WebElement slider2 = driver.findElement(By.id("slider-2"));
		WebElement btnDisabled = driver.findElement(By.id("button-disabled"));

		List<WebElement> elementsToCheck = new ArrayList<WebElement>();
		// Add all elements
		elementsToCheck.add(email);
		elementsToCheck.add(ageUnder18);
		elementsToCheck.add(education);
		elementsToCheck.add(job1);
		elementsToCheck.add(interestDev);
		elementsToCheck.add(slider1);
		elementsToCheck.add(btnEnabled);

		elementsToCheck.add(password);
		elementsToCheck.add(ageRadioBbtnDisable);
		elementsToCheck.add(biography);
		elementsToCheck.add(job2);
		elementsToCheck.add(interestCheckBoxDisable);
		elementsToCheck.add(slider2);
		elementsToCheck.add(btnDisabled);

		for (WebElement el : elementsToCheck) {
			isElementEnabled(el);
		}

	}

	@Test
	public void TC_03_IsSelected() throws Exception {
		driver.get("http://daominhdam.890m.com/");

		WebElement optionUnder18 = driver.findElement(By.id("under_18"));
		WebElement optionDev = driver.findElement(By.id("development"));

		optionUnder18.click();
		optionDev.click();

		Assert.assertTrue(isElementSelected(optionUnder18));
		Assert.assertTrue(isElementSelected(optionDev));

		if (!isElementSelected(optionUnder18)){
			optionUnder18.click();
		}
		if(!isElementSelected(optionDev)) {
			optionDev.click();
		}

	}
	

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();

	}

	public boolean isElementDisplayed(WebElement el) {
		boolean isDisplayed = false;
		if (el.isDisplayed()) {
			System.out.println("Element is displayed in the page");
			isDisplayed = true;
		} else {
			System.out.println("Element is not displayed in the page");
		}
		return isDisplayed;
	}

	public boolean isElementEnabled(WebElement el) {
		boolean isDisplayed = false;
		if (el.isEnabled()) {
			System.out.println("Element is enabled!");
			isDisplayed = true;
		} else {
			System.out.println("Element is disabled!");
		}
		return isDisplayed;
	}

	public boolean isElementSelected(WebElement el) {
		boolean isSelected = false;
		if (el.isSelected()) {
			System.out.println("Element is selected!");
			isSelected = true;
		} else {
			System.out.println("Element is not selected!");
		}
		return isSelected;
	}

}
