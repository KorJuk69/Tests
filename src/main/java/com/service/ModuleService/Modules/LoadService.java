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
public class LoadService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public LoadService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/load/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        driver.findElement(By.id("catSelector9")).click();
        driver.findElement(By.id("cus3")).click();
        String name = "Load name " + materialCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "Load brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "Load message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);
        String version = "Load version " + materialCode;
        driver.findElement(By.name("vers")).sendKeys(version);
        new Select(driver.findElement(By.name("licence"))).selectByVisibleText("Условно-бесплатно");
        new Select(driver.findElement(By.name("os"))).selectByVisibleText("FreeBSD");
        new Select(driver.findElement(By.name("lng"))).selectByVisibleText("Русский");
        String llink = "Load llink " + materialCode;
        driver.findElement(By.name("llink")).sendKeys(llink);
        String lsize = "Load lsize " + materialCode;
        driver.findElement(By.name("lsize")).sendKeys(lsize);
        String aname = "Load aname " + materialCode;
        driver.findElement(By.name("aname")).sendKeys(aname);
        String aemail = "Load aemail " + materialCode;
        driver.findElement(By.name("aemail")).sendKeys(aemail);
        String asite = "Load asite " + materialCode;
        driver.findElement(By.name("asite")).sendKeys(asite);
        String proglink = "Load proglink " + materialCode;
        driver.findElement(By.name("proglink")).sendKeys(proglink);
        String doclink = "Load doclink " + materialCode;
        driver.findElement(By.name("doclink")).sendKeys(doclink);
        String tag = "Load tag " + materialCode;
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
        fields.put("version", version.substring(0, 18));
        fields.put("llink", "http://" + llink);
        fields.put("lsize", lsize.substring(0, 15));
        fields.put("aname", aname);
        fields.put("aemail", aemail);
        fields.put("asite", "http://" + asite);
        fields.put("proglink", "http://" + proglink);
        fields.put("doclink", "http://" + doclink);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.id("filename")).getText();
        String messageAdded = driver.findElement(By.className("eText")).getText();
        String briefAdded = driver.findElement(By.id("brief")).getText();
        String versionAdded = driver.findElement(By.id("vers")).getText();
        String llinkAdded = driver.findElement(By.id("llink")).getText();
        String lsizeAdded = driver.findElement(By.id("lsize")).getText();
        String anameAdded = driver.findElement(By.id("aname")).getText();
        String aemailAdded = driver.findElement(By.id("aemail")).getText();
        String asiteAdded = driver.findElement(By.id("asite")).getText();
        String proglinkAdded = driver.findElement(By.id("proglink")).getText();
        String doclinkAdded = driver.findElement(By.id("doclink")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("Load add test").setException("Load name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("Load add test").setException("Load message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("Load add test").setException("Load brief doesn't match");
            driver.quit();
        } else if (!fields.get("version").equals(versionAdded)) {
            testRepository.getTest("Load add test").setException("Load version doesn't match");
            driver.quit();
        } else if (!fields.get("llink").equals(llinkAdded)) {
            testRepository.getTest("Load add test").setException("Load llink doesn't match");
            driver.quit();
        } else if (!fields.get("lsize").equals(lsizeAdded)) {
            testRepository.getTest("Load add test").setException("Load lsize doesn't match");
            driver.quit();
        } else if (!fields.get("aname").equals(anameAdded)) {
            testRepository.getTest("Load add test").setException("Load aname doesn't match");
            driver.quit();
        } else if (!fields.get("aemail").equals(aemailAdded)) {
            testRepository.getTest("Load add test").setException("Load aemail doesn't match");
            driver.quit();
        } else if (!fields.get("asite").equals(asiteAdded)) {
            testRepository.getTest("Load add test").setException("Load asite doesn't match");
            driver.quit();
        } else if (!fields.get("proglink").equals(proglinkAdded)) {
            testRepository.getTest("Load add test").setException("Load proglink doesn't match");
            driver.quit();
        } else if (!fields.get("doclink").equals(doclinkAdded)) {
            testRepository.getTest("Load add test").setException("Load doclink doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("Load add test").setException("Load tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("Load add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("Load", "/load");
    }
}
