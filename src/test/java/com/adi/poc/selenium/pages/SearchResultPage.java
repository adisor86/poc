package com.adi.poc.selenium.pages;

import com.adi.poc.selenium.SeleniumActions;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SearchResultPage {
    @Autowired
    SeleniumActions seleniumActions;

    private By noResultFoundMessageLocator = By.cssSelector("*[class ='alert alert-warning']");
    private List<By> listOfProductsReturnedAfterSearch = (List<By>) By.cssSelector("ul[class = 'product_list grid row']");

    public List<By> retrieveNumberOfProductsFound() {
        return listOfProductsReturnedAfterSearch;
    }

    public String retrieveEmptyProductSearchMessage() {
        return seleniumActions.getTextFromElement(noResultFoundMessageLocator);
    }
}
