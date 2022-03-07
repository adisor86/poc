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
    private By cartLocator = By.xpath("//div[@class='shopping_cart']//a[@title='View my shopping cart']");
    private By womenMainFilter = By.xpath("//a[@title='Women']");

    public void navigateToLoginScreen() {
        seleniumActions.clickOnWebElement(signInLocator);
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
        seleniumActions.clickOnWebElement(submitProductSearchButtonLocator);
    }

    public void clickOnWomenMainFilterOption() {
        seleniumActions.clickOnWebElement(womenMainFilter);
    }

    public void openShoppingCartSummary() {
        seleniumActions.clickOnWebElement(cartLocator);
    }

}
