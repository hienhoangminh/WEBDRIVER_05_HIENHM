package selenium_api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_05_Custom_Dropdown{

	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait waitTime;

	// 1 - AngularJS
	// Food dropdown list
	By favFood = By.id("mat-select-0");
	By optionLst = By.xpath("//mat-option/span[@class='mat-option-text']");
	By selectedFood = By.xpath("//mat-select[@aria-label='Favorite food']//div[@class='mat-select-value']/span/span");
	// Element for scrolling down to it
	By neighborNode = By
			.xpath("//div[@class='docs-example-viewer-title-spacer' and text()='Select with reset option']");
	// Pokemon list
	By pokemonSelect = By.id("mat-select-6");
	By grassPokemonList = By.xpath("//label[text()='Grass']/../mat-option/span[@class='mat-option-text']");
	By selectedPokemon = By.xpath("//mat-select[@aria-label='Pokemon']//div[@class='mat-select-value']/span/span");

	// 2 - KendoUI
	// Color dropdown list
	By capColor = By.xpath("//span[@class='k-input' and text()='Black']/..");
	By colorOptions = By.xpath("//ul[@id='color_listbox']/li[contains(@class,'k-item')]");
	By selectedColor = By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input']");
	// Size dropdown list
	By capSize = By.xpath("//span[@class='k-input' and contains(text(),'S')]/..");
	By sizeOptions = By.xpath("//ul[@id='size_listbox']/li[contains(@class,'k-item')]");
	By selectedSize = By.xpath("//span[@aria-owns='size_listbox']//span[@class='k-input']");

	// 3 - VueJS
	By item = By.xpath("//li[@class='dropdown-toggle']");
	By itemList = By.xpath("//ul[@class='dropdown-menu']/li");
	By selectedItem = By.xpath("//li[@class='dropdown-toggle']");

	// 4 - Advanced
	By topicName = By.id("the-basics1");

	By selectButton = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/button");

	By monthList = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/div//li//span");
	By firstMonth = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/div//li//span[text()='January']");
	By secondMonth = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/div//li//span[text()='February']");
	By sixthMonth = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/div//li//span[text()='June']");
	By eighthMonth = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/div//li//span[text()='August']");

	By selectedMonth = By.xpath("//p[@id='e1_t']/div[contains(@class,'w300')]/button[@class='ms-choice']/span");

	// 5 - Semantic
	// Single dropdown list
	By input = By.xpath("//div[text()='Select Friend']/..");
	By friendList = By.xpath("//div[@class='default text' and text()='Select Friend']/following-sibling::div/div");
	By selectedFriend = By.xpath(
			"//div[@class='another dropdown example']/div[@class='ui fluid selection dropdown']/div[@class='text']");

	// Multiple select dropdown list
	By tpicName = By.xpath("//h4[text()='Multiple Selection']");

	By skillSelection = By.xpath(
			"//p[text()='A selection dropdown can allow multiple selections']/following-sibling::div/i[@class='dropdown icon']");
	By skillList = By.xpath("//div[@class='menu transition visible']/div[@class='item']");

	By firstSelectedSkill = By.xpath("//a[@class='ui label transition visible'][1]");
	By secondSelectedSkill = By.xpath("//a[@class='ui label transition visible'][2]");
	By thirdSelectedSkill = By.xpath("//a[@class='ui label transition visible'][3]");

	// 6 - Indrimuska
	By parentNode = By.xpath("//div[text()='Into this']");

	By selectInput = By.xpath("//div[text()='Default']/following-sibling::div/input");
	By carSelect = By.xpath("//div[text()='Default']/following-sibling::div/ul");
	By tagName = By.tagName("li");
    String jQuerySelector = "#default-place>input";
    
	@BeforeTest
	public void setUp() throws Exception {
		// System.setProperty("webdriver.chrome.driver", "drivers//chromedriver");
		// driver = new ChromeDriver();
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		waitTime = new WebDriverWait(driver, 60);
	}

	@Test
	public void TC_01_AngularJS_Dropdown() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");

		selectOptionByValue(favFood, optionLst, selectedFood, "Tacos");
		Thread.sleep(3000);

		JsScrollToElement(driver.findElement(neighborNode));

		selectOptionByValue(pokemonSelect, grassPokemonList, selectedPokemon, "Bellsprout");
		Thread.sleep(3000);

	}

	@Test
	public void TC_02_KendoUI_Dropdown() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");

		selectOptionByValue(capColor, colorOptions, selectedColor, "Orange");
		Thread.sleep(3000);

		selectOptionByValue(capSize, sizeOptions, selectedSize, "M - 7 1/4\"");
		Thread.sleep(3000);

	}

	@Test
	public void TC_03_VueJs_Dropdown() throws Exception {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");

		selectOptionByValue(item, itemList, selectedItem, "Third Option");
		Thread.sleep(3000);
	}

	@Test
	public void TC_04_WenZhiXin_Multiple_Dropdown() throws Exception {
		driver.get("http://wenzhixin.net.cn/p/multiple-select/docs/");

		JsScrollToElement(driver.findElement(topicName));
		Thread.sleep(3000);

		selectMultipleOptionByValueList(selectButton, monthList, selectedMonth, "January, February, June", ", ",
				"January, February, June");
		Thread.sleep(3000);

		selectMultipleOptionByValueList(selectButton, monthList, selectedMonth, "August", ",", "4 of 12 selected");
		Thread.sleep(3000);

	}

	@Test
	public void TC_05_SemanticUI_Multiple_Dropdown() throws Exception {
		driver.get("https://semantic-ui.com/modules/dropdown.html");

		JsScrollToElement(driver.findElement(input));
		Thread.sleep(3000);

		selectOptionByValue(input, friendList, selectedFriend, "Justen Kitsune");
		Thread.sleep(3000);

		selectMultipleOptionByValue(tpicName, skillSelection, skillList, firstSelectedSkill, "Angular");
		selectMultipleOptionByValue(tpicName, skillSelection, skillList, secondSelectedSkill, "NodeJS");
		selectMultipleOptionByValue(tpicName, skillSelection, skillList, thirdSelectedSkill, "UI Design");

		// Add 5s to wait for show

		Thread.sleep(5000);

	}

	@Test
	public void TC_06_Indrimuska_Dropdown() throws Exception {
		driver.get("http://indrimuska.github.io/jquery-editable-select/");

		selectOptionForEditableDropdown(selectInput, carSelect, tagName,jQuerySelector, "Land Rover");

	}

	@AfterTest
	public void tearDown() throws Exception {
		driver.quit();
	}

	public void selectOptionByValue(By inputBy, By optionsBy, By actualBy, String value) {
		// Click on element
		clickToExpandOrFoldDropdownList(inputBy);

		List<WebElement> elementList = driver.findElements(optionsBy);
		// Wait until we get all values inside
		List<WebElement> lstOptions = waitTime.until(ExpectedConditions.visibilityOfAllElements(elementList));

		// Loop through the list of retrieved element
		for (WebElement el : lstOptions) {
			if (el.getText().trim().equalsIgnoreCase(value)) {
				el.click();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		// Verify if right value is selected.
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(actualBy)).getText(), value);
	}

	public void selectMultipleOptionByValue(By scrolllBy, By inputBy, By optionsBy, By actualBy, String value) {
		// Click on element
		clickToExpandOrFoldDropdownList(inputBy);

		List<WebElement> elementList = driver.findElements(optionsBy);
		// Wait until we get all values inside
		List<WebElement> lstOptions = waitTime.until(ExpectedConditions.visibilityOfAllElements(elementList));

		// Loop through the list of retrieved element
		for (WebElement el : lstOptions) {
			if (el.getText().trim().equalsIgnoreCase(value)) {
				el.click();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		clickToExpandOrFoldDropdownList(inputBy);

		// Verify if right value is selected.
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(actualBy)).getText(), value);

		JsScrollToElement(driver.findElement(scrolllBy));

	}

	public void selectOptionForEditableDropdown(By inputBy, By selectBy, By tag, String cssSel,String value) {

		clickToExpandOrFoldDropdownList(inputBy);

		WebElement select = driver.findElement(selectBy);

		List<WebElement> options = waitTime.until(ExpectedConditions.visibilityOfAllElements(select.findElements(tag)));

		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(value)) {
				option.click();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		// Scroll to top
		JsScrollToTop();
		
		String s = "return $('%s').val();";
		Assert.assertEquals((String) js.executeScript(String.format(s, cssSel)), value);

	}

	public void selectMultipleOptionByValueList(By inputBy, By optionsBy, By actualBy, String value, String delimeter,
			String expectedResult) {
		clickToExpandOrFoldDropdownList(inputBy);

		List<WebElement> elementList = driver.findElements(optionsBy);
		// Wait until we get all values inside
		List<WebElement> lstOptions = waitTime.until(ExpectedConditions.visibilityOfAllElements(elementList));

		String[] valList = value.split(delimeter);
		// Loop through the list of retrieved element
		for (String val : valList) {
			for (WebElement el : lstOptions) {
				// scrollToElementLocation(el);
				if (el.getText().equalsIgnoreCase(val)) {
					el.click();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;

				}
			}
		}
		// Verify if we selected correct value
		Assert.assertEquals(waitTime.until(ExpectedConditions.visibilityOfElementLocated(actualBy)).getText(),
				expectedResult);

		clickToExpandOrFoldDropdownList(inputBy);
	}

	public void clickToExpandOrFoldDropdownList(By inputBy) {
		WebElement inputToClick = waitTime.until(ExpectedConditions.elementToBeClickable(inputBy));
		inputToClick.click();
	}

	public void JsScrollToElement(WebElement el) {
		js.executeScript("arguments[0].scrollIntoView();", el);
	}

	public void JsScrollToTop() {
		js.executeScript("document.documentElement.scrollTop = 0;");
	}

}
