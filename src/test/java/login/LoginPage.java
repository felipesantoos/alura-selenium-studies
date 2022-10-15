package login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginPage {

    public static final String LOGIN_URL = "http://localhost:8080/login";
    private final WebDriver browser;

    public LoginPage() {
        configureEnvironmentVariables();
        this.browser = new EdgeDriver();
        this.browser.navigate().to(LOGIN_URL);
    }

    public static void configureEnvironmentVariables() {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
    }

    public void fillForm(String username, String password) {
        System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
        this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
    }

    public void clickOnLoginButton() {
        this.browser.findElement(By.id("btn-login")).click();
    }

    public String getCurrentUrl() {
        return this.browser.getCurrentUrl();
    }

    public String getLoggedUsername() {
        return this.browser.findElement(By.id("logged-user-username")).getText();
    }

    public String getPageSource() {
        return this.browser.getPageSource();
    }

    public String getElementTextByClassName(String className) {
        return this.browser.findElement(By.className(className)).getText();
    }

    public boolean isElementFoundedById(String id) {
        try {
            this.browser.findElement(By.id(id));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void redirectTo(String url) {
        this.browser.navigate().to(url);
    }

    public void close() {
        this.browser.quit();
    }
}
