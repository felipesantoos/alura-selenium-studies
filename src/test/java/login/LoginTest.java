package login;

import org.junit.jupiter.api.*;

public class LoginTest {
    private LoginPage loginPage;

    @AfterAll
    public static void afterAll() {
        LoginPage.configureEnvironmentVariables();
    }

    @BeforeEach
    public void beforeEach() {
        this.loginPage = new LoginPage();
    }

    @AfterEach
    public void afterEach() {
        this.loginPage.close();
    }

    @Test
    public void mustLoginSuccessfully() {
        String targetUrl = "http://localhost:8080/leiloes";
        String username = "fulano";
        String password = "pass";

        this.loginPage.fillForm(username, password);
        this.loginPage.clickOnLoginButton();

        Assertions.assertNotEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(targetUrl, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(username, this.loginPage.getLoggedUsername());
    }

    @Test
    public void mustFailsBecauseUsernameIsEmpty() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "";
        String password = "pass";
        String expectedError = "Usuário e senha inválidos.";

        this.loginPage.fillForm(username, password);
        this.loginPage.clickOnLoginButton();

        Assertions.assertNotEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(targetUrl, this.loginPage.getCurrentUrl());
        Assertions.assertTrue(this.loginPage.getPageSource().contains(expectedError));
        String currentError = this.loginPage.getElementTextByClassName("alert-danger");
        Assertions.assertEquals(expectedError, currentError);
        Assertions.assertFalse(this.loginPage.isElementFoundedById("logged-user-username"));
    }

    @Test
    public void shouldFailsBecausePasswordIsEmpty() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "fulano";
        String password = "";
        String expectedError = "Usuário e senha inválidos.";

        this.loginPage.fillForm(username, password);
        this.loginPage.clickOnLoginButton();

        Assertions.assertNotEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(targetUrl, this.loginPage.getCurrentUrl());
        Assertions.assertTrue(this.loginPage.getPageSource().contains(expectedError));
        String actualError = this.loginPage.getElementTextByClassName("alert-danger");
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertFalse(this.loginPage.isElementFoundedById("logged-user-username"));
    }

    @Test
    public void shouldFailsBecauseUsernameIsNotFound() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "nonexistent-username";
        String password = "pass";
        String expectedError = "Usuário e senha inválidos.";

        this.loginPage.fillForm(username, password);
        this.loginPage.clickOnLoginButton();

        Assertions.assertNotEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(targetUrl, this.loginPage.getCurrentUrl());
        Assertions.assertTrue(this.loginPage.getPageSource().contains(expectedError));
        String actualError = this.loginPage.getElementTextByClassName("alert-danger");
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertFalse(this.loginPage.isElementFoundedById("logged-user-username"));
    }

    @Test
    public void shouldFailsBecausePasswordIsWrong() {
        String targetUrl = "http://localhost:8080/login?error";
        String username = "fulano";
        String password = "wrong-password";
        String expectedError = "Usuário e senha inválidos.";

        this.loginPage.fillForm(username, password);
        this.loginPage.clickOnLoginButton();

        Assertions.assertNotEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(targetUrl, this.loginPage.getCurrentUrl());
        Assertions.assertTrue(this.loginPage.getPageSource().contains(expectedError));
        String actualError = this.loginPage.getElementTextByClassName("alert-danger");
        Assertions.assertEquals(expectedError, actualError);
        Assertions.assertFalse(this.loginPage.isElementFoundedById("logged-user-username"));
    }

    @Test
    public void shouldNotAccessRestrictPageWithoutLoginBefore() {
        String restrictUrl = "http://localhost:8080/leiloes/2";

        this.loginPage.redirectTo(restrictUrl);

        Assertions.assertNotEquals(restrictUrl, this.loginPage.getCurrentUrl());
        Assertions.assertEquals(LoginPage.LOGIN_URL, this.loginPage.getCurrentUrl());

        Assertions.assertFalse(this.loginPage.getPageSource().contains("Dados do Leilão"));
    }
}
