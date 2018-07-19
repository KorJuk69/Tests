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
public class BoardService implements ModuleService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;
    private final DeleteMaterialService deleteMaterialService;

    public BoardService(TestUtils testUtils, UserService userService, TestRepository testRepository, DeleteMaterialService deleteMaterialService) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
        this.deleteMaterialService = deleteMaterialService;
    }

    @Override
    public void add() {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua/board/0-0-0-0-1");
        long materialCode = System.currentTimeMillis();
        driver.findElement(By.id("catSelector9")).click();
        driver.findElement(By.id("cus2")).click();
        new Select(driver.findElement(By.name("filter3"))).selectByVisibleText("Информация");
        String name = "Board name " + materialCode;
        driver.findElement(By.className("manFlTitle")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id='txtPart555brief']/div/span/a[@data-uemode='3']")).click();
        String brief = "Board brief " + materialCode;
        driver.findElement(By.id("brief")).sendKeys(brief);
        driver.findElement(By.xpath("//div[@id='txtPart555message']/div/span/a[@data-uemode='3']")).click();
        String message = "Board message " + materialCode;
        driver.findElement(By.id("message")).sendKeys(message);

        String aname = "Board aname " + materialCode;
        driver.findElement(By.name("aname")).sendKeys(aname);
        String aemail = "Board aemail " + materialCode;
        driver.findElement(By.name("aemail")).sendKeys(aemail);
        String asite = "Board asite " + materialCode;
        driver.findElement(By.name("asite")).sendKeys(asite);
        String phone = "Board phone " + materialCode;
        driver.findElement(By.name("phone")).sendKeys(phone);
        new Select(driver.findElement(By.id("bdF10"))).selectByVisibleText("2019");

        String field1 = "Board field 1 " + materialCode;
        driver.findElement(By.id("bdF15")).sendKeys(field1);
        String field2 = "Board field 2 " + materialCode;
        driver.findElement(By.id("bdF16")).sendKeys(field2);
        String tag = "Board tag " + materialCode;
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
        fields.put("phone", phone);
        fields.put("field1", field1);
        fields.put("field2", field2);
        fields.put("tag", tag);
        verifyAdd(fields, driver);
    }

    @Override
    public void verifyAdd(Map<String, String> fields, WebDriver driver) {
        String nameAdded = driver.findElement(By.className("eTitle")).getText().replaceFirst(".*\n", "");
        String briefAdded = driver.findElement(By.className("eText")).getText();
        String messageAdded = driver.findElement(By.id("eMessage")).getText();
        String anameAdded = driver.findElement(By.xpath("//span[@class='ed-value']/u")).getText();
        String aemailAdded = driver.findElement(By.id("aemail")).getText();
        String asiteAdded = driver.findElement(By.id("asite")).getText();
        String field1Added = driver.findElement(By.id("field1")).getText();
        String field2Added = driver.findElement(By.id("field2")).getText();
        String tagAdded = driver.findElement(By.className("eTag")).getText();
        if (!fields.get("name").equals(nameAdded)) {
            testRepository.getTest("Board add test").setException("Board name doesn't match");
            driver.quit();
        } else if (!fields.get("message").equals(messageAdded)) {
            testRepository.getTest("Board add test").setException("Board message doesn't match");
            driver.quit();
        } else if (!fields.get("brief").equals(briefAdded)) {
            testRepository.getTest("Board add test").setException("Board brief doesn't match");
            driver.quit();
        } else if (!fields.get("aname").equals(anameAdded)) {
            testRepository.getTest("Board add test").setException("Board aname doesn't match");
            driver.quit();
        } else if (!fields.get("aemail").equals(aemailAdded)) {
            testRepository.getTest("Board add test").setException("Board aemail doesn't match");
            driver.quit();
        } else if (!fields.get("asite").equals(asiteAdded)) {
            testRepository.getTest("Board add test").setException("Board asite doesn't match");
            driver.quit();
        } else if (!fields.get("field1").equals(field1Added)) {
            testRepository.getTest("Board add test").setException("Board field1 doesn't match");
            driver.quit();
        } else if (!fields.get("field2").equals(field2Added)) {
            testRepository.getTest("Board add test").setException("Board field2 doesn't match");
            driver.quit();
        } else if (!fields.get("tag").equals(tagAdded)) {
            testRepository.getTest("Board add test").setException("Board tag doesn't match");
            driver.quit();
        } else {
            testRepository.getTest("Board add test").setPassed(true);
            driver.quit();
        }
    }

    @Override
    public void delete() {
        deleteMaterialService.delete("Board", "/board");
    }
}
