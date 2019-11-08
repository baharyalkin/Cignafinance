package com.turkcell.web.step;

import com.thoughtworks.gauge.Step;
import com.turkcell.web.base.BasePageUtil;
import com.turkcell.web.base.BaseTest;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StepImplementation extends BaseTest {

    private static Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(StepImplementation.class);

        BasePageUtil methods;
    public static int DEFAULT_MAX_ITERATION_COUNT = 150;
    public static int DEFAULT_MILLISECOND_WAIT_AMOUNT = 100;
    private static String SAVED_ATTRIBUTE;

    public StepImplementation() {

        PropertyConfigurator.configure(StepImplementation.class.getClassLoader().getResource("log4j.properties"));
        methods = new BasePageUtil(driver);
    }

    public static String getSavedAttribute() {
        return SAVED_ATTRIBUTE;
    }

    @Step("Switch to frame by index <index>")
    public void switchToFrameByIndex(int index) {
        methods.switchFrameByIndex(index);
    }

    @Step("Switch to frame by name <name>")
    public void switchToFrameByIndex(String name) {
        methods.switchFrameByIndex(name);
    }

    @Step("Switch to main frame")
    public void switchToMainFrame() {
        methods.switchMainFrame();
    }

    @Step("Wait <milliSeconds> milliSeconds")
    public void waitByMilliSeconds(long milliSeconds) {
       methods.waitByMilliSeconds(milliSeconds);
       logger.info(milliSeconds + " milisaniye bekledi");
    }

    @Step({"Wait <seconds> seconds","<saniye> saniye bekle"})
    public void waitBySeconds(long seconds) {
        methods.waitBySeconds(seconds);
        logger.info(seconds + " saniye bekledi");
    }

    @Step("print page source")
    public void printPageSource() {
        System.out.println(methods.getPageSource());
        logger.info(" sayfa kaynağını görüntüledi");
    }



    @Step({"Wait for element then click <key>",
            "Elementi bekle ve sonra tıkla <key>"})
    public void checkElementExistsThenClick(String key) {
        getElementWithKeyIfExists(key);
        clickElement(key);
        logger.info(" elementi bekledi ve sonra tıkladı");

    }
    @Step({"Click to element <key>",
            " Elementine tıkla <key>"})
    public void clickElement(String key) {

        methods.isElementVisible(key,30);
        methods.focusToElementJs(key);
        waitByMilliSeconds(500);
        methods.isElementClickable(key,30);
        methods.clickElementBy(key);
        logger.info(key + " elemente tıkladı");
    }


    @Step({"Click to element <key> without focus",
            "Elementine odaklanmadan tıkla <key>"})
    public void clickElementWithoutFocus(String key) {

        methods.isElementVisible(key,30);
        methods.hoverElement(methods.findElement(key));
        waitByMilliSeconds(500);
        methods.isElementClickable(key,30);
        methods.clickElementBy(key);
        logger.info(key + " elemente tıkladı");
    }

    @Step({"Click to element js <key>",
            "Elementine js ile tıkla <key>"})
    public void clickElementWithjs(String key) {

        methods.isElementVisible(key,30);
        waitByMilliSeconds(500);
        methods.isElementClickable(key,30);
        WebElement element = methods.findElement(key);
        methods.javaScriptClicker(driver,element);
        logger.info(key + " elemente tıkladı");
    }


    @Step({"Click to hover element <key>",
            "Elementinin üzerine gelip tıkla <key>"})
    public void clickHoverElement(String key) {

        methods.isElementVisible(key,5);
        WebElement element = methods.findElement(key);
        methods.hoverElement(element);
        waitByMilliSeconds(500);
        methods.clickElement(element);
        logger.info(key + " elemente tıkladı");
    }









    @Step({"Check if element <key> exists",
            "Wait for element to load with key <key>",
            "Element var mı kontrol et <key>",
            "Elementinin yüklenmesini bekle <key>"})
    public WebElement getElementWithKeyIfExists(String key) {

        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                return driver.findElement(by);
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementinin sayfada olduğu kontrol edildi");
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
        return null;
    }

    @Step("Go to <url> address")
    public void goToUrl(String url) {
        driver.get(url);
        logger.info( " verilen" + url+ " adresine gitti");
    }

    @Step({"Wait for element to load with key <key>",
            "Elementinin yüklenmesini bekle key <key>"})
    public void waitElementLoadWithCss(String key) {
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(methods.getBy(key)).size() > 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + "elementinin sayfada yüklenmesi beklenildi");
        }
        Assert.fail("Element: '" + key + "' doesn't exist.");
    }

    @Step({"Check if element <key> exists else print message <message>",
            "Element <key> var mı kontrol et yoksa hata mesajı ver <message>"})
    public void getElementWithKeyIfExistsWithMessage(String key, String message) {

        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() > 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(message);
    }


    @Step({" <key> Click on element if available",
            "Element <key> varsa tıkla"})
    public void existClickByKey(String key) {

        System.out.println("varsa tıklaya girdi");

      //  By by = methods.getBy(key);

        WebElement element;
        element= methods.findElement(key);

            if(element!= null){
                clickElement(key);
            }
        }


    @Step({"Check if element <key> not exists",
            "Element yok mu kontrol et <key>"})
    public void checkElementNotExists(String key) {
        By by = methods.getBy(key);

        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (driver.findElements(by).size() == 0) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail("Element '" + key + "' still exist.");
    }

    @Step({"Upload file in project <path> to element <key>",
            "Proje içindeki <path> dosyayı elemente upload et <key>"})
    public void uploadFile(String path, String key) {
        String pathString = System.getProperty("user.dir") + "/";
        pathString = pathString + path;
        methods.sendKeys(key,pathString);
    }

    @Step({"Write value <text> to element <key>",
            "<text> textini elemente yaz <key>"})
    public void sendKeys(String text, String key) {
        if (!key.equals("")) {
            methods.sendKeys(key,text);
            logger.info(text +" değeri" + key + " elementine yazdı ");
        }
    }

    @Step({"Click with javascript to key <key>",
            "Javascript ile key tıkla <key>"})
    public void javascriptClickerWithCss(String key) {
        Assert.assertTrue("Element bulunamadı", methods.isDisplayedBy(methods.getBy(key)));
        methods.javaScriptClicker(driver, driver.findElement(methods.getBy(key)));
        logger.info(key + " elementine javascript ile tıkladı");

    }

    @Step({"Check if current URL contains the value <expectedURL>",
            "Şuanki URL <url> değerini içeriyor mu kontrol et"})
    public void checkURLContainsRepeat(String expectedURL) {
        int loopCount = 0;
        String actualURL = "";
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualURL = driver.getCurrentUrl();

            if (actualURL != null && actualURL.contains(expectedURL)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(expectedURL + " şu anki URL değerini içeriyor mu kontrol edildi");
        }
        Assert.fail(
                "Actual URL doesn't match the expected." + "Expected: " + expectedURL + ", Actual: "
                        + actualURL);
    }

    @Step({"Send TAB key to element <key>",
            "Elemente TAB keyi yolla <key>"})
    public void sendKeyToElementTAB(String key) {
        methods.sendKeys(key,Keys.TAB);
        logger.info(key + " TAB basıldı");
    }

    @Step({"Send BACKSPACE key to element <key>",
            "Elemente BACKSPACE keyi yolla <key>"})
    public void sendKeyToElementBACKSPACE(String key) {
        methods.sendKeys(key,Keys.BACK_SPACE);
        logger.info(key + " BACK_SPACE basıldı");

    }

    @Step({"Send ESCAPE key to element <key>",
            "Elemente ESCAPE keyi yolla <key>"})
    public void sendKeyToElementESCAPE(String key) {
        methods.sendKeys(key,Keys.ESCAPE);
        logger.info(key + " ESCAPE basıldı");

    }

    @Step({"Send ENTER key to element <key>","Elemente ENTER key yolla <key>"})
    public void sendKeyToElementENTER(String key){
        methods.sendKeys(key,Keys.ENTER);
        logger.info(key + " ENTER basıldı");
    }


    @Step({"Check if element <key> has attribute <attribute>",
            "<key> elementi <attribute> niteliğine sahip mi"})
    public void checkElementAttributeExists(String key, String attribute) {
        WebElement element = methods.findElement(key);

        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) != null) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliğine sahip mi kontrol edildi");
        }
        Assert.fail("Element DOESN't have the attribute: '" + attribute + "'");
    }

    @Step({"Check if element <key> not have attribute <attribute>",
            "<key> elementi <attribute> niteliğine sahip değil mi"})
    public void checkElementAttributeNotExists(String key, String attribute) {
        WebElement element = methods.findElement(key);

        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (element.getAttribute(attribute) == null) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail("Element STILL have the attribute: '" + attribute + "'");
    }

    @Step({"Check if <key> element's attribute <attribute> equals to the value <expectedValue>",
            "<key> elementinin <attribute> niteliği <value> değerine sahip mi"})
    public void checkElementAttributeEquals(String key, String attribute, String expectedValue) {
        WebElement element = methods.findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.equals(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliği" +expectedValue+ " değerine sahip mi kontrol edildi");
        }
        Assert.fail("Element's attribute value doesn't match expected value");
    }

    @Step({"Check if <key> element's attribute <attribute> contains the value <expectedValue>",
            "<key> elementinin <attribute> niteliği <value> değerini içeriyor mu"})
    public void checkElementAttributeContains(String key, String attribute, String expectedValue) {
        WebElement element = methods.findElement(key);

        String actualValue;
        int loopCount = 0;
        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            actualValue = element.getAttribute(attribute).trim();
            if (actualValue.contains(expectedValue)) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
            logger.info(key + " elementi" + attribute + "niteliği" +expectedValue+ " değerine içeriyor mu kontrol edildi");

        }
        Assert.fail("Element's attribute value doesn't contain expected value");
    }

    @Step({"Write <value> to <attributeName> of element <key>",
            "<value> değerini <attribute> niteliğine <key> elementi için yaz"})
    public void setElementAttribute(String value, String attributeName, String key) {
        String attributeValue = methods.getAttribute(key,attributeName);
        methods.sendKeys(key,attributeValue, value);
        logger.info(value + " değerini" + attributeName + "niteliğine" + key + " elementi için yazdırıldı");

    }

    @Step({"Write <value> to <attributeName> of element <key> with Js",
            "<value> değerini <attribute> niteliğine <key> elementi için JS ile yaz"})
    public void setElementAttributeWithJs(String value, String attributeName, String key) {
        WebElement webElement = methods.findElement(key);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + value + "')", webElement);
        logger.info(value + " değerini" + attributeName + "niteliğine" + key + " elementi için JS ile yazdırıldı");

    }

    @Step({"Clear text of element <key>",
            "<key> elementinin text alanını temizle"})
    public void clearInputArea(String key) {
        methods.findElement(key).clear();
        logger.info(key + " elementinin text alanını temizledi");
    }

    @Step({"Clear text of element <key> with BACKSPACE",
            "<key> elementinin text alanını BACKSPACE ile temizle"})
    public void clearInputAreaWithBackspace(String key) {
        methods.actionClearElement(key);
        logger.info(key + " elementinin text alanını BACKSPACE ile temizledi");
    }

    @Step({"Change page zoom to <value>%",
            "Sayfanın zoom değerini değiştir <value>%"})
    public void chromeZoomOut(String value) {
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript("document.body.style.zoom = '" + value + "%'");
        logger.info("sayfanın zoom değeri değiştirildi");
    }


    @Step({"Open new tab",
            "Yeni sekme aç"})
    public void chromeOpenNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        logger.info("yen sekme açıldı");
    }

    @Step({"Focus on tab number <number>",
            "<number> numaralı sekmeye odaklan"})//Starting from 1
    public void chromeFocusTabWithNumber(int number) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(number - 1));
        logger.info(number + " numaralı sekmeye odaklandı");
    }

    @Step({"Focus on last tab",
            "Son sekmeye odaklan"})
    public void chromeFocusLastTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        logger.info(  " Son sekmeye odaklandı");

    }

    @Step({"Focus on frame with <key>",
            "Frame'e odaklan <key>"})
    public void chromeFocusFrameWithNumber(String key) {
        WebElement webElement = methods.findElement(key);
        driver.switchTo().frame(webElement);
    }

    @Step({"Save attribute <attribute> value of element <key>",
            "<attribute> niteliğini sakla <key> elementi için"})
    public void saveAttributeValueOfElement(String attribute, String key) {
        SAVED_ATTRIBUTE = methods.getAttribute(key,attribute);
        System.out.println("Saved attribute value is: " + SAVED_ATTRIBUTE);
    }

    @Step({"Write saved attribute value to element <key>",
            "Kaydedilmiş niteliği <key> elementine yaz"})
    public void writeSavedAttributeToElement(String key) {
        methods.sendKeys(key,SAVED_ATTRIBUTE);
    }

    @Step("Check if <int> amount element exists with same key <key>")
    public void checkElementCount(int expectedAmount, String key) {
        int loopCount = 0;

        while (loopCount < DEFAULT_MAX_ITERATION_COUNT) {
            if (expectedAmount == methods.findElements(key).size()) {
                return;
            }
            loopCount++;
            waitByMilliSeconds(DEFAULT_MILLISECOND_WAIT_AMOUNT);
        }
        Assert.fail(
                "Expected element count failed. Expected amount:" + expectedAmount + " Actual amount:"
                        + methods.findElements(key).size());
    }

    @Step({"Check if element <key> contains text <expectedText>",
            "<key> elementi <text> değerini içeriyor mu kontrol et"})
    public void checkElementContainsText(String key, String expectedText) {
        Boolean containsText = methods.getText(key).contains(expectedText);
        Assert.assertTrue("Expected text is not contained", containsText);
    }

    @Step({"Refresh page",
            "Sayfayı yenile"})
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Step({"Write random value to element <key>",
            "<key> elementine random değer yaz"})
    public void writeRandomValueToElement(String key) {
        methods.sendKeys(key,methods.randomString(15));
    }

    @Step({"Write random value to element <key> starting with <text>",
            "<key> elementine <text> değeri ile başlayan random değer yaz"})
    public void writeRandomValueToElement(String key, String startingText) {
        String randomText = startingText + methods.randomString(15);
        methods.sendKeys(key,randomText);
    }

    @Step({"Print element text by key <key>",
            "Elementin text değerini yazdır key <key>"})
    public void printElementText(String key) {
        System.out.println(methods.getText(key));
    }

    @Step({"Write value <string> to element <key> with focus",
            "<string> değerini <key> elementine focus ile yaz"})
    public void sendKeysWithFocus(String text, String key) {
        methods.actionSendKeys(key, text);
    }

    @Step({"Click to element <key> with focus",
            "<key> elementine focus ile tıkla"})
    public void clickElementWithFocus(String key) {
        methods.actionClickElement(key);
    }

    @Step({"Write date with <int> days added to current date to element <key>",
            "Bugünkü tarihe <int> gün ekle ve <key> elementine yaz"})
    public void writeDateWithDaysAdded(int daysToAdd, String key) {
        if (!key.equals("")) {
            methods.sendKeys(key,methods.getCurrentDateThenAddDays(daysToAdd));
           // int i= 4;
           // String a="//*[@class=\"search-item col lg-1 md-1 sm-1  custom-hover not-fashion-flex\"]["+i+"]";

        }
    }

    @Step({"Accept Chrome alert popup",
            "Chrome uyarı popup'ını kabul et"})
    public void acceptChromeAlertPopup() {
        driver.switchTo().alert().accept();
    }

    @Step("<key> elementine kadar swipe yap")
    public void swipetoElement(String key){

        WebElement element =  methods.findElement(key);
        Actions ts= new Actions(driver);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        ts.moveToElement(element).build().perform();
        waitBySeconds(1);
        logger.info(key + "elemente kadar swipe yapıldı");
    }



    @Step("<key> elementten random sec")
    public void randomChoose(String key)  {

        List<WebElement> productList = methods.findElements(key);

        final Random random = new Random();

        int sayi = random.nextInt(productList.size());

        System.out.println( productList.get(sayi).getText());
        productList.get(sayi).click();
        logger.info(key + " random seçildi");
    }

    @Step("<key> li elementin üzerine gel")
    public void hoverElementKey(String key){
        waitBySeconds(5);
        methods.hoverElementwithkey(key);
        logger.info(key + " elementin üzerine geldi");
    }


    @Step("cookies sil")
    public void Cookies(){
       methods.deleteAllCookies();
    }
    @Step("<deger>  degerini <box> checkboxtan  sec")
    public void checkBoxSelector(String deger , String box){

        methods.clickElementBy(box);

        System.out.println(box);
        if (box.contains("dogumTarihiGun") || box.contains("dogumTarihiAy") || box.contains("dogumTarihiYil"))
        {
            methods.birthDayCheckBox(deger,box);
        }else if (box.contains("tarifelerCheckBox")){



            WebElement lastElement= driver.findElement(By.xpath("//*[@id=\"drop_tl_amount_top_up\"]//*[@data-url='"+deger+"-TL-yukle']"));
            methods.hoverElement(lastElement);
            waitByMilliSeconds(500);
            methods.clickElement(lastElement);
        }else if(box.contains("sonKullanimTarihiAy")){

            WebElement lastElement= driver.findElement(By.xpath("//*[@id=\"trk_dropdown_payment_card_month\"]//*[@data-val="+deger+"]"));
            methods.hoverElement(lastElement);
            waitByMilliSeconds(500);
            methods.clickElement(lastElement);
        }else if(box.contains("sonKullanımTarihiYil")){

            WebElement lastElement= driver.findElement(By.xpath("//*[@id=\"trk_dropdown_payment_card_year\"]//*[@data-val=\""+deger+"\"]"));
            methods.hoverElement(lastElement);
            waitByMilliSeconds(500);
            methods.clickElement(lastElement);
        }


    }

    @Step("<deger> elementine new tab acarak tıkla")
    public void newTab(String deger){
        driver.get("https://e-sirket.mkk.com.tr/esir/Dashboard.jsp#/sirketbilgileri/11332");
    }


    @Step("cerezleri temizle")
    public void cleanCookies(){
        driver.manage().deleteAllCookies();
    }
}
