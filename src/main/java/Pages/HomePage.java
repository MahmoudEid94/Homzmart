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

}
