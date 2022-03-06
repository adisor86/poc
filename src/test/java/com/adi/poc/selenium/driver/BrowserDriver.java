package com.adi.poc.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("BrowserDriver")
public class BrowserDriver {
    @Value("${browser.type}")
    private String browserType;

    @Autowired
    BrowserFactory browserFactory;

    private WebDriver driver;

    public synchronized WebDriver getCurrentDriver() {
        if (driver == null) {
            driver = browserFactory.initBrowser(BrowserTypes.getBrowserFromName(browserType));
            System.out.println("Browser launched is: " + browserType);
            Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
        }
        return driver;
    }
   private class BrowserCleanup implements Runnable {
        public void run() {
            close();
        }
    }

    public void close() {
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                System.out.println("Browser session closed");
            } catch (Exception e) {
                System.out.println("Exception happened when closing the driver - " + e);
            }
        }

        // TODO: implement closure for remote execution
    }
}
