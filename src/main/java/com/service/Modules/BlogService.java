package com.service.Modules;

import com.repository.TestRepository;
import com.service.TestUtils;
import com.service.UserService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;

    public BlogService(TestUtils testUtils, UserService userService, TestRepository testRepository) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/blog/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        new Select(driver.findElement(By.name("cat"))).selectByVisibleText("TestKategoriya");
        String name = "Blog name " + materialCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "Blog brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "Blog message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String field1 = "Blog field 1 " + materialCode;
        driver.findElement(By.id("blF13")).sendKeys(field1);
        String field2 = "Blog field 2 " + materialCode;
        driver.findElement(By.id("blF14")).sendKeys(field2);
        String tag = "Blog tag " + materialCode;
        driver.findElement(By.id("suggEdit")).sendKeys(tag);
        driver.findElement(By.className("manFlSbm")).click();
        while (!driver.findElement(By.className("myWinSuccess")).isDisplayed()) {
            testUtils.sleep(1);
        }
        String newsURL = driver.findElement(By.xpath("//div[@class='myWinCont']/a")).getAttribute("href");
        driver.get(newsURL);
        Map<String, String> fields = new HashMap<>();
        fields.put("name", name);
        fields.put("brief", brief);
        fields.put("message", message);
        fields.put("field1", field1);
        fields.put("field2", field2);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String messageAdded = driver.findElement(By.className("eMessage")).getText();
        String field1Added = driver.findElement(By.id("field1")).getText();
        String field2Added = driver.findElement(By.id("field2")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        driver.get("http://selenium.at.ua/blog");
        String briefAdded = driver.findElement(By.className("eMessage")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("Blog add test").setException("Blog name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("Blog add test").setException("Blog message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("Blog add test").setException("Blog brief doesn't match");
            driver.quit();
        } else if (!fields.get("field1").equals(field1Added)) {
            testRepository.getTest("Blog add test").setException("Blog field1 doesn't match");
            driver.quit();
        } else if (!fields.get("field2").equals(field2Added)) {
            testRepository.getTest("Blog add test").setException("Blog field2 doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("Blog add test").setException("Blog tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("Blog add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/blog");
        WebElement menuButton;
        try {
            menuButton = driver.findElement(By.xpath("//table[@class='eBlock']/tbody/tr/td/div"));
        } catch (NoSuchElementException e) {
            testRepository.getTest("Blog delete test").setException("Blog material isn't displayed");
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
        verifyDelete(title, driver);
    }

    @Override
    public void verifyDelete(WebElement title, WebDriver driver) {
        List<WebElement> titles = driver.findElements(By.className("eTitle"));
        if (!titles.contains(title)) {
            testRepository.getTest("Blog delete test").setPassed(true);
            driver.quit();
        } else {
            testRepository.getTest("Blog delete test").setException("Blog material isn't delete");
            driver.quit();
        }
    }
}
