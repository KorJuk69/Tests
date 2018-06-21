package com.service;

import org.openqa.selenium.WebDriver;

public interface UserService {
    void uidLogin();

    void uidLogin(WebDriver driver);

    void uidRegistrationFirst();

    void uidRegistrationSecond(WebDriver driver, String captcha);

    String getRandomEmail();

    void toAdminPanel(WebDriver driver);

    void changeRegistrationMethod(WebDriver driver, String method);
}
