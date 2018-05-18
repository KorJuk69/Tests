package com.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DriverServiceImpl implements DriverService {
    @Override
    public WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\SeleniumChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
