package selenium_api;


import com.github.javafaker.Faker;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Topic_02_Xpath_Css_Locator {

	WebDriver driver = new FirefoxDriver();
	String baseUrl = "http://live.guru99.com";
	String currentUrl;
	
	@BeforeTest
	public void beforeTest() {
		System.out.println("Setup for each test..."); 
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	@Test(priority=0)
	public void TC01_VerifyUrlAndTitle() {
		
		String title = driver.getTitle();
		System.out.println("Check point TC01_VerifyUrlAndTitle_1 : Verifying the title..."); 
		Assert.assertEquals("Home page", title);

		// Click on My Account link
		WebElement myAccount = driver.findElement(
				By.xpath("//div[@class='footer']//span[text()='Account']/../../../ul/li[@class='first']/a"));
		myAccount.click();

		// Click on Create an Account button
		WebElement createAccount = driver.findElement(By.xpath("//a[@class='button' and @title='Create an Account']"));
		createAccount.click();

		// Back to Login page 
		driver.navigate().back(); 
		currentUrl = driver.getCurrentUrl();
		System.out.println("Check point TC01_VerifyUrlAndTitle_2 : Verifying URL after going back..."); 
		Assert.assertEquals("http://live.guru99.com/index.php/customer/account/login/", currentUrl);	
		// Forward to account creation page
		
		driver.navigate().forward();
		currentUrl = driver.getCurrentUrl();
		System.out.println("Check point TC01_VerifyUrlAndTitle_3 : Verifying URL after going forward..."); 
		Assert.assertEquals("http://live.guru99.com/index.php/customer/account/create/", currentUrl);


	}

	@Test(priority=1)
	public void TC02_VerifyLoginEmpty() {
				
		WebElement myAccount = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='footer']//span[text()='Account']/../../../ul/li[@class='first']/a")));
		myAccount.click();

		// Click Login without input anything
		WebElement loginBtn = driver.findElement(By.id("send2"));
		loginBtn.click();
        
		// Verify error message
		WebElement errorMsg1 = driver.findElement(By.id("advice-required-entry-email"));
		System.out.println("Check point TC02_VerifyLoginEmpty_1 : Verify if error message is shown to inform that email is required...");
		Assert.assertEquals("This is a required field.", errorMsg1.getText());
		
		System.out.println("Check point TC02_VerifyLoginEmpty_2 : Verify if error message is shown to inform that password is required...");
		WebElement errorMsg2 = driver.findElement(By.id("advice-required-entry-pass"));
		Assert.assertEquals("This is a required field.", errorMsg2.getText());

	}

	@Test(priority=2)
	public void TC03_VerifyLoginWithInvalidEmail() {
		
		System.out.println("TC03_VerifyLoginWithInvalidEmail is running..."); 

		WebElement myAccount = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='footer']//span[text()='Account']/../../../ul/li[@class='first']/a")));
		myAccount.click();

		// Input invalid user email
		WebElement userEmail = driver.findElement(By.id("email"));
		userEmail.sendKeys("123434234@12312.123123");

		// Click Login
		WebElement loginBtn = driver.findElement(By.id("send2"));
		loginBtn.click();

		//Verify error message 
		WebElement errorMsg = driver.findElement(By.id("advice-validate-email-email"));
		System.out.println("Check point TC03_VerifyLoginWithInvalidEmail_1 : Verify if error message is shown to inform that email format is invalid..."); 
		Assert.assertEquals("Please enter a valid email address. For example johndoe@domain.com.", errorMsg.getText());
		
	}

	@Test(priority=3)
	public void TC04_VerifyLoginWithInvalidPassword() {

		WebElement myAccount = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='footer']//span[text()='Account']/../../../ul/li[@class='first']/a")));
		myAccount.click();

		// Input valid user email
		WebElement userEmail = driver.findElement(By.id("email"));
		userEmail.sendKeys("automation@gmail.com");

		// Input invalid password
		WebElement userPass = driver.findElement(By.id("pass"));
		userPass.sendKeys("123");

		// Click Login
		WebElement loginBtn = driver.findElement(By.id("send2"));
		loginBtn.click();

		// Verify error message
		WebElement errorMsg = driver.findElement(By.id("advice-validate-password-pass"));
		System.out.println("Check point TC04_VerifyLoginWithInvalidPassword_1 : Verify if error message is shown to inform that password length is incorrect..."); 
		Assert.assertEquals("Please enter 6 or more characters without leading or trailing spaces.",
				errorMsg.getText());


	}

	@Test(priority=4)
	public void TC05_VerifyAccountCreation() {
		
		// Create fake data
		Faker faker = new Faker();
        String passWord = faker.internet().password();


		// Click on My Account link
		WebElement myAccount = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='footer']//span[text()='Account']/../../../ul/li[@class='first']/a")));
		myAccount.click();

		// Click on Create an Account button
		WebElement createAccount = driver.findElement(By.xpath("//a[@class='button' and @title='Create an Account']"));
		createAccount.click();
        
		
		//Input : firstName/lastName/email/password/confirmationPassword
		
		WebElement firstName = driver.findElement(By.id("firstname"));
		firstName.sendKeys(faker.name().firstName());

		WebElement lastName = driver.findElement(By.id("lastname"));
		lastName.sendKeys(faker.name().lastName());

		WebElement email = driver.findElement(By.id("email_address"));
		email.sendKeys(faker.internet().emailAddress());

		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(passWord);

		WebElement confirmPassword = driver.findElement(By.id("confirmation"));
		confirmPassword.sendKeys(passWord);
        
		// Click on Register button
		WebElement registerBtn = driver.findElement(By.xpath("//button[@title='Register']"));
		registerBtn.click();

		// Check validation message
		WebElement validateMsg = driver.findElement(By.xpath("//ul[@class='messages']/li[@class='success-msg']//span"));
		
		System.out.println("Check point TC05_VerifyAccountCreation_1 : Verify if validation message is shown to inform that account registration is succeeded..."); 
		Assert.assertEquals("Thank you for registering with Main Website Store.", validateMsg.getText());

		// Click on Account link to open dropdown menu
		WebElement accountMgmt = driver.findElement(By.xpath("//a[@data-target-element='#header-account']"));
		accountMgmt.click();
        
		// Click on Logout
		WebElement logoutBtn = driver.findElement(By.cssSelector("div#header-account .links a[title='Log Out']"));
		logoutBtn.click();

		// Check if logout message is disappered
		boolean logoutMsg = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName("h1")));
         
		// If yes then redirection is finished and we go bback to Home page.
		if (logoutMsg) {
			System.out.println("Redirection is finished..."); 
			String title = driver.getTitle();
			// Verify title
			System.out.println("Check point TC05_VerifyAccountCreation_2 : Verify if we are redirected to homepage..."); 
			Assert.assertEquals("Home page", title);
		}
		

	}

}
