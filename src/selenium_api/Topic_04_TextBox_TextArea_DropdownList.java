package selenium_api;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

public class Topic_04_TextBox_TextArea_DropdownList {

	WebDriver driver;
	String actualResult;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	String phoneNumber;
	String name, zipCode, newName;
	String oldAddress, oldCity, nwAddress, nwCity, state;
	Faker faker;
	Address address;
	final String userName = "mngr150067";
	final String userPassword = "esEpepE";

	@BeforeTest
	public void randomData() {
		faker = new Faker();
		address = faker.address();
		name = faker.name().firstName() + " " + faker.name().lastName();
		zipCode = generateZipCode();
		phoneNumber = generatePhoneNumber();
		oldCity = address.city();
		oldAddress = address.streetAddress();
		nwAddress = address.streetAddress();
		nwCity = address.city();
		state = address.state();
	}

	public String generateZipCode() {
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		return String.valueOf(n);
	}

	public String generatePhoneNumber() {
		Random rnd = new Random();
		int rnd1 = 10 + rnd.nextInt(90);
		int rnd2 = 100 + rnd.nextInt(900);
		int rnd3 = 100 + rnd.nextInt(900);
		String pNum = "09" + rnd1 + rnd2 + rnd3;
		return pNum;
	}

	@BeforeClass
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void TC01_DropdownList() {
		System.out.println("Start the first test!");
		driver.get("http://daominhdam.890m.com/");

		// Get the element
		WebElement jobRole = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='job1']")));

		// Select object needs to be instantiated in order to
		// manipulate dropdown list
		Select dropdownJobRole = new Select(jobRole);
		// Verify if the dropdown list is multiple selection
		Assert.assertFalse(dropdownJobRole.isMultiple());

		dropdownJobRole.selectByVisibleText("Automation Tester");
		// Verify if "Automation Tester" is selected.
		actualResult = dropdownJobRole.getFirstSelectedOption().getText();
		Assert.assertEquals("Automation Tester", actualResult);

		// Set value = Manual Tester
		dropdownJobRole.selectByValue("manual");
		actualResult = dropdownJobRole.getFirstSelectedOption().getText();
		Assert.assertEquals("Manual Tester", actualResult);

		// Select by index = 3 cause index starts from 0.
		dropdownJobRole.selectByIndex(3);
		actualResult = dropdownJobRole.getFirstSelectedOption().getText();
		Assert.assertEquals("Mobile Tester", actualResult);

