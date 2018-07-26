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
public class PublService implements ModuleService{

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public PublService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/publ/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        driver.findElement(By.id("catSelector9")).click();
        driver.findElement(By.id("cus4")).click();
        String name = "Publ name " + materialCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "Publ brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "Publ message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String aname = "Publ aname " + materialCode;
        driver.findElement(By.name("aname")).sendKeys(aname);
        String aemail = "Publ aemail " + materialCode;
        driver.findElement(By.name("aemail")).sendKeys(aemail);
        String asite = "Publ asite " + materialCode;
        driver.findElement(By.name("asite")).sendKeys(asite);
        String source = "Publ source " + materialCode;
        driver.findElement(By.id("puF7")).sendKeys(source);
        String tag = "Publ tag " + materialCode;
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
        fields.put("aemail", aemail);
        fields.put("asite", "http://" + asite);
        fields.put("source", "http://" + source);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String messageAdded = driver.findElement(By.className("eText")).getText();
        String anameAdded = driver.findElement(By.xpath("//span[@class='ed-value']/u")).getText();
        String aemailAdded = driver.findElement(By.id("aemail")).getText();
        String asiteAdded = driver.findElement(By.id("asite")).getText();
        String sourceAdded = driver.findElement(By.id("source")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        driver.get("http://selenium.at.ua/publ");
        String briefAdded = driver.findElement(By.className("eMessage")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("Publ add test").setException("Publ name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("Publ add test").setException("Publ message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("Publ add test").setException("Publ brief doesn't match");
            driver.quit();
        } else if (!fields.get("aname").equals(anameAdded)) {
            testRepository.getTest("Publ add test").setException("Publ aname doesn't match");
            driver.quit();
        } else if (!fields.get("aemail").equals(aemailAdded)) {
            testRepository.getTest("Publ add test").setException("Publ aemail doesn't match");
            driver.quit();
        } else if (!fields.get("asite").equals(asiteAdded)) {
            testRepository.getTest("Publ add test").setException("Publ asite doesn't match");
            driver.quit();
        } else if (!fields.get("source").equals(sourceAdded)) {
            testRepository.getTest("Publ add test").setException("Publ source doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("Publ add test").setException("Publ tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("Publ add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("Publ", "/publ");
    }
}
