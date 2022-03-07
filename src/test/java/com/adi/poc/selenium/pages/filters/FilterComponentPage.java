package com.adi.poc.selenium.pages.filters;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.filters.enums.CategoriesFilter;
import com.adi.poc.selenium.pages.filters.enums.ColorFilter;
import com.adi.poc.selenium.pages.filters.enums.SizeFilter;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilterComponentPage {
    @Autowired
    SeleniumActions seleniumActions;
    public FilterComponentPage selectTopsFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(CategoriesFilter.TOPS.label));
        return this;
    }

    public FilterComponentPage selectDressesFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(CategoriesFilter.DRESSES.label));
        return this;
    }

    public FilterComponentPage selectSizeSFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(SizeFilter.S.name()));
        return this;
    }

    public FilterComponentPage selectSizeMFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(SizeFilter.M.name()));
        return this;
    }

    public FilterComponentPage selectSizeLFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(SizeFilter.L.name()));
        return this;
    }

    public FilterComponentPage selectColorBeigeFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(ColorFilter.Beige.name()));
        return this;
    }

    public FilterComponentPage selectColorWhiteFilter() {
        seleniumActions.clickOnWebElement(retrieveFilterSizeOption(ColorFilter.White.name()));
        return this;
    }
    //TODO: implement rest of color filters

    private By retrieveFilterSizeOption(String option) {
        return By.xpath("//div[@class='layered_filter']//a[text()='" + option + "']");
    }
}
