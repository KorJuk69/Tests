package com.service;

import com.data.Captcha;
import com.repository.CaptchaRepository;
import com.repository.TestRepository;
import com.service.Modules.BlogService;
import com.service.Modules.DirService;
import com.service.Modules.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    private final NewsService newsService;
    private final BlogService blogService;
    private final DirService dirService;
    private final UserService userService;
    private final CaptchaRepository captchaRepository;
    private final TestRepository testRepository;

    public TestServiceImpl(NewsService newsService, BlogService blogService, DirService dirService, UserService userService, CaptchaRepository captchaRepository, TestRepository testRepository) {
        this.newsService = newsService;
        this.blogService = blogService;
        this.dirService = dirService;
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
