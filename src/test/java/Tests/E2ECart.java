package Tests;

import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductPage;
import Pages.ResultsPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class E2ECart extends BaseTest{

    @Test
    public void testAddItemsToCart() throws InterruptedException {
        HomePage homePage = new HomePage(driver);

        for (int i =0 ; i<2 ; i++) {
            homePage.hoverOverItem(7);
            ResultsPage resultsPage = homePage.openCategory("Food Containers");
            ProductPage productPage = resultsPage.openRandomProduct();
            productPage.addItemToCart();
            productPage.verifyItemAddedToCart(productPage.getProductDetails());
        }

        CartPage cart = homePage.openCartPage();
        assertEquals(cart.calculateTotal(cart.getItemsPrices()),cart.getCartTotal());
    }
}
