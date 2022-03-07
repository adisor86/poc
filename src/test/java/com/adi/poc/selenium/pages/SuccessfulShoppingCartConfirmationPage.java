package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulShoppingCartConfirmationPage {
    @Autowired
    SeleniumActions seleniumActions;
    @Autowired
    HomePage homePage;
    @Autowired
    ShoppingCartSummaryPage shoppingCartSummaryPage;

    private By continueShoppingButtonLocator = By.xpath("//div[@class='button-container']//*[@title='Continue shopping']");
    private By proceedToCheckoutButtonLocator = By.xpath("//div[@class='button-container']//*[@title='Proceed to checkout']");

    public HomePage selectContinueShoppingOption() {
        seleniumActions.clickOnWebElement(continueShoppingButtonLocator);
        return homePage;
    }

    public ShoppingCartSummaryPage selectProceedToCheckoutOption() {
        seleniumActions.clickOnWebElement(proceedToCheckoutButtonLocator);
        return shoppingCartSummaryPage;
    }
}
