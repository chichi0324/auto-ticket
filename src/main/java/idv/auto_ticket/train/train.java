package idv.auto_ticket.train;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class train {
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

	public static void main(String[] args) throws ParseException {
		List<OrderData> datas = new ArrayList<OrderData>();
		datas.add(new OrderData("???", "2019/02/17-07", "004", "100", "6417", "1"));
		datas.add(new OrderData("???", "2019/02/17-07", "004", "100", "6473", "1", "0"));
		datas.add(new OrderData("???", "2019/02/17-07", "004", "100", "6653", "1"));
		datas.add(new OrderData("???", "2019/02/17-07", "004", "100", "6439", "1"));
		datas.add(new OrderData("???", "2019/02/17-07", "004", "100", "6421", "1"));

		int time = 0;

		for (OrderData data : datas) {
			getTicket(data, time);
			try {
				element = driver.findElement(By.tagName("strong"));
				if (!"該區間無剩餘座位".equals(element.getText())) {
					break;
				}
			} catch (Exception e) {
				break;
			}
			time = time + 1;
		}

	}

	public static void getTicket(OrderData data, int times) {
		driver.get(Url);
		driver.findElement(By.id("person_id")).sendKeys(data.getPerson_id());// 身分證
		driver.findElement(By.id("getin_date")).sendKeys(data.getGetin_date());// 乘車日期(ex:2019/02/10【日】)
		driver.findElement(By.id("from_station")).sendKeys(data.getFrom_station());// 起站代碼(ex:051-花蓮)
		driver.findElement(By.id("to_station")).sendKeys(data.getTo_station());// 到站代碼(ex:102-板橋)
		driver.findElement(By.id("train_no")).sendKeys(data.getTrain_no());// 車次代碼(ex:6417)
		if (data.getOrder_qty_str() != null) {
			driver.findElement(By.id("order_qty_str")).sendKeys(data.getOrder_qty_str());// 訂票張數(ex:1)
		} else {
			driver.findElement(By.id("order_qty_str")).click();
			driver.findElement(By.id("n_order_qty_str")).sendKeys(data.getN_order_qty_str());// 訂票張數:一般車廂(普悠瑪)
			driver.findElement(By.id("c_order_qty_str")).sendKeys(data.getC_order_qty_str());// 訂票張數:桌型座位(普悠瑪)
		}

		if (times == 0) {
			// 設定搶票時間
			// DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			// long startTicketTime = format.parse("20190210151800").getTime();
			// long now;
			// do {
			// now = System.currentTimeMillis();
			// } while (startTicketTime != now);
		}

		driver.findElement(By.className("btn")).submit();// 點選"開始訂票"

		// focas在驗證碼框
		jse.executeScript("document.querySelector('#randInput').focus()");
		// 驗證碼框，tab設1
		jse.executeScript("document.querySelector('#randInput').setAttribute('tabindex', 1)");
		// 確認按鈕，tab設2
		jse.executeScript("document.querySelector('#sbutton').setAttribute('tabindex', 2)");

		// 非驗證碼頁面才繼續
		while ("http://railway.hinet.net/Foreign/common/check_etno1.jsp?language=zh_TW"
				.equals(driver.getCurrentUrl())) {

		}
	}

}

