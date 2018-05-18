package com.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final DriverService driverService;

    public UserServiceImpl(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public void uidLogin() {
        WebDriver driver = driverService.getDriver();
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        WebElement emailField = driver.findElement(By.id("uid_email"));
        emailField.sendKeys("dima.k@ucoz-team.net");
        WebElement passwordField = driver.findElement(By.id("uid_password"));
        passwordField.sendKeys("cSMJiMoS");
        WebElement loginButton = driver.findElement(By.id("uid-form-submit"));
        loginButton.click();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        driver.quit();
    }

    @Override
    public void uidLogin(WebDriver driver) {
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        WebElement emailField = driver.findElement(By.id("uid_email"));
        emailField.sendKeys("dima.k@ucoz-team.net");
        WebElement passwordField = driver.findElement(By.id("uid_password"));
        passwordField.sendKeys("cSMJiMoS");
        WebElement loginButton = driver.findElement(By.id("uid-form-submit"));
        loginButton.click();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
