package com.repository;

import com.data.Captcha;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class CaptchaRepository {

    private Map<String, Captcha> captchas;

    public CaptchaRepository() {
        captchas = new LinkedHashMap<>();
    }

    public void addCaptcha(String testName, WebDriver driver, byte[] captchaImg){
        captchas.put(testName, new Captcha(driver, captchaImg));
    }

    public Map<String, Captcha> getCaptchas(){
        return captchas;
    }

    public void clearCaptchas(){
        captchas.clear();
    }

}
