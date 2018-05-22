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
        driver.findElement(By.className("manFlTitle")).sendKeys("New test news");
        driver.findElement(By.cssSelector("a[data-uemode='3']")).click();
        driver.findElement(By.id("message")).sendKeys("News desc");
        driver.findElement(By.className("manFlSbm")).click();
        while (!driver.findElement(By.className("myWinSuccess")).isDisplayed()){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        driver.quit();
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
