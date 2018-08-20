package selenium_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_06_Button_Radio_Checkbox_Alert {

	WebDriver driver;
	JavascriptExecutor js;
	
	String currentUrl = "";
	final String userName = "hienhoangminh";
	
	By myAccount = By.xpath("//div[@class='footer']//a[text()='My Account']");
	By createAccount = By.xpath("//a[@title='Create an Account']");
	By airConditioning = By.xpath("//label[contains(text(),'air conditioning')]/preceding-sibling::input");
	By carEngine = By.xpath("//label[contains(text(),'2.0 Petrol')]/preceding-sibling::input");
	By alertBtn = By.xpath("//button[text()='Click for JS Alert']");
	By confirmBtn = By.xpath("//button[text()='Click for JS Confirm']");
	By promptBtn = By.xpath("//button[text()='Click for JS Prompt']");
	By resMsg = By.id("result");
	
	@BeforeTest
	public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","drivers//chromedriver");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
	}

	@Test
	public void TC_01_LiveGuru() throws Exception {
		System.out.println("Run the first test case...");
        driver.get("http://live.guru99.com/");

        WebElement myAccLink = driver.findElement(myAccount);
        myAccLink.click();
        
        currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://live.guru99.com/index.php/customer/account/login/");
        
        WebElement createAccBtn = driver.findElement(createAccount);
        clickElementByJavaScript(createAccBtn);
        
        currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://live.guru99.com/index.php/customer/account/create/");
        
	}
	
	@Test
	public void TC_02_Telerik_VerifyCheckBox() throws Exception {
		System.out.println("Run the second test case...");

        driver.get("https://demos.telerik.com/kendo-ui/styling/checkboxes");

        WebElement equipment =  driver.findElement(airConditioning);
        clickElementByJavaScript(equipment);
        // Wait sometime to ensure that element is clicked.
        Thread.sleep(5000);
   
        Assert.assertTrue(equipment.isSelected());
        
        if(equipment.isSelected()) {
            clickElementByJavaScript(equipment);
            Assert.assertFalse(equipment.isSelected());
      }
        // Wait sometime to ensure that element is unclicked.
        Thread.sleep(5000);
	}
	
	@Test
	public void TC_03_Telerik_VerifyRadioButton() throws Exception {
		System.out.println("Run the third test case...");

        driver.get("https://demos.telerik.com/kendo-ui/styling/radios");
        WebElement radio2Petrol =  driver.findElement(carEngine);
        clickElementByJavaScript(radio2Petrol);
        
        Assert.assertTrue(radio2Petrol.isSelected());
        
        if(!radio2Petrol.isSelected()) {
          radio2Petrol.click();
        }
	}
	
	@Test
	public void TC_04_DMD_VerifyAlertWhenAccepted() throws Exception {
		System.out.println("Run the fourth test case...");

        driver.get("http://daominhdam.890m.com/");
        WebElement jsAlertBbtn =  driver.findElement(alertBtn);
        WebElement resultMessage =  driver.findElement(resMsg);

        jsAlertBbtn.click();
        
        Alert alert = driver.switchTo().alert();
        String txtAlert = alert.getText();
        
        Assert.assertEquals(txtAlert,"I am a JS Alert");
        
        alert.accept();
        
        Assert.assertEquals(resultMessage.getText(),"You clicked an alert successfully");

	}
	
	@Test
	public void TC_05_DMD_VerifyAlertWhenCanceled() throws Exception {
		System.out.println("Run the fifth test case...");
        driver.get("http://daominhdam.890m.com/");
        WebElement jsConfirmBtn =  driver.findElement(confirmBtn);
        WebElement resultMessage =  driver.findElement(resMsg);

        jsConfirmBtn.click();
        
        Alert alert = driver.switchTo().alert();
        String txtAlert = alert.getText();
        
        Assert.assertEquals(txtAlert,"I am a JS Confirm");
        
        alert.dismiss();
        
        Assert.assertEquals(resultMessage.getText(),"You clicked: Cancel");
	}
	
	@Test
	public void TC_06_DMD_VerifyAlertWhenInputted() throws Exception {
		System.out.println("Run the sixth test case...");

        driver.get("http://daominhdam.890m.com/");
        WebElement jsConfirmBtn =  driver.findElement(promptBtn);
        WebElement resultMessage =  driver.findElement(resMsg);

        jsConfirmBtn.click();
        
        Alert alert = driver.switchTo().alert();
        String txtAlert = alert.getText();
        
        Assert.assertEquals(txtAlert,"I am a JS prompt");
        
        alert.sendKeys(userName);
        alert.accept();
        
        Assert.assertEquals(resultMessage.getText(),"You entered: " + userName);
	}
	

	@AfterTest
	public void tearDown() throws Exception {
        driver.quit();
	}
	
	public void clickElementByJavaScript(WebElement el) {
		js.executeScript("arguments[0].click();", el);
	}
}
