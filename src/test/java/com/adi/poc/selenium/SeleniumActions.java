package com.adi.poc.selenium;

import com.adi.poc.selenium.driver.BrowserDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service("SeleniumActions")
public class SeleniumActions {
    @Autowired
    private BrowserDriver browserDriver;

    private static final String SCROLL_INTO_VIEW_JS = "arguments[0].scrollIntoView(true);";

    public void navigateTo(String url) {
        browserDriver.getCurrentDriver().get(url);
        System.out.println("Navigated to :" + url);
    }

    public void clickOnElement(By by) {
        scrollToElementJs(by);
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(by));
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

    public List<WebElement> getWebElements(By by) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(by));
        return getDriver().findElements(by);
    }

    public void mouseHoverOverElementAndClickOption(WebElement elemToHover, WebElement elemToClick) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elemToHover).click(elemToClick)
                .build().perform();
    }

    public WebDriver getDriver() {
        return browserDriver.getCurrentDriver();
    }

    public void quitDriver() {
        browserDriver.close();
    }

    private void scrollToElementJs(By by) {
        scrollToElementJs(getWebElement(by));
    }

    private void scrollToElementJs(WebElement webElement) {
        ((JavascriptExecutor) browserDriver.getCurrentDriver()).executeScript(SCROLL_INTO_VIEW_JS, webElement);
    }
}
