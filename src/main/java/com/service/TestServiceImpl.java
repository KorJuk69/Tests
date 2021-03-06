package com.service;

import com.data.Captcha;
import com.repository.CaptchaRepository;
import com.repository.TestRepository;
import com.service.ModuleService.Modules.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    private final NewsService newsService;
    private final BlogService blogService;
    private final DirService dirService;
    private final FAQService faqService;
    private final BoardService boardService;
    private final PublService publService;
    private final LoadService loadService;
    private final UserService userService;
    private final CaptchaRepository captchaRepository;
    private final TestRepository testRepository;

    public TestServiceImpl(NewsService newsService, BlogService blogService, DirService dirService, FAQService faqService, BoardService boardService, PublService publService, LoadService loadService, UserService userService, CaptchaRepository captchaRepository, TestRepository testRepository) {
        this.newsService = newsService;
        this.blogService = blogService;
        this.dirService = dirService;
        this.faqService = faqService;
        this.boardService = boardService;
        this.publService = publService;
        this.loadService = loadService;
        this.userService = userService;
        this.captchaRepository = captchaRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void distributeTests(List<String> selectedTests) {
        for (String test : selectedTests) {
            switch (test) {
                case "News add test":
                    newsService.add();
                    break;
                case "News delete test":
                    newsService.delete();
                    break;
                case "Blog add test":
                    blogService.add();
                    break;
                case "Blog delete test":
                    blogService.delete();
                    break;
                case "Dir add test":
                    dirService.add();
                    break;
                case "Dir delete test":
                    dirService.delete();
                    break;
                case "FAQ add test":
                    faqService.add();
                    break;
                case "FAQ delete test":
                    faqService.delete();
                    break;
                case "Board add test":
                    boardService.add();
                    break;
                case "Board delete test":
                    boardService.delete();
                    break;
                case "Publ add test":
                    publService.add();
                    break;
                case "Publ delete test":
                    publService.delete();
                    break;
                case "Load add test":
                    loadService.add();
                    break;
                case "Load delete test":
                    loadService.delete();
                    break;
                case "Login test":
                    userService.uidLogin();
                    break;
                case "UID registration test":
                    userService.uidRegistrationFirst();
                    break;
            }
        }
    }

    @Override
    public void distributeTestsWithCaptcha() {
        for (Map.Entry<String, Captcha> e : captchaRepository.getCaptchas().entrySet()){
            switch (e.getKey()){
                case "UID registration test with captcha" :
                    userService.uidRegistrationSecond(e.getValue().getDriver(), e.getValue().getTextCaptcha());
                    break;
            }
        }
    }

    @Override
    public void clearTestBools() {
        testRepository.getTests().forEach(test -> test.setSelected(false));
        testRepository.getTests().forEach(test -> test.setPassed(false));
    }
}
