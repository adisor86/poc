package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SearchResultPage {
    @Autowired
    SeleniumActions seleniumActions;
    @Autowired
    SuccessfulShoppingCartConfirmationPage shoppingCartConfirmationPage;

    private By noResultFoundMessageLocator = By.cssSelector("*[class ='alert alert-warning']");
    private By returnedListOfProducts = By.xpath("//ul[@class='product_list grid row']//div[@class='product-container']");
    private By addToCartButton = By.xpath("//div[@class='button-container']//*[@title='Add to cart']");
    private By moreButton = By.xpath("//div[@class='button-container']//*[@title='View']");

    public List<WebElement> retrieveNumberOfProductsFound() {
        return seleniumActions.getWebElements(returnedListOfProducts);
    }

    public String retrieveEmptyProductSearchMessage() {
        return seleniumActions.getTextFromElement(noResultFoundMessageLocator);
    }

    public SuccessfulShoppingCartConfirmationPage addFirstProductItemToCart() {
        seleniumActions.mouseHoverOverElementAndClickOption(seleniumActions.getWebElements(returnedListOfProducts).get(0), seleniumActions.getWebElement(addToCartButton));
        return shoppingCartConfirmationPage;
    }

    public void viewMore() {
        seleniumActions.clickOnWebElement(moreButton);
    }
}
