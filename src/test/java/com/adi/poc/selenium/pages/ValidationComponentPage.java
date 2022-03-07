package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Component
public class ValidationComponentPage {
    @Autowired
    private SeleniumActions seleniumActions;

    private By authenticationFailedLocator = By.cssSelector("div[class='alert alert-danger']");
    private By noResultFoundMessageLocator = By.cssSelector("*[class ='alert alert-warning']");

    public String retrieveTextFromValidationAlert() {
        waitToLoadValidationMessage(authenticationFailedLocator);
        return seleniumActions.getTextFromElement(authenticationFailedLocator);
    }

    public String retrieveEmptyCartWarningMessage() {
        waitToLoadValidationMessage(noResultFoundMessageLocator);
        return seleniumActions.getTextFromElement(noResultFoundMessageLocator);
    }

    private void waitToLoadValidationMessage(By by) {
        await().atMost(5, TimeUnit.SECONDS)
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .until(() -> !seleniumActions.getTextFromElement(by).isEmpty());
    }
}
