package com.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NewsServiceImpl implements NewsService {

    private final DriverService driverService;
    private final UserService userService;

    public NewsServiceImpl(DriverService driverService, UserService userService) {
        this.driverService = driverService;
        this.userService = userService;
    }

    @Override
    public void addNews() {
        WebDriver driver = driverService.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/news/0-0-0-0-1");
        WebElement newsTitle = driver.findElement(By.className("manFlTitle"));
        newsTitle.sendKeys("New test news");
        WebElement toHTMLCodesButton = driver.findElement(By.cssSelector("a[data-uemode='3']"));
        toHTMLCodesButton.click();
        WebElement newsDescription = driver.findElement(By.id("message"));
        newsDescription.sendKeys("News desc");
        WebElement addButton = driver.findElement(By.className("manFlSbm"));
        addButton.click();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        if (driver.findElement(By.className("myWinSuccess")).isDisplayed()){
            driver.quit();
        } else {
            throw new RuntimeException("News isn't added");
        }
    }

    @Override
    public void deleteNews() {
        WebDriver driver = driverService.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/");
        WebElement menuButton = driver.findElement(By.className("u-mpanel-toggle"));
        menuButton.click();
        menuButton.click();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        WebElement deleteButton = driver.findElement(By.cssSelector("li.u-mpanel-del a"));
        deleteButton.click();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        driver.switchTo().alert().accept();
        driver.quit();
    }
}
