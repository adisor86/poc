package com.adi.poc.selenium.pages.filters;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.filters.enums.ColorFilter;
import com.adi.poc.selenium.pages.filters.enums.SizeFilter;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogFilterComponent {
    @Autowired
    SeleniumActions seleniumActions;

    public CatalogFilterComponent selectSizeSFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.S.name()));
        return this;
    }

    public CatalogFilterComponent selectSizeMFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.M.name()));
        return this;
    }

    public CatalogFilterComponent selectSizeLFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.L.name()));
        return this;
    }

    public CatalogFilterComponent selectColorBeigeFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(ColorFilter.Beige.name()));
        return this;
    }

    public CatalogFilterComponent selectColorWhiteFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(ColorFilter.White.name()));
        return this;
    }
    //TODO: implement rest of color filters

    private By retrieveFilterSizeOption(String option) {
        return By.xpath("//div[@class='layered_filter']//a[text()='" + option + "']");
    }
}
