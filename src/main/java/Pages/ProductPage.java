package Pages;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.*;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    private By productName = By.className("name-with-rating");
    private By productPrice = By.xpath("//div[@class='item-detail']//div[@class='productPrices__specialPrice']/p");
    private By addToCartButton = By.xpath("//div[@class='hz-button-icon-wrapper']");
    private By confirmationMsg = By.xpath("//div[@class='toastify on success toastify-center toastify-top'] ");

    private By cartIcon = By.id("HEADER_CART_BRIEF_ICON");
    private By closCartOverlay = By.id("HEADER_CART_BRIEF_CLOSE_BUTTON");
    private By cartProductsList = By.xpath("//div[@class='cartItem__containerr orders-card-items']/div");
    private String productNameCart = "//div[@class='cart__detailsScroll']//p[normalize-space()='%s']";
    private String productPriceCart = "//div[@class='cart__detailsScroll']//span[contains(text(),'%s')]";



    public ProductPage (WebDriver driver){
        this.driver = driver;
    }

    public String[] getProductDetails(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));

        String productNameST = driver.findElement(productName).getText();
        String productPriceST = driver.findElement(productPrice).getText();

        return new String[]{productNameST,productPriceST};
    }

    public void addItemToCart () throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));

        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                driver.findElement(addToCartButton));
        Thread.sleep(500);
        driver.findElement(addToCartButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMsg));
        // Wait until item being added to cart
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(confirmationMsg)));

    }

    public void verifyItemAddedToCart (String[] productInfo){
        // open cart
        driver.findElement(cartIcon).click();

        //Wait until the items appear in the cart
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductsList));


        //We compare the product Name for added item within the cart
        assertEquals(
                driver.findElement(By.xpath(String.format(productNameCart,productInfo[0]))).getText(),productInfo[0]);
        /*
         We compare the Price of added item within the cart
         The item price returned add String with .00, but the price in cart has no .00
         So to uniquely define the element within cart we removed .00 and after that we added it again to match
         attributes
         */
        assertEquals(
                (driver.findElement(By.xpath(String.format(productPriceCart,(productInfo[1].replace(".00", ""))))).getText()+".00")
                ,productInfo[1]);

        //Close the cart overlay
        driver.findElement(closCartOverlay).click();

    }


}