		int nbOptions = dropdownJobRole.getOptions().size();
		Assert.assertEquals(nbOptions, 5);

	}

	@Test
	public void TC02_CustomDropdownList() {
		System.out.println("Start the second test!");
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");

		WebElement numberDropdown = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.id("number-button")));
		// Select numDd = new Select(numberDropdown);

		numberDropdown.click();

		List<WebElement> options = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//li[@class='ui-menu-item']/div"), 19));

		for (int i = 0; i < options.size(); i++) {
			String value = options.get(i).getText();
			if (Integer.valueOf(value) == 19) {
				options.get(i).click();
			}
		}

		WebElement resultSelected = new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")));

		String value = resultSelected.getText();

		Assert.assertEquals("19", value);

	}

	@Test
	public void TC03_TextArea() {

		System.out.println("Start the third test!");

		goToUrl();

		login();

		clickCreateCustomer();

		fillDataForm();

		String cusId = getCustomerId();

		clickEditCustomerLink();

		searchCustomerId(cusId);

		updateCustomerInfo();

	}

	public void goToUrl() {
		driver.get("http://demo.guru99.com/v4/");
		System.out.println("Checkpoint 1 : Check the URL...");
		Assert.assertEquals("Guru99 Bank Home Page", driver.getTitle());
	}

	public void login() {
		WebElement userId = driver.findElement(By.name("uid"));
		userId.sendKeys(userName);

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(userPassword);

		WebElement btnLogin = driver.findElement(By.name("btnLogin"));
		btnLogin.click();

		System.out.println("Checkpoint 2 : Check if login sucessfully...");
		WebElement heading3 = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='heading3']/td")));
		String headingText = heading3.getText();
		Pattern pattern = Pattern.compile("Manger Id : (.*?)$");
		Matcher matcher = pattern.matcher(headingText);
		while (matcher.find()) {
			System.out.println("Checkpoint 2bis : Check if user displayed is correct.");
			Assert.assertEquals(userName, matcher.group(1));
		}

	}

	public void clickCreateCustomer() {
		WebElement newCustomerLk = new WebDriverWait(driver, 15).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//ul[@class='menusubnav']//a[text()='New Customer']")));
		newCustomerLk.click();

		System.out.println("Checkpoint 3 : Check if we are redirected to create customer page...");
		Assert.assertEquals("Guru99 Bank New Customer Entry Page", driver.getTitle());

	}

	public void fillDataForm() {
		WebElement cusName = driver.findElement(By.name("name"));
		cusName.sendKeys(name);

		WebElement dob = driver.findElement(By.id("dob"));
		dob.sendKeys(sdf.format(faker.date().birthday()));

		WebElement custAddr = driver.findElement(By.name("addr"));
		custAddr.sendKeys(oldAddress);

		WebElement custCity = driver.findElement(By.name("city"));
		custCity.sendKeys(oldCity);

		WebElement cusState = driver.findElement(By.name("state"));
		cusState.sendKeys(state);

		WebElement cusPin = driver.findElement(By.name("pinno"));
		cusPin.sendKeys(zipCode);

		WebElement cusPhoneNo = driver.findElement(By.name("telephoneno"));
		cusPhoneNo.sendKeys(phoneNumber);

		WebElement cusEmail = driver.findElement(By.name("emailid"));
		cusEmail.sendKeys(faker.internet().emailAddress());

		WebElement cusPass = driver.findElement(By.name("password"));
		cusPass.sendKeys(faker.internet().password());

		WebElement submitBtn = driver.findElement(By.name("sub"));
		submitBtn.click();
	}

	public String getCustomerId() {
		WebElement custIdField = new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//table[@id='customer']//td[text()='Customer ID']/following-sibling::td")));
		System.out.println("User id is : " + custIdField.getText());
		return custIdField.getText();
	}

	public void clickEditCustomerLink() {
		WebElement editCust = new WebDriverWait(driver, 15).until(ExpectedConditions
				.elementToBeClickable(By.xpath("//ul[@class='menusubnav']//a[text()='Edit Customer']")));
		editCust.click();

		System.out.println("Checkpoint 4 : Check if we are redirected to edit customer page...");
		Assert.assertEquals("Guru99 Bank Edit Customer Page", driver.getTitle());
	}

	public void searchCustomerId(String cId) {
		WebElement cusId = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cusid']")));
		cusId.sendKeys(cId);

		WebElement searchBtn = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='AccSubmit']")));
		searchBtn.click();

	}

	public void updateCustomerInfo() {
		// Get the WebElement of city field and address field
		WebElement cusCity = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='city']")));
		WebElement custAddr = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name='addr']")));
		WebElement submitBtn = new WebDriverWait(driver, 15)
				.until(ExpectedConditions.elementToBeClickable(By.name("sub")));

		System.out.println("Checkpoint 5 : Verify if old info is displayed...");

		Assert.assertEquals(cusCity.getAttribute("value"), oldCity);
		Assert.assertEquals(custAddr.getText(), oldAddress);

		cusCity.clear();
		cusCity.sendKeys(nwCity);

		custAddr.clear();
		custAddr.sendKeys(nwAddress);

		submitBtn.click();

		// WebElement heading = new WebDriverWait(driver,
		// 15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='heading3']")));
		WebElement updAddress = driver
				.findElement(By.xpath("//table[@id='customer']//td[text()='Address']/following-sibling::td"));
		WebElement updCity = driver
				.findElement(By.xpath("//table[@id='customer']//td[text()='City']/following-sibling::td"));

		// Verify if confirmation message is raised and values are updated.
		// Assert.assertEquals("Customer details updated Successfully!!!",
		// heading.getText());
		System.out.println("Checkpoint 6 : Verify if information is updated...");

		Assert.assertEquals(updAddress.getText(), nwAddress);
		Assert.assertEquals(updCity.getText(), nwCity);

	}

}
