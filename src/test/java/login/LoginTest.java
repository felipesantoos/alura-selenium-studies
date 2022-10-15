package login;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest {

    private static final String URL_LOGIN = "http://localhost:8080/login";
    private WebDriver browser;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
    }

    @BeforeEach
    public void beforeEach() {
        this.browser = new EdgeDriver();
        this.browser.navigate().to(URL_LOGIN);
    }

    @AfterEach
    public void afterEach() {
        this.browser.quit();
    }

    @Test
    public void mustLoginSuccessfully() {
        String targetUrl = "http://localhost:8080/leiloes";
        String username = "fulano";
        String password = "pass";

        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
        this.browser.findElement(By.id("btn-login")).click();

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(URL_LOGIN, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        String loggedUserUsername = this.browser.findElement(By.id("logged-user-username")).getText();
        Assertions.assertEquals(username, loggedUserUsername);
    }

    @Test
    public void mustFailsBecauseUsernameIsEmpty() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "";
        String password = "pass";
        String expectedError = "Usuário e senha inválidos.";

        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
        this.browser.findElement(By.id("btn-login")).click();

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(URL_LOGIN, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(this.browser.getPageSource().contains(expectedError));
        String currentError = this.browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, currentError);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> this.browser.findElement(By.id("logged-user-username")));
    }

    @Test
    public void shouldFailsBecausePasswordIsEmpty() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "fulano";
        String password = "";
        String expectedError = "Usuário e senha inválidos.";

        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
        this.browser.findElement(By.id("btn-login")).click();

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(URL_LOGIN, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(this.browser.getPageSource().contains(expectedError));
        String actualError = this.browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> this.browser.findElement(By.id("logged-user-username")));
    }

    @Test
    public void shouldFailsBecauseUsernameIsNotFound() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "nonexistent-username";
        String password = "pass";
        String expectedError = "Usuário e senha inválidos.";

        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
        this.browser.findElement(By.id("btn-login")).click();

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(URL_LOGIN, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(this.browser.getPageSource().contains(expectedError));
        String actualError = this.browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> this.browser.findElement(By.id("logged-user-username")));
    }

    @Test
    public void shouldFailsBecausePasswordIsWrong() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "fulano";
        String password = "wrong-password";
        String expectedError = "Usuário e senha inválidos.";

        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
        this.browser.findElement(By.id("btn-login")).click();

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(URL_LOGIN, currentUrl);
        Assertions.assertEquals(targetUrl, currentUrl);
        Assertions.assertTrue(this.browser.getPageSource().contains(expectedError));
        String actualError = this.browser.findElement(By.className("alert-danger")).getText();
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> this.browser.findElement(By.id("logged-user-username")));
    }

    @Test
    public void shouldNotAccessRestrictPageWithoutLoginBefore() {
        String initialUrl = "http://localhost:8080/leiloes/2";

        this.browser.navigate().to(initialUrl);

        String currentUrl = this.browser.getCurrentUrl();
        Assertions.assertNotEquals(initialUrl, currentUrl);
        Assertions.assertEquals(URL_LOGIN, currentUrl);
        Assertions.assertFalse(this.browser.getPageSource().contains("Dados do Leilão"));
    }
}
