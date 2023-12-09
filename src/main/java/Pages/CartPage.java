package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private By itemsPrice = By.xpath("//p[@class='cartItem__price']");
    private By cartTotal = By.xpath("//div[@class='summary__total']//b");

    public CartPage (WebDriver driver){
        this.driver = driver;
    }

    public float[] getItemsPrices(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(itemsPrice)));

        List<WebElement> pricesST = driver.findElements(itemsPrice);
        float[] pricesF = new float[pricesST.size()];
        for (int i=0 ; i < pricesST.size() ; i++){
            pricesF[i] = Float.parseFloat(pricesST.get(i).getText());
        }
        return pricesF;
    }

    public float calculateTotal(float[] prices){
        float totalCalc = 0;
        for (int i=0; i< prices.length ; i++){
            totalCalc = totalCalc + prices[i];
        }
        return totalCalc;
    }

    public float getCartTotal(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotal));

        String totalST = driver.findElement(cartTotal).getText();
        // Extract the numerical part of the string
        // Removes all non-numeric characters
        String numericalPart = totalST.replaceAll("\\D+", "");

        return Float.parseFloat(numericalPart);
    }



}
