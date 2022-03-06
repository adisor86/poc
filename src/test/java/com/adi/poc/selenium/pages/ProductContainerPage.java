package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductContainerPage {
    @Autowired
    SeleniumActions seleniumActions;

    private By addToCartButton = By.xpath("//div[@class='button-container']//*[@title='Add to cart']");
    private By moreButton = By.xpath("//div[@class='button-container']//*[@title='View']");

    public void addProductToCart() {
        seleniumActions.clickOnElement(addToCartButton);
    }

    public void viewMore() {
        seleniumActions.clickOnElement(moreButton);
    }
}
