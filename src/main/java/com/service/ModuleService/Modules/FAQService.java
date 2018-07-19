package com.service.ModuleService.Modules;

import com.repository.TestRepository;
import com.service.ModuleService.DeleteMaterialService;
import com.service.ModuleService.ModuleService;
import com.service.TestUtils;
import com.service.UserService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FAQService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public FAQService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/faq/0-0-0-1");
        long materialCode = System.currentTimeMillis();
        new Select(driver.findElement(By.id("fqF7"))).selectByVisibleText("TestKategoriya");
        String name = "FAQ name " + materialCode;
        driver.findElement(By.className("manFl")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "FAQ brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "FAQ message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String aname = "FAQ aname " + materialCode;
        driver.findElement(By.name("name")).sendKeys(aname);
        String email = "FAQ email " + materialCode;
        driver.findElement(By.xpath("//td[@class='manTd2']/input[@name='email']")).sendKeys(email);
        String tag = "FAQ tag " + materialCode;
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
        fields.put("aname", aname);
        fields.put("email", email);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        int elementNumber = driver.findElements(By.className("eBlock")).size();
        String path = "//div[@id='entryID" + elementNumber + "']/table/tbody/tr/td";
        String nameAdded = driver.findElement(By.xpath(path + "/div[@class='eTitle']/a")).getText();
        String messageAdded = driver.findElement(By.xpath(path + "/div[@class='eAnswer']")).getText();
        String emailAdded = driver.findElement(By.xpath(path + "/div[@id='email']")).getText();
        String anameAdded = driver.findElement(By.xpath(path + "/a")).getText();
        String tagAdded = driver.findElement(By.xpath(path + "/div[@id='tags']")).getText();
        String briefAdded = driver.findElement(By.xpath(path + "/div[@class='eMessage']")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ brief doesn't match");
            driver.quit();
        } else if (!fields.get("aname").equals(anameAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ aname doesn't match");
            driver.quit();
        } else if (!fields.get("email").equals(emailAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ email doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("FAQ add test").setException("FAQ tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("FAQ add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("FAQ", "/faq/1-1");
    }
}
