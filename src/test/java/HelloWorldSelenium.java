import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class HelloWorldSelenium {

    @Test
    public void hello() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        //WebDriver browser = new ChromeDriver();

        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        WebDriver browser = new EdgeDriver();
        browser.navigate().to("http://localhost:8080/leiloes");
        browser.quit();
    }
}
