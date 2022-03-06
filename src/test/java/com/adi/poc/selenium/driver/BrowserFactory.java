package com.adi.poc.selenium.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class BrowserFactory {

    @Value("${browser.localExecution}")
    private boolean localExecution;
    @Value("${browser_stack.user}")
    private String browserStackUser;
    @Value("${browser_stack.key}")
    private String browserStackKey;
    @Value("${browser_stack.os}")
    private String browserStackOs;
    @Value("${browser_stack.osVersion}")
    private String browserStackOsVersion;
    @Value("${browser_stack.browser}")
    private String browserStackBrowser;
    @Value("${browser_stack.browserVersion}")
    private String browserStackBrowserVersion;


    public WebDriver initBrowser(BrowserTypes browserType) {
        WebDriver driver = localExecution ? initLocalDriverExecution(browserType) : initBrowserStackExecution();
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver initLocalDriverExecution(BrowserTypes type) {
        System.out.println("Local execution has been initiated.");
        switch (type) {
            case CHROME: {
                return createChromeDriver();
            }
            case FIREFOX: {
                return createFirefoxDriver();
            }
            default:
                throw new RuntimeException("Browser not implemented yet!");
        }
    }

    private WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        Map<String, Object> pref = new HashMap<String, Object>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("ignore-certificate-errors");
        options.setExperimentalOption("prefs", pref);
        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    private WebDriver initBrowserStackExecution() {
        System.out.println("Browser stack execution has been initiated.");
        String url = "https://" + browserStackUser + ":" + browserStackKey + "@hub-cloud.browserstack.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", browserStackOs);
        caps.setCapability("os_version", browserStackOsVersion);
        caps.setCapability("browser", browserStackBrowser);
        caps.setCapability("browser_version", browserStackBrowserVersion);
        try {
            return new RemoteWebDriver(new URL(url), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initiate RemoteWebDriver instance.");
        }
    }
}
