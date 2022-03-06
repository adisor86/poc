package com.adi.poc.tests.ui;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.HomePage;
import com.adi.poc.selenium.pages.SearchResultPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UiSearchProductsTests {
    @Autowired
    private SeleniumActions seleniumActions;
    @Autowired
    private HomePage homePage;
    @Autowired
    private SearchResultPage searchResultPage;

    @Value("${url.uiBaseurl}")
    private String url;

    private final String NO_RESULTS_SEARCH_CRITERIA = "pandatestnoresult";
    private final String noResultsMessage = "No results were found for your search \"".concat(NO_RESULTS_SEARCH_CRITERIA).concat("\"");

    @Test
    void searchWhileUsingProductWithoutAnyMatch() {
        seleniumActions.navigateTo(url);
        homePage.introduceProductToSearchInput(NO_RESULTS_SEARCH_CRITERIA)
                .submitProductSearch();
        Assertions.assertEquals(noResultsMessage, searchResultPage.retrieveEmptyProductSearchMessage());
    }
}
