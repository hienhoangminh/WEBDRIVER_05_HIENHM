package selenium_api;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
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

public class Topic_07_UserInteractions {

	WebDriver driver;
	WebDriverWait waitTime;
	ChromeOptions options;
    Actions actions;
    JavascriptExecutor js;
    Alert alert;
    // 1 - daominhdam.890m.com
    By linkWithToolttip = By.xpath("//a[text()='Hover over me']");
    By tooltipText = By.xpath("//div[@class='tooltip-inner']");
	
    // 2 - www.myntra.com/
    By accountContainer = By.xpath("//div[@class='desktop-userIconsContainer']");
    By loginLink = By.xpath("//a[text()='login']");
    By loginForm = By.xpath("//div[@class='login-box']");
    
    // 3 - http://jqueryui.com/resources/demos/selectable/display-grid.html
    By fromNumber = By.xpath("//ol[@id='selectable']/li[text()='1']");
    By toNumber = By.xpath("//ol[@id='selectable']/li[text()='4']");
    By selectedNumber = By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']");
    
    
    // 4 - http://www.seleniumlearn.com/double-click
    By doubleClick = By.xpath("//button[contains(text(),'Double-Click')]");
  
    // 5 - http://swisnl.github.io/jQuery-contextMenu/demo.html
    By rightClick = By.xpath("//span[text()='right click me']");
    By quitButton = By.xpath("//li[contains(@class,'context-menu-icon-quit')]/span[text()='Quit']");

    //6 - https://demos.telerik.com/kendo-ui/dragdrop/angular
    By dragEl = By.id("draggable");
    By dropEl = By.id("droptarget");
    
  // 7 - http://jqueryui.com/resources/demos/droppable/default.html
    By dragJqueryEl = By.id("draggable");
    By dropJqueryEl = By.id("droppable");
    
	@BeforeTest
	public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","drivers//chromedriver");
        options = new ChromeOptions();
        options.addArguments("--disable-notifications");
		driver = new ChromeDriver(options);
		actions = new Actions(driver);
		js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        waitTime = new WebDriverWait(driver,60);
	}
	
	@AfterTest
	public void tearDown() throws Exception {
       driver.quit();
	}
	
	@Test
	public void TC_01_MouseOver_DMD() throws Exception {
		driver.get("http://daominhdam.890m.com/");
		
		scrollToBottom();
		
		mouseOverElement(linkWithToolttip);
		
		Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfElementLocated(tooltipText)).isDisplayed());

		Thread.sleep(6000);
		
		
	}
	
	@Test
	public void TC_02_MouseOver_Myntra() throws Exception {
		driver.get("http://www.myntra.com/");
		
		mouseOverElement(accountContainer);
		
		clickOnElement(loginLink);
		
		Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfElementLocated(loginForm)).isDisplayed());

		Thread.sleep(6000);
		
		
	}
	
	@Test
	public void TC_03_ClickAndHold_JQueryUI() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		
		clickAndHold(fromNumber, toNumber);
		
		Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(selectedNumber))).size()==4);

		Thread.sleep(6000);
		
	}
	
	@Test
	public void TC_04_DoubleClick_SeleniumLearn() throws Exception {
		driver.get("http://www.seleniumlearn.com/double-click");
		
		doubleClick(doubleClick);
		
        verifyAndAcceptAlert("The Button was double-clicked.");
        
		Thread.sleep(6000);

		
	}
	
	@Test
	public void TC_05_RightClick_Swisnl() throws Exception {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		
		rightClickOnEl(rightClick);
		
        mouseOverElement(quitButton);
				
		Assert.assertTrue(waitTime.until(ExpectedConditions.visibilityOfElementLocated(quitButton)).isDisplayed());
		
		Thread.sleep(3000);
		
		clickOnElement(quitButton);
		
        verifyAndAcceptAlert("clicked: quit");

		Thread.sleep(3000);

	}
	
	@Test
	public void TC_06_DragAndDrop_DemosTelerik() throws InterruptedException {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");
		
		Assert.assertNotEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(dropEl)).getText(),"You did great!");

		dragAndDrop(dragEl, dropEl);
		
		Thread.sleep(3000);
		
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(dropEl)).getText(),"You did great!");

		Thread.sleep(3000);
	}
	
	@Test
	public void TC_07_DragAndDrop_JQueryUi() throws InterruptedException {
		driver.get("http://jqueryui.com/resources/demos/droppable/default.html");
		
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(dropJqueryEl)).getText(),"Drop here");

		dragAndDrop(dragJqueryEl, dropJqueryEl);
		
		Thread.sleep(3000);
		
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(dropJqueryEl)).getText(),"Dropped!");

		Thread.sleep(3000);
	}
	
	
	public void scrollToBottom() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");

	}
	
	public void JsScrollToElement(WebElement el) {
		js.executeScript("arguments[0].scrollIntoView();", el);
	}
	
	public void mouseOverElement(By elementBy) {
		actions.moveToElement(driver.findElement(elementBy)).build().perform();
	}
	
	public void clickOnElement(By elementBy) {
		waitTime.until(ExpectedConditions.elementToBeClickable(driver.findElement(elementBy))).click();
	}
	
	public void clickAndHold(By fromBy,By toBy) {
		WebElement fromEl = waitTime.until(ExpectedConditions.visibilityOfElementLocated(fromBy));
		WebElement toEl = waitTime.until(ExpectedConditions.visibilityOfElementLocated(toBy));

		actions.clickAndHold(fromEl).moveToElement(toEl).release().perform();
	}
	
	public void doubleClick(By doubleClickBy) {
		WebElement clickedEl = waitTime.until(ExpectedConditions.elementToBeClickable(doubleClickBy));

		actions.doubleClick(clickedEl).build().perform();
	}
	
	public void verifyAndAcceptAlert(String expectedText) {
		alert = driver.switchTo().alert();
		Assert.assertEquals(expectedText,alert.getText());
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		alert.accept();
	    
	}
	
	public void rightClickOnEl(By inputBy) {
		WebElement rightClickedEl = waitTime.
				until(ExpectedConditions.
						visibilityOfElementLocated(inputBy));

	    actions.contextClick(rightClickedEl).perform();
	}
	
	public void dragAndDrop(By dragElBy,By dropElBy) {
		WebElement sourceEl = waitTime.
				until(ExpectedConditions.
						elementToBeClickable(dragElBy));
		WebElement targetEl = waitTime.
				until(ExpectedConditions.
						elementToBeClickable(dropElBy));
		actions.dragAndDrop(sourceEl, targetEl).build().perform();
	
	}
}
