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
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final DriverService driverService;
    private final CaptchaRepository captchaRepository;
    private final TestRepository testRepository;

    public UserServiceImpl(DriverService driverService, CaptchaRepository captchaRepository, TestRepository testRepository) {
        this.driverService = driverService;
        this.captchaRepository = captchaRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void uidLogin() {
        WebDriver driver = driverService.getDriver();
        uidLogin(driver);
        testRepository.getTests().stream()
                .filter(test -> test.getName().equals("Login test"))
                .findFirst().get().setPassed(true);
        driver.quit();
    }

    @Override
    public void uidLogin(WebDriver driver) {
        driver.get("https://login.uid.me/?site=2selenium&ref=http%3A//selenium.at.ua/");
        driver.findElement(By.id("uid_email")).sendKeys("dima.k@ucoz-team.net");
        driver.findElement(By.id("uid_password")).sendKeys("cSMJiMoS");
        driver.findElement(By.id("uid-form-submit")).click();
        while (!driver.getCurrentUrl().equals("http://selenium.at.ua/")) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void uidRegistrationFirst() {
        WebDriver driver = driverService.getDriver();

        driver.get("http://selenium.at.ua/register");
        if (driver.findElements(By.className("uf-reg-wrap")).size() == 0){
            changeRegistrationMethod(driver, "uid");
        }

        driver.get("http://selenium.at.ua/register");
        driver.findElement(By.id("uf-password")).sendKeys("Test-User");
        driver.findElement(By.id("uf-name")).sendKeys("Test");
        driver.findElement(By.id("uf-surname")).sendKeys("User");
        driver.findElement(By.id("uf-nick")).sendKeys("TestU");
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
        String email = getRandomEmail();

        driver.findElement(By.id("uf-email")).sendKeys(email);
        driver.findElement(By.id("fCode")).sendKeys(captcha);
        driver.findElement(By.id("uf-submit")).click();
        if (driver.findElements(By.id("uf-captcha-status-icon")).size() > 0){
            driver.quit();
            throw new RuntimeException("Incorrect captcha");
        }
        //verifyEmail(driver, email);
        if (driver.findElement(By.cssSelector("div.register-form-wrapper h2")).getText().equals("Отлично!")) {
            testRepository.getTests().stream()
                    .filter(test -> test.getName().equals("UID registration test"))
                    .findFirst().get().setPassed(true);
            driver.quit();
        } else {
            throw new RuntimeException("Registration failed");
        }
    }

    @Override
    public String getRandomEmail() {
        WebDriver driver = driverService.getDriver();
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
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        driver.findElement(By.xpath("//button[@class='prior u-form-btn js-button-instance']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[@class='u-alert_text']")));
    }

    private void verifyEmail(WebDriver driver, String email){
        driver.get("https://temp-mail.org/ru/");
        driver.findElement(By.id("click-to-change")).click();
        String emailName = email.substring(0, email.indexOf('@'));
        String emailDomain = email.substring(email.indexOf('@'), email.length());
        driver.findElement(By.id("mail")).sendKeys(emailName); //не видит элемент
        new Select(driver.findElement(By.id("domain"))).selectByVisibleText(emailDomain);
        driver.findElement(By.id("postbut")).click();
        driver.findElement(By.id("click-to-refresh")).click();
        driver.findElement(By.className("title-subject")).click();
        driver.findElement(By.xpath("//a[@rel='external']")).click();
    }
}
