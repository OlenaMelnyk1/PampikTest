package Pampik;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class PampikTest {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://pampik.com/");
    }

    @Test (priority = 1)
    public void  checkLinkPromo() {
    driver.findElement(By.xpath("//span [text()='Акції та знижки' or text()='Акции и скидки']")).click();
    assertTrue(driver.getCurrentUrl().contains("promo"));
}


    @Test (priority = 2)
    public void checkThatListContains20Products(){
        driver.findElement(By.xpath("//li[contains(@class,'first')]/a[contains(@href,'category/progulki-i-poezdki')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div/a[contains(@href,'922')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(By.xpath("//ul[@id='products-list']//li[contains(@class,'listing__item')]"));
        assertEquals(elements.size(),20);

}
    @Test (priority = 3)
    public void checkThatProductAddedToCart() {
        driver.findElement(By.xpath("//li[contains(@class,'first')]/a[contains(@href,'category/progulki-i-poezdki')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div/a[contains(@href,'922')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//ul[@id='products-list']//li[contains(@class,'listing__item')]")).click();
        new WebDriverWait(driver, 30).until(
        webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath("//button[@data-href='/cart']")).click();
        assertEquals(driver.findElement(xpath("//div[@class='cart-block ']//span[@class='elem-counter']")).getText(), "1");
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
