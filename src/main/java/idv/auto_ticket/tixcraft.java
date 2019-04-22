package idv.auto_ticket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class tixcraft {
	private static WebDriver driver;
	private static WebElement element;
	private static List<WebElement> areas;
	private static JavascriptExecutor jse;
	private static String activePageUrl;
	private static String areaUrl;
	private static int areaNo;
	static {
		// System.setProperty("webdriver.chrome.driver",
		// "driver/chromedriver_win.exe");// 導入chromedriver for window
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver_mac");// 導入chromedriver
																					// for
																					// mac

		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--start-maximized");//windows
		// chromeOptions.addArguments("--kiosk");//macOS
		// driver = new ChromeDriver(chromeOptions);
		driver = new ChromeDriver();

		areas = new ArrayList<WebElement>();
		jse = (JavascriptExecutor) driver;
		activePageUrl = "https://tixcraft.com/activity/detail/19_Aimer";
		areaNo = 0;
		driver.get("https://tixcraft.com");// 拓元網站

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		activePageUrl = activePageUrl.replace("detail", "game");
		driver.get("https://tixcraft.com/login/facebook");// fb登入

		// *****************登入畫面**********************
		driver.findElement(By.id("email")).sendKeys("");// 帳號
		driver.findElement(By.id("pass")).sendKeys("");// 密碼
		driver.findElement(By.id("loginbutton")).submit();// 點選"登入"
		try {// 已是會員的情況，只輸入密碼
			driver.findElement(By.xpath("//*[@id='u_0_u']/div/div[2]/table/tbody/tr[1]/td/input"))
					.sendKeys("");// 密碼
			driver.findElement(By.xpath("//*[@id='u_0_3']")).submit();
			;
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
		// ******************活動畫面********************
		driver.get(activePageUrl);

		// 設定搶票時間
		// DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// long startTicketTime=format.parse("20190217150000").getTime();
		// long now;
		// do{
		// now=System. currentTimeMillis();
		// }while(startTicketTime!=now);

		// 若時間寫得太慢，頁面未出現立即訂購按鈕，過濾exception，再refresh在點擊一次
		while (true) {
			try {
				driver.get(activePageUrl);// 重新進入演唱會"購票頁面
				driver.findElements(By.className("btn-next")).get(areaNo).click();// 演唱會場次，點選"立即訂購"

//				getContract();// 若有買賣契約()

				// https://tixcraft.com/ticket/area/18_IU/5149
				// https://tixcraft.com/ticket/verify/18_IU/5149
				areaUrl = driver.getCurrentUrl().replace("verify", "area");
				break;
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}
		}
		// ******************選擇座位畫面********************

		do {
			getFansQuestion(element,jse);
			// 搶票過程中若被系統踢出來，自動點擊"立即訂購"
			try {
				if (activePageUrl.equals(driver.getCurrentUrl())) {
					driver.findElements(By.className("btn-next")).get(areaNo).click();// 點選"立即訂購"

					getContract();// 若有買賣契約()

				}
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

			// 若驗證碼打錯或該區域沒票跳出alert訊息
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				choiceTicketNo(driver, element, areas, jse);// 驗證碼打錯再選一次張數和點擊同意
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

			// -----自動選擇張數和點擊同意-----
			try {
				choiceTicketNo(driver, element, areas, jse);
			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

			// 自動選擇區域
			try {

				System.out.println("current url:" + driver.getCurrentUrl());
				System.out.println("areaUrl url:" + areaUrl);

				if (areaUrl.equals(driver.getCurrentUrl())) {
					// *********看狀況選擇區域為自動或手動**********
					// 方法1：自動-> choiceArea
					choiceArea(driver, element, areas, jse, areaUrl);
					// 方法2：手動-> choiceAreaWithoutTicket
					//choiceAreaWithoutTicket(driver, element, areas, jse, areaUrl);
				}

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
			System.out.println("****");
		} while (true);

	}

	// ******************選擇區域畫面********************
	public static void choiceArea(WebDriver driver, WebElement element, List<WebElement> areas, JavascriptExecutor jse,
			String url) {
		do {
			driver.get(url);
			areas = driver.findElement(By.className("area-list")).findElements(By.tagName("a"));
			System.out.println("**********數量：" + areas.size());
			if (areas.size() > 0) {
				int index = 0;

				// for (int i=0;i<areas.size();i++) {
				// String sit=areas.get(i).getText();
				// System.out.println(sit);
				// if(sit.indexOf("熱")!=-1){ //熱賣中
				// index=i; //若是熱賣中直接點擊，熱賣中第一區的index
				// }else if(sit.indexOf("剩")!=-1){ //剩餘 XX位子
				// int
				// number=Integer.parseInt(sit.substring((sit.indexOf("剩")+3),
				// sit.length()));
				// if(number>10){ //找出剩餘大於10位置的第一區的index
				// index=i;
				// break;
				// }
				// }
				// }
				areas.get(index).click();
				choiceTicketNo(driver, element, areas, jse);// 自動選擇張數和點擊同意
			}
			System.out.println("**area**");
		} while (areas.size() <= 0);
	}

	public static void choiceAreaWithoutTicket(WebDriver driver, WebElement element, List<WebElement> areas,
			JavascriptExecutor jse, String url) {
		areas = driver.findElement(By.className("area-list")).findElements(By.tagName("a"));
		while (areas.size() <= 0) {
			driver.get(url);
			areas = driver.findElement(By.className("area-list")).findElements(By.tagName("a"));
			System.out.println("**********數量：" + areas.size());
		}
	}

	// ******************選擇張數畫面********************
	public static void choiceTicketNo(WebDriver driver, WebElement element, List<WebElement> areas,
			JavascriptExecutor jse) {

		// 選擇張數(選一張)
		element = driver.findElement(By.className("mobile-select"));
		element.click();
		element.sendKeys("1");

		// 勾選同意
		driver.findElement(By.id("TicketForm_agree")).click();

		// focas在驗證碼
		jse.executeScript("$('#TicketForm_verifyCode').focus()", element);

		do {
			element = driver.findElement(By.id("TicketForm_verifyCode"));
			System.out.println("TicketForm:" + element.getAttribute("value"));
		} while (element.getAttribute("value").length() != 4);
		driver.findElement(By.id("ticketPriceSubmit")).click();

	}

	// ******************買賣契約填寫********************
	public static void getContract() {
		if (driver.getCurrentUrl().contains("verify") && "買賣契約書：".equals(
				driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[2]/div/form/div[1]/h4")).getText())) {
			try {
				driver.findElement(By.id("checkCode"))
						.sendKeys(driver
								.findElement(By.xpath("/html/body/div/div[2]/div/div/div[2]/div/form/div[1]/div/font"))
								.getText());
				driver.findElement(By.id("submitButton")).click();
				// 若驗證碼打錯或該區域沒票跳出alert訊息
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// ******************focas粉絲答題輸入匡********************
	public static void getFansQuestion(WebElement element,JavascriptExecutor jse) {
		if (driver.getCurrentUrl().contains("verify") && "買賣契約書：".equals(
				driver.findElement(By.xpath("/html/body/div/div[2]/div/div/div[2]/div/form/div[1]/h4")).getText())) {
			try {
				// focas在粉絲答題匡
				jse.executeScript("$('#checkCode').focus()", element);
				while (driver.getCurrentUrl().contains("verify")){
					
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
