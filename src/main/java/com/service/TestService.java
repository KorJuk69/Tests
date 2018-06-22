package com.service;

import java.util.List;

public interface TestService {

    void distributeTests(List<String> selectedTests);

    void distributeTestsWithCaptcha();

    void clearTestBools();
}
