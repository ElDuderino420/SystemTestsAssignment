package test;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarsTest1 {

  private static final int WAIT_MAX = 4;
  static WebDriver driver;


  @BeforeClass
  public static void setup() {
    /*########################### IMPORTANT ######################*/
    /*## Change this, according to your own OS and location of driver(s) ##*/    
    /*############################################################*/    
    //System.setProperty("webdriver.gecko.driver", "C:\\diverse\\drivers\\geckodriver.exe");
    System.setProperty("webdriver.chrome.driver","D:\\Drivers\\chromedriver.exe");
    
    //Reset Database
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    driver = new ChromeDriver();
    driver.get("http://localhost:3000");
  }

  @AfterClass
  public static void tearDown() {
    //driver.quit();
    //Reset Database 
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
  }

  @Test
  //Verify that page is loaded and all expected data are visible
  public void test1() throws Exception {
    (new WebDriverWait(driver, WAIT_MAX)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
      WebElement e = d.findElement(By.tagName("tbody"));
      List<WebElement> rows = e.findElements(By.tagName("tr"));
      Assert.assertThat(rows.size(), is(5));
      return true;
    });
  }

  @Test
  //Verify the filter functionality 
  public void test2() throws Exception {
    //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
    WebElement element = driver.findElement(By.id("filter"));
    element.sendKeys("2002");
    WebElement e = driver.findElement(By.tagName("tbody"));
    List<WebElement> rows = e.findElements(By.tagName("tr"));
    Assert.assertThat(rows.size(), is(2));
  }


  @Test
  //Verify the filter functionality
  public void test3() throws Exception {
    WebElement element = driver.findElement(By.id("filter"));
    element.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
    WebElement e = driver.findElement(By.tagName("tbody"));
    List<WebElement> rows = e.findElements(By.tagName("tr"));
    Assert.assertThat(rows.size(), is(5));
  }

  @Test
  //Verify the sort functionality
  public void test4() throws Exception {
    WebElement element = driver.findElement(By.id("h_year"));
    element.click();

    WebElement e = driver.findElement(By.tagName("tbody"));
    List<WebElement> rows = e.findElements(By.tagName("tr"));
    String firstid = rows.get(0).findElements(By.tagName("td")).get(0).getText();
    String lastid = rows.get(4).findElements(By.tagName("td")).get(0).getText();
    Assert.assertThat(firstid, is("938"));
    Assert.assertThat(lastid, is("940"));
  }


  @Test
  //Verify the sort functionality
  public void test5() throws Exception {


    WebElement e = driver.findElement(By.tagName("tbody"));
    List<WebElement> rows = e.findElements(By.tagName("tr"));
    WebElement actions = rows.get(0).findElements(By.tagName("td")).get(7);
    WebElement edit = actions.findElements(By.tagName("a")).get(0);
    edit.click();

    WebElement desc = driver.findElement(By.id("description"));
    desc.clear();
    desc.sendKeys("Cool car");

    WebElement save = driver.findElement(By.id("save"));
    save.click();

    String desct = rows.get(0).findElements(By.tagName("td")).get(5).getText();
    Assert.assertThat(desct, is("Cool car"));
  }

    @Test
    //Verify the sort functionality
    public void test6() throws Exception {
        WebElement newc = driver.findElement(By.id("new"));
        newc.click();

        WebElement save = driver.findElement(By.id("save"));
        save.click();

        WebElement submiterr = driver.findElement(By.id("submiterr"));
        Assert.assertThat(submiterr.getText(), is("All fields are required"));

        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(5));
    }

    @Test
    //Verify the sort functionality
    public void test7() throws Exception {
        WebElement newc = driver.findElement(By.id("new"));
        newc.click();

        WebElement year = driver.findElement(By.id("year"));
        year.sendKeys("2008");
        WebElement reg = driver.findElement(By.id("registered"));
        reg.sendKeys("2002-5-5");
        WebElement make = driver.findElement(By.id("make"));
        make.sendKeys("Kia");
        WebElement model = driver.findElement(By.id("model"));
        model.sendKeys("Rio");
        WebElement desc = driver.findElement(By.id("description"));
        desc.sendKeys("As new");
        WebElement price = driver.findElement(By.id("price"));
        price.sendKeys("31000");


        WebElement save = driver.findElement(By.id("save"));
        save.click();

        WebElement e = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = e.findElements(By.tagName("tr"));
        Assert.assertThat(rows.size(), is(6));
    }


}
