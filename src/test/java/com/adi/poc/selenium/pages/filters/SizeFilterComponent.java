package com.adi.poc.selenium.pages.filters;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.filters.enums.SizeFilter;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SizeFilterComponent {
    @Autowired
    SeleniumActions seleniumActions;

    public SizeFilterComponent selectSizeSFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.S.name()));
        return this;
    }

    public SizeFilterComponent selectSizeMFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.M.name()));
        return this;
    }

    public SizeFilterComponent selectSizeLFilter() {
        seleniumActions.clickOnElement(retrieveFilterSizeOption(SizeFilter.L.name()));
        return this;
    }

    private By retrieveFilterSizeOption(String option) {
        return By.xpath("//div[@class='layered_filter']//a[text()='" + option + "']");
    }
}
