package com.service;

import com.repository.CaptchaRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final DriverService driverService;
    private final CaptchaRepository captchaRepository;

    public UserServiceImpl(DriverService driverService, CaptchaRepository captchaRepository) {
        this.driverService = driverService;
        this.captchaRepository = captchaRepository;
    }

    @Override
    public void uidLogin() {
        WebDriver driver = driverService.getDriver();
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        driver.findElement(By.id("uid_email")).sendKeys("dima.k@ucoz-team.net");
        driver.findElement(By.id("uid_password")).sendKeys("cSMJiMoS");
        driver.findElement(By.id("uid-form-submit")).click();
        while (!driver.getCurrentUrl().equals("http://selenium.at.ua/")) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        driver.quit();
    }

    @Override
    public void uidLogin(WebDriver driver) {
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        driver.findElement(By.id("uid_email")).sendKeys("dima.k@ucoz-team.net");
        driver.findElement(By.id("uid_password")).sendKeys("cSMJiMoS");
        driver.findElement(By.id("uid-form-submit")).click();
        while (!driver.getCurrentUrl().equals("http://selenium.at.ua/")) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void uidRegistrationFirst() {
        WebDriver driver = driverService.getDriver();

        String email = getRandomEmail(driver);

        driver.get("http://selenium.at.ua/register");
        driver.findElement(By.id("uf-name")).sendKeys("Test");
        driver.findElement(By.id("uf-surname")).sendKeys("User");
        driver.findElement(By.id("uf-submit")).click();
        driver.findElement(By.id("uf-email")).sendKeys(email);
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] array = null;
        try {
            array = Files.readAllBytes(scrFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        captchaRepository.setDriver(driver);
        captchaRepository.setScreen(array);
    }

    @Override
    public void uidRegistrationSecond(WebDriver driver, String captcha) {
        driver.findElement(By.id("fCode")).sendKeys(captcha);
        driver.findElement(By.id("policy")).click();
        driver.findElement(By.id("uf-terms")).click();
        driver.findElement(By.id("uf-submit")).click();
        if (driver.findElement(By.cssSelector("div.register-form-wrapper h2")).getText().equals("Отлично!")) {
            driver.quit();
        } else {
            throw new RuntimeException("Registration failed");
        }
    }

    @Override
    public String getRandomEmail(WebDriver driver) {
        driver.get("https://temp-mail.org/ru/");
        driver.findElement(By.id("click-to-delete")).click();
        return driver.findElement(By.id("mail")).getAttribute("value");
    }
}
