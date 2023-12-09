package Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;

public class HomePage {

    private WebDriver driver;
    private By menuList = By.xpath("//div[@class='siteMenu__container']/div/div");
    private By cartIcon = By.id("HEADER_CART_BRIEF_ICON");
    private By goToCartButton = By.id("HEADER_CART_BRIEF_GO_TO_CART_BUTTON");

    public HomePage (WebDriver driver){
        this.driver = driver;
    }

    /**
     *
     * @param itemIndex start with index 1
     */
    public void hoverOverItem(int itemIndex){

        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuList));
        action.moveToElement(driver.findElements(menuList).get(itemIndex-1)).perform();
    }

    public ResultsPage openCategory(String requiredCategory){
        driver.findElement(By.linkText(requiredCategory)).click();
        return new ResultsPage(driver);
    }

    public CartPage openCartPage(){
        driver.findElement(cartIcon).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(goToCartButton));

        driver.findElement(goToCartButton).click();
        return new CartPage(driver);
    }


    public static void main(String[] args) throws InterruptedException {
        // Use WebDriverManager to set up the ChromeDriver binary
        WebDriverManager.chromedriver().setup();
        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        // Open the Website
        driver.get("https://homzmart.com/en/");
        // Maximize the browser window (optional)
        driver.manage().window().maximize();


        By menuList = By.xpath("//div[@class='siteMenu__container']/div/div");
        Actions action = new Actions(driver);
        Thread.sleep(2000);
        System.out.println(driver.findElements(menuList).size());
        action.moveToElement(driver.findElements(menuList).get(6)).perform();

        driver.findElement(By.linkText("Food Containers")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(6));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='title hz-fs-25']")));
        action.moveToElement(driver.findElement(By.id("HEADER_MAIN_LOGO"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='listing__card']/div")));

        By productsList = By.xpath("//div[@class='listing__card']/div");

        List<WebElement> products = driver.findElements(productsList);

        System.out.println(products.size());
        Random random = new Random();
        int randomInt = random.nextInt(products.size());

        System.out.println(randomInt);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                products.get(randomInt));
        Thread.sleep(1500);

        products.get(randomInt).click();

        By productName = By.className("name-with-rating");
        By productPrice = By.xpath("//div[@class='item-detail']//div[@class='productPrices__specialPrice']/p");


        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        System.out.println(driver.findElement(productName).getText());
        String price = driver.findElement(productPrice).getText();
        float priceF = Float.parseFloat(price);
        System.out.println(priceF);
        System.out.println(priceF+1.05);
        System.out.println(priceF+1.5);
        System.out.println(priceF+2.00);

        By addToCartButton = By.xpath("//div[@class='hz-button-icon-wrapper']");
        By confirmationMsg = By.xpath("//div[@class='toastify on success toastify-center toastify-top'] ");
        wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                driver.findElement(addToCartButton));
        Thread.sleep(1500);
        driver.findElement(addToCartButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMsg));
        System.out.println(driver.findElement(confirmationMsg).getText());

         By cartIcon = By.id("HEADER_CART_BRIEF_ICON");
         String productNameCart = "//div[@class='cart__detailsScroll']//p[contains(text(),'%s')]";
         String productPriceCart = "//span[contains(text(),'%s')]";
        By cartProductsList = By.xpath("//div[@class='cartItem__containerr orders-card-items']/div");
        driver.findElement(cartIcon).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(cartProductsList));

     //  assertEquals(driver.findElement(productName).getText(),driver.findElement(productNameCart).getText());
      // assertEquals(driver.findElement(productPrice).getText(),(driver.findElement(productPriceCart).getText()+"
        // .00"));

       String pName = driver.findElement(productName).getText();
       String pPrice = driver.findElement(productPrice).getText();

        assertEquals(pName,
                driver.findElement(By.xpath(String.format(productNameCart,pName))).getText());
        assertEquals(pPrice,
                (driver.findElement(By.xpath(String.format(productPriceCart,(pPrice.replace(".00", ""))))).getText()+
                        ".00"));


        System.out.println("Done");


    }
}
