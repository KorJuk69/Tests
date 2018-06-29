package com.controller;

import com.data.Captcha;
import com.data.Test;
import com.repository.CaptchaRepository;
import com.repository.TestRepository;
import com.service.ResponseCodeService;
import com.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TestController {

    private final TestService testService;
    private final CaptchaRepository captchaRepository;
    private final TestRepository testRepository;
    private final ResponseCodeService responseCodeService;

    public TestController(TestService testService, CaptchaRepository captchaRepository, TestRepository testRepository, ResponseCodeService responseCodeService) {
        this.testService = testService;
        this.captchaRepository = captchaRepository;
        this.testRepository = testRepository;
        this.responseCodeService = responseCodeService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/startTests")
    public String startTests(@RequestParam(value = "selectedTests", required = false) ArrayList<String> selectedTests, Model model){
        testService.clearTestBools();
        selectedTests.forEach(selectedTest ->
                testRepository.getTests().stream()
                .filter(test -> test.getName().equals(selectedTest))
                .findFirst().get().setSelected(true));
        testService.distributeTests(selectedTests);
        if (isAllCaptchaEntered(model)) return "captchaEnter";
        return testResult(model);
    }

    @PostMapping("/setCaptcha")
    public String setCaptcha(@RequestParam("captcha") String captcha, @RequestParam("testName") String testName, Model model){
        captchaRepository.getCaptchas().get(testName).setTextCaptcha(captcha);
        if (isAllCaptchaEntered(model)) return "captchaEnter";
        testService.distributeTestsWithCaptcha();
        captchaRepository.clearCaptchas();
        return "testResult";
    }

    @GetMapping("/testResult")
    public String testResult(Model model){
        List<Test> selectedTests = testRepository.getTests().stream()
                .filter(Test::isSelected).collect(Collectors.toList());
        model.addAttribute("selectedTests", selectedTests);
        return "testResult";
    }

    @GetMapping("/responseCodes")
    public String getResponseCodes(Model model){
        model.addAttribute("pages", responseCodeService.getPages());
        return "responseCodes";
    }

    @PostMapping("/responseCodesResult")
    public String responseCodes(Model model, @RequestParam(value = "selectedPages", required = false) ArrayList<String> selectedPages){
        model.addAttribute("responseCodes", responseCodeService.getResponseCodes(selectedPages));
        return "responseCodesResult";
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
        return testRepository.getTests();
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
