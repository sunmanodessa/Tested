import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;

public class SwagTest {

    @Test

    public static void swag() throws InterruptedException {
        final String path = "C:\\Applications\\chromedriver\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        WebDriver browser = new ChromeDriver();
        WebDriver driver;

//        final String path = "C:\\Applications\\geckodriver\\geckodriver.exe";
//        System.setProperty("webdriver.gecko.driver",path);
//        WebDriver browser = new FirefoxDriver();

// go to SWAG
        browser.get("https://www.saucedemo.com/");
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


//Login to Swag
        browser.findElement(By.xpath("//*[@id=\"user-name\"]")).click();
//Login
        browser.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys("standard_user");
//Password
        browser.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("secret_sauce");
        Thread.sleep(2000);
        browser.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

//Filtering by price

        List<WebElement> elements2 = browser.findElements(By.className("inventory_item_price"));
        List<Integer> price2 = elements2.stream().map(c -> {
            WebElement a =  c;
            String value = a.getAttribute("textContent");
            int x = Integer.parseInt(value.replaceAll("[^0-9]", ""));
            return x;
        }).collect(Collectors.toList());

//        System.out.println(price2);
//_price
        browser.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select")).click();
        browser.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div[2]/span/select/option[4]")).click();

        List<WebElement> elements = browser.findElements(By.className("inventory_item_price"));
        List<Integer> priceSort = elements.stream().map(c -> {
            WebElement a =  c;
            String value = a.getAttribute("textContent");
            int x = Integer.parseInt(value.replaceAll("[^0-9]", ""));
            return x;
        }).collect(Collectors.toList());

//        System.out.println(priceSort);
//_price_after_filtering
        List<Integer> priceTemp = new ArrayList<Integer>(price2);
        Collections.sort(priceTemp, Collections.reverseOrder());
        System.out.println(priceTemp);

//        System.out.println(priceTemp.equals(priceSort));
        Assert.assertTrue(priceTemp.equals(priceSort));


//_inventory

        List<WebElement> inv_name = browser.findElements(By.className("inventory_item_name"));
        List<String> all_inv = new ArrayList<>();
        for(int i=0; i<inv_name.size(); i++){
             all_inv.add(inv_name.get(i).getText());

//            System.out.println(inv_name.get(i).getText());
        }
//Add to cart
        browser.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).click();

//go to shop card
        browser.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        List<WebElement> name = browser.findElements(By.className("inventory_item_name"));
        List<String> inv = new ArrayList<>();
        for(int i=0; i<name.size(); i++){
            inv.add(name.get(i).getText());

//            System.out.println(name.get(i).getText());
        }

        ArrayList<String> all_inv_Copy = new ArrayList<>(all_inv);
        all_inv_Copy.removeIf(x -> !inv.contains(x));
//        System.out.println(inv.equals(all_inv_Copy));
        Assert.assertTrue(inv.equals(all_inv_Copy));

        browser.quit();
    }
}

