package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
    @Autowired
    SeleniumActions seleniumActions;

    private By signInLocator = By.className("header_user_info");
    private By signOutLocator = By.className("logout");
    private By searchProductInputLocator = By.id("search_query_top");
    private By submitProductSearchButtonLocator = By.name("submit_search");

    public void navigateToLoginScreen() {
        seleniumActions.clickOnElement(signInLocator);
    }

    public boolean signOutOptionIsDisplayed() {
        return seleniumActions.isElementDisplayed(signOutLocator);
    }

    public boolean signInOptionIsDisplayed() {
        return seleniumActions.isElementDisplayed(signInLocator);
    }

    public HomePage introduceProductToSearchInput(String productName) {
        seleniumActions.fillText(searchProductInputLocator, productName);
        return this;
    }

    public void submitProductSearch() {
        seleniumActions.clickOnElement(submitProductSearchButtonLocator);
    }
}
