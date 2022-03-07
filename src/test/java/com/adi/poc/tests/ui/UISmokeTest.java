package com.adi.poc.tests.ui;

import com.adi.poc.selenium.SeleniumActions;
import com.adi.poc.selenium.pages.*;
import com.adi.poc.selenium.pages.filters.FilterComponentPage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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
    FilterComponentPage filterComponentPage;
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
        homePage.clickOnWomenMainFilterOption();
        filterComponentPage.selectTopsFilter()
                .selectSizeLFilter()
                .selectSizeMFilter();
        searchResultPage.addFirstProductItemToCart()
                .selectProceedToCheckoutOption();
        Assertions.assertEquals(1, shoppingCartSummaryPage.retrieveExistingItemsFromCartSummary().size(), "Actual number of items in cart is 0 or more than 1.");
    }

    @Test
    @Order(2)
    public void removeExistingItemFromCart() {
        System.out.println("Smoke - validate product is added & later removed successfully from cart.");
        loginPage.doLogin(username, password);
        homePage.clickOnWomenMainFilterOption();
        filterComponentPage.selectDressesFilter()
                .selectSizeLFilter()
                .selectColorWhiteFilter();
        searchResultPage.addFirstProductItemToCart().
                selectContinueShoppingOption();
        homePage.openShoppingCartSummary();
        Assertions.assertEquals(1, shoppingCartSummaryPage.retrieveExistingItemsFromCartSummary().size(), "Actual number of items does not match expected value");
        shoppingCartSummaryPage.removeItemFromCartSummaryFromGivenPosition(0);
        Assertions.assertEquals("Your shopping cart is empty.", validationComponentPage.retrieveEmptyCartWarningMessage());
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
