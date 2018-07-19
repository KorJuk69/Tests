package com.service.ModuleService.Modules;

import com.repository.TestRepository;
import com.service.ModuleService.DeleteMaterialService;
import com.service.ModuleService.ModuleService;
import com.service.TestUtils;
import com.service.UserService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NewsService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public NewsService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/news/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        String name = "News name " + materialCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "News brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "News message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String field1 = "News field 1 " + materialCode;
        driver.findElement(By.id("nwF13")).sendKeys(field1);
        String field2 = "News field 2 " + materialCode;
        driver.findElement(By.id("nwF14")).sendKeys(field2);
        String field3 = "News field 3 " + materialCode;
        driver.findElement(By.id("nwF15")).sendKeys(field3);
        String field4 = "News field 4 " + materialCode;
        driver.findElement(By.id("nwF16")).sendKeys(field4);
        String field5 = "News field 5 " + materialCode;
        driver.findElement(By.id("nwF17")).sendKeys(field5);
        String tag = "News tag " + materialCode;
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
        fields.put("field3", field3);
        fields.put("field4", field4);
        fields.put("field5", field5);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String messageAdded = driver.findElement(By.className("eMessage")).getText();
        String field1Added = driver.findElement(By.id("field1")).getText();
        String field2Added = driver.findElement(By.id("field2")).getText();
        String field3Added = driver.findElement(By.id("field3")).getText();
        String field4Added = driver.findElement(By.id("field4")).getText();
        String field5Added = driver.findElement(By.id("field5")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        driver.get("http://selenium.at.ua/news");
        String briefAdded = driver.findElement(By.className("eMessage")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("News add test").setException("News name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("News add test").setException("News message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("News add test").setException("News brief doesn't match");
            driver.quit();
        } else if (!fields.get("field1").equals(field1Added)) {
            testRepository.getTest("News add test").setException("News field1 doesn't match");
            driver.quit();
        } else if (!fields.get("field2").equals(field2Added)) {
            testRepository.getTest("News add test").setException("News field2 doesn't match");
            driver.quit();
        } else if (!fields.get("field3").equals(field3Added)) {
            testRepository.getTest("News add test").setException("News field3 doesn't match");
            driver.quit();
        } else if (!fields.get("field4").equals(field4Added)) {
            testRepository.getTest("News add test").setException("News field4 doesn't match");
            driver.quit();
        } else if (!fields.get("field5").equals(field5Added)) {
            testRepository.getTest("News add test").setException("News field5 doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("News add test").setException("News tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("News add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("News", "/news");
    }
}
