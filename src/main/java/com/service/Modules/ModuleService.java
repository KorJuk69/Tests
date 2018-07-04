package com.service.Modules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public interface ModuleService {
    void add();

    void verifyAdd(Map<String, String> fields, WebDriver driver);

    void delete();

    void verifyDelete(WebElement title, WebDriver driver);
}
