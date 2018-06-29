package com.service;

import com.repository.TestRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;

    public NewsServiceImpl(TestUtils testUtils, UserService userService, TestRepository testRepository) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
    }

    @Override
    public void addNews() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/news/0-0-0-0-1");
        long newsCode = System.currentTimeMillis();
        String newsName = "News name " + newsCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(newsName);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String newsBrief = "News brief " + newsCode;
        driver.findElement(By.id("brief")).sendKeys(newsBrief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String newsMessage = "News message " + newsCode;
        driver.findElement(By.id("message")).sendKeys(newsMessage);
        String newsField1 = "News field 1 " + newsCode;
        driver.findElement(By.id("nwF13")).sendKeys(newsField1);
        String newsField2 = "News field 2 " + newsCode;
        driver.findElement(By.id("nwF14")).sendKeys(newsField2);
        String newsField3 = "News field 3 " + newsCode;
        driver.findElement(By.id("nwF15")).sendKeys(newsField3);
        String newsField4 = "News field 4 " + newsCode;
        driver.findElement(By.id("nwF16")).sendKeys(newsField4);
        String newsField5 = "News field 5 " + newsCode;
        driver.findElement(By.id("nwF17")).sendKeys(newsField5);
        String newsTag = "News tag " + newsCode;
        driver.findElement(By.id("suggEdit")).sendKeys(newsTag);
        driver.findElement(By.className("manFlSbm")).click();
        while (!driver.findElement(By.className("myWinSuccess")).isDisplayed()) {
            testUtils.sleep(1);
        }
        String newsURL = driver.findElement(By.xpath("//div[@class='myWinCont']/a")).getAttribute("href");
        driver.get(newsURL);

        String newsNameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String newsMessageAdded = driver.findElement(By.className("eMessage")).getText();
        String newsField1Added = driver.findElement(By.id("field1")).getText();
        String newsField2Added = driver.findElement(By.id("field2")).getText();
        String newsField3Added = driver.findElement(By.id("field3")).getText();
        String newsField4Added = driver.findElement(By.id("field4")).getText();
        String newsField5Added = driver.findElement(By.id("field5")).getText();
        String newsTagAdded = driver.findElement(By.className("eTag")).getText();
        driver.get("http://selenium.at.ua/news");
        String newsBriefAdded = driver.findElement(By.className("eMessage")).getText();

        if (!newsName.equals(newsNameAdded)) {
            testRepository.getTest("News add test").setException("News name doesn't match");
            driver.quit();
        } else if (!newsMessage.equals(newsMessageAdded)) {
            testRepository.getTest("News add test").setException("News message doesn't match");
            driver.quit();
        } else if (!newsBrief.equals(newsBriefAdded)) {
            testRepository.getTest("News add test").setException("News brief doesn't match");
            driver.quit();
        } else if (!newsField1.equals(newsField1Added)) {
            testRepository.getTest("News add test").setException("News field1 doesn't match");
            driver.quit();
        } else if (!newsField2.equals(newsField2Added)) {
            testRepository.getTest("News add test").setException("News field2 doesn't match");
            driver.quit();
        } else if (!newsField3.equals(newsField3Added)) {
            testRepository.getTest("News add test").setException("News field3 doesn't match");
            driver.quit();
        } else if (!newsField4.equals(newsField4Added)) {
            testRepository.getTest("News add test").setException("News field4 doesn't match");
            driver.quit();
        } else if (!newsField5.equals(newsField5Added)) {
            testRepository.getTest("News add test").setException("News field5 doesn't match");
            driver.quit();
        } else if (!newsTag.equals(newsTagAdded)) {
            testRepository.getTest("News add test").setException("News tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("News add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void deleteNews() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/news");
        WebElement menuButton;
        try {
            menuButton = driver.findElement(By.xpath("//table[@class='eBlock']/tbody/tr/td/div"));
        } catch (NoSuchElementException e) {
            testRepository.getTest("News delete test").setException("News isn't displayed");
            driver.quit();
            return;
        }
        WebElement title = driver.findElement(By.className("eTitle"));
        menuButton.click();
        menuButton.click();
        testUtils.sleep(1);
        WebElement deleteButton = driver.findElement(By.cssSelector("li.u-mpanel-del a"));
        deleteButton.click();
        testUtils.sleep(1);
        driver.switchTo().alert().accept();
        testUtils.sleep(1);
        driver.navigate().refresh();
        List<WebElement> titles = driver.findElements(By.className("eTitle"));
        if (!titles.contains(title)) {
            testRepository.getTest("News delete test").setPassed(true);
            driver.quit();
        } else {
            testRepository.getTest("News delete test").setException("News isn't delete");
            driver.quit();
        }
    }
}
