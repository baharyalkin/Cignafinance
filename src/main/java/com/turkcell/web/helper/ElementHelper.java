package com.turkcell.web.helper;

import com.turkcell.web.model.ElementInfo;
import org.openqa.selenium.By;

public class ElementHelper {


  public static By getElementInfoToBy(ElementInfo elementInfo){
    By by = null;
    if (elementInfo.getType().equals("css")) {
      by = By.cssSelector(elementInfo.getValue());
    } else if (elementInfo.getType().equals("id")) {
      by = By.id(elementInfo.getValue());
    } else if (elementInfo.getType().equals("xpath")) {
      by = By.xpath(elementInfo.getValue());
    }else if (elementInfo.getType().equals("class")){
      by = By.className(elementInfo.getValue());
    }
    return by;
  }


}