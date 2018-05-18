package com.controller;

import com.service.NewsService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final NewsService newsService;
    private final UserService userService;

    public IndexController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
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
}
