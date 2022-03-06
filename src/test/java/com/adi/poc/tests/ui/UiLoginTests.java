package com.adi.poc.tests.ui;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.HomePage;
import com.adi.poc.selenium.pages.LoginPage;
import com.adi.poc.selenium.pages.ValidationComponentPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UiLoginTests {
    @Autowired
    private LoginPage loginPage;
    @Autowired
    private HomePage homePage;
    @Autowired
    private ValidationComponentPage validationPage;
    @Autowired
    private SeleniumActions seleniumActions;

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    @Value("${url.uiBaseurl}")
    private String url;

    @Test
    void performLoginWithValidCredentialsTest() {
        System.out.println("Test about to validate login functionality while using valid credentials.");
        homePage.navigateToLoginScreen();
        loginPage.enterUsername(username)
                .enterPassword(password)
                .clickSignInButton();
        Assertions.assertTrue(homePage.signOutOptionIsDisplayed());
    }

    @Test
    void performLoginWithInvalidUsernameTest() {
        System.out.println("Test about to validate login functionality while using invalid username.");
        homePage.navigateToLoginScreen();
        loginPage.enterUsername("invalid@email")
                .enterPassword(password)
                .clickSignInButton();
        Assertions.assertEquals("There is 1 error\nInvalid email address.", validationPage.retrieveTextFromValidationAlert());
        Assertions.assertTrue(homePage.signInOptionIsDisplayed(), "Login is performed though invalid username was provided.");
    }

    @Test
    void performLoginWithInvalidPasswordTest() {
        System.out.println("Test about to validate login functionality while using invalid password.");
        homePage.navigateToLoginScreen();
        loginPage.enterUsername(username)
                .enterPassword(password.concat("test"))
                .clickSignInButton();
        Assertions.assertEquals("There is 1 error\nAuthentication failed.", validationPage.retrieveTextFromValidationAlert());
        Assertions.assertTrue(homePage.signInOptionIsDisplayed(), "Login is performed though invalid password was provided.");
    }

    @BeforeEach
    private void setup() {
        seleniumActions.navigateTo(url);
    }

    @AfterEach
    private void quitDriver() {
        seleniumActions.quitDriver();
    }
}
