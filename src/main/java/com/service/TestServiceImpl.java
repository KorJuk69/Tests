package com.service;

import com.data.Captcha;
import com.repository.CaptchaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    private final NewsService newsService;
    private final UserService userService;
    private final CaptchaRepository captchaRepository;

    public TestServiceImpl(NewsService newsService, UserService userService, CaptchaRepository captchaRepository) {
        this.newsService = newsService;
        this.userService = userService;
        this.captchaRepository = captchaRepository;
    }

    @Override
    public void distributeTests(List<String> selectedTests) {
        for (String test : selectedTests) {
            switch (test) {
                case "News add test":
                    newsService.addNews();
                    break;
                case "News delete test":
                    newsService.deleteNews();
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

    //добавить второй метод с капчей
}
