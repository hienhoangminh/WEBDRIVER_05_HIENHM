package selenium_api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_10_UploadFile {

	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait waitTime;
	
	final String fileName = "bird.jpg";
    final String uploadFilePath = ".\\upload\\uploadFile.exe";
    String path = System.getProperty("user.dir") + "\\upload\\bird.jpg";
    
	// SendKeys so do not need to click on file
	By inputValueField = By.xpath("//input[@type='file']");
	By inputtedFileName = By.xpath("//p[text()='" + fileName + "']");
	By uploadFileBtn = By.xpath("//p[text()='" + fileName + "']/../../td/button");
	By uploadedFileName = By.xpath("//p[@class='name']/a[@title='" + fileName + "']");
	// By progressBar = By.xpath("//p[@class='size']/following-sibling::div[@role='progressbar']");

	// Need to click so modify a little bit the selector
	By inputAutoIt = By.cssSelector(".fileinput-button");
	
	// 4 - Free Choice - SendKeys
	By upFileField_Encodable = By.xpath("//input[@id='uploadname1']");
	By folderDir_Encodable = By.xpath("//select[@name='subdir1']");
	String folderPath_Encodable = " /uploaddemo/files/";
	By newSubDir_Encodable = By.xpath("//input[@id='newsubdir1']");
	By emailField_Encodable = By.xpath("//input[@id='formfield-email_address']");
	By nameField_Encodable = By.xpath("//input[@id='formfield-first_name']");
	By uploadBtn_Encodable = By.xpath("//input[@id='uploadbutton']");
	By viewUploadedFiles = By.xpath("//a[text()='View Uploaded Files']");
	
	By assertEmail = By.xpath("//div[@id='uploadDoneContainer']//dd[contains(text(),'Email Address')]");
	By assertUserName = By.xpath("//div[@id='uploadDoneContainer']//dd[contains(text(),'First Name')]");

	
	String customFolderNameLink = "//a[text()='%s']";
	String customFileNameLink = "//a[text()='%s']";

	
	String prefix_Encodable = "hien";
	final String email_Encodable = "hienhm@yopmail.com";
	final String name_Encodable = "Hien HOANG";

	//Custom element, should be processed before running the test
	String subFolderName;
	By subFolderLink;
	By fileLink;
	
	@BeforeTest
	public void setUp() throws Exception {
		// SendKeys - Passed with Chrome
		// AutoiIt - Passed with Chrome
		// Robot - Passed with Chrome
		// 4th test - Passed with Chrome
//		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
//		driver = new ChromeDriver();

		// SendKeys - Passed with IE
		// AutoiIt - Passed with IE
		// Robot - Passed with IE
		// 4th test - Passed with IE
//		 System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
//		 driver = new InternetExplorerDriver();

		// SendKeys - Passed with Firefox
		// AutoIt - Passed with Firefox
		// Robot - Passed with Firefox
		// 4th test - Passed with Firefox
		driver = new FirefoxDriver();

		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		waitTime = new WebDriverWait(driver, 60);
		
		//Initialize the name for subfolder
		subFolderName =  getRandomSubFolderName(prefix_Encodable);

		// Modify the xpath to match with name of subfolder.
		subFolderLink = By.xpath(formatCustomStringWithValue(customFolderNameLink, subFolderName));
		fileLink = By.xpath(formatCustomStringWithValue(customFileNameLink, fileName));

	}

	// http://blueimp.github.com/jQuery-File-Upload/
	// Upload by sendKeys
	// @Test
	public void TC_01_uploadBySendKeys() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		inputValueInWebElement(inputValueField, path);
    
		// Assert for progress bar is not stable - so comment it first
//		Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfElementLocated(progressBar)).isDisplayed());

		Assert.assertTrue(isWebElementDisplayed(inputtedFileName));

		clickOnWebElement(uploadFileBtn);

//		Assert.assertTrue(waitTime.until(ExpectedConditions.invisibilityOfElementLocated(progressBar)));

		Assert.assertTrue(isWebElementDisplayed(uploadedFileName));

	}

	// http://blueimp.github.com/jQuery-File-Upload/
	// Upload by AutoIt
	// @Test
	public void TC_02_uploadByAutoIt() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		
		clickOnWebElement(inputAutoIt);
		
		runAutoItScript(driver,".\\upload\\uploadFile.exe",path);

		Assert.assertTrue(isWebElementDisplayed(inputtedFileName));

        clickOnWebElement(uploadFileBtn);
		Assert.assertTrue(isWebElementDisplayed(uploadedFileName));
		
	}

	// http://blueimp.github.com/jQuery-File-Upload/
	// Upload by Robot
	//@Test
	public void TC_03_uploadByRobot() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		clickOnWebElement(inputAutoIt);

		uploadFileWithRobot(path);
		
		Assert.assertTrue(isWebElementDisplayed(inputtedFileName));

		clickOnWebElement(uploadFileBtn);

		// Wait for upload finished.
		Thread.sleep(3000);

		Assert.assertTrue(isWebElementDisplayed(uploadedFileName));
		
	}
	
	@Test
	public void TC_04_uploadWithFreeChoice() throws Exception {
		
		driver.get("https://encodable.com/uploaddemo/");
		
		inputValueInWebElement(upFileField_Encodable, path);

		Thread.sleep(2000);
		
		selectFolderToUpload(folderDir_Encodable,folderPath_Encodable);
		
		Thread.sleep(2000);
		
		inputValueInWebElement(newSubDir_Encodable,subFolderName);

		Thread.sleep(2000);
				
		scrollToElement(uploadBtn_Encodable);
		
		Thread.sleep(2000);

		inputValueInWebElement(emailField_Encodable, email_Encodable);
		
		Thread.sleep(2000);

		inputValueInWebElement(nameField_Encodable, name_Encodable);

        clickOnWebElement(uploadBtn_Encodable);
		// Wait for upload finished.
		Thread.sleep(2000);
		
		Assert.assertTrue(isTextVisibleInWebElement(assertEmail, email_Encodable));

		Assert.assertTrue(isTextVisibleInWebElement(assertUserName, name_Encodable));
		
		clickOnWebElement(viewUploadedFiles);
		
		Assert.assertTrue(isWebElementDisplayed(subFolderLink));
		
		Thread.sleep(2000);
		
 		clickOnWebElement(subFolderLink);
 		
		Assert.assertTrue(isWebElementDisplayed(fileLink));

	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}

	public void clickOnWebElement(By elBy) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.elementToBeClickable(elBy));
			el.click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void inputValueInWebElement(By elBy, String value) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			el.sendKeys(value);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public boolean isWebElementDisplayed(By elBy) {
		try {
			WebElement el = driver.findElement(elBy);
			return el.isDisplayed();
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}
	
	public boolean isTextVisibleInWebElement(By elBy,String value) {
		try {
			WebElement el = waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			return el.getText().contains(value);
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}


	public void clickOnElementByJS(By elBy) {
		js.executeScript("arguments[0].click();", driver.findElement(elBy));
	}

	public void uploadFileWithRobot(String Path) {
		StringSelection stringSelection = new StringSelection(Path);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public String getTheWindowTitleByDriver(WebDriver driver) {
		String wTitle = "";
		if(driver instanceof ChromeDriver) {
			wTitle = "Open";
		}else if(driver instanceof FirefoxDriver){
			wTitle = "File Upload";	
		}else if(driver instanceof InternetExplorerDriver){
			wTitle = "Choose File to Upload";
		}
		return wTitle;
	}
	
	public String getScriptByDriver(WebDriver driver,String pathExe,String filePath) {
		String wTitle,cmd = "";
		wTitle = getTheWindowTitleByDriver(driver);
		cmd = pathExe + " -w " + wTitle + " -f " + filePath;
		return cmd;
	}
	
	public void runAutoItScript(WebDriver driver,String pathExe,String filePath) {
		String cmd = getScriptByDriver(driver, pathExe, filePath);
		
		try {
			Runtime.getRuntime().exec(cmd);
			// Wait sometime for upload image
			Thread.sleep(5000);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void selectFolderToUpload(By elBy,String value) {
		try {
			WebElement folderList = waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy));
			Select folderDd = new Select(folderList);
			
			folderDd.selectByVisibleText(value);
		}catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
	}
	
	public int getRandomNumber() {
		 Random rnd = new Random(); 
		 int n = 100000 + rnd.nextInt(900000);
		 return n;
	}
	
	public void scrollToElement(By elBy) {
		js.executeScript("arguments[0].scrollIntoView();", waitTime.until(ExpectedConditions.visibilityOfElementLocated(elBy)));
	
	}
	
	public String getRandomSubFolderName(String prefix) {
		String name = "";
		name = prefix + getRandomNumber();
		return name;
	}
	
	public String formatCustomStringWithValue(String custom,String value) {
		String result = String.format(custom, value);
		return result;
	}
}
