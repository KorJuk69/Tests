package com.data;

import org.openqa.selenium.WebDriver;

public class Captcha {
    private WebDriver driver;
    private byte[] captchaImg;
    private String textCaptcha;

    public Captcha(WebDriver driver, byte[] captchaImg) {
        this.driver = driver;
        this.captchaImg = captchaImg;
    }

    public Captcha() {
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

    public String getTextCaptcha() {
        return textCaptcha;
    }

    public void setTextCaptcha(String textCaptcha) {
        this.textCaptcha = textCaptcha;
    }
}
