package login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest {

    @Test
    public void mustLoginSuccessfully() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        WebDriver browser = new EdgeDriver();
        String initialUrl = "http://localhost:8080/login";
        String targetUrl = "http://localhost:8080/leiloes";

        browser.navigate().to(initialUrl);
        browser.findElement(By.id("username")).sendKeys("fulano");
        browser.findElement(By.id("password")).sendKeys("pass");
        browser.findElement(By.id("btn-login")).click();

        String currentUrl = browser.getCurrentUrl();
        Assertions.assertNotEquals(initialUrl, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        String loggedUserUsername = browser.findElement(By.id("username")).getText();
        Assertions.assertEquals("fulano", loggedUserUsername);

        browser.quit();
    }
}
