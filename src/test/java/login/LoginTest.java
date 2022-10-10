package login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest {

    @Test
    public void mustLoginSuccessfully() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        WebDriver browser = new EdgeDriver();
        String initialUrl = "http://localhost:8080/login";
        String targetUrl = "http://localhost:8080/leiloes";
        String username = "fulano";
        String password = "pass";

        browser.navigate().to(initialUrl);
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
        browser.findElement(By.id("btn-login")).click();

        String currentUrl = browser.getCurrentUrl();
        Assertions.assertNotEquals(initialUrl, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        String loggedUserUsername = browser.findElement(By.id("logged-user-username")).getText();
        Assertions.assertEquals(username, loggedUserUsername);

        browser.quit();
    }

    @Test
    public void mustFailsBecauseUsernameIsEmpty() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        WebDriver browser = new EdgeDriver();
        String initialUrl = "http://localhost:8080/login";
        String targetUrl = "http://localhost:8080/login?error";
        String username = "";
        String password = "pass";
        String expectedError = "Usuário e senha inválidos.";

        browser.navigate().to(initialUrl);
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
        browser.findElement(By.id("btn-login")).click();

        String currentUrl = browser.getCurrentUrl();
        Assertions.assertNotEquals(initialUrl, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(browser.getPageSource().contains(expectedError));
        String currentError = browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, currentError);
        Assertions.assertThrows(NoSuchElementException.class, () -> browser.findElement(By.id("logged-user-username")));

        browser.quit();
    }

    @Test
    public void shouldFailsBecausePasswordIsEmpty() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        WebDriver browser = new EdgeDriver();
        String username = "fulano";
        String password = "";
        String initialUrl = "http://localhost:8080/login";
        String targetUrl = "http://localhost:8080/login?error";
        String expectedError = "Usuário e senha inválidos.";

        browser.navigate().to(initialUrl);
        browser.findElement(By.id("username")).sendKeys(username);
        browser.findElement(By.id("password")).sendKeys(password);
        browser.findElement(By.id("btn-login")).click();

        String currentUrl = browser.getCurrentUrl();
        Assertions.assertNotEquals(initialUrl, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(browser.getPageSource().contains(expectedError));
        String actualError = browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertThrows(NoSuchElementException.class, () -> browser.findElement(By.id("logged-user-username")));
    }
}
