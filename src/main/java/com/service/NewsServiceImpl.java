package com.service;

import com.repository.TestRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NewsServiceImpl implements NewsService {

    private final DriverService driverService;
    private final UserService userService;
    private final TestRepository testRepository;

    public NewsServiceImpl(DriverService driverService, UserService userService, TestRepository testRepository) {
        this.driverService = driverService;
        this.userService = userService;
        this.testRepository = testRepository;
    }

    @Override
    public void addNews() {
        WebDriver driver = driverService.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/news/0-0-0-0-1");
        String newsName = "New test news" + System.currentTimeMillis();
        driver.findElement(By.className("manFlTitle")).sendKeys(newsName);
        driver.findElement(By.cssSelector("a[data-uemode='3']")).click();
        String newsDescription = "News desc" + System.currentTimeMillis();
        driver.findElement(By.id("message")).sendKeys(newsDescription);
        driver.findElement(By.className("manFlSbm")).click();
        while (!driver.findElement(By.className("myWinSuccess")).isDisplayed()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        String newsURL = driver.findElement(By.xpath("//div[@class='myWinCont']/a")).getAttribute("href");
        driver.get(newsURL);
        String newsNameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String newsDescriptionAdded = driver.findElement(By.className("eMessage")).getText();
        if (newsName.equals(newsNameAdded) && newsDescription.equals(newsDescriptionAdded)) {
            testRepository.getTests().stream()
                    .filter(test -> test.getName().equals("News add test"))
                    .findFirst().get().setPassed(true);
            driver.quit();
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
        testRepository.getTests().stream()
                .filter(test -> test.getName().equals("News delete test"))
                .findFirst().get().setPassed(true);
        driver.quit();
    }
}
