package com.turkcell.web.base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.commons.lang.StringUtils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.URL;
import java.time.Duration;



import static java.lang.System.getenv;

public class BaseTest {


  protected static WebDriver driver;
  protected static FluentWait<WebDriver> webDriverWait;

  @BeforeScenario
  public void setUp() throws Exception{
    //String baseUrl = "https://www.turkcell.com.tr/";
    String baseUrl = "https://www.cignafinans.com.tr/";
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    if (StringUtils.isNotEmpty(getenv("key"))) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("test-type");
      options.addArguments("disable-popup-blocking");
      options.addArguments("ignore-certificate-errors");
      options.addArguments("disable-translate");
      options.addArguments("start-maximized");
      options.addArguments("--no-sandbox");
      options.setExperimentalOption("w3c",false);
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      capabilities.setCapability("key", System.getenv("key"));
      driver = new RemoteWebDriver(new URL("http://hub.testinium.io/wd/hub"), capabilities);
      ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
    } else {
      System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
      ChromeOptions options = new ChromeOptions();
      options.addArguments("test-type");
      options.addArguments("disable-popup-blocking");
      options.addArguments("ignore-certificate-errors");
      options.addArguments("disable-translate");
      options.addArguments("start-maximized");
      options.addArguments("--no-sandbox");
      options.setExperimentalOption("w3c",false);
      driver = new ChromeDriver(options);
    }
    /**driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
     driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
     driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);*/

    driver.get(baseUrl);
    driver.manage().window().fullscreen();
  }

  @AfterScenario
  public void tearDown(){
    if(driver != null) {
      driver.quit();
    }
  }


}
