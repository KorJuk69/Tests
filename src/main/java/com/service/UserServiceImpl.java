package com.service;

import com.repository.CaptchaRepository;
import com.repository.TestRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class UserServiceImpl implements UserService {

    private final TestUtils testUtils;
    private final CaptchaRepository captchaRepository;
    private final TestRepository testRepository;

    private long currentUserCode;

    public UserServiceImpl(TestUtils testUtils, CaptchaRepository captchaRepository, TestRepository testRepository) {
        this.testUtils = testUtils;
        this.captchaRepository = captchaRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void uidLogin() {
        WebDriver driver = testUtils.getDriver();
        uidLogin(driver);
        testRepository.getTest("Login test").setPassed(true);
        driver.quit();
    }

    @Override
    public void uidLogin(WebDriver driver) {
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        driver.findElement(By.id("uid_email")).sendKeys("dima.k@ucoz-team.net");
        driver.findElement(By.id("uid_password")).sendKeys("cSMJiMoS");
        driver.findElement(By.id("uid-form-submit")).click();
        try {
            if (driver.findElement(By.className("uid-error")).isEnabled()){
                uidLogin(driver);
            }
        } catch (NoSuchElementException ignored){
        }
        while (!driver.getCurrentUrl().equals("http://selenium.at.ua/")) {
            testUtils.sleep(1);
        }
    }

    @Override
    public void uidRegistrationFirst() {
        WebDriver driver = testUtils.getDriver();

        driver.get("http://selenium.at.ua/register");
        if (driver.findElements(By.className("uf-reg-wrap")).size() == 0){
            changeRegistrationMethod(driver, "uid");
        }

        driver.get("http://selenium.at.ua/register");
        currentUserCode = System.currentTimeMillis();
        driver.findElement(By.id("uf-password")).sendKeys("U-" + currentUserCode);
        driver.findElement(By.id("uf-name")).sendKeys("Test" + currentUserCode);
        driver.findElement(By.id("uf-surname")).sendKeys("User" + currentUserCode);
        driver.findElement(By.id("uf-nick")).sendKeys("TestU" + currentUserCode);
        new Select(driver.findElement(By.id("uf-birthday-d"))).selectByVisibleText("3");
        new Select(driver.findElement(By.id("uf-birthday-m"))).selectByVisibleText("Март");
        new Select(driver.findElement(By.id("uf-birthday-y"))).selectByVisibleText("1994");
        driver.findElement(By.id("uf-gender")).click();
        new Select(driver.findElement(By.id("uf-location"))).selectByVisibleText("Киев");
        driver.findElement(By.id("policy")).click();
        driver.findElement(By.id("uf-terms")).click();
        driver.findElement(By.id("uf-submit")).click();
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] captchaImg = null;
        try {
            captchaImg = Files.readAllBytes(scrFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        captchaRepository.addCaptcha("UID registration test with captcha", driver, captchaImg);
    }

    @Override
    public void uidRegistrationSecond(WebDriver driver, String captcha) {
        WebDriver emailDriver = testUtils.getDriver();
        emailDriver.get("https://temp-mail.org/ru/");
        String email = emailDriver.findElement(By.id("mail")).getAttribute("value");

        driver.findElement(By.id("uf-email")).sendKeys(email);
        driver.findElement(By.id("fCode")).sendKeys(captcha);
        driver.findElement(By.id("uf-submit")).click();
        if (driver.findElements(By.id("uf-captcha-status-icon")).size() > 0){
            testRepository.getTest("UID registration test").setException("Registration failed");
            emailDriver.quit();
            driver.quit();
            return;
        }
        verifyEmail(emailDriver, email);
        driver.get("http://selenium.at.ua/index/8");
        String nick = driver.findElement(By.xpath("//div[@id='block1']/a")).getText();
        String fullName = driver.findElement(By.id("block5")).getText();
        if (nick.equals("TestU" + currentUserCode)&&
                (fullName.equals("Имя: Test" + currentUserCode + " User" + currentUserCode + " [ Мужчина ]"))){
            testRepository.getTest("UID registration test").setPassed(true);
            driver.quit();
        } else {
            testRepository.getTest("UID registration test").setException("Registration failed");
            driver.quit();
        }
    }

    @Override
    public String getRandomEmail() {
        WebDriver driver = testUtils.getDriver();
        driver.get("https://temp-mail.org/ru/");
        driver.findElement(By.id("click-to-delete")).click();
        String email = driver.findElement(By.id("mail")).getAttribute("value");
        driver.quit();
        return email;
    }

    @Override
    public void toAdminPanel(WebDriver driver) {
        driver.get("http://selenium.at.ua/admin");
        driver.findElement(By.name("password")).sendKeys("Ucoz-Test");
        driver.findElement(By.cssSelector("div#subbutlform a")).click();
    }

    @Override
    public void changeRegistrationMethod(WebDriver driver, String method) {
        toAdminPanel(driver);
        driver.findElement(By.xpath("//nav[@class='u-nav u-clear']/li[2]/a")).click();
        driver.findElement(By.xpath("//div[@id='mCSB_3_container']/li[2]/a")).click();
        if (method.equalsIgnoreCase("uid")){
            driver.findElement(By.xpath("//label[@for='auth_fieldset-allow_unet-0']/span")).click();
        } else {
            driver.findElement(By.xpath("//label[@for='auth_fieldset-allow_unet-1']/span")).click();
        }
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,-250)", "");
        testUtils.sleep(1);
        driver.findElement(By.xpath("//button[@class='prior u-form-btn js-button-instance']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[@class='u-alert_text']")));
    }

    private void verifyEmail(WebDriver driver, String email){
        /*driver.get("https://temp-mail.org/ru/");
        driver.findElement(By.id("click-to-change")).click();
        driver.findElement(By.id("click-to-change")).click();
        String emailName = email.substring(0, email.indexOf('@'));
        String emailDomain = email.substring(email.indexOf('@'), email.length());
        driver.findElement(By.xpath("//div[@class='col-sm-10']/input[@id='mail']")).sendKeys(emailName);
        new Select(driver.findElement(By.id("domain"))).selectByVisibleText(emailDomain);
        driver.findElement(By.id("postbut")).click();*/
        driver.findElement(By.id("click-to-refresh")).click();
        driver.findElement(By.className("title-subject")).click();
        driver.findElement(By.xpath("//table/tbody/tr/td/table/tbody/tr/td/a[@rel='external']")).click();
        driver.quit();
    }
}
