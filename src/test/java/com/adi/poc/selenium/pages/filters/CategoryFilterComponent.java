package com.adi.poc.selenium.pages.filters;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.filters.enums.CategoryFilter;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFilterComponent {
    @Autowired
    SeleniumActions seleniumActions;

    public CategoryFilterComponent selectTopsFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.TOPS.label));
        return this;
    }

    public CategoryFilterComponent selectDressesFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.DRESSES.label));
        return this;
    }

    public void selectTopsTShirtFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.T_SHIRTS.label));
    }

    public void selectTopsBlousesFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.BLOUSES.label));
    }

    public void selectDressesCasualDressesFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.CASUAL.label));
    }

    public void selectDressesEveningDressesFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.EVENING.label));
    }

    public void selectDressesSummerDressesFilter() {
        seleniumActions.clickOnElement(retrieveFilterCategoryOption(CategoryFilter.SUMMER.label));
    }

    private By retrieveFilterCategoryOption(String option) {
        return By.xpath("//div[@id='categories_block_left']//a[contains(text(),'" + option + "')]");
    }
}