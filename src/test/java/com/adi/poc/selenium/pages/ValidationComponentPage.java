package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationComponentPage {
    @Autowired
    private SeleniumActions seleniumActions;

    private By authenticationFailedLocator = By.cssSelector("div[class='alert alert-danger']");
    private By noResultFoundMessageLocator = By.cssSelector("*[class ='alert alert-warning']");

    public String retrieveTextFromValidationAlert() {
        return seleniumActions.getTextFromElement(authenticationFailedLocator);
    }

    public String retrieveTextFromEmptySearchResultWarning() {
        return seleniumActions.getTextFromElement(noResultFoundMessageLocator);
    }
}
