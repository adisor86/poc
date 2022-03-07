package com.adi.poc.tests.ui;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.HomePage;
import com.adi.poc.selenium.pages.SearchResultPage;
import com.adi.poc.selenium.pages.filters.FilterComponentPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    FilterComponentPage filterComponentPage;
    @Autowired
    private SearchResultPage searchResultPage;

    @Value("${url.uiBaseurl}")
    private String url;

    private final String NO_RESULTS_SEARCH_CRITERIA = "pandatestnoresult";
    private final String MATCHING_RESULTS_CRITERIA = "printed";
    private final String noResultsMessage = "No results were found for your search \"".concat(NO_RESULTS_SEARCH_CRITERIA).concat("\"");

    @Test
    void searchWhileUsingNoMatchingProductCriteria() {
        System.out.println("Test about to validate search functionality when no product matches the search criteria.");
        homePage.introduceProductToSearchInput(NO_RESULTS_SEARCH_CRITERIA)
                .submitProductSearch();
        Assertions.assertEquals(noResultsMessage, searchResultPage.retrieveEmptyProductSearchMessage());
    }

    @Test
    void searchWhileUsingMatchingProductCriteria() {
        System.out.println("Test about to validate search functionality when at least one product matches the search criteria.");
        homePage.introduceProductToSearchInput(MATCHING_RESULTS_CRITERIA)
                .submitProductSearch();
        Assertions.assertTrue(searchResultPage.retrieveNumberOfProductsFound().size() > 0, "Search list is empty, though it should have been at least one product being returned.");
    }

    @Test
    void filterProductsWhileUsingMoreFilterCriteria() {
        System.out.println("Test about to validate filter functionality when more filters are used and at least one product is retruned");
        homePage.clickOnWomenMainFilterOption();
        filterComponentPage.selectTopsFilter()
                .selectSizeLFilter()
                .selectSizeMFilter();
        Assertions.assertTrue(searchResultPage.retrieveNumberOfProductsFound().size() > 0, "Search list is empty, though it should have been at least one product being returned.");
    }

    @BeforeEach
    private void setup() {
        seleniumActions.navigateTo(url);
    }

    @AfterEach
    private void quitDriver() {
        seleniumActions.quitDriver();
    }
}
