package idv.auto_ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class kktix {
	private static WebDriver driver;
	private static WebElement element;
	private static List<WebElement> areas;
	private static JavascriptExecutor jse;
	private static String areaUrl;
	private static int number;
	static {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");// 導入chromedriver

		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--start-maximized");//windows
		// chromeOptions.addArguments("--kiosk");//macOS
		// driver = new ChromeDriver(chromeOptions);
		driver = new ChromeDriver();
		areas = new ArrayList<WebElement>();
		jse = (JavascriptExecutor) driver;
		areaUrl = "https://kktix.com/events/2w9et2aw-kaohsiung/registrations/new";// 區域頁面
		number = 2;
		driver.get("https://kktix.com/");// kktix網站

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String parentWindowId = driver.getWindowHandle();
		driver.get("https://kktix.com/users/sign_in?back_to=https%3A%2F%2Fkktix.com%2F");// 登入
		driver.findElement(By.id("fb-login")).click();
		Thread.sleep(2000);

		// *****************登入畫面**********************
		Set<String> allWindowsId = driver.getWindowHandles();
		// 所有的視窗選擇登入的視窗
		for (String windowId : allWindowsId) {
			System.out.println("driver.getTitle(): " + driver.switchTo().window(windowId).getTitle());
			if (driver.switchTo().window(windowId).getTitle().contains("登入 Facebook | Facebook")) {
				driver.switchTo().window(windowId);
				break;
			}
		}
		Thread.sleep(500);
		driver.findElement(By.id("email")).sendKeys("???");// 帳號
		driver.findElement(By.id("pass")).sendKeys("???");// 密碼
		driver.findElement(By.id("loginbutton")).submit();// 點選"登入"

		// if (driver.getWindowHandles().size() == 2) {
		// Thread.sleep(3000);// clan044clue238
		// }

		// 回到母視窗
		driver.switchTo().window(parentWindowId);

		System.out.println("parentWindowId: " + driver.getTitle());

		// ******************選擇區域畫面********************
		driver.get(areaUrl);

		// 設定搶票時間
		// DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// long startTicketTime = format.parse("20190209120000").getTime();
		// long now;
		// do {
		// now = System.currentTimeMillis();
		// } while (startTicketTime != now);

		driver.get(areaUrl);

		while (true) {
			try {
				if (areaUrl.equals(driver.getCurrentUrl())) {
					choiceArea(driver);
				}
				// if(areas.size() > 0){
				// break;
				// }
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

			// 若驗證碼打錯或該區域沒票跳出alert訊息
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

			// 若關閉瀏覽器，關閉及釋放driver，跳出迴圈
			try {
				driver.getWindowHandle();
				driver.getWindowHandles().size();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				driver.quit();
				System.exit(1);
				break;
			}
			System.out.println("*******");
		}
	}

	// *****************選擇區域畫面********************
	public static void choiceArea(WebDriver driver) throws InterruptedException {

		driver.get(areaUrl);
		Thread.sleep(500);
		// 選擇區域及張數
		areas = driver.findElements(By.className("plus"));
		System.out.println("button size:" + areas.size());
		if (areas.size() > 0) {
			for (int i = 0; i < number; i++) {
				areas.get(0).click();// 預設第一個位置
			}
		}

		// 勾選同意
		driver.findElement(By.id("person_agree_terms")).click();

		// **********************若有粉絲答題，需手動填寫及點選下一步*******
		try {
			element = driver.findElement(By.name("captcha_answer"));
			// element.sendKeys("D4"); //粉絲答題答案
			focasAndTab();

			while (areaUrl.equals(driver.getCurrentUrl())) {
				if ("答案錯誤，再試一次！".equals(driver.findElement(By.name("captcha_answer")).getAttribute("placeholder"))
						&& "".equals(element.getAttribute("value"))) {
					focasAndTab();
				}
			}
		} catch (Exception e) {
			// **********************無粉絲答題*********************

			try {
				driver.findElement(By.xpath("//*[@id='registrationsNewApp']/div/div[5]/div[5]/button")).click();
			} catch (Exception e1) {
				// 只有一種價位，"下一步"的位置往前一個div，可能會exception
				driver.findElement(By.xpath("//*[@id='registrationsNewApp']/div/div[5]/div[4]/button")).click();
			}

			Thread.sleep(5000);
		}

	}

	public static void focasAndTab() {

		try {
			// focas在粉絲答題框
			jse.executeScript(
					"document.querySelector('#registrationsNewApp > div > div:nth-child(5) > div.captcha.ng-scope > div > div > div > div > div > div > div > input').focus()");
			// 粉絲答題框，tab設1
			jse.executeScript(
					"document.querySelector('#registrationsNewApp > div > div:nth-child(5) > div.captcha.ng-scope > div > div > div > div > div > div > div > input').setAttribute('tabindex', 1)");
			// 下一步按鈕，tab設2
			jse.executeScript(
					"document.querySelector('#registrationsNewApp > div > div:nth-child(5) > div.form-actions.plain.align-center > button').setAttribute('tabindex', 2)");

		} catch (Exception e) {
		}
	}
}
