package idv.auto_ticket;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class thsrc {
	private static WebDriver driver;
	private static WebElement element;
	private static List<WebElement> areas;
	private static JavascriptExecutor jse;
	private static String Url;
	private static int number;
	static {
//		System.setProperty("webdriver.chrome.driver", "driver/chromedriver_win.exe");// 導入chromedriver for window
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver_mac");// 導入chromedriver for mac


		// ChromeOptions chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--start-maximized");//windows
		// chromeOptions.addArguments("--kiosk");//macOS
		// driver = new ChromeDriver(chromeOptions);
		driver = new ChromeDriver();
		areas = new ArrayList<WebElement>();
		jse = (JavascriptExecutor) driver;
		Url = "http://railway.hinet.net/Foreign/TW/etno1.html";// 訂票頁面
		number = 2;
		driver.get(Url);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		http://www.thsrc.com.tw/tw/TimeTable/SearchResult
//		https://irs.thsrc.com.tw/IMINT/
	}

}
