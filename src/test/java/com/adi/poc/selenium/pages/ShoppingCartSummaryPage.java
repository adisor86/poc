package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShoppingCartSummaryPage {
    @Autowired
    SeleniumActions seleniumActions;

    private By deleteIconLocator = By.xpath("//td[@data-title='Delete']");
    private By cartItems = By.xpath("//tr[contains(@id,'product')]");

    public void removeItemFromCartSummaryFromGivenPosition(int position) {
        List<WebElement> deleteIcons = seleniumActions.getWebElements(deleteIconLocator);
        System.out.println("About to delete product item");
        seleniumActions.clickOnWebElement(deleteIcons.get(position));
    }

    public List<WebElement> retrieveExistingItemsFromCartSummary() {
        return seleniumActions.getWebElements(cartItems);
    }
}
