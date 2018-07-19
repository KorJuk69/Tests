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
public class DirService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public DirService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/dir/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        driver.findElement(By.className("u-comboeditcell")).click();
        driver.findElement(By.id("cus20")).click();
        String name = "Dir name " + materialCode;
        driver.findElement(By.id("drF1")).sendKeys(name);
        String link = "Dir link " + materialCode;
        driver.findElement(By.id("drF2")).sendKeys(link);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "Dir brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "Dir message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String aname = "Dir aname " + materialCode;
        driver.findElement(By.id("drF7")).sendKeys(aname);
        String email = "Dir email" + materialCode;
        driver.findElement(By.id("drF8")).sendKeys(email);
        String site = "Dir site " + materialCode;
        driver.findElement(By.id("drF9")).sendKeys(site);
        String field1 = "Dir field1 " + materialCode;
        driver.findElement(By.id("drF10")).sendKeys(field1);
        String field2 = "Dir field2 " + materialCode;
        driver.findElement(By.id("drF11")).sendKeys(field2);
        String tag = "Dir tag " + materialCode;
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
        fields.put("link", "http://" + link);
        fields.put("message", message);
        fields.put("aname", aname);
        fields.put("email", email);
        fields.put("site", site);
        fields.put("field1", field1);
        fields.put("field2", field2);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String linkAdded = driver.findElement(By.xpath("//table[@class='eBlock']/tbody/tr/td/a")).getText();
        String briefAdded = driver.findElement(By.className("eText")).getText();
        String messageAdded = driver.findElement(By.className("eMessage")).getText();
        String field1Added = driver.findElement(By.id("field1")).getText();
        String field2Added = driver.findElement(By.id("field2")).getText();
        String emailAdded = driver.findElement(By.id("aEmail")).getText();
        String anameAdded = driver.findElement(By.id("aName")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("Dir add test").setException("Dir name doesn't match");
            driver.quit();
        } else if (!fields.get("link").equals(linkAdded)) {
            testRepository.getTest("Dir add test").setException("Dir link doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("Dir add test").setException("Dir message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("Dir add test").setException("Dir brief doesn't match");
            driver.quit();
        } else if (!fields.get("field1").equals(field1Added)) {
            testRepository.getTest("Dir add test").setException("Dir field1 doesn't match");
            driver.quit();
        } else if (!fields.get("field2").equals(field2Added)) {
            testRepository.getTest("Dir add test").setException("Dir field2 doesn't match");
            driver.quit();
        } else if (!fields.get("email").equals(emailAdded)) {
            testRepository.getTest("Dir add test").setException("Dir email doesn't match");
            driver.quit();
        } else if (!fields.get("aname").equals(anameAdded)) {
            testRepository.getTest("Dir add test").setException("Dir aname doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("Dir add test").setException("Dir tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("Dir add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("Dir", "/dir");
    }
}
