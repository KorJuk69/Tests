package com.controller;

import com.repository.CaptchaRepository;
import com.service.NewsService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    private final NewsService newsService;
    private final UserService userService;
    private final CaptchaRepository captchaRepository;

    public IndexController(NewsService newsService, UserService userService, CaptchaRepository captchaRepository) {
        this.newsService = newsService;
        this.userService = userService;
        this.captchaRepository = captchaRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/addNews")
    public String addNews(Model model) {
        newsService.addNews();
        model.addAttribute("isNewsPresent", true);
        return "index";
    }

    @GetMapping("/login")
    public String uidLogin(Model model) {
        userService.uidLogin();
        model.addAttribute("isLogin", true);
        return "index";
    }

    @GetMapping("/delete")
    public String deleteNews(Model model) {
        newsService.deleteNews();
        model.addAttribute("isDelete", true);
        return "index";
    }

    @GetMapping("/uidRegistration")
    public String uidRegistration() {
        userService.uidRegistrationFirst();
        return "captchaEnter";
    }

    @PostMapping("/uidRegistrationCaptcha")
    public String uidRegistrationCaptcha(Model model, @RequestParam("captcha") String captcha) {

        userService.uidRegistrationSecond(captchaRepository.getDriver(), captcha);
        model.addAttribute("isRegister", true);
        return "index";
    }

    @GetMapping("/captchaScreen")
    @ResponseBody
    public byte[] getScreenData()  {
        return captchaRepository.getScreen();
    }
}
