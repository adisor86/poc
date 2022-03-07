package com.adi.poc.tests.ui;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.*;
import com.adi.poc.selenium.pages.filters.CatalogFilterComponent;
import com.adi.poc.selenium.pages.filters.CategoryFilterComponent;
import com.adi.poc.selenium.pages.filters.enums.MainFilter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UISmokeTest {
    @Autowired
    SeleniumActions seleniumActions;
    @Autowired
    LoginPage loginPage;
    @Autowired
    HomePage homePage;
    @Autowired
    CategoryFilterComponent categoryFilterComponent;
    @Autowired
    CatalogFilterComponent catalogFilterComponent;
    @Autowired
    SuccessfulShoppingCartConfirmationPage cartConfirmationPage;
    @Autowired
    ShoppingCartSummaryPage shoppingCartSummaryPage;
    @Autowired
    SearchResultPage searchResultPage;
    @Autowired
    ValidationComponentPage validationComponentPage;


    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;
    @Value("${url.uiBaseurl}")
    private String url;

    @Test
    @Order(1)
    public void addItemToCart() {
        System.out.println("Smoke - validate product is successfully added to cart.");
        loginPage.doLogin(username, password);
        homePage.clickOnMainFilterOption(MainFilter.WOMEN.label);
        categoryFilterComponent.selectTopsFilter()
                .selectTopsTShirtFilter();
        catalogFilterComponent.selectSizeLFilter()
                .selectSizeMFilter();
        searchResultPage.addProductToCart()
                .selectProceedToCheckoutOption();
        Assertions.assertEquals(1, shoppingCartSummaryPage.retrieveExistingItemsFromCartSummary().size(), "Actual number of items in cart is 0 or more than 1.");
    }

    @Test
    @Order(2)
    public void removeExistingItemFromCart() {
        System.out.println("Smoke - validate product is successfully removed from cart.");
        loginPage.doLogin(username, password);
        homePage.clickOnMainFilterOption(MainFilter.WOMEN.label);
        categoryFilterComponent.selectTopsFilter()
                .selectTopsTShirtFilter();
        catalogFilterComponent.selectSizeLFilter()
                .selectSizeMFilter();
        searchResultPage.addProductToCart()
                .selectContinueShoppingOption();
        homePage.clickOnMainFilterOption(MainFilter.DRESSES.label);
        categoryFilterComponent.selectDressesEveningDressesFilter();
        catalogFilterComponent.selectSizeLFilter()
                .selectColorBeigeFilter();
        searchResultPage.addProductToCart();
        searchResultPage.addProductToCart().selectContinueShoppingOption();
        homePage.openShoppingCartSummary();
        Assertions.assertEquals(2, shoppingCartSummaryPage.retrieveExistingItemsFromCartSummary().size(), "Actual number of items does not match expected value");
        shoppingCartSummaryPage.removeItemFromCartSummaryFromGivenPosition(1);
        Assertions.assertEquals(1, shoppingCartSummaryPage.retrieveExistingItemsFromCartSummary().size(), "Actual number of items does not match expected value");
        shoppingCartSummaryPage.removeItemFromCartSummaryFromGivenPosition(0);
        Assertions.assertEquals("Your shopping cart is empty.", validationComponentPage.retrieveTextFromEmptySearchResultWarning());
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
