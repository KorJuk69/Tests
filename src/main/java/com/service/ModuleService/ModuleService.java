package com.service.ModuleService;

import org.openqa.selenium.WebDriver;

import java.util.Map;

public interface ModuleService {
    void add();

    void verifyAdd(Map<String, String> fields, WebDriver driver);

    void delete();
}
