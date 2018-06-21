package com.data;

import org.openqa.selenium.WebDriver;

public class Test {

    private String name;
    private WebDriver driver;
    private byte[] captchaImg;
    private boolean isPassed = false;

    public Test(String name) {
        this.name = name;
    }

    public Test() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public byte[] getCaptchaImg() {
        return captchaImg;
    }

    public void setCaptchaImg(byte[] captchaImg) {
        this.captchaImg = captchaImg;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
