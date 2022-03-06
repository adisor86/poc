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

    private By noResultFoundMessageLocator = By.cssSelector("*[class ='alert alert-warning']");
    private By returnedListOfProducts = By.xpath("//ul[@class='product_list grid row']//div[@class='product-container']");

    public List<WebElement> retrieveNumberOfProductsFound() {
        return seleniumActions.getDriver().findElements(returnedListOfProducts);
    }

    public String retrieveEmptyProductSearchMessage() {
        return seleniumActions.getTextFromElement(noResultFoundMessageLocator);
    }
}
