package testNGBasic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, 5);
	String actualUrl = "http://localhost/employee/admin/index.php";

	@BeforeTest()
	public void a() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("http://localhost/employee/admin/index.php");
	}

	@Test(priority = 0)
	public void b() {
		String expectedUrl = driver.getCurrentUrl();
		Assert.assertEquals(expectedUrl, actualUrl);

		driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[1]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[1]/input")).sendKeys("admin");

		driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[2]/input")).clear();
		driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[2]/input")).sendKeys("admin123");

		String username = driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[1]/input")).getAttribute("value");
		String password = driver.findElement(By.xpath("//*[@id=\"b-frm\"]/div[2]/input")).getAttribute("value");

		if (username.equals("admin") && password.equals("admin123")) {

			WebElement bbtn = driver.findElement(By.xpath("//*[@id=\"b-frm\"]/button[1]"));
			bbtn.click();

		} else {
			System.out.println("Username or Password is invalid");
		}

	}

	@Test(dependsOnMethods = "b", priority = 1)
	public void c() {
		String empUrl = "http://localhost/employee/admin/employee.php";

		WebElement employeeLink = driver.findElement(By.id("Employee"));
		employeeLink.click();
		String expectedUrl = driver.getCurrentUrl();
		Assert.assertEquals(expectedUrl, empUrl);
	}

	@Test(dependsOnMethods = "c", priority = 2)
	public boolean d() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement searchbox = driver.findElement(By.xpath("//*[@id=\"table_filter\"]/label/input"));
		searchbox.sendKeys("a");

		String resultCountText = driver.findElement(By.id("table_info")).getText();
		// System.out.println(resultCountText);

		if (resultCountText.contains("Showing 0 to 0 of 0 entries")) {
			System.out.println("data undetected");
			return false;
		} else {
			System.out.println("data detected");
			return true;
		}

	}
	@Test(dependsOnMethods = "c", priority = 3)
	public void e() {

		if (d()) {

			WebElement deleteRow = driver
					.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[7]/center/button[2]"));
			deleteRow.click();

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

			Alert alert = driver.switchTo().alert();
			System.out.println("Employee Delete Alert Message is :" + alert.getText());
			alert.accept();

		} else {
			System.out.println("data undetected");
		}

	}

	@Test(dependsOnMethods = "c", priority = 4)
	public void f() {

		if (d()) {

			WebElement updateRow = driver
					.findElement(By.xpath("//*[@id=\"table\"]/tbody/tr[1]/td[7]/center/button[1]"));
			updateRow.click();

			WebElement closeBtn = driver.findElement(By.xpath("//*[@id=\"new_employee\"]/div/div/div/button"));
			wait.until(ExpectedConditions.visibilityOf(closeBtn));

			closeBtn.click();
		} else {
			System.out.println("empty row result");
		}

	}
	@Test(dependsOnMethods = "c", priority = 5)
	public void g() {
		WebElement g = driver.findElement(By.id("new_emp_btn"));
		g.click();
		
		WebElement closeBtn = driver.findElement(By.xpath("//*[@id=\"new_employee\"]/div/div/div/button"));
		wait.until(ExpectedConditions.visibilityOf(closeBtn));

		closeBtn.click();
		
	}
	
	@Test(dependsOnMethods = "b", priority = 6)
	public void  h() {
		WebElement Logout = driver.findElement(By.xpath("//a[text() = \"Admin Administrator \"]"));
		Logout.click();
	}

	
	 @AfterTest public void i() { driver.close(); }
	 

}
