package com.service.ModuleService;

import com.repository.TestRepository;
import com.service.TestUtils;
import com.service.UserService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteMaterialService {

    private final TestUtils testUtils;
    private final UserService userService;
    private final TestRepository testRepository;

    public DeleteMaterialService(TestUtils testUtils, UserService userService, TestRepository testRepository) {
        this.testUtils = testUtils;
        this.userService = userService;
        this.testRepository = testRepository;
    }

    public void delete(String moduleName, String moduleURL) {
        WebDriver driver = testUtils.getDriver();
        userService.uidLogin(driver);
        driver.get("http://selenium.at.ua" + moduleURL);
        WebElement menuButton;
        try {
            menuButton = driver.findElement(By.xpath("//table[@class='eBlock']/tbody/tr/td/div"));
        } catch (NoSuchElementException e) {
            testRepository.getTest(moduleName + " delete test").setException(moduleName +  " material isn't displayed");
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
        verifyDelete(title, driver, moduleName);
    }

    private void verifyDelete(WebElement title, WebDriver driver, String moduleName) {
        List<WebElement> titles = driver.findElements(By.className("eTitle"));
        if (!titles.contains(title)) {
            testRepository.getTest(moduleName + " delete test").setPassed(true);
            driver.quit();
        } else {
            testRepository.getTest(moduleName + " delete test").setException(moduleName + " material isn't delete");
            driver.quit();
        }
    }
}
