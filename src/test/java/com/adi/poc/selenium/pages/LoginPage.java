package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginPage {
    @Autowired
    SeleniumActions seleniumActions;
    @Autowired
    HomePage homePage;

    private By usernameInputLocator = By.id("email");
    private By passwordInputLocator = By.id("passwd");
    private By signInButtonLocator = By.id("SubmitLogin");

    public LoginPage enterUsername(String username) {
        seleniumActions.fillText(usernameInputLocator, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        seleniumActions.fillText(passwordInputLocator, password);
        return this;
    }

    public void clickSignInButton() {
        seleniumActions.clickOnWebElement(signInButtonLocator);
    }


    public void doLogin(String username, String password) {
        homePage.navigateToLoginScreen();
        enterUsername(username)
                .enterPassword(password)
                .clickSignInButton();
    }
}
