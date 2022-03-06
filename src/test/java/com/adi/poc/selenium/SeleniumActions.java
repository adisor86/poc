package com.adi.poc.selenium;

import com.adi.poc.selenium.driver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service("SeleniumActions")
public class SeleniumActions {
    @Autowired
    private BrowserDriver browserDriver;

    public void navigateTo(String url) {
        browserDriver.getCurrentDriver().get(url);
        System.out.println("Navigated to :" + url);
    }

    public void clickOnElement(By by) {
        getWebElement(by).click();
    }

    public void fillText(By by, String text) {
        getWebElement(by).sendKeys(text);
    }

    public boolean isElementDisplayed(By by) {
        return getWebElement(by).isDisplayed();
    }

    public String getTextFromElement(By by) {
        return getWebElement(by).getText();
    }

    public WebElement getWebElement(By by) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(by));
        return getDriver().findElement(by);
    }

    public WebDriver getDriver() {
        return browserDriver.getCurrentDriver();
    }

    public void quitDriver() {
        browserDriver.close();
    }
}
