package com.controller;

import com.data.Captcha;
import com.data.Test;
import com.repository.CaptchaRepository;
import com.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    private final TestService testService;
    private final CaptchaRepository captchaRepository;

    public TestController(TestService testService, CaptchaRepository captchaRepository) {
        this.testService = testService;
        this.captchaRepository = captchaRepository;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/startTests")
    public String startTests(@RequestParam(value = "selectedTests", required = false) ArrayList<String> selectedTests, Model model){
        testService.distributeTests(selectedTests);
        if (isAllCaptchaEntered(model)) return "captchaEnter";
        return "index";
    }

    @GetMapping
    public String captchaEnter(String testName, Model model) {
        model.addAttribute("testName", testName);
        return "captchaEnter";
    }

    @PostMapping("/setCaptcha")
    public String setCaptcha(@RequestParam("captcha") String captcha, @RequestParam("testName") String testName, Model model){
        captchaRepository.getCaptchas().get(testName).setTextCaptcha(captcha);
        if (isAllCaptchaEntered(model)) return "captchaEnter";
        testService.distributeTestsWithCaptcha();
        captchaRepository.clearCaptchas();
        return "index";
    }

    @GetMapping("/captchaScreen")
    @ResponseBody
    public byte[] getCaptchaImg()  {
        for (Map.Entry<String, Captcha> e : captchaRepository.getCaptchas().entrySet()){
            if (e.getValue().getTextCaptcha() == null){
                return e.getValue().getCaptchaImg();
            }
        }
        return null;
    }

    @ModelAttribute("testList")
    public List<Test> getTestList(){
        List<Test> tests = new ArrayList<>();
        tests.add(new Test("News add test"));
        tests.add(new Test("News delete test"));
        tests.add(new Test("Login test"));
        tests.add(new Test("UID registration test"));
        return tests;
    }

    private boolean isAllCaptchaEntered(Model model) {
        if (!captchaRepository.getCaptchas().isEmpty()){
            for (Map.Entry<String, Captcha> e : captchaRepository.getCaptchas().entrySet()){
                if (e.getValue().getTextCaptcha() == null){
                    model.addAttribute("testName", e.getKey());
                    return true;
                }
            }
        }
        return false;
    }
}
