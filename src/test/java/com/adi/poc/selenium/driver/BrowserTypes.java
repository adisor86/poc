package com.adi.poc.selenium.driver;

public enum BrowserTypes {
    CHROME,
    FIREFOX;

    public static BrowserTypes getBrowserFromName(String browser) throws IllegalArgumentException {
        for (BrowserTypes b : values()) {
            if (b.toString().equalsIgnoreCase(browser)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Invalid browser: " + browser);
    }
}

