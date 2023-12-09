package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ResultsPage {

    private WebDriver driver;

    private By category = By.xpath("//p[@class='title hz-fs-25']");
    private By productsList = By.xpath("//div[@class='listing__card']/div");
    private By logo = By.id("HEADER_MAIN_LOGO");


    public ResultsPage (WebDriver driver){
        this.driver = driver;
    }

    public String checkResultPageCategory (){
        return driver.findElement(category).getText();
    }

    public ProductPage openRandomProduct() throws InterruptedException {
        // We need to hover over any other part of the page to close the overlay of sub-categories, so we will hover
        // over the logo
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(logo)).perform();

        // Wait until the products are visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsList));

        // Get all products in list
        List<WebElement> products = driver.findElements(productsList);
        // Generate random number represent the product
        Random random = new Random();
        int randomInt = random.nextInt(products.size());
        // Scroll to the required product
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
                products.get(randomInt));
        Thread.sleep(1500);
        products.get(randomInt).click();
        return new ProductPage (driver);
    }




}
