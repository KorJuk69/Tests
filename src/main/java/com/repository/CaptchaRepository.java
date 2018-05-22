package com.repository;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Repository;

@Repository
public class CaptchaRepository {

    private WebDriver driver;
    private byte[] screen;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public byte[] getScreen() {
        return screen;
    }

    public void setScreen(byte[] screen) {
        this.screen = screen;
    }
}
